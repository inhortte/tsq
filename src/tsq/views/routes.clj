(ns tsq.views.routes
  (:require [tsq.helpers.quotes :as quotehelper]
            [tsq.views.quotes :as quotes]
            [tsq.views.common :as common]
            [tsq.views.menu :as menu]
            [noir.session :as session])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        tsq.middleware))

(defpartial ajax-hovno []
  [:script "ajax_hovno();"])

;; :metaquotes is a hashtable - {:count #quotes, :current quote#}
(pre-route "/*" {}
           (when-not (session/get :metaquotes)
             (quotehelper/reset-metaquotes)))

(defpage "/" []
  (common/layout
   (quotes/random-quote)))

(defpage "/random" []
  (quotes/random-quote)) ; ajax

(defpage "/quote/:function" {:keys [function]} ; ajax
  ((resolve (symbol (str "tsq.views.quotes/" function)))))

;; author set/reset

(defn author-change []
  (quotehelper/reset-metaquotes)
  (html (menu/menu)
        (ajax-hovno)))

(defpage "/set-author/:id" {:keys [id]} ; ajax
  (session/put! :author id)
  (author-change))

(defpage "/clear-author" [] ; ajax
  (session/remove! :author)
  (author-change))
 
(defpage "/refresh-menu" [] ; ajax
  (html (menu/menu)
        (ajax-hovno)))

(defpage "/reset-quotes" [] ; ajax
  (quotehelper/reset-metaquotes)
  (quotes/random-quote))

(defpage "/current-quote" [] ; ajax
  (quotes/current-quote))
