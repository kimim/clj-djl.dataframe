(ns clj-djl.dataframe.reductions-test
  (:require
   [clojure.test :refer [deftest is]]
   [clj-djl.dataframe :as df]
   [clj-djl.dataframe.reductions :as reductions]
   [clj-djl.dataframe.functional :as dfn]))

(deftest aggregate-columns-test
  (let [DF (df/dataframe
            {:department ["finance" "research" "research" "finance" "manufacture" "manufacture"]
             :date ["2020-01-01" "2020-01-01" "2020-01-02" "2020-01-02" "2020-01-01" "2020-01-02"]
             :cost [10 20 30 40 50 60]
             :revenue [20 30 40 50 60 70]}
            {:dataframe-name "cost-revenue"
             :parser-fn {:date :local-date}})
        DF-agg (-> DF
                   (reductions/aggregate-columns
                    :department
                    {:department (reductions/first-value :department)
                     :cost-sum (reductions/sum :cost)
                     :revenue-avg (reductions/mean :revenue)
                     :n-dates (reductions/count-distinct :date)})
                   (df/sort-by-column :department))
        DF-grp (-> DF
                   (df/group-by-column :department)
                   (->>
                    (map (fn [[k df]]
                           {:department k
                            :n-dates (df/row-count df)
                            :cost-sum (dfn/sum (df :cost))
                            :revenue-avg (dfn/mean (df :revenue))})))
                   (df/dataframe)
                   (df/sort-by-column :department))]
    (is (= 3 (df/row-count DF-agg)))
    (is (dfn/equals (DF-agg :n-dates)
                    (DF-grp :n-dates)))
    (is (dfn/equals (DF-agg :cost-sum)
                    (DF-grp :cost-sum)))
    (is (dfn/equals (DF-agg :revenue-avg)
                    (DF-grp :revenue-avg)))))
