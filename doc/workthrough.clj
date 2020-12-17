(ns workthrough
  (:require [notespace.api :as notespace]
            [notespace.kinds :as kind]))

;; Notespace
^kind/hidden
(comment
  ;; Manually start an empty notespace
  (notespace/init-with-browser)
  ;; Renders the notes and listens to file changes
  (notespace/listen)
  ;; Clear an existing notespace browser
  (notespace/init)
  ;; Evaluating a whole notespace
  (notespace/eval-this-notespace)
  ;; Generate static html
  (notespace/render-static-html (str "docs/"
                                     (clojure.string/replace
                                      (last (clojure.string/split (str *ns*) #"\."))
                                      "-" "_")
                                     ".html")))

["# Dataset manipulation with clj-djl.dataframe"]
["## Introduction"]

(require '[clj-djl.dataframe :as df])
(def DF (df/->dataframe {:V1 (take 9 (cycle [1 2]))
                         :V2 (range 1 10)
                         :V3 (take 9 (cycle [0.5 1.0 1.5]))
                         :V4 (take 9 (cycle ["A" "B" "C"]))}))
^kind/dataset-grid
DF

["## Functionality"]

["### Dataframe"]

["Dataframe is a special type which can be considered as a map of columns
implemented around tech.ml.datatype library. Each column can be considered as
named sequence of typed data. Supported types include integers, floats, string,
boolean, date/time, objects etc."]

["#### Dataframe creation"]

["Dataset can be created from various of types of Clojure structures and files:
    single values
    sequence of maps
    map of sequences or values
    sequence of columns (taken from other dataset or created manually)
    sequence of pairs
    file types: raw/gzipped csv/tsv, json, xls(x) taken from local file system or URL
    input stream"]

["Dataset from single value."]

^kind/dataset-grid
(df/->dataframe {:A [999]})

["Set column name for single value. Also set the dataset name with optional
`:dataframe-name`."]

^kind/dataset-grid
(df/->dataframe {:A [999]} {:dataframe-name "Single Value"})

["Map of columns (first = column name, second = value(s))."]

^kind/dataset-grid
(df/->dataframe {:A [33] :B [5] :C [:a]})

["Sequence of maps:"]

^kind/dataset-grid
(df/->dataframe [{:A 1 :B "X" :C :a}
                 {:A 2 :B "X" :C :a}
                 {:A 3 :B "X" :C :a}
                 {:A 4 :B "X" :C :a}
                 {:A 5 :B "X" :C :a}
                 {:A 6 :B "X" :C :a}])

["You can put any value inside a column"]

^kind/dataset
(df/->dataframe {:A [[3 4 5] [:a :b]] :B "X"})

["Missing values are marked by nil"]

^kind/dataset
(df/->dataframe [{:a nil :b 1} {:a 3 :b 4} {:a 11}])

["Import CSV file"]

(defonce weather
  (df/->dataframe "https://vega.github.io/vega-lite/examples/data/seattle-weather.csv"))

^kind/dataset
weather

["#### Saving"]

["#### Dataset related functions"]

["Summary functions about the dataset like number of rows, columns and basic
stats."]

["Number of rows"]

(df/row-count weather)

["Number of columns"]

(df/column-count weather)

["Shape of the dataset, [row count, column count]"]

(df/shape weather)

["General info about dataset. There are three variants:

    default - containing information about columns with basic statistics
   :basic - just name, row and column count and information if dataset is a result of group-by operation
   :columns - columns’ metadata"]

^kind/dataset
(df/descriptive-stats weather)

^kind/dataset
(df/descriptive-stats weather {:stat-names [:col-name :mean :min :max :values]})

^kind/dataset
(df/descriptive-stats weather {:stat-names [:col-name :mean :min :max :values]
                               :n-categorical-values 2})

["General info in another format:"]

(df/brief weather)

["Getting a dataset name"]

(df/dataframe-name weather)

["Setting a dataset name (operation is immutable)."]

(->> "seattle-weather"
     (df/set-dataframe-name weather)
     (df/dataframe-name))

["#### Columns and rows"]

["Get columns and rows as sequences. column, columns and rows treat grouped
dataset as regular one. See Groups to read more about grouped datasets."]

["Select column."]

(weather "wind")
(df/column weather "date")

["Columns as sequence"]

(take 2 (df/columns weather))

["Columns as map"]

(keys weather)
(df/column-names weather)

(= (keys weather)
   (df/column-names weather))

["`vals` gets seq of columns, it is same as `columns`"]

(vals weather)
(df/columns weather)

(= (df/columns weather)
   (vals weather))

["Rows as sequence of maps"]

(take 2 (df/mapseq-reader weather))

["#### Printing"]

;; TODO

["### Group by"]

["https://scicloj.github.io/tablecloth/index.html#Group-by"]

["Grouping by is an operation which splits dataset into subdatasets and pack it
into new special type of… dataset. I distinguish two types of dataset: regular
dataset and grouped dataset. The latter is the result of grouping."]

["Grouped dataset is annotated in by :grouped? meta tag and consist following
columns:

- :name - group name or structure
- :group-id - integer assigned to the group
- :data - groups as datasets"]

["Almost all functions recognize type of the dataset (grouped or not) and
operate accordingly.

You can't apply reshaping or join/concat functions on grouped datasets."]

["### Grouping"]

["Grouping is done by calling group-by function with arguments:

    ds - dataset
    grouping-selector - what to use for grouping
    options:
        :result-type - what to return:
            :as-dataset (default) - return grouped dataset
            :as-indexes - return rows ids (row number from original dataset)
            :as-map - return map with group names as keys and subdataset as values
            :as-seq - return sequens of subdatasets
        :select-keys - list of the columns passed to a grouping selector function

All subdatasets (groups) have set name as the group name, additionally group-id is in meta.

Grouping can be done by:

    single column name
    seq of column names
    map of keys (group names) and row indexes
    value returned by function taking row as map (limited to :select-keys)

Note: currently dataset inside dataset is printed recursively so it renders poorly from markdown. So I will use :as-seq result type to show just group names and groups."]

["List of columns in grouped dataset"]

DF

(df/group-by-column DF :V1)

["List of columns in grouped dataset treated as regular dataset"]

;; TODO

["Content of the grouped dataset"]

;; TODO

["Grouped dataset as map"]

(keys (df/group-by-column DF :V1))

(vals (df/group-by-column DF :V1))

["To get groups as sequence or a map can be done from grouped dataset using groups->seq and groups->map functions.

Groups as seq can be obtained by just accessing :data column."]

["Group as map:"]

(-> {"a" [1 1 2 2]
     "b" ["a" "b" "c" "d"]}
    (df/->dataframe)
    (df/group-by-column "a"))

["Group as seq:"]
(-> {"a" [1 1 2 2]
     "b" ["a" "b" "c" "d"]}
    (df/->dataframe)
    (df/group-by-column "a")
    vals)

["Grouping by more than one column. You can see that group names are maps. When
ungrouping is done these maps are used to restore column names."]

(df/group-by DF (juxt :V1 :V3))

["Grouping can be done by providing just row indexes. This way you can assign
the same row to more than one group."]


(df/group-by-column->indexes DF :V1)


(df/group-by DF #(> 5 (:V2 %)))
