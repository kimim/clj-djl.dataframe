(ns clj-djl.dataset
  (:require
   [clj-djl.ndarray :as nd]
   [tech.v3.dataset :as ds]))

(defn ->ndarray
  "Convert dataset to NDArray"
  [ndm dataset]
  (nd/t (nd/create ndm (map vec (ds/columns dataset)))))
