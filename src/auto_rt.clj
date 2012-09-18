(ns auto-rt
  (:require [twitter.oauth :as oauth]
            [twitter.api.restful :as restful]
            [twitter.api.streaming :as streaming])
  (:import (twitter.callbacks.protocols AsyncStreamingCallback)))

(defn env
  "Retrieve the value of var-name from the system environment, or nil"
  [var-name]
  (System/getenv var-name))

(def ^:dynamic *creds* (make-oauth-creds 
                         (env "APP_CONSUMER_KEY")
                         (env "APP_CONSUMER_SECRET")
                         (env "USER_ACCESS_TOKEN")
                         (env "USER_ACCESS_TOKEN_SECRET")))

(defn on-bodypart
  "Called when a new message is received from the streaming api"
  [response baos]
  (println (.toString baos)))

(defn on-failure
  "Called when the streaming api returns a 4xx response"
  [response]
  (println response)
  (exit))

(defn on-exception
  "Called when an exception is thrown"
  [response throwable]
  (println (.toString throwable))
  (exit))

(def ^:dynamic *streaming-callback*
  (AsyncStreamingCallback.
    on-bodypart
    on-failure
    on-exception))

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))
