(ns clj-djl.dataframe.missing-test
  (:require
   [clojure.test :refer [deftest is]]
   [clj-djl.dataframe :as ds]))

(deftest replace-missing-test
  ;; error?
  #_(let [ds (ds/->dataset {:a [nil 2 nil 4 nil 6 nil]
                          :b [3 nil 6 nil 9 nil 12]
                          :c [nil "A" nil "B" nil "C" nil]
                          :d ["A" nil "B" nil "C" nil "D"]
                          })
        ds' (ds/replace-missing ds :mid)]
    (is (= [2 2 3 4 5 6 6] (vec (ds' :a))))
    (is (= [3 5 6 8 9 11 12] (vec (ds' :b))))
    (is (= ["B" "B" "B" "C" "C" "C"] (vec (ds' :c))))
    ds')
  #_(let [ds (ds/->dataset {:a [nil 2. nil 4. nil 6. nil]
                          :b [3. nil 6. nil 9. nil 12.]
                          :c [nil "A" nil "B" nil "C" nil]
                          :d ["A" nil "B" nil "C" nil "D"]
                          })
        ds' (ds/replace-missing ds :mid)]
    (is (= [2 2 3 4 5 6 6] (vec (ds' :a))))
    (is (= [3 4.5 6 7.5 9 10.5 12] (vec (ds' :b))))
    (is (= ["B" "B" "B" "C" "C" "C"] (vec (ds' :c))))
    ds')
  (let [ds (ds/->dataset {:a [nil 2 nil 4 nil 6 nil]
                          :b [3 nil 6 nil 9 nil 12]
                          :c [nil "A" nil "B" nil "C" nil]
                          :d ["A" nil "B" nil "C" nil "D"]
                          })
        ds' (ds/replace-missing ds :up)]
    (is (= [2 2 4 4 6 6 6] (vec (ds' :a))))
    (is (= [3 6 6 9 9 12 12] (vec (ds' :b))))
    (is (= ["A" "A" "B" "B" "C" "C" "C"] (vec (ds' :c))))
    (is (= ["A" "B" "B" "C" "C" "D" "D"] (vec (ds' :d))))
    ds')
  (let [ds (ds/->dataset {:a [nil 2 nil 4 nil 6 nil]
                          :b [3 nil 6 nil 9 nil 12]
                          :c [nil "A" nil "B" nil "C" nil]
                          :d ["A" nil "B" nil "C" nil "D"]
                          })
        ds' (ds/replace-missing ds :down)]
    (is (= [2 2 2 4 4 6 6] (vec (ds' :a))))
    (is (= [3 3 6 6 9 9 12] (vec (ds' :b))))
    (is (= ["A" "A" "A" "B" "B" "C" "C"] (vec (ds' :c))))
    (is (= ["A" "A" "B" "B" "C" "C" "D"] (vec (ds' :d))))
    ds')
  (let [ds (ds/->dataset {:a [nil 2 nil 4 nil 6 nil]
                          :b [3 nil 6 nil 9 nil 12]
                          :c [nil "A" nil "B" nil "C" nil]
                          :d ["A" nil "B" nil "C" nil "D"]
                          })
        ds' (ds/replace-missing ds :lerp)]
    (is (= [2 2 3 4 5 6 6] (vec (ds' :a))))
    (is (= [3 5 6 8 9 11 12] (vec (ds' :b))))
    ;; :lerp for categorical, is same as :down
    (is (= ["A" "A" "A" "B" "B" "C" "C"] (vec (ds' :c))))
    (is (= ["A" "A" "B" "B" "C" "C" "D"] (vec (ds' :d))))
    ds')
  (let [ds (ds/->dataset {:a [nil 2. nil 4. nil 6. nil]
                          :b [3. nil 6. nil 9. nil 12.]
                          :c [nil "A" nil "B" nil "C" nil]
                          :d ["A" nil "B" nil "C" nil "D"]
                          })
        ds' (ds/replace-missing ds :lerp)]
    (is (= [2.0 2.0 3.0 4.0 5.0 6.0 6.0] (vec (ds' :a))))
    (is (= [3.0 4.5 6.0 7.5 9.0 10.5 12.0] (vec (ds' :b))))
    ;; :lerp for categorical, is same as :down
    (is (= ["A" "A" "A" "B" "B" "C" "C"] (vec (ds' :c))))
    (is (= ["A" "A" "B" "B" "C" "C" "D"] (vec (ds' :d))))
    ds'))