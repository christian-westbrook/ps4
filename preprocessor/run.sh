#!/bin/bash

javac *.java

java MapBuilder

java FeatureBuilder

#sort -nr tf-idf.txt > tf-idf-sorted.txt
