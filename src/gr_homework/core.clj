(ns gr-homework.core
	(:use [clojure.tools.namespace.repl :only (refresh)]
				gr-homework.parsing
				gr-homework.printing
				gr-homework.sorting)
	(:require	[org.httpkit.server :as http]
						[compojure.core :refer [defroutes context GET POST ANY]]
            [ring.util.response :refer [response not-found]]
						[ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.reload :refer [wrap-reload]]
						[cheshire.core :as json])
  (:gen-class))

;;
;; RECORDS ATOM
;;

(def records (atom nil))

(defn records-response
	"If there are no records stored, responds with a message indicating such.
   Otherwise, runs the given sorting func on stored records, json encodes, 
   sends."
	[sorting-func]
  (response
   (if (nil? @records)
     "No records stored.\n"
		 (json/generate-string
			(->> (sorting-func @records)
					 (map #(assoc % :dob (date-str (:dob %)))))
			{:pretty true}))))

;;
;; REST SERVER
;;

(defroutes gr-homework-routes
	(context "/records" req
					 (POST "/" {body :body}
                 (try
                   (let [rec (parse-record (slurp body))]
										 (swap! records conj rec)
										 (response (str "Added record for "
																		(:first rec) " " (:last rec)
																		".\n")))
                   (catch Exception e
                     {:status 500
                      :body (str "Could not add record: Exception "
                                 (.getMessage e)
                                 "\n")})))
					 (GET "/gender" req
                (records-response sort-by-gender-then-last-name-ascending))
					 (GET "/birthdate" req
                (records-response sort-by-dob-ascending))
					 (GET "/name" req
                (records-response sort-by-last-name-ascending)))
  (ANY "*" req (not-found "404 Not Found:\nFill this in with usage instructions!\n")))

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
	 (println)
	 (println "Reading" (count filenames) "input files...")
	 (print-three-outputs (parse-multiple-records-files filenames)))
	([]
   (start-server)
   (println "Server started.")))
