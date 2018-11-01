#!/bin/bash

rm -rf data
rm -rf ngrams

javac *.java

java MapBuilder $1

java FeatureBuilder

#sort -nr tf-idf.txt > tf-idf-sorted.txt
