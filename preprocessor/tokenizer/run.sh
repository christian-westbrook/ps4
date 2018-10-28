#!/bin/bash

#wc -l negative.txt | awk '{print $1}'

javacc *.jj

javac *.java

java TFIDF

java PS4Tokenizer './tfidf-data/'
