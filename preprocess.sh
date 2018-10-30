#!/bin/bash

cd preprocessor
cd tokenizer

./run.sh

cd ..

./run.sh

cd ..

cd nb

javac -cp '../' *.java

cd ..

cd lr

javac -cp '../' *.java

cd ..

cd pr

javac -cp '../' *.java