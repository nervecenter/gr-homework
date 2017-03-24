(ns gr-homework.parsing-test
  (:require [clojure.test :refer :all]
            [gr-homework.parsing :refer :all]
            [clj-time.core :as t]))

(deftest determine-separator-test
  (testing "Testing that we can correctly determine separators."
    (is (= (str (determine-separator "Ritchie|Dennis|male|yellow|09-09-1941"))
           (str #"\|")))
    (is (= (str (determine-separator "Rit Chie|Dennis|male|yellow|09-09-1941"))
           (str #" ")))
    (is (= (str (determine-separator "Washington,George,male,red,02-22-1732"))
           (str #",")))
    (is (= (str (determine-separator "Randall Lisa female orange 06-18-1962"))
           (str #" ")))
    (is (thrown?
         Exception
         (determine-separator "Randall;Lisa;female;orange;06-18-1962")))
    (is (thrown?
         Exception
         (determine-separator "RandallLisafemaleorange06-18-1962")))))

(deftest parse-date-test
  (testing "Testing that we can correctly parse dates."
    (is (= (parse-date "09-09-1941")
           (t/local-date 1941 9 9)))
    (is (= (parse-date "02-22-1732")
           (t/local-date 1732 2 22)))
    (is (= (parse-date "06-18-1962")
           (t/local-date 1962 6 18)))
    (is (thrown?
         Exception
         (parse-date "09/09/1941")))
    (is (thrown?
         Exception
         (parse-date "09-1941-09")))
    (is (thrown?
         Exception
         (parse-date "1941-09-09")))))

(deftest parse-record-test
  (testing "Testing that we can correctly parse records."
    (is (= (parse-record "Ritchie|Dennis|male|yellow|09-09-1941")
           {:last "Ritchie"
            :first "Dennis"
            :gender "male"
            :fav-color "yellow"
            :dob (t/local-date 1941 9 9)}))
    (is (= (parse-record "Randall Lisa female orange 06-18-1962")
           {:last "Randall"
            :first "Lisa"
            :gender "female"
            :fav-color "orange"
            :dob (t/local-date 1962 6 18)}))
    (is (= (parse-record "Washington,George,male,red,02-22-1732")
           {:last "Washington"
            :first "George"
            :gender "male"
            :fav-color "red"
            :dob (t/local-date 1732 2 22)}))
    (is (thrown?
         Exception
         (parse-record "Randall;Lisa;female;orange;06-18-1962")))
    (is (thrown?
         Exception
         (parse-record "RandallLisafemaleorange06-18-1962")))
    (is (thrown?
         Exception
         (parse-record "Ran Dall Lisa female orange 06-18-1962")))
    (is (thrown?
         Exception
         (parse-record "Ran Dall,Lisa,female,orange,06-18-1962")))))

(deftest parse-records-file-test
  (testing "Testing that we can correctly parse a file."
    (is (= (parse-records-file "res/commas.txt")
           [{:last "Collazo"
             :first "Christopher"
             :gender "male"
             :fav-color "green"
             :dob (t/local-date 1991 6 21)}
            {:last "Washington"
             :first "George"
             :gender "male"
             :fav-color "red"
             :dob (t/local-date 1732 2 22)}]))
    (is (= (parse-records-file "res/pipes.txt")
           [{:last "Earhart"
             :first "Amelia"
             :gender "female"
             :fav-color "blue"
             :dob (t/local-date 1897 7 24)}
            {:last "Ritchie"
             :first "Dennis"
             :gender "male"
             :fav-color "yellow"
             :dob (t/local-date 1941 9 9)}]))
    (is (= (parse-records-file "res/spaces.txt")
           [{:last "Lovelace"
             :first "Ada"
             :gender "female"
             :fav-color "purple"
             :dob (t/local-date 1815 12 10)}
            {:last "Randall"
             :first "Lisa"
             :gender "female"
             :fav-color "orange"
             :dob (t/local-date 1962 6 18)}]))
    (is (thrown?
         Exception
         (parse-records-file "res/semicolons.txt")))))

(deftest parse-multiple-records-files-test
  (testing "Testing that we can correctly parse multiple files."
    (is (= (parse-multiple-records-files ["res/commas.txt"
                                          "res/pipes.txt"
                                          "res/spaces.txt"])
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
             :dob (t/local-date 1962 6 18)}]))
    (is (thrown?
         Exception
         (parse-multiple-records-files ["res/semicolons.txt"
                                        "res/slashes.txt"
                                        "res/pipes.txt"])))))
