(ns clj-djl.dataframe.update-columns-test
  (:require [clj-djl.dataframe :as df]
            [clj-djl.dataframe.column-filters :as cf]
            [clj-djl.dataframe.functional :as dfn]
            [clojure.test :refer [deftest is]]))

(deftest update-columns-selector-fn
  (let [df (df/->dataframe {:a [1. 2. 3. 4.]
                            :b [5 6 7 8]
                            :c ["A" "B" "C" "D"]})
        df' (-> df
                (df/update-columns cf/numeric
                                   #(dfn// (dfn/- % (dfn/mean %))
                                           (dfn/standard-deviation %))))]
    (is (> 0.001 (Math/abs (reduce + (map - [-1.16189 -0.38729 0.38729 1.16189] (vec (df' :a)))))))
    (is (> 0.001 (Math/abs (reduce + (map - [-1.16189 -0.38729 0.38729 1.16189] (vec (df' :b)))))))
    (is (= ["A" "B" "C" "D"] (vec (df' :c)))))

  (let [df (df/->dataframe {:a [1. 2. 3. 4.]
                            :b [5 6 7 8]
                            :c ["A" "B" "C" "D"]})
        df' (as-> df $
              (df/update-columns $ (df/column-names (cf/numeric $))
                                 #(dfn// (dfn/- % (dfn/mean %))
                                         (dfn/standard-deviation %)))
                )]
    (is (> 0.001 (Math/abs (reduce + (map - [-1.16189 -0.38729 0.38729 1.16189] (vec (df' :a)))))))
    (is (> 0.001 (Math/abs (reduce + (map - [-1.16189 -0.38729 0.38729 1.16189] (vec (df' :b)))))))
    (is (= ["A" "B" "C" "D"] (vec (df' :c))))))
