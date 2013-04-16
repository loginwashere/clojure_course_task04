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
(def thread-item-edit (l/select main-html (l/id= "thread-item-edit")))

(def thread-row (l/select main-html (l/id= "thread-row")))

(l/defragment thread-frag thread-row  [{:keys [_id header content]}]
  (l/element= :h2) (l/content header)
  (l/element= :span) (l/content content)
  (l/element= :a) (l/attr :href (str "/boards/" _id)))

;; Used to show an board in the boards list
;; Path: /boards/
(l/defragment board-frag board-row  [{:keys [_id header content]}]
  (l/element= :h2) (l/content header)
  (l/element= :span) (l/content content)
  (l/element= :a) (l/attr :href (str "/boards/" _id)))

;; Shows board details
;; Path: /boards/:id
(l/defragment board-item-frag board-item [{:keys [_id header content threads]}]
  (l/element= :h2) (l/content header)
  (l/element= :span) (l/content content)
  (l/id= "edit") (l/attr :href (str "/boards/edit/" _id))
  (l/id= "delete") (l/attr :onclick (str "deleteArticle(" _id ")"))
  (l/id= "delete") (l/attr :href (str "/boards/delete/" _id))
  (l/id= "create-thread") (l/attr :href (str "/boards/" _id "/threads/new"))
  (l/id= "thread-grid")
    (l/content
      (for [thread threads]
        (thread-frag thread))))

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

(l/defragment thread-new-form thread-item-edit [board-id]
  (l/id= "close-thread-create") (l/attr :href (str "/boards/" board-id))
  (l/element= :form) (l/attr :action (str "/boards/" board-id "/threads/create")))

(l/defragment thread-new-item-frag board-item [{:keys [_id header content threads]}]
  (l/element= :h2) (l/content header)
  (l/element= :span) (l/content content)
  (l/id= "edit") (l/attr :href (str "/boards/edit/" _id))
  (l/id= "delete") (l/attr :onclick (str "deleteArticle(" _id ")"))
  (l/id= "delete") (l/attr :href (str "/boards/delete/" _id))
  (l/id= "create-thread-container") (l/content (thread-new-form _id))
  (l/id= "thread-grid")
    (l/content
      (for [thread threads]
        (thread-frag thread))))

;; Shows a from for board creating
;; Path: /boards/delete/:id
(l/defragment board-delete-item-frag board-item-edit []
  (l/id= "close") (l/attr :href "/boards")
  (l/element= :h2) (l/content "Are you sure?"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Pages


;; Boards
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

(defn show-new-thread [board]
  (l/document main-html
              (l/id= "board-grid")
              (l/content
                (thread-new-item-frag board))))

(defn delete-board []
  (l/document main-html
              (l/id= "board-grid")
              (l/content
                (board-delete-item-frag))))

;; Threads
(defn show-thread [board]
  (l/document main-html
              (l/id= "board-grid")
              (l/content
                (board-item-frag board))))

(defn delete-thread []
  (l/document main-html
              (l/id= "board-grid")
              (l/content
                (board-delete-item-frag))))