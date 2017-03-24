;; Copyright © 2017 Chris Collazo

(ns gr-homework.sorting
  (:require [clj-time.core :as t]))

(defn female?
  "Returns true if a given record notes a female."
  [rec]
  (= (:gender rec) "female"))

(defn male?
  "Returns true if a given record notes a male."
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
