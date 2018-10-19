#!/bin/bash

#wc -l negative.txt | awk '{print $1}'

javacc *.jj

javac *.java

java PS4Tokenizer './input-data/'
