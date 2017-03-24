(ns gr-homework.core
  (:use [clojure.tools.namespace.repl :only (refresh)]
        gr-homework.parsing
        gr-homework.printing
        gr-homework.sorting)
  (:require [org.httpkit.server :as http]
            [compojure.core :refer [defroutes context GET POST ANY]]
            [ring.util.response :refer [response not-found]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.reload :refer [wrap-reload]]
            [cheshire.core :as json])
  (:gen-class))

;; An atom to act as a live store for records
(def records (atom nil))

;; A general handler for our three GET actions
;; In a larger project, this would likely be organized in a GET namespace
(defn records-response
  "If there are no records stored, responds with a message indicating such.
   Otherwise, runs the passed-in sorting func on stored records,
   JSON encodes, sends the resulting JSON back to client."
  [sorting-func]
  (response
   (if (nil? @records)
     "No records stored.\n"
     (json/generate-string
      (->> (sorting-func @records)
           (map #(assoc % :dob (date-str (:dob %)))))
      {:pretty true}))))

;; Our single POST action's handler
;; In a larger project, this would likely be organized in a POST namespace
(defn post-record
  "Given a string of a raw record line, post it to the atom
   and return a response depending on success or failure."
  [line]
  (try
    (let [rec (parse-record line)]
      (swap! records conj rec)
      (response (str "Added record for "
                     (:first rec) " " (:last rec)
                     ".\n")))
    (catch Exception e
      {:status 500
       :body (str "Could not add record: Exception "
                  (.getMessage e)
                  "\n")})))

;;
;; HTTP-Kit/Ring/Compojure Setup and Config
;;

(defroutes gr-homework-routes
  (context "/records" req
           (POST "/" {body :body}
                 (post-record (slurp body)))
           (GET "/gender" req
                (records-response sort-by-gender-then-last-name-ascending))
           (GET "/birthdate" req
                (records-response sort-by-dob-ascending))
           (GET "/name" req
                (records-response sort-by-last-name-ascending)))
  (ANY "*" req (not-found "404 Not Found:\nCheck README.md for instructions!\n")))

(def app
  (-> gr-homework-routes
      (wrap-content-type)
      (wrap-reload)))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn start-server []
  (when (nil? @server)
    (reset! server (http/run-server #'app {:port 8080}))))

(defn restart-server []
  (when-not (nil? @server)
    (do (stop-server) (start-server))))

(defn -main
  "If given nothing, starts the REST server.
   Otherwise, any number of files can be named. Their records
   will be printed to standard output sorted in the three specified sort
   configurations."
  ([& filenames]
   (println "\nReading" (count filenames) "input files...")
   (print
    (try
      (three-outputs-str (parse-multiple-records-files filenames))
      (catch Exception e
        (str "Couldn't parse a file: " (.getMessage e))))))
  ([]
   (start-server)
   (println "Server started.")))
