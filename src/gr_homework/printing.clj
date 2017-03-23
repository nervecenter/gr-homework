(ns gr-homework.printing
	(:use gr-homework.sorting)
	(:require [clj-time.core :as t]))

(defn date-str
	"Return a printable string from a clj-time date, in the specified format."
	[localdate]
	(str (t/month localdate) "/" (t/day localdate) "/" (t/year localdate)))

(defn print-record
	"Neatly print a record to standard output."
	[rec]
  (println)
  (println "First name:      " (:first rec))
  (println "Last name:       " (:last rec))
  (println "Gender:          " (:gender rec))
  (println "Favorite color:  " (:fav-color rec))
  (println "Date of birth:   " (date-str (:dob rec))))

(defn print-records
	"Print any number of records neatly to standard output."
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
	"Prints the three specified sorted variations to standard output."
	[records]
	(output1 records)
	(output2 records)
	(output3 records))
