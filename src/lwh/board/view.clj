(ns lwh.board.view
  (:require [me.raynes.laser :as l]
            [clojure.java.io :refer [file]]))


(def main-html
  (l/parse
   (slurp (clojure.java.io/resource "public/html/main.html"))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Fragments

(def board-row (l/select main-html (l/id= "board-row")))
(def board-item (l/select main-html (l/id= "board-item")))
(def board-item-edit (l/select main-html (l/id= "board-item-edit")))


;; Used to show an board in the boards list
;; Path: /boards/
(l/defragment board-frag board-row  [{:keys [_id header content]}]
  (l/element= :h2) (l/content header)
  (l/element= :span) (l/content content)
  (l/element= :a) (l/attr :href (str "/boards/" _id)))

;; Shows board details
;; Path: /boards/:id
(l/defragment board-item-frag board-item [{:keys [_id header content]}]
  (l/element= :h2) (l/content header)
  (l/element= :span) (l/content content)
  (l/id= "edit") (l/attr :href (str "/boards/edit/" _id))
  (l/id= "delete") (l/attr :onclick (str "deleteArticle(" _id ")"))
  (l/id= "delete") (l/attr :href (str "/boards/delete/" _id)))

;; Shows a form for board editting
;; Path: /boards/edit/:id
(l/defragment board-edit-item-frag board-item-edit [{:keys [_id header content]}]
  (l/id= "header") (l/attr :value header)
  (l/element= :textarea) (l/content content)
  (l/id= "close") (l/attr :href (str "/boards/" _id))
  (l/element= :form) (l/attr :action (str "/boards/update/" _id)))

;; Shows a from for board creating
;; Path: /boards/new
(l/defragment board-new-item-frag board-item-edit []
  (l/id= "close") (l/attr :href "/boards")
  (l/element= :form) (l/attr :action (str "/boards/create")))

;; Shows a from for board creating
;; Path: /boards/delete/:id
(l/defragment board-delete-item-frag board-item-edit []
  (l/id= "close") (l/attr :href "/boards")
  (l/element= :h2) (l/content "Are you sure?"))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Pages



(defn show-board-list [board-list]
  (l/document main-html
              (l/id= "board-grid")
              (l/content
               (for [board board-list]
                 (board-frag board)))))


(defn show-board [board]
  (l/document main-html
              (l/id= "board-grid")
              (l/content
                (board-item-frag board))))


(defn edit-board [board]
  (l/document main-html
              (l/id= "board-grid")
              (l/content
                (board-edit-item-frag board))))


(defn show-new-board []
  (l/document main-html
              (l/id= "board-grid")
              (l/content
                (board-new-item-frag))))

(defn delete-board []
  (l/document main-html
              (l/id= "board-grid")
              (l/content
                (board-delete-item-frag))))