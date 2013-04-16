(ns lwh.board.routes
  (:use compojure.core)
  (:require [compojure.route :as route]
            [lwh.board.model :as model]
            [lwh.board.view :as view]
            [ring.util.response :as resp]
            [noir.util.middleware :as noir]
            [noir.session :as session]))

;; Boards controllers
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
  (let [board {:board-id board-id,
               :header header,
               :content content}]
    (model/update-board board)
    (view/show-board board)))

(defn show-new-board []
  (view/show-new-board))

(defn create-board [board]
  (model/create-board board)
  (resp/redirect "/boards"))

;; Threads cntrollers
(defn show-thread-list [board-id]
  (view/show-board-list (model/select-thread board-id)))

(defn show-thread [board-id thread-id]
  (view/show-board (model/find-thread board-id thread-id)))

(defn edit-thread [board-id thread-id]
  (view/edit-board (model/find-thread board-id thread-id)))

(defn delete-thread [board-id thread-id]
  (model/delete-thread board-id)
  (view/delete-thread)
  (resp/redirect (str "/boards/" board-id)))

(defn update-thread [board-id thread-id header content]
  (let [thread {:board-id board-id,
                :thread-id thread-id
                :header header,
                :content content}]
    (model/update-thread thread)
    (view/show-thread thread)))

(defn show-new-thread [board-id]
  (view/show-new-thread (model/find-board board-id)))

(defn create-thread [board-id thread]
  (model/create-thread board-id thread)
  (resp/redirect (str "/boards/" board-id)))

;; Posts controllers
(defn show-post-list [board-id thread-id]
  (view/show-board-list (model/select-board)))

(defn show-post [board-id thread-id post-id]
  (view/show-board (model/find-board board-id)))

(defn edit-post [board-id thread-id post-id]
  (view/edit-board (model/find-board board-id)))

(defn delete-post [board-id thread-id post-id]
  (model/delete-board board-id)
  (view/delete-board)
  (resp/redirect (str "/boards/" board-id "/thread/" thread-id)))

(defn update-post [board-id thread-id post-id header content]
  (let [post {:board-id board-id,
              :thread-id thread-id,
              :post-id post-id,
              :header header,
              :content content}]
    (model/update-board post)
    (view/show-board post)))

(defn show-new-post []
  (view/show-new-board))

(defn create-post [board-id thread-id post]
  (model/create-board post)
  (resp/redirect (str "/boards/" board-id "/thread/" thread-id)))

;; Routes
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
    (show-thread-list))
  (GET
    "/boards/:board-id/threads/new"
    [board-id]
    (show-new-thread board-id))
  (POST
    "/boards/:board-id/threads/create"
    req
    (create-thread (:params req)))
  (GET
    "/boards/:board-id/threads/:thread-id"
    [board-id]
    (show-thread board-id))
  (GET
    "/boards/:board-id/threads/edit/:thread-id"
    [board-id]
    (edit-thread board-id))
  (POST
    "/boards/:board-id/threads/update/:thread-id"
    [board-id header content]
    (update-thread board-id header content))
  (POST
    "/boards/:board-id/threads/delete/:thread-id"
    [board-id]
    (delete-thread board-id))

  ;; Posts routes
  (GET
    "/boards/:board-id/threads/:thread-id/posts"
    []
    (show-post-list))
  (GET
    "/boards/:board-id/threads/:thread-id/posts/new"
    []
    (show-new-post))
  (POST
    "/boards/:board-id/threads/:thread-id/posts/create"
    req
    (create-post (:params req)))
  (GET
    "/boards/:board-id/threads/:thread-id/posts/:post-id"
    [board-id]
    (show-post board-id))
  (GET
    "/boards/:board-id/threads/:thread-id/posts/edit/:post-id"
    [board-id]
    (edit-post board-id))
  (POST
    "/boards/:board-id/threads/:thread-id/posts/update/:post-id"
    [board-id header content]
    (update-post board-id header content))
  (POST
    "/boards/:board-id/threads/:thread-id/posts/delete/:post-id"
    [board-id]
    (delete-post board-id))

  (route/resources "/")
  (route/not-found "Not Found"))