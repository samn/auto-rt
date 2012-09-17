(ns auto-rt.core
  (:require [twitter.oauth :as oauth]
            [twitter.api.restful :as twitter]))

(defn env
  "Retrieve the value of var-name from the system environment, or nil"
  [var-name]
  (System/getenv var-name))

(def ^:dynamic *creds* (make-oauth-creds 
                         (env "APP_CONSUMER_KEY")
                         (env "APP_CONSUMER_SECRET")
                         (env "USER_ACCESS_TOKEN")
                         (env "USER_ACCESS_TOKEN_SECRET")))

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))
