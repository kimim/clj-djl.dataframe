(ns clj-djl.core-test
  (:require
   [clojure.test :refer :all]
   [clj-djl.ndarray :as nd]
   [clj-djl.dataset :as ds]))

(deftest ->ndarray-test
  (with-open [ndm (nd/new-base-manager)]
    (let [DS (ds/->dataset [{:A 1 :B 2} {:A 3 :B 4}])
          array (ds/->ndarray ndm DS)
          expected (nd/create ndm [[1 2] [3 4]])]
      (is (= array expected)))))
