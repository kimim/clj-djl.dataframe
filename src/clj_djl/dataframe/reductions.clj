(ns clj-djl.dataframe.reductions
  (:require
   [tech.v3.dataset.reductions :as reductions]
   [tech.v3.datatype.export-symbols :refer [export-symbols]])
  (:refer-clojure :exclude [distinct]))

(export-symbols tech.v3.dataset.reductions
                first-value
                sum
                mean
                row-count
                distinct-int32
                distinct
                count-distinct
                group-by-column-agg)

(defn aggregate-columns
  [ds-or-seq colname agg-map & [options]]
  (let [ds-seq (if (sequential? ds-or-seq) ds-or-seq [ds-or-seq])]
    (if (nil? options)
      (group-by-column-agg colname agg-map ds-seq)
      (group-by-column-agg colname agg-map options ds-seq))))
