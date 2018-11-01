#!/bin/bash

rm -rf test-output

cd ..

cd preprocessor
cd tokenizer

./run.sh '13'

cd ..

./run.sh '13'

cd ..

cd nb

javac -cp '../' *.java

cd ..

cd lr

javac -cp '../' *.java

cd ..

cd pr

javac -cp '../' *.java
