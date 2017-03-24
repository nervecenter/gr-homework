(ns gr-homework.sorting-test
  (:require [clojure.test :refer :all]
            [gr-homework.sorting :refer :all]
						[clj-time.core :as t]))	

(def example-records
	[{:last "Collazo"	
		:first "Christopher"
		:gender "male"
		:fav-color "green"
		:dob (t/local-date 1991 6 21)}
	 {:last "Washington"
		:first "George"
		:gender "male"
		:fav-color "red"
		:dob (t/local-date 1732 2 22)}
	 {:last "Earhart"
		:first "Amelia"
		:gender "female"
		:fav-color "blue"
		:dob (t/local-date 1897 7 24)}
	 {:last "Ritchie"
		:first "Dennis"
		:gender "male"
		:fav-color "yellow"
		:dob (t/local-date 1941 9 9)}
	 {:last "Lovelace"
		:first "Ada"
		:gender "female"
		:fav-color "purple"
		:dob (t/local-date 1815 12 10)}
	 {:last "Randall"
		:first "Lisa"
		:gender "female"
		:fav-color "orange"
		:dob (t/local-date 1962 6 18)}])

(deftest female?-test
	(testing "Testing that we correctly determine that a record is female."
		(is (= (female? (example-records 0)) false))
    (is (= (female? (example-records 2)) true))
		(is (= (female? nil) false))))

(deftest male?-test
	(testing "Testing that we correctly determine that a record is male."
		(is (= (male? (example-records 0)) true))
    (is (= (male? (example-records 2)) false))
		(is (= (male? nil) true))))

(deftest sort-by-last-name-ascending-test
  (testing "Testing that we correctly sort by last name, ascending."
    (is (= (sort-by-last-name-ascending example-records)
					 [{:last "Collazo"	
						 :first "Christopher"
						 :gender "male"
						 :fav-color "green"
						 :dob (t/local-date 1991 6 21)}
						{:last "Earhart"
						 :first "Amelia"
						 :gender "female"
						 :fav-color "blue"
						 :dob (t/local-date 1897 7 24)}
						{:last "Lovelace"
						 :first "Ada"
						 :gender "female"
						 :fav-color "purple"
						 :dob (t/local-date 1815 12 10)}
						{:last "Randall"
						 :first "Lisa"
						 :gender "female"
						 :fav-color "orange"
						 :dob (t/local-date 1962 6 18)}
						{:last "Ritchie"
						 :first "Dennis"
						 :gender "male"
						 :fav-color "yellow"
						 :dob (t/local-date 1941 9 9)}
						{:last "Washington"
						 :first "George"
						 :gender "male"
						 :fav-color "red"
						 :dob (t/local-date 1732 2 22)}]))
		(is (= (sort-by-last-name-ascending nil) '()))))

(deftest sort-by-last-name-descending-test
  (testing "Testing that we correctly sort by last name, descending."
    (is (= (sort-by-last-name-descending example-records)
					 [{:last "Washington"
						 :first "George"
						 :gender "male"
						 :fav-color "red"
						 :dob (t/local-date 1732 2 22)}
						{:last "Ritchie"
						 :first "Dennis"
						 :gender "male"
						 :fav-color "yellow"
						 :dob (t/local-date 1941 9 9)}
						{:last "Randall"
						 :first "Lisa"
						 :gender "female"
						 :fav-color "orange"
						 :dob (t/local-date 1962 6 18)}
						{:last "Lovelace"
						 :first "Ada"
						 :gender "female"
						 :fav-color "purple"
						 :dob (t/local-date 1815 12 10)}
						{:last "Earhart"
						 :first "Amelia"
						 :gender "female"
						 :fav-color "blue"
						 :dob (t/local-date 1897 7 24)}
						{:last "Collazo"	
						 :first "Christopher"
						 :gender "male"
						 :fav-color "green"
						 :dob (t/local-date 1991 6 21)}]))
		(is (= (sort-by-last-name-descending nil) '()))))

(deftest sort-by-gender-then-last-name-ascending-test
  (testing "Testing that we correctly sort by gender, then
            last name ascending."
    (is (= (sort-by-gender-then-last-name-ascending example-records)
					 [{:last "Earhart"
						 :first "Amelia"
						 :gender "female"
						 :fav-color "blue"
						 :dob (t/local-date 1897 7 24)}
						{:last "Lovelace"
						 :first "Ada"
						 :gender "female"
						 :fav-color "purple"
						 :dob (t/local-date 1815 12 10)}
						{:last "Randall"
						 :first "Lisa"
						 :gender "female"
						 :fav-color "orange"
						 :dob (t/local-date 1962 6 18)}
						{:last "Collazo"	
						 :first "Christopher"
						 :gender "male"
						 :fav-color "green"
						 :dob (t/local-date 1991 6 21)}
						{:last "Ritchie"
						 :first "Dennis"
						 :gender "male"
						 :fav-color "yellow"
						 :dob (t/local-date 1941 9 9)}
						{:last "Washington"
						 :first "George"
						 :gender "male"
						 :fav-color "red"
						 :dob (t/local-date 1732 2 22)}]))
		(is (= (sort-by-gender-then-last-name-ascending nil) '()))))

(deftest sort-by-dob-ascending-test
  (testing "Testing that we correctly sort by birth date, ascending."
    (is (= (sort-by-dob-ascending example-records)
					 [{:last "Washington"
						 :first "George"
						 :gender "male"
						 :fav-color "red"
						 :dob (t/local-date 1732 2 22)}
						{:last "Lovelace"
						 :first "Ada"
						 :gender "female"
						 :fav-color "purple"
						 :dob (t/local-date 1815 12 10)}
						{:last "Earhart"
						 :first "Amelia"
						 :gender "female"
						 :fav-color "blue"
						 :dob (t/local-date 1897 7 24)}
						{:last "Ritchie"
						 :first "Dennis"
						 :gender "male"
						 :fav-color "yellow"
						 :dob (t/local-date 1941 9 9)}
						{:last "Randall"
						 :first "Lisa"
						 :gender "female"
						 :fav-color "orange"
						 :dob (t/local-date 1962 6 18)}
						{:last "Collazo"	
						 :first "Christopher"
						 :gender "male"
						 :fav-color "green"
						 :dob (t/local-date 1991 6 21)}]))
		(is (= (sort-by-dob-ascending nil) '()))))
