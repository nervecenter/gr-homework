(ns gr-homework.parsing
	(:require [clojure.string :as str]
						[clj-time.core :as t]))

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
															"of entries in record, or bad separator.")))
			{:last (entries 0)
			 :first (entries 1)
			 :gender (str/lower-case (entries 2))
			 :fav-color (entries 3)
			 :dob (parse-date (entries 4))})))

(defn parse-records-file
	"Parses records in a file, must contain line separations.
   Returns a vector of records."
	[filename]
	(->> filename
			 (slurp)
			 (str/split-lines)
			 (mapv parse-record)))

(defn parse-multiple-records-files
	"Parses multiple records files, concats them.
   Returns a vector of records."
	[filenames]
	(->> filenames
			 (map parse-records-file)
			 (reduce concat)
			 (vec)))
