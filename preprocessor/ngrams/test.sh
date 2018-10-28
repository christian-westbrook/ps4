#!/bin/bash

if [ ! -d "test-output" ]; then
    mkdir test-output
fi

files=(./*.txt)

for ((i=0; i < ${#files[@]} ; i++)); do 
    fileName="$(cut -d'/' -f2 <<< ${files[$i]})"
    sort ${files[$i]} | uniq -c | sort -nr > "./test-output/freq-${fileName}"
done

files=(./test-output/*.txt)

for ((i=0; i < ${#files[@]} ; i++)); do

    #printf "${files[$i]}\n"

    wc -l ${files[$i]}
    
    awk '{SUM+=$1}END{print SUM}' ${files[$i]}

done

cat train-negative-1.txt train-neutral-1.txt train-positive-1.txt > "./test-output/all.txt"
sort "./test-output/all.txt" | uniq -c | sort -nr > "./test-output/freq-all.txt"
wc -l "./test-output/freq-all.txt"
