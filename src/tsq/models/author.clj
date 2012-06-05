(ns tsq.models.author
  (:use [tsq.helpers.db :only [get-one get-all put-one str->object-id]]))

(defn get-by-name
  "A string or regular expression will do."
  [name]
  (get-one :author {:name name}))

(defn get-id-by-name
  "A string or a regular expression will do."
  [name]
  (:_id (get-by-name name)))

(defn get-by-id [id]
  (get-one :author {:_id (str->object-id id)}))

(defn new-author
  "Takes a string, creates the author, returns an id. If the author already exists, his/her id is returned."
  [name]
  (if-let [a (get-one :author {:name name})]
    (:_id a)
    (do
      (put-one :author {:name name})
      (:_id (get-one :author {:name name})))))

(defn get-authors [& options]
  (if (seq options)
    ((partial get-all :author) options)
    (get-all :author :sort {:name 1})))

