(ns gr-homework.core
	(:use [clojure.tools.namespace.repl :only (refresh)])
	(:require [clojure.string :as str])
  (:gen-class))

(defn determine-separator
	[line]
	(loop [line-left line]
		(condp = (first line-left)
			\| \| 
			\, \, 
			\space \space
			nil (throw (Exception. "No or unrecognized separators in record."))
			(recur (rest line-left)))))

(defn parse-date
	[date-str]
	date-str)

(defn parse-record
	[line]
	(let [entries (->> (determine-separator line)
										 (str)
										 (re-pattern)
										 (str/split line))]
		(if (not= (count entries) 5)
			(throw (Exception. "Invalid number of entries in record."))
			{:last (entries 0)
			 :first (entries 1)
			 :gender (str/lower-case (entries 2))
			 :fav-color (entries 3)
			 :dob (parse-date (entries 4))})))

(defn parse-records-file
	[filename]
	(->> (slurp filename)
			 (str/split-lines)
			 (map parse-record)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
