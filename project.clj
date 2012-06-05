(defproject tsq "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [noir "1.2.1"]
                           [congomongo "0.1.8"]
                           [clj-time "0.3.4"]]
            :plugins [[lein-swank "1.4.4"]
                      [lein-ring "0.6.2"]] ;; remove for heroku?
            :ring {:handler tsq.server/handler} ;; remove for heroku? 
            :main tsq.server)

