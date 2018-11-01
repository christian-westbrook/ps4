This folder contains a script that can be used to generate various text, such as ngrams.

The build.sh script compiles the entire project.

The run.sh script execute the project in debug mode. 

In debug mode, the program write the ouput of the mapbuilder, specifically ngrams, to disk.

After running the run.sh script, you can run gen-freq.sh to generate frequencies of the testoutput. This script also sorts the tf-idf scores.

In order to see the probability output for a sentence, you need to enter some input as a command argument.
