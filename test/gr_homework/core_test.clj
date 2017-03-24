(ns gr-homework.core-test
  (:use gr-homework.core
        gr-homework.sorting)
  (:require [clojure.test :refer :all]
            [clj-time.core :as t]
            [ring.util.response :refer [response not-found]]
            [cheshire.core :as json]))

;; A sample set of records, these are also used in other test namespaces
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

(deftest records-response-test
  (testing "Ensuring our GET responses are consistent with the state
            of the records atom."
    (is (= (records-response sort-by-dob-ascending)
           (response "No records stored.\n")))
    (reset! records example-records)
    (is (= (:body (records-response sort-by-dob-ascending))
           (slurp "res/birthdate.json")))
    (is (= (:body (records-response sort-by-last-name-ascending))
           (slurp "res/name.json")))
    (is (= (:body (records-response sort-by-gender-then-last-name-ascending))
           (slurp "res/gender.json")))))

(deftest records-response-test
  (testing "Ensuring our POST action responses are consistent."
    (is (= (:body (post-record "Ritchie|Dennis|male|yellow|09-09-1941"))
           "Added record for Dennis Ritchie.\n"))
    (is (= (:body (post-record "Randall Lisa female orange 06-18-1962"))
           "Added record for Lisa Randall.\n"))
    (is (= (:body (post-record "Washington,George,male,red,02-22-1732"))
           "Added record for George Washington.\n"))
    (is (= (:status (post-record "Randall;Lisa;female;orange;06-18-1962"))
           500))
    (is (= (:status (post-record "RandallLisafemaleorange06-18-1962"))
           500))
    (is (= (:status (post-record "Ran Dall Lisa female orange 06-18-1962"))
           500))
    (is (= (:status (post-record "Ran Dall,Lisa,female,orange,06-18-1962"))
           500))))
