(ns tsq.helpers.db
  (:use tsq.middleware
        [somnium.congomongo :only [make-connection with-mongo fetch-one mongo!
                                   fetch fetch-by-ids mass-insert! insert!
                                   destroy! update! object-id connection?
                                   authenticate]]
        [somnium.congomongo.config :only [*mongo-config*]]))

(def localhost-mongo-url "mongodb://:@localhost:27017/tsq")
(def dotcloud-mongo-url "mongodb://inhortte:mustelid@tsqdb-inhortte-data-0.dotcloud.com:29015/tsq")
(defn split-mongo-url [url]
  "Splits typical mongo-url into a map of constituent parts."
  (let [matcher (re-matcher #"^.*://(.*?):(.*?)@(.*?):(\d+)/(.*)$" url)]
    (when (.find matcher)
      (zipmap [:m :user :pass :host :port :db] (re-groups matcher)))))
(defn maybe-init []
  (when (not (connection? *mongo-config*))
    (let [config (split-mongo-url dotcloud-mongo-url)]
      (mongo! :db (:db config) :host (:host config)
              :port (Integer. (:port config)))
      (authenticate (:user config) (:pass config)))))

(defn str->object-id [s]
  (if (= (class s) java.lang.String) (object-id s) s))

(defn quote-merge [good & bad]
  (maybe-init)
  (let [good-id (:_id (fetch-one :author
                                 :where {:name good}))
        bad-ids (map :_id (fetch :author
                                 :where {:$or
                                         (reduce #(conj %1 {:name %2})
                                                 [] bad)}))
        bad-qs (mapcat #(fetch :quote :where {:author_id %})
                       bad-ids)]
    (doseq [q bad-qs]
      (update! :quote q (assoc q :author_id good-id)))
    (doseq [aid bad-ids]
      (destroy! :author
                (fetch-one :author
                           :where {:_id aid})))))

(defn change-author-name [bad good]
  (maybe-init)
  (let [bad-a (fetch-one :author :where {:name bad})]
    (update! :author bad-a (assoc bad-a :name good))))

(defmacro get-one [coll m & options]
  `(do (maybe-init) (fetch-one (keyword ~coll) :where ~m ~@options)))

(defmacro get-many [coll m & options]
  `(do (maybe-init) (fetch (keyword ~coll) :where ~m ~@options)))

(defn get-all [coll & options]
  (maybe-init)
  (eval
   `(fetch (keyword ~coll) ~@options)))

(defn get-last [coll]
  (maybe-init)
  (first (fetch (keyword coll) :limit 1 :sort {:created_at -1})))

(defn put-one [coll m]
  (maybe-init)
  (insert! (keyword coll) m))

(defn update-one [coll old new]
  (maybe-init)
  (update! (keyword coll) old new))


