(ns lwh.board.routes
  (:use compojure.core)
  (:require [compojure.route :as route]
            [lwh.board.model :as model]
            [lwh.board.view :as view]
            [ring.util.response :as resp]
            [noir.util.middleware :as noir]
            [noir.session :as session]))

(defn show-board-list []
  (view/show-board-list (model/select-board)))

(defn show-board [id]
  (view/show-board (model/find-board id)))

(defn edit-board [id]
  (view/edit-board (model/find-board id)))

(defn delete-board [id]
  (model/delete-board id)
  (view/delete-board)
  (resp/redirect "/boards"))

(defn update-board [id header content]
  (let [board {:id id, :header header, :content content}]
    (model/update-board board)
    (view/show-board board)))

(defn show-new-board []
  (view/show-new-board))

(defn create-board [board]
  (model/create-board board)
  (resp/redirect "/boards"))

(defroutes app-routes

  (GET "/" [] (resp/redirect "/boards"))

  ;; Show boards list
  (GET "/boards" [] (show-board-list))

  ;; Show form for a new board
  (GET "/boards/new" [] (show-new-board))

  ;; Create new board
  (POST "/boards/create" req (create-board (:params req)))

  ;; Show board details
  (GET "/boards/:id" [id] (show-board id))

  ;; Show form for editting board
  (GET "/boards/edit/:id" [id] (edit-board id))

  ;; Update board
  (POST "/boards/update/:id" [id header content] (update-board id header content))

  ;; Delete board
  (POST "/boards/delete/:id" [id] (delete-board id))

  (route/resources "/")
  (route/not-found "Not Found"))