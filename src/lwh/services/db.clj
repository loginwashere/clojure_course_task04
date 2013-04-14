(ns lwh.services.db
  (:use [monger.core :only [connect-via-uri!]]))

(defn get-env [] (into {} (System/getenv)))

(defn get-collection-name [] "boards")

(defn get-app-name [env] (get env "OPENSHIFT_APP_NAME" "board"))

(defn get-db-name [env] (get-app-name env))

(defn get-uri [env]
  (str
    (get env "OPENSHIFT_MONGODB_DB_URL" "mongodb://127.0.0.1/")
    (get-db-name env)))

(monger.core/connect-via-uri! (get-uri (get-env)))