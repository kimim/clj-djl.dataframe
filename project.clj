(defproject clj-djl/dataframe "0.1.2"
  :description "Dataframe for clj-djl, based on tech.ml.dataset"
  :url "http://github.com/kimim/clj-djl.dataset"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clj-djl "0.1.3"]
                 [techascent/tech.ml.dataset "5.00-beta-18"
                  :exclusions [ch.qos.logback/logback-classic]]]
  :main ^:skip-aot clj-djl.dataframe.core
  :repl-options {:init-ns clj-djl.dataframe.core}
  :profiles {:codox
             {:dependencies [[codox-theme-rdash "0.1.2"]]
              :plugins [[lein-codox "0.10.7"]]
              :codox {:project {:name "clj-djl.dataframe"}
                      :themes [:rdash]
                      :source-paths ["src"]
                      :source-uri "https://github.com/kimim/clj-djl.dataframe/blob/master/{filepath}#L{line}"
                      :output-path "docs"}}}
  :aliases {"codox" ["with-profile" "codox" "codox"]}
  :repositories [["sonatype" "https://oss.sonatype.org/content/repositories/snapshots/"]
                 ["jitpack" "https://jitpack.io"]])
