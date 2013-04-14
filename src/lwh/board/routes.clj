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

(defn show-board [board-id]
  (view/show-board (model/find-board board-id)))

(defn edit-board [board-id]
  (view/edit-board (model/find-board board-id)))

(defn delete-board [board-id]
  (model/delete-board board-id)
  (view/delete-board)
  (resp/redirect "/boards"))

(defn update-board [board-id header content]
  (let [board {:board-id board-id, :header header, :content content}]
    (model/update-board board)
    (view/show-board board)))

(defn show-new-board []
  (view/show-new-board))

(defn create-board [board]
  (model/create-board board)
  (resp/redirect "/boards"))

(defroutes app-routes

  (GET "/" [] (resp/redirect "/boards"))

  ;; Boards routes
  (GET
    "/boards"
    []
    (show-board-list))
  (GET
    "/boards/new"
    []
    (show-new-board))
  (POST
    "/boards/create"
    req
    (create-board (:params req)))
  (GET
    "/boards/:board-id"
    [board-id]
    (show-board board-id))
  (GET
    "/boards/edit/:board-id"
    [board-id]
    (edit-board board-id))
  (POST
    "/boards/update/:board-id"
    [board-id header content]
    (update-board board-id header content))
  (POST
    "/boards/delete/:board-id"
    [board-id]
    (delete-board board-id))

  ;; Threads routes
  (GET
    "/boards/:board-id/threads"
    []
    (show-board-list))
  (GET
    "/boards/:board-id/threads/new"
    []
    (show-new-board))
  (POST
    "/boards/:board-id/threads/create"
    req
    (create-board (:params req)))
  (GET
    "/boards/:board-id/threads/:thread-id"
    [board-id]
    (show-board board-id))
  (GET
    "/boards/:board-id/threads/edit/:thread-id"
    [board-id]
    (edit-board board-id))
  (POST
    "/boards/:board-id/threads/update/:thread-id"
    [board-id header content]
    (update-board board-id header content))
  (POST
    "/boards/:board-id/threads/delete/:thread-id"
    [board-id]
    (delete-board board-id))

  ;; Posts routes
  (GET
    "/boards/:board-id/threads/:thread-id/posts"
    []
    (show-board-list))
  (GET
    "/boards/:board-id/threads/:thread-id/posts/new"
    []
    (show-new-board))
  (POST
    "/boards/:board-id/threads/:thread-id/posts/create"
    req
    (create-board (:params req)))
  (GET
    "/boards/:board-id/threads/:thread-id/posts/:post-id"
    [board-id]
    (show-board board-id))
  (GET
    "/boards/:board-id/threads/:thread-id/posts/edit/:post-id"
    [board-id]
    (edit-board board-id))
  (POST
    "/boards/:board-id/threads/:thread-id/posts/update/:post-id"
    [board-id header content]
    (update-board board-id header content))
  (POST
    "/boards/:board-id/threads/:thread-id/posts/delete/:post-id"
    [board-id]
    (delete-board board-id))

  (route/resources "/")
  (route/not-found "Not Found"))