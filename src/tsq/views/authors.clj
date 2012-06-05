(ns tsq.views.authors
  (:require [tsq.models.author :as author])
  (:use [noir.core :only [defpartial]]
        hiccup.core
        hiccup.page-helpers))

(defpartial author-list []
  (map (fn [a]
         (html
          (link-to {:id (str "au" (:_id a))} "#" (:name a))
          [:br.clear]))
       (author/get-authors)))
