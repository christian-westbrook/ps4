//===============================================================
// PROGRAM: TFIDF.java
// ASSIGNMENT: Problem Set 4
// CLASS: Natural Language Processing
// DATE: Nov 1 2018
// AUTHOR: Renae Fisher
// ABSTRACT: This class separates a file into test data and training data.
//  After gathering metrics & separating the data, it performs tf-idf.
//  It gather additional metrics, which are saved to the stats directory.
//  These metrics are used in the NB portion of the runtime application.
//===============================================================

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class TFIDF {
    
    static String input = "./clean-data/";
    static String testOut = "./test-data/";
    static String trainOut = "./train-data/";
    static String tfidfOut = "./tfidf-data/";
    static String statsOut = "./stats/";
    
    static Iterator it;
    static Map.Entry pair;
    
    static HashMap <String, Integer> stats;
    static HashMap<String,Integer> sentFreq;
    static HashMap<String,Integer> sent;
        
    static BufferedReader br;
    static BufferedWriter bw;
    static BufferedWriter test;
    static BufferedWriter train;
    static String read;
    static String[] spl;
    static String key;
        
    static int freq;
    static int allIDF; // Used for idf calculation
    static int allSent; // Used for tf calcuation
    static int disWords; // Used to count distinct words
    static int allSentences; // Used to count number of lines
    static int seed = 5000;
    
    static double tf;
    static double score;
    
    static boolean debug = false;

    public static void main(String[] args) {

        int tmp;
    
        if(args != null) {
        
            if(args.length == 1) {
            
                tmp = Integer.parseInt(args[0]);
                
                if(tmp == 13) {
                    debug = true;
                }
            
            }
        
        }
    
        // Create directories.

        String[] dirNames = {testOut,trainOut,tfidfOut,statsOut};
        setupOutputDirs(dirNames);
    
        // First gather metrics, then write separate files for test data and training data.
        
        sentFreq = new HashMap<String,Integer>();
        sent = new HashMap<String,Integer>();
        
        gatherMetrics();
        writeNewFiles();
        
        // Write stats & sentence freq to disk.
        
        writeMetrics();
   
    }
    
    public static void setupOutputDirs(String[] dirNames) {
 
        File dir;
 
        for(int i = 0; i < dirNames.length; i++) {
         
            dir = new File(dirNames[i]);
            
            if(!dir.exists()) {
                dir.mkdir();
            }
            
        }
        
    }
    
    public static void gatherMetrics() {
    
        try {
        
            File f = new File(input);
            File[] files = f.listFiles();
        
            Random rand = new Random();
            String fileName;
            boolean rollNext = true;
            boolean newline;
            int roll = 0;
            int step = 1;

            allIDF = 0;
            
            for(File fi : files) {

                fileName = "train-"+fi.getName();
  
                br = new BufferedReader(new FileReader(fi));
                test = new BufferedWriter(new FileWriter(testOut+"test-"+fi.getName()));
                train = new BufferedWriter(new FileWriter(trainOut+fileName));

                while((read = br.readLine())!=null) {
                    
                    // Every five lines, pick one line at random to be written to test-data.
                    // Other lines will be written to training data.
                    
                    if(rollNext) {
                        roll = rand.nextInt(5)+1;
                        rollNext = false;
                    }
                    
                    if(step == roll) {
                    
                        // Write to test data.
                    
                        test.write(read+"\n");
                    
                    } else {
                    
                        spl = read.split(" ");
                    
                        for(int i = 0; i < spl.length; i++) {
                        
                            // Write to training data.
                            
                            train.write(spl[i]);
                            
                            if(i < spl.length - 1) {
                                train.write(" ");
                            }

                            // Count the occurence of a word within a sentence.
                            // Count each occurence once.
                        
                            if(sent.get(spl[i]) == null) {
                            
                                // Add to global map, sentFreq.
                                
                                if(sentFreq.get(spl[i]) == null) {
                                
                                    sentFreq.put(spl[i],1);
                                
                                } else {
                                
                                    freq = sentFreq.get(spl[i]);
                                    freq++;
                                    
                                    sentFreq.put(spl[i],freq);
                                
                                }
                                
                                sent.put(spl[i],1);
                            }

                        }

                        train.write("\n");
                    
                        // Clear sentence map.
                        sent.clear();
                        
                        // Count number of sentences, for IDF calcuation.
                        allIDF++;

                    }
                    
                    // Increment step counter. If we reach 1, roll again in the next iteration.
                    
                    step++;

                    if(step == 6) {
                        step = 1;
                        rollNext = true;
                    }

                }

                test.close();
                train.close();
                br.close();
                
            }

        } catch (IOException e) {
    
            e.printStackTrace();
            System.exit(1);

        }
    
    }
    
    public static void writeNewFiles() {
    
        BufferedWriter dbug = null;
        HashSet<String> distinct = new HashSet<>();
        File f;
    
        try {
        
            if(debug) {
                f = new File("../../pr/tfidf-out/");
                
                if(!f.exists()) {
                    f.mkdir();
                }
            
                dbug = new BufferedWriter(new FileWriter("../../pr/tfidf-out/tfidf-scores.txt"));
            }

            f = new File(trainOut);
            File[] files = f.listFiles();

            // Used to store metrics used in further calculations.
            
            stats = new HashMap<String, Integer>(files.length * 2);
            disWords = 0;
            allSentences = 0;
            int sentenceCount;
            int sentWords;
            int wordCount;
            
            for(File fi : files) {
            
                br = new BufferedReader(new FileReader(fi));
                bw = new BufferedWriter(new FileWriter(tfidfOut+fi.getName()));

                sentenceCount = 0;
                wordCount = 0;

                while((read = br.readLine())!=null) {
                    
                    spl = read.split(" ");
                    
                    sentWords = 0;
                    sent.clear();
                    
                    for(int i = 0; i < spl.length; i++) {
                        
                        // Count the number of words in the sentence.
                        sentWords++;
                        
                        // Calculate the frequencies of words in a sentence.
                            
                        if(sent.get(spl[i]) == null) {
                            
                            sent.put(spl[i],1);
                                
                            
                        } else {
                            
                            freq = sent.get(spl[i]);
                            freq++;
                                
                            sent.put(spl[i],freq);
                            
                        }

                    }
                    
                    sentenceCount++;
                    
                    // Calculate frequencies for all words in a sentence.

                    it = sent.entrySet().iterator();
                    
                    while (it.hasNext()) {
                    
                        pair = (Map.Entry)it.next();
                        key = (String)pair.getKey();
                    
                        tf = (int)pair.getValue() / ((double)sentWords);
                        score = tf * (int)Math.log10( ((double)allIDF) / (int)sentFreq.get(key));

                        if(score > 0.05) {

                            bw.write(key+"\n");
                            
                            if(debug) {
                                dbug.write(String.format("%4.2f ",score)+key+"\n");
                            }
                            
                            // Create a list of the number of distinct words. This will be V.
                        
                            if(!distinct.contains(key)) {
                                distinct.add(key);
                                disWords++;
                            }
                            
                            wordCount++;
                            
                        }
                            
                    }
                    
                }
                
                // Count the number of lines from the tf-idf data.
                // Associate a file name with a sentence count, for that file.
        
                allSentences += sentenceCount;
                stats.put(fi.getName()+"-n",wordCount);
                stats.put(fi.getName()+"-s",sentenceCount);
                
                bw.close();
                br.close();
                
            }
            
            if(debug) {
                dbug.close();
            }


        } catch (IOException e) {
    
            e.printStackTrace();
            System.exit(1);

        }

    }
    
    public static void writeMetrics() {
    
    
        try {
        
            File f = new File(statsOut+"stats.map");

            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream foos = new ObjectOutputStream(fos);
            foos.writeObject(stats);
            foos.close();

            f = new File(statsOut+"stats.dat");
            f.createNewFile();
            BufferedWriter mbw = new BufferedWriter(new FileWriter(f));
            mbw.write(disWords+"\n");
            mbw.write(allSentences+"\n");
            mbw.close();
        
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    
    }

}
