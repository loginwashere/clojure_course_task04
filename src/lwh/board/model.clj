(ns lwh.board.model
  (:use [lwh.services.db]
        [monger.collection :only [insert find-maps find-one-as-map update] :as mc])
  (:import [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern]))

(defn create-board [item]
  (let [oid (ObjectId.)
        doc {:header (:header item) :content (:content item)}]
    (mc/insert (get-collection-name) (merge doc {:_id oid}))))

(defn select-board []
  (mc/find-maps (get-collection-name)))

(defn find-board [board-id]
  (mc/find-one-as-map (get-collection-name) {:_id (ObjectId. board-id)}))

(defn update-board [item]
  (mc/update
    (get-collection-name)
    {:_id (ObjectId. (:board-id item))}
    {:header (:header item) :content (:content item)}))

(defn delete-board [board-id]
  (mc/remove (get-collection-name) {:_id (ObjectId. board-id)}))