(ns gr-homework.core
	(:use [clojure.tools.namespace.repl :only (refresh)])
	(:require [clojure.string :as str]
						[clj-time.core :as t])
  (:gen-class))

(defn determine-separator
	"Returns the separator of a record as a pattern.
   Throws an exception a pipe, comma, or space is not found."
	[line]
	(loop [line-left line]
		(condp = (first line-left)
			\| #"\|" 
			\, #"," 
			\space #" "
			nil (throw (Exception. "in determine-separator: No or unrecognized separators in record."))
			(recur (rest line-left)))))

(defn parse-date
	"Parses a date string in MM-DD-YYYY into a clj-time
   LocalDate object. Throws if it doesn't find three elements."
	[date-str]
	(let [date-nums (mapv read-string (str/split date-str #"-"))]
		(if (not= 3 (count date-nums))
			(throw (Exception. "in parse-date: Dates must be in MM-DD-YYYY format."))
			(t/local-date (date-nums 2) (date-nums 0) (date-nums 1)))))

(defn parse-record
	"Parses an individual record, or one line.
   Expects, in order, last name, first name, gender,
   favorite color, and date of birth."
	[line]
	(let [entries (->> (determine-separator line)
										 (str/split line))]
		(if (not= (count entries) 5)
			(throw (Exception. "in parse-record: Invalid number of entries in record."))
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
	"Parses multiple records in a file, must contain
   line separations."
	[filename]
	(parse-records-string (slurp filename)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
