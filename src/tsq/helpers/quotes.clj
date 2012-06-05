(ns tsq.helpers.quotes
  (:require [tsq.models.quote :as quote]
            [tsq.models.author :as author]
            [noir.session :as session])
  (:use [tsq.helpers.db :only [str->object-id]]
        tsq.middleware))

(defn get-current-metaquotes []
  (let [a (session/get :author)]
    (log (str "get-current-metaquotes: author: " (if (nil? a) "nil" a))))
  (let [a (session/get :author)]
    (if (nil? a)
      (quote/get-all-metaquotes)
      (quote/get-metaquotes-by-author-id (str->object-id a)))))

(defn get-current-metaquote []
  (nth (get-current-metaquotes) (:current (session/get :metaquotes))))

(defn get-random-metaquote []
  (let [mqs (get-current-metaquotes)
        c (count mqs)
        r (rand-int c)]
    (session/put! :metaquotes (hash-map :count c :current r))
    (nth mqs r)))

(defn get-next-metaquote []
  (let [{c :count n :current :as q} (session/get :metaquotes)]
    (when (< (inc n) c)
      (session/put! :metaquotes (assoc q :current (inc n)))
      (nth (get-current-metaquotes) (inc n)))))

(defn get-previous-metaquote []
  (let [{c :count n :current :as q} (session/get :metaquotes)]
    (when (> n 0)
      (session/put! :metaquotes (assoc q :current (dec n)))
      (nth (get-current-metaquotes) (dec n)))))

(defn reset-metaquotes
  "Finds all pertinent quotes according to current author and date ranges and updates :quotes to match it."
  []
  (let [c (count (get-current-metaquotes))]
    (session/put! :metaquotes (hash-map :count c
                                        :current (rand-int c)))))
