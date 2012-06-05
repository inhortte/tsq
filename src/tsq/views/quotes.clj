(ns tsq.views.quotes
  (:require [tsq.helpers.quotes :as quotehelper]
            [tsq.models.quote :as quote]
            [tsq.models.author :as author]
            [tsq.helpers.time :as time])
  (:use [noir.core :only [defpartial]]
        tsq.middleware
        hiccup.core
        hiccup.page-helpers))

(defpartial a-quote [q]
  [:div.quote
   [:div.text
    (:quote q)]
   [:br.clear]
   [:div.author
    (str "-" (:name (author/get-by-id (:author_id q))))]]
  [:br.clear])

(defn a-metaquote [mq]
  (html
   [:div.metaquote
    (concat
     (for [q (quote/get-quotes-from-metaquote mq)]
       (a-quote q)))
    [:div.date
     (time/format-date (:datestamp mq))]]
   [:br.clear]))

(defn current-quote []
  (a-metaquote (quotehelper/get-current-metaquote)))

(defn random-quote []
  (let [mq (quotehelper/get-random-metaquote)]
    (a-metaquote mq)))

(defn next-quote []
  (a-metaquote (quotehelper/get-next-metaquote)))

(defn previous-quote []
  (a-metaquote (quotehelper/get-previous-metaquote)))
