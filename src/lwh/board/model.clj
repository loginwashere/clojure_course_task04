(ns lwh.board.model
  (:use [lwh.services.db]
        [monger.collection :only [insert find-maps find-one-as-map update] :as mc])
  (:import [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern]))

;; Boards
(defn create-board [item]
  (let [oid (ObjectId.)
        doc {:header (:header item) :content (:content item) :threads (list)}]
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

;; Threads
(defn create-thread [board-id item]
  (let [board-id (ObjectId. board-id)
        doc {:header (:header item) :content (:content item)}]
    (mc/update (get-collection-name) {:_id board-id} {"$addToSet" {:threads doc}})))

(defn select-thread [board-id]
  (mc/find-maps (get-collection-name)))

(defn find-thread [board-id thread-id]
  (mc/find-one-as-map (get-collection-name) {:_id (ObjectId. board-id)}))

(defn update-thread [board-id thread-id item]
  (mc/update
    (get-collection-name)
    {:_id (ObjectId. (:board-id item))}
    {:header (:header item) :content (:content item)}))

(defn delete-thread [board-id tread-id]
  (mc/remove (get-collection-name) {:_id (ObjectId. board-id)}))

;; Posts
(defn create-post [item]
  (let [oid (ObjectId.)
        doc {:header (:header item) :content (:content item)}]
    (mc/insert (get-collection-name) (merge doc {:_id oid}))))

(defn select-post []
  (mc/find-maps (get-collection-name)))

(defn find-post [board-id]
  (mc/find-one-as-map (get-collection-name) {:_id (ObjectId. board-id)}))

(defn update-post [item]
  (mc/update
    (get-collection-name)
    {:_id (ObjectId. (:board-id item))}
    {:header (:header item) :content (:content item)}))

(defn delete-post [board-id]
  (mc/remove (get-collection-name) {:_id (ObjectId. board-id)}))