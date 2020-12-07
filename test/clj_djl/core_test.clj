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

(deftest shape-test
  (let [DS (ds/->dataset [{:A 1 :B 2 :C 3} {:A 4 :B 5 :C 6}])
        shape- (ds/shape DS)
        expected [2 3]]
    (is (= shape- expected))))

(comment
  (def ndm (nd/new-base-manager))
  (def DS (ds/->dataset [{:A 1 :B 2 :C 3} {:A 3 :B 4 :C 5}]))
  (ds/shape DS)
  (def array (ds/->ndarray ndm DS))
  (def expected (nd/create ndm [[1 2] [3 4]])))
