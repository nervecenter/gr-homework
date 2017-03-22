(ns gr-homework.core
	(:use [clojure.tools.namespace.repl :only (refresh)])
	(:require [clojure.string :as str]
						[clj-time.core :as t]
						[org.httpkit.server :as http]
						[compojure.core :refer [defroutes context GET POST ANY]]
						[ring.middleware.content-type :refer [wrap-content-type]])
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
	[& filenames]
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
	(do
		(println)
		(println "First name:      " (:first rec))
		(println "Last name:       "  (:last rec))
		(println "Gender:          " (:gender rec))
		(println "Favorite color:  " (:fav-color rec))
		(println "Date of birth:   " (date-str (:dob rec)))))

(defn print-records
	[records]
	(dorun (map print-record records)))

(defn output1
	[records]
	(do
		(println)
		(println "Sorted by gender (female first), last name ascending:")
		(print-records (sort-by-gender-then-last-name-ascending records))))

(defn output2
	[records]
	(do
		(println)
		(println "Sorted by birth date, ascending:")
		(print-records (sort-by-dob-ascending records))))

(defn output3
	[records]
	(do
		(println)
		(println "Sorted by last name, descending:")
		(print-records (sort-by-last-name-descending records))))

(defn print-three-outputs
	[records]
	(output1 records)
	(output2 records)
	(output3 records))

;;
;; REST SERVER
;;

(defroutes gr-homework-routes
	(context "/records" req
					 (POST "/" req (org-page-handler req))
					 (GET "/gender" req (org-data-handler req))
					 (GET "/birthdate" req (org-responses-handler req))
					 (GET "/name" req (issue-page-handler req)))

(defn -main
  "I don't do a whole lot ... yet."
  ([in1 in2 in3]
	 (println)
	 (println "Reading three input files...")
	 (print-three-outputs (parse-multiple-records-files in1 in2 in3)))
	([]
	 (do
		 (println "Starting server..."))))
