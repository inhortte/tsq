(ns tsq.views.common
  (:require [tsq.views.authors :as authors]
            [tsq.views.menu :as menu])
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css include-js html5]]))

(defpartial layout [& content]
  (html5
   [:head
    [:title "The Three Subject Quotebook"]
    (include-css "/css/reset.css")
    (include-css "/css/blueprint/screen.css")
    (include-css "/css/tsq.css")
    (include-js "/js/jquery-1.7.1.min.js")
    (include-js "/js/tsq.js")]
   [:body
    [:div.container
     [:div.main.span-20
      [:div#header
       [:div#pagetitle
        "The Three Subject Quotebook"]
       [:div#menu
        (menu/menu)]]
      [:div#pohiline.span-15.round
       [:div#content.round content]]
      [:div#sidebar.span-5.round
       (authors/author-list)]]]]))
