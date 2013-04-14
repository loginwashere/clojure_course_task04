(ns lwh.board.handler
  (:use compojure.core)
  (:require [org.httpkit.server :as server]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [lwh.board.model :as model]
            [lwh.board.view :as view]
            [ring.util.response :as resp]
            [noir.util.middleware :as noir]
            [noir.session :as session]
            ))

(def env (into {} (System/getenv)))

(defn show-article-list []
  (view/show-article-list (model/select-article)))

(defn show-article [id]
  (view/show-article (model/find-article id)))

(defn edit-article [id]
  (view/edit-article (model/find-article id)))

(defn delete-article [id]
  (model/delete-article id)
  (view/delete-article)
  (resp/redirect "/boards"))

(defn update-article [id header content]
  (let [article {:id id, :header header, :content content}]
    (model/update-article article)
    (view/show-article article)))

(defn show-new-article []
  (view/show-new-article))

(defn create-article [article]
  (model/create-article article)
  (resp/redirect "/boards"))

(defroutes app-routes

  (GET "/" [] (resp/redirect "/boards"))

  ;; Show boards list
  (GET "/boards" [] (show-article-list))

  ;; Show form for a new board
  (GET "/boards/new" [] (show-new-article))

  ;; Create new board
  (POST "/boards/create" req (create-article (:params req)))

  ;; Show board details
  (GET "/boards/:id" [id] (show-article id))

  ;; Show form for editting board
  (GET "/boards/edit/:id" [id] (edit-article id))

  ;; Update board
  (POST "/boards/update/:id" [id header content] (update-article id header content))

  ;; Delete board
  (POST "/boards/delete/:id" [id] (delete-article id))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (->
   [(handler/site app-routes)]
   noir/app-handler
   noir/war-handler
   ))

(defn -main [& args]
  (server/run-server app { :ip (env "HOST") :port (Integer/parseInt (env "PORT")) }))

(comment
  ;; Function for inspecting java objects
  (use 'clojure.pprint)
  (defn show-methods [obj]
    (-> obj .getClass .getMethods vec pprint)))