;; Copyright © 2017 Chris Collazo

(ns gr-homework.printing-test
  (:require [clojure.test :refer :all]
            [gr-homework.printing :refer :all]
            [gr-homework.core-test :refer [example-records]]
            [clj-time.core :as t])) 

(deftest date-str-test
  (testing "Testing if we format date strings correctly and consistently."
    (is (= (date-str (t/local-date 1948 12 22)) "12/22/1948"))
    (is (= (date-str (t/local-date 763  6  9))  "6/9/763"))
    (is (= (date-str (t/local-date 2245 1  30)) "1/30/2245"))))

(deftest record-str-test
  (testing "Testing if we format record strings correctly and consistently."
    (is (= (record-str (example-records 5))
           (str "\nFirst name:      Lisa"
                "\nLast name:       Randall"
                "\nGender:          female"
                "\nFavorite color:  orange"
                "\nDate of birth:   6/18/1962\n")))
    (is (= (record-str (example-records 1))
           (str "\nFirst name:      George"
                "\nLast name:       Washington"
                "\nGender:          male"
                "\nFavorite color:  red"
                "\nDate of birth:   2/22/1732\n")))))
  
(deftest multiple-records-str-test
  (testing "Testing if we format several record strings
            correctly and consistently."
    (is (= (multiple-records-str example-records)
           (str
            "\nFirst name:      Christopher"
            "\nLast name:       Collazo"
            "\nGender:          male"
            "\nFavorite color:  green"
            "\nDate of birth:   6/21/1991\n"
            "\nFirst name:      George"
            "\nLast name:       Washington"
            "\nGender:          male"
            "\nFavorite color:  red"
            "\nDate of birth:   2/22/1732\n"
            "\nFirst name:      Amelia"
            "\nLast name:       Earhart"
            "\nGender:          female"
            "\nFavorite color:  blue"
            "\nDate of birth:   7/24/1897\n"
            "\nFirst name:      Dennis"
            "\nLast name:       Ritchie"
            "\nGender:          male"
            "\nFavorite color:  yellow"
            "\nDate of birth:   9/9/1941\n"
            "\nFirst name:      Ada"
            "\nLast name:       Lovelace"
            "\nGender:          female"
            "\nFavorite color:  purple"
            "\nDate of birth:   12/10/1815\n"
            "\nFirst name:      Lisa"
            "\nLast name:       Randall"
            "\nGender:          female"
            "\nFavorite color:  orange"
            "\nDate of birth:   6/18/1962\n"
            )))))

(deftest output1-test
  (testing "Testing if we format records strings by gender
            correctly and consistently."
    (is (= (output1 example-records)
           (str
            "\nSorted by gender (female first), last name ascending:\n"
            "\nFirst name:      Amelia"
            "\nLast name:       Earhart"
            "\nGender:          female"
            "\nFavorite color:  blue"
            "\nDate of birth:   7/24/1897\n"
            "\nFirst name:      Ada"
            "\nLast name:       Lovelace"
            "\nGender:          female"
            "\nFavorite color:  purple"
            "\nDate of birth:   12/10/1815\n"
            "\nFirst name:      Lisa"
            "\nLast name:       Randall"
            "\nGender:          female"
            "\nFavorite color:  orange"
            "\nDate of birth:   6/18/1962\n"
            "\nFirst name:      Christopher"
            "\nLast name:       Collazo"
            "\nGender:          male"
            "\nFavorite color:  green"
            "\nDate of birth:   6/21/1991\n"
            "\nFirst name:      Dennis"
            "\nLast name:       Ritchie"
            "\nGender:          male"
            "\nFavorite color:  yellow"
            "\nDate of birth:   9/9/1941\n"
            "\nFirst name:      George"
            "\nLast name:       Washington"
            "\nGender:          male"
            "\nFavorite color:  red"
            "\nDate of birth:   2/22/1732\n"
            )))))

(deftest output2-test
  (testing "Testing if we format records strings by birth date
            correctly and consistently."
    (is (= (output2 example-records)
           (str
            "\nSorted by birth date, ascending:\n"
            "\nFirst name:      George"
            "\nLast name:       Washington"
            "\nGender:          male"
            "\nFavorite color:  red"
            "\nDate of birth:   2/22/1732\n"
            "\nFirst name:      Ada"
            "\nLast name:       Lovelace"
            "\nGender:          female"
            "\nFavorite color:  purple"
            "\nDate of birth:   12/10/1815\n"
            "\nFirst name:      Amelia"
            "\nLast name:       Earhart"
            "\nGender:          female"
            "\nFavorite color:  blue"
            "\nDate of birth:   7/24/1897\n"
            "\nFirst name:      Dennis"
            "\nLast name:       Ritchie"
            "\nGender:          male"
            "\nFavorite color:  yellow"
            "\nDate of birth:   9/9/1941\n"
            "\nFirst name:      Lisa"
            "\nLast name:       Randall"
            "\nGender:          female"
            "\nFavorite color:  orange"
            "\nDate of birth:   6/18/1962\n"
            "\nFirst name:      Christopher"
            "\nLast name:       Collazo"
            "\nGender:          male"
            "\nFavorite color:  green"
            "\nDate of birth:   6/21/1991\n"
            )))))

(deftest output3-test
  (testing "Testing if we format records strings by last name,
            descending correctly and consistently."
    (is (= (output3 example-records)
           (str
            "\nSorted by last name, descending:\n"
            "\nFirst name:      George"
            "\nLast name:       Washington"
            "\nGender:          male"
            "\nFavorite color:  red"
            "\nDate of birth:   2/22/1732\n"
            "\nFirst name:      Dennis"
            "\nLast name:       Ritchie"
            "\nGender:          male"
            "\nFavorite color:  yellow"
            "\nDate of birth:   9/9/1941\n"
            "\nFirst name:      Lisa"
            "\nLast name:       Randall"
            "\nGender:          female"
            "\nFavorite color:  orange"
            "\nDate of birth:   6/18/1962\n"
            "\nFirst name:      Ada"
            "\nLast name:       Lovelace"
            "\nGender:          female"
            "\nFavorite color:  purple"
            "\nDate of birth:   12/10/1815\n"
            "\nFirst name:      Amelia"
            "\nLast name:       Earhart"
            "\nGender:          female"
            "\nFavorite color:  blue"
            "\nDate of birth:   7/24/1897\n"
            "\nFirst name:      Christopher"
            "\nLast name:       Collazo"
            "\nGender:          male"
            "\nFavorite color:  green"
            "\nDate of birth:   6/21/1991\n"
            )))))

(deftest three-outputs-str-test
  (testing "Testing if we format all three outputs
            correctly and consistently."
    (is (= (three-outputs-str example-records)
           (str
            "\nSorted by gender (female first), last name ascending:\n"
            "\nFirst name:      Amelia"
            "\nLast name:       Earhart"
            "\nGender:          female"
            "\nFavorite color:  blue"
            "\nDate of birth:   7/24/1897\n"
            "\nFirst name:      Ada"
            "\nLast name:       Lovelace"
            "\nGender:          female"
            "\nFavorite color:  purple"
            "\nDate of birth:   12/10/1815\n"
            "\nFirst name:      Lisa"
            "\nLast name:       Randall"
            "\nGender:          female"
            "\nFavorite color:  orange"
            "\nDate of birth:   6/18/1962\n"
            "\nFirst name:      Christopher"
            "\nLast name:       Collazo"
            "\nGender:          male"
            "\nFavorite color:  green"
            "\nDate of birth:   6/21/1991\n"
            "\nFirst name:      Dennis"
            "\nLast name:       Ritchie"
            "\nGender:          male"
            "\nFavorite color:  yellow"
            "\nDate of birth:   9/9/1941\n"
            "\nFirst name:      George"
            "\nLast name:       Washington"
            "\nGender:          male"
            "\nFavorite color:  red"
            "\nDate of birth:   2/22/1732\n"
            "\nSorted by birth date, ascending:\n"
            "\nFirst name:      George"
            "\nLast name:       Washington"
            "\nGender:          male"
            "\nFavorite color:  red"
            "\nDate of birth:   2/22/1732\n"
            "\nFirst name:      Ada"
            "\nLast name:       Lovelace"
            "\nGender:          female"
            "\nFavorite color:  purple"
            "\nDate of birth:   12/10/1815\n"
            "\nFirst name:      Amelia"
            "\nLast name:       Earhart"
            "\nGender:          female"
            "\nFavorite color:  blue"
            "\nDate of birth:   7/24/1897\n"
            "\nFirst name:      Dennis"
            "\nLast name:       Ritchie"
            "\nGender:          male"
            "\nFavorite color:  yellow"
            "\nDate of birth:   9/9/1941\n"
            "\nFirst name:      Lisa"
            "\nLast name:       Randall"
            "\nGender:          female"
            "\nFavorite color:  orange"
            "\nDate of birth:   6/18/1962\n"
            "\nFirst name:      Christopher"
            "\nLast name:       Collazo"
            "\nGender:          male"
            "\nFavorite color:  green"
            "\nDate of birth:   6/21/1991\n"
            "\nSorted by last name, descending:\n"
            "\nFirst name:      George"
            "\nLast name:       Washington"
            "\nGender:          male"
            "\nFavorite color:  red"
            "\nDate of birth:   2/22/1732\n"
            "\nFirst name:      Dennis"
            "\nLast name:       Ritchie"
            "\nGender:          male"
            "\nFavorite color:  yellow"
            "\nDate of birth:   9/9/1941\n"
            "\nFirst name:      Lisa"
            "\nLast name:       Randall"
            "\nGender:          female"
            "\nFavorite color:  orange"
            "\nDate of birth:   6/18/1962\n"
            "\nFirst name:      Ada"
            "\nLast name:       Lovelace"
            "\nGender:          female"
            "\nFavorite color:  purple"
            "\nDate of birth:   12/10/1815\n"
            "\nFirst name:      Amelia"
            "\nLast name:       Earhart"
            "\nGender:          female"
            "\nFavorite color:  blue"
            "\nDate of birth:   7/24/1897\n"
            "\nFirst name:      Christopher"
            "\nLast name:       Collazo"
            "\nGender:          male"
            "\nFavorite color:  green"
            "\nDate of birth:   6/21/1991\n"
            )))))
