(ns lwh.board.handler
  (:use compojure.core)
  (:require [org.httpkit.server :as server]
            [compojure.handler :as handler]
            [lwh.board.routes :only [app-routes] :as routes]
            [noir.util.middleware :as noir]
            [noir.session :as session]))

(def env (into {} (System/getenv)))

(def app
  (->
    [(handler/site routes/app-routes)]
    noir/app-handler
    noir/war-handler))

(defn -main [& args]
  (server/run-server app { :ip (env "HOST") :port (Integer/parseInt (env "PORT")) }))