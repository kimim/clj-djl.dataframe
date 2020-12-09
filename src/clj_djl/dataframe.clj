(ns clj-djl.dataframe
  (:require
   [clj-djl.ndarray :as nd]
   [tech.v3.dataset :as ds]
   [tech.v3.datatype.export-symbols :refer [export-symbols]])
  (:refer-clojure
   :exclude
   [filter group-by sort-by concat take-nth shuffle rand-nth update]))

(export-symbols tech.v3.dataset
                row-count
                column-count
                column
                columns
                column-names
                has-column?
                columns-with-missing-seq
                add-column
                new-column
                remove-column
                remove-columns
                drop-columns
                update-column
                order-column-names
                update-columns
                rename-columns
                select
                select-by-index
                unordered-select
                select-columns
                select-columns-by-index
                select-rows
                select-rows-by-index
                drop-rows
                remove-rows
                missing
                drop-missing
                add-or-update-column
                assoc-ds
                group-by->indexes
                group-by-column->indexes
                group-by
                group-by-column
                sort-by
                sort-by-column
                filter
                filter-column
                unique-by
                unique-by-column
                concat
                concat-copying
                concat-inplace
                take-nth
                ensure-array-backed
                brief
                categorical->one-hot
                replace-missing
                head
                tail)

(def ->dataframe ds/->dataset)
(def dataframe-name ds/dataset-name)
(def set-dataframe-name ds/set-dataset-name)
(def dataframe->data ds/dataset->data)
(def data->dataframe ds/data->dataset)

(defn ->ndarray
  "Convert dataframe to NDArray"
  [ndm dataframe]
  (nd/t (nd/create ndm (map vec (ds/columns dataframe)))))

;; because ndarray shape use row index first, col index next.
;; we'd better to map dataset shape to ndarray shape in this lib

(defn shape
  "Get the shape of dataset, row count first"
  [dataframe]
  (vec (reverse (ds/shape dataframe))))

(defn select-by-index
  [dataframe row-index col-index]
  (ds/select-by-index dataframe col-index row-index))

(defn replace-missing
  "Replace missing with:

  - builtin strategys: `:mid` `:up` `:down` and `:lerp`
  - value
  - or column function with missing slot dropped
  "
  ([df]
   (ds/replace-missing df))
  ([df strategy]
   (cond
     ;; one of the builtin strategies
     (contains? #{:mid :up :down :lerp} strategy)
     (ds/replace-missing df strategy)
     :else
     (ds/replace-missing df :all :value strategy)))
  ([df col-sel strategy]
   (cond
     ;; one of the builtin strategies
     (contains? #{:mid :up :down :lerp} strategy)
     (ds/replace-missing df col-sel strategy)
     :else
     (ds/replace-missing df col-sel :value strategy))))
