(ns tsq.models.quote
  (:require [tsq.models.author :as author]
            [noir.session :as session])
  (:use [tsq.helpers.db :only [get-one get-all put-one
                               get-many str->object-id]]
        tsq.middleware))

(defn get-metaquote [q]
  (get-one :metaquote {:_id (:metaquote_id q)}))

(defn get-all-metaquotes []
  (get-all :metaquote))

(defn get-quotes-from-metaquote [mq]
  (map #(get-one :quote {:_id %}) (:quote_ids mq)))

(defn get-by-author-id [id]
  (get-many :quote {:author_id id}))

(defn get-by-author-name [name]
  (get-by-author-id (author/get-id-by-name name)))

(defn get-metaquotes-by-author-id [id]
  (sort-by :datestamp (seq (reduce #(conj %1 (get-metaquote %2)) #{}
                                   (get-by-author-id id)))))

(defn get-metaquotes-by-author-name [name]
  (get-metaquotes-by-author-id (author/get-id-by-name name)))

