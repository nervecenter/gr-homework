(ns gr-homework.printing
	(:use gr-homework.sorting)
	(:require [clj-time.core :as t]))

(defn date-str
	"Return a printable string from a clj-time date, in the m/d/yyyy format."
	[localdate]
	(str (t/month localdate) "/" (t/day localdate) "/" (t/year localdate)))

(defn record-str
	"Format a neat multiline representation of a record for standard output."
	[rec]
  (str "\nFirst name:      " (:first rec)
			 "\nLast name:       " (:last rec)
			 "\nGender:          " (:gender rec)
			 "\nFavorite color:  " (:fav-color rec)
			 "\nDate of birth:   " (date-str (:dob rec))
			 "\n"))

(defn multiple-records-str
	"Concat any number of records for neat standard output."
	[records]
	(reduce str (map record-str records)))

(defn output1
	[records]
  (str "\nSorted by gender (female first), last name ascending:\n"
			 (multiple-records-str
				(sort-by-gender-then-last-name-ascending records))))

(defn output2
	[records]
  (str "\nSorted by birth date, ascending:\n"
			 (multiple-records-str
				(sort-by-dob-ascending records))))

(defn output3
	[records]
  (str "\nSorted by last name, descending:\n"
			 (multiple-records-str
				(sort-by-last-name-descending records))))

(defn three-outputs-str
	"Prints the three specified sorted variations to standard output."
	[records]
	(str (output1 records)
			 (output2 records)
			 (output3 records)))
