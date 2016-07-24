#!/usr/bin/env bash

mvn install:install-file -Dfile=./lib/skandium-1.0.0.jar -DgroupId=cl.niclabs -DartifactId=skandium -Dversion=1.0.0 -Dpackaging=jar
mvn install