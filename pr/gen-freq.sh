#!/bin/bash

rm -rf ngram-freq
rm -rf misc

if [ -d "../preprocessor/ngrams/" ]; then
    
    if [ ! -d "./ngram-freq/" ]; then
        mkdir ngram-freq
    fi
    
    if [ ! -d "./misc/" ]; then
        mkdir misc
    fi
    
    prDir="../../pr/ngram-freq/"

    cd ../preprocessor/ngrams/

    files=(./*.txt)

    for ((i=0; i < ${#files[@]} ; i++)); do 
        fileName="$(cut -d'/' -f2 <<< ${files[$i]})"
        sort ${files[$i]} | uniq -c | sort -nr > "${prDir}freq-${fileName}"
    done

    cat train-negative-1.txt train-neutral-1.txt train-positive-1.txt > "${prDir}all.txt"
    sort "${prDir}all.txt" | uniq -c | sort -nr > "${prDir}freq-all.txt"
    #wc -l "${prDir}all.txt"

    cd ../../pr/

    files=(./ngram-freq/*.txt)

    echo "" > "./misc/wc.txt"
    echo "" > "./misc/freq.txt"
    
    for ((i=0; i < ${#files[@]} ; i++)); do
        wc -l ${files[$i]} >> "./misc/wc.txt"
        echo "${files[$i]}: " >> "./misc/freq.txt"
        awk '{SUM+=$1}END{print SUM}' ${files[$i]} >> "./misc/freq.txt"
    done
    
    sort "./tfidf-out/tfidf-scores.txt" | sort -nr > "./tfidf-out/tfidf-scores-sorted.txt"

fi







