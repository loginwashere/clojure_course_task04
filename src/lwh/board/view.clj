(ns lwh.board.view
  (:require [me.raynes.laser :as l]
            [clojure.java.io :refer [file]]))


(def main-html
  (l/parse
   (slurp (clojure.java.io/resource "public/html/main.html"))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Fragments

(def article-row (l/select main-html (l/id= "article-row")))
(def article-item (l/select main-html (l/id= "article-item")))
(def article-item-edit (l/select main-html (l/id= "article-item-edit")))


;; Used to show an article in the articles list
;; Path: /articles/
(l/defragment article-frag article-row  [{:keys [_id header content]}]
  (l/element= :h2) (l/content header)
  (l/element= :span) (l/content content)
  (l/element= :a) (l/attr :href (str "/articles/" _id)))

;; Shows article details
;; Path: /article/:id
(l/defragment article-item-frag article-item [{:keys [_id header content]}]
  (l/element= :h2) (l/content header)
  (l/element= :span) (l/content content)
  (l/id= "edit") (l/attr :href (str "/articles/edit/" _id))
  (l/id= "delete") (l/attr :onclick (str "deleteArticle(" _id ")"))
  (l/id= "delete") (l/attr :href (str "/articles/delete/" _id)))

;; Shows a form for article editting
;; Path: /article/edit/:id
(l/defragment article-edit-item-frag article-item-edit [{:keys [_id header content]}]
  (l/id= "header") (l/attr :value header)
  (l/element= :textarea) (l/content content)
  (l/id= "close") (l/attr :href (str "/articles/" _id))
  (l/element= :form) (l/attr :action (str "/articles/update/" _id)))

;; Shows a from for article creating
;; Path: /article/new
(l/defragment article-new-item-frag article-item-edit []
  (l/id= "close") (l/attr :href "/articles")
  (l/element= :form) (l/attr :action (str "/articles/create")))

;; Shows a from for article creating
;; Path: /article/delete/:id
(l/defragment article-delete-item-frag article-item-edit []
  (l/id= "close") (l/attr :href "/articles")
  (l/element= :h2) (l/content "Are you sure?"))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Pages



(defn show-article-list [article-list]
  (l/document main-html
              (l/id= "article-grid")
              (l/content
               (for [article article-list]
                 (article-frag article)))))


(defn show-article [article]
  (l/document main-html
              (l/id= "article-grid")
              (l/content
               (article-item-frag article))))


(defn edit-article [article]
  (l/document main-html
              (l/id= "article-grid")
              (l/content
               (article-edit-item-frag article))))


(defn show-new-article []
  (l/document main-html
              (l/id= "article-grid")
              (l/content
               (article-new-item-frag))))

(defn delete-article []
  (l/document main-html
              (l/id= "article-grid")
              (l/content
               (article-delete-item-frag))))