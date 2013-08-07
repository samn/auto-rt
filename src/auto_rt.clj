(ns auto-rt
  (:require [twitter.oauth :as oauth]
            [twitter.api.restful :as restful]
            [twitter.api.streaming :as streaming]
            [cheshire.core :as json])
  (:import (twitter.callbacks.protocols SyncStreamingCallback)))

(defn env
  "Retrieve the value of var-name from the system environment, or nil"
  [var-name]
  (System/getenv var-name))

(def creds (oauth/make-oauth-creds 
             (env "APP_CONSUMER_KEY")
             (env "APP_CONSUMER_SECRET")
             (env "USER_ACCESS_TOKEN")
             (env "USER_ACCESS_TOKEN_SECRET")))

(def user-id (env "USER_ID"))

(defn die!
  ([] (die! 1))
  ([code] (System/exit code)))

(defn valid-tweeter?
  "Returns true of there is a user in the response json, and that it isn't protected."
  [json]
  (when-let [user (:user json)]
    (not (or (= user-id (:id_str user))
             (:protected user)))))

(defn mentions-user?
  "Looks for user-id in the list of @mentions in tweet.
  Returns true if user-id is mentioned, false otherwise."
  [tweet]
  (->> tweet
       :entities
       :user_mentions
       (some (comp (partial = user-id) :id_str))))

(defn on-bodypart
  "Called when a new message is received from the streaming api"
  [response baos]
  (let [tweet (json/parse-string (.toString baos) true)]
    (when (and (valid-tweeter? tweet) (mentions-user? tweet))
      (println "Trying to RT status" (:id_str tweet))
      (restful/statuses-retweet-id :oauth-creds creds :params {:id (:id_str tweet)}))))

(defn on-failure
  "Called when the streaming api returns a 4xx response.
   Kill this process and let Heroku bounce it to reconnect."
  [response]
  (println response)
  (die!))

(defn on-exception
  "Called when an exception is thrown.
   Kill this process and let Heroku bounce it to reconnect."
  [response throwable]
  (println (.toString throwable))
  (die!))

(def sync-streaming-callback
  (SyncStreamingCallback.
    on-bodypart
    on-failure
    on-exception))

(defn -main
  [& args]
  (println "Startup!")
  (println "auto-rt for user id" user-id)
  (streaming/user-stream :oauth-creds creds :callbacks sync-streaming-callback))
