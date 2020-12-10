(ns clj-djl.dataframe-test
  (:require
   [clojure.test :refer :all]
   [clj-djl.ndarray :as nd]
   [clj-djl.dataframe :as df]))

(deftest dataframe->ndarray-test
  (with-open [ndm (nd/new-base-manager)]
    (let [DS (df/->dataframe [{:A 1 :B 2} {:A 3 :B 4}])
          array (df/->ndarray ndm DS)
          expected (nd/create ndm [[1 2] [3 4]])]
      (is (= array expected)))))

(deftest shape-test
  (let [DS (df/->dataframe [{:A 1 :B 2 :C 3} {:A 4 :B 5 :C 6}])
        shape- (df/shape DS)
        expected [2 3]]
    (is (= shape- expected))))


(deftest ndarray->dataframe-test
  (with-open [ndm (nd/new-base-manager)]
    (let [nd (nd/create ndm [[1 2 3] [4 5 6]])
          df (df/->dataframe nd)]
      (is (= [1 4] (vec (df 0))))
      (is (= [2 5] (vec (df 1))))
      (is (= [3 6] (vec (df 2)))))
    (let [nd (nd/create ndm [1 2 3])]
      (is (thrown? java.lang.IllegalArgumentException
                   (df/->dataframe nd))))
    (let [nd (nd/create ndm [[[1 2 3] [1 2 3]] [[1 2 3] [1 2 3]]])]
      (is (thrown? java.lang.IllegalArgumentException
                   (df/->dataframe nd))))))

(comment
  (def ndm (nd/new-base-manager))
  (def DS (df/->dataframe [{:A 1 :B 2 :C 3} {:A 3 :B 4 :C 5}]))
  (df/shape DS)
  (def array (df/->ndarray ndm DS))
  (def expected (nd/create ndm [[1 2] [3 4]])))
