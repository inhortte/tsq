(ns tsq.server
  (:require [noir.server :as server]))

(server/load-views "src/tsq/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'tsq})))

;; for ring & dotcloud.
(def handler (server/gen-handler {:mode :dev
                                  :ns 'tsq}))
