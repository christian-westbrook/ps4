#!/bin/bash

#wc -l negative.txt | awk '{print $1}'

rm -rf clean-data
rm -rf stats
rm -rf test-data
rm -rf tfidf-data
rm -rf train-data

javacc *.jj

javac *.java

java PS4Tokenizer './input-data/'

java TFIDF $1
