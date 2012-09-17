(ns auto-rt.core
  (:require [twitter.oauth :as oauth]
            [twitter.api.restful :as twitter]))

(defn env
  "Retrieve the value of var-name from the system environment, or nil"
  [var-name]
  (System/getenv var-name))

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))
