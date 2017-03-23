(ns gr-homework.core
	(:use [clojure.tools.namespace.repl :only (refresh)])
	(:require [clojure.string :as str]
						[clj-time.core :as t]
						[org.httpkit.server :as http]
						[compojure.core :refer [defroutes context GET POST ANY]]
            [ring.util.response :refer [response not-found]]
						[ring.middleware.content-type :refer [wrap-content-type]]
						[ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.reload :refer [wrap-reload]]
						[cheshire.core :as json])
  (:gen-class))

;;
;; PARSING FUNCTIONS
;;

(defn determine-separator
	"Returns the separator of a record as a pattern.
   Throws an exception if a pipe, comma, or space is not found."
	[line]
	(loop [line-left line]
		(condp = (first line-left)
			\| #"\|" 
			\, #"," 
			\space #" "
			nil (throw (Exception. (str "in determine-separator: No or "
																	"unrecognized separators in record.")))
			(recur (rest line-left)))))

(defn parse-date
	"Parses a date string in MM-DD-YYYY into a clj-time
   LocalDate object. Throws if it doesn't find three elements."
	[date-str]
	(let [date-nums (mapv #(. Integer parseInt %) (str/split date-str #"-"))]
		(if (not= 3 (count date-nums))
			(throw (Exception. "in parse-date: Dates must be in MM-DD-YYYY format."))
			(t/local-date (date-nums 2) (date-nums 0) (date-nums 1)))))

(defn parse-record
	"Parses an individual record, or one line.
   Expects, in order, last name, first name, gender,
   favorite color, and date of birth."
	[line]
	(let [entries (str/split line (determine-separator line))]
		(if (not= (count entries) 5)
			(throw (Exception. (str "in parse-record: Invalid number "
															"of entries in record.")))
			{:last (entries 0)
			 :first (entries 1)
			 :gender (str/lower-case (entries 2))
			 :fav-color (entries 3)
			 :dob (parse-date (entries 4))})))

(defn parse-records-string
	"Parses multiple records in a raw string, must contain
   line separations."
	[rec-str]
	(mapv parse-record (str/split-lines rec-str)))

(defn parse-records-file
	"Parses records in a file, must contain
   line separations."
	[filename]
	(parse-records-string (slurp filename)))

(defn parse-multiple-records-files
	"Parses multiple records files, concats them into
   one collection of records."
	[filenames]
	(->> filenames
			 (map slurp)
			 (map parse-records-string)
			 (reduce concat)))

;;
;; SORTING FUNCTIONS
;;

(defn female?
	[rec]
	(= (:gender rec) "female"))

(defn male?
	[rec]
	(not (female? rec)))

(defn sort-by-last-name-ascending
	[records]
	(sort #(compare (:last %1) (:last %2)) records))

(defn sort-by-last-name-descending
	[records]
	(sort #(compare (:last %2) (:last %1)) records))

(defn sort-by-gender-then-last-name-ascending
	[records]
	(concat (sort-by-last-name-ascending (filter female? records))
					(sort-by-last-name-ascending (filter male? records))))

(defn sort-by-dob-ascending
	[records]
	(sort #(t/before? (:dob %1) (:dob %2)) records))

;;
;; PRINTING FUNCTIONS
;;

(defn date-str
	[localdate]
	(str (t/month localdate) "/" (t/day localdate) "/" (t/year localdate)))

(defn print-record
	[rec]
  (println)
  (println "First name:      " (:first rec))
  (println "Last name:       " (:last rec))
  (println "Gender:          " (:gender rec))
  (println "Favorite color:  " (:fav-color rec))
  (println "Date of birth:   " (date-str (:dob rec))))

(defn print-records
	[records]
	(dorun (map print-record records)))

(defn output1
	[records]
  (println)
  (println "Sorted by gender (female first), last name ascending:")
  (print-records (sort-by-gender-then-last-name-ascending records)))

(defn output2
	[records]
  (println)
  (println "Sorted by birth date, ascending:")
  (print-records (sort-by-dob-ascending records)))

(defn output3
	[records]
  (println)
  (println "Sorted by last name, descending:")
  (print-records (sort-by-last-name-descending records)))

(defn print-three-outputs
	[records]
	(output1 records)
	(output2 records)
	(output3 records))

;;
;; RECORDS ATOM
;;

(def records (atom nil))

(defn records-response [sorting-func]
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
                   (swap! records conj (parse-record (slurp body)))
                   (response "Added record.\n")
                   (catch Exception e
                     {:status 500
                      :body (str "Could not add record:\nException "
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
      (wrap-json-response)
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
  "I don't do a whole lot ... yet."
  ([& filenames]
	 (println)
	 (println "Reading" (count filenames) "input files...")
	 (print-three-outputs (parse-multiple-records-files filenames)))
	([]
   (start-server)
   (println "Server started.")))
