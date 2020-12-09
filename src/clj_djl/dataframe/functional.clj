(ns clj-djl.dataframe.functional
  (:require
   [tech.v3.datatype.export-symbols :refer [export-symbols]])
  (:refer-clojure
   :exclude
   [+ - * /]))

(export-symbols tech.v3.datatype.functional
                +
                -
                *
                /
                mean
                standard-deviation)

(def std standard-deviation)
