(ns tsq.views.menu
  (:require [noir.session :as session]
            [tsq.models.author :as author])
  (:use [noir.core :only [defpartial]]
        hiccup.core
        hiccup.page-helpers))

(defpartial menu []
  (let [{c :count n :current} (session/get :metaquotes)]
    [:ul
     (when (> n 0)
       [:li
        (link-to {:id "previous"} "#" "&lt;Previous")])
     [:li
      (link-to {:id "random"} "#" "Random Quote")]
     (when (< (inc n) c)
       [:li
        (link-to {:id "next"} "#" "Next&gt;")])
     (when-let [ca-id (session/get :author)]
       [:li#current_author
        (:name (author/get-by-id ca-id))
        (link-to {:id (str "ca" ca-id)} "#" " (clear)")])
     [:li
      (link-to "#" "Submit Quote")]]))
