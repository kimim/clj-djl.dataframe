#!/bin/sh
export version=0.1.1
lein uberjar
lein pom
lein localrepo install target/dataset-$version.jar clj-djl/dataset $version
mv pom.xml ~/.m2/repository/clj-djl/dataset/$version/dataset-$version.pom
