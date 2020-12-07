(ns clj-djl.dataset
  (:require
   [clj-djl.ndarray :as nd]
   [tech.v3.dataset :as ds]
   [tech.v3.datatype.export-symbols :refer [export-symbols]])
  (:refer-clojure
   :exclude
   [filter group-by sort-by concat take-nth shuffle rand-nth update]))

(export-symbols tech.v3.dataset
                ->dataset
                dataset-name
                set-dataset-name
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
                dataset->data
                data->dataset
                brief
                categorical->one-hot
                replace-missing
                head
                tail)

(defn ->ndarray
  "Convert dataset to NDArray"
  [ndm dataset]
  (nd/t (nd/create ndm (map vec (ds/columns dataset)))))

;; because ndarray shape use row index first, col index next.
;; we'd better to map dataset shape to ndarray shape in this lib

(defn shape
  "Get the shape of dataset, row count first"
  [dataset]
  (vec (reverse (ds/shape dataset))))

(defn select-by-index
  [dataset row-index col-index]
  (ds/select-by-index dataset col-index row-index))
