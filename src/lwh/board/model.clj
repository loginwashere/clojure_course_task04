(ns lwh.board.model
  (:use [lwh.services.db]
        [monger.collection :only [insert find-maps find-one-as-map update] :as mc])
  (:import [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern]))

(defn create-article [item]
  (mc/insert (get-collection-name) item))

(defn select-article []
  (mc/find-maps (get-collection-name)))

(defn find-article [id]
  (mc/find-one-as-map (get-collection-name) {:_id (ObjectId. id)}))

(defn update-article [item]
  (mc/update
    (get-collection-name)
    {:_id (ObjectId. (:id item))}
    {:header (:header item) :content (:content item)}))

(defn delete-article [id]
  (mc/remove (get-collection-name) {:_id (ObjectId. id)}))