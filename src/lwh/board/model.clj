(ns lwh.board.model
  (:use [monger.core :only [connect! connect set-db! get-db]]
        [monger.collection :only [insert insert-batch] :as mc])
  (:import [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern]))

(def env (into {} (System/getenv)))

(def dbhost (get env "OPENSHIFT_MONGODB_DB_HOST"))
(def dbport (get env "OPENSHIFT_MONGODB_DB_PORT"))

;; localhost, default port
(connect! (str "mongodb://" dbhost ":" dbport "/"))
(set-db! (monger.core/get-db "board"))


(defn create-article [item]
  (insert "articles" item))

(defn select-article []
  (mc/find-maps "articles"))

(defn find-article [id]
  (mc/find-one-as-map "articles" {:_id (ObjectId. id)}))

(defn update-article [item]
  (mc/update "articles" {:_id (ObjectId. (:id item))} {:header (:header item) :content (:content item)}))

(defn delete-article [id]
  (mc/remove "articles" {:_id (ObjectId. id)}))