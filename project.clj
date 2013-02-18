(defproject auto-rt "0.1.1-SNAPSHOT"
  :description "Automatically RT @mentions"
  :url "https://github.com/samn/auto-rt"
  :min-lein-version "2.0.0"                     
  :main auto-rt 
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [twitter-api "0.6.10"]
                 [cheshire "4.0.2"]])
