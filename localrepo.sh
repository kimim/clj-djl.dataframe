#!/bin/sh
export version=0.1.1
lein uberjar
lein pom
lein localrepo install target/dataframe-$version.jar clj-djl/dataframe $version
mv pom.xml ~/.m2/repository/clj-djl/dataframe/$version/dataframe-$version.pom
