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
    
    static HashMap <String, Integer> stats;
    static HashMap<String,HashMap<String,Double>> wordFreqTable;
    static HashMap<String,Double> wordFreq;
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
    static int allWords; // Used to count distinct words
    static int allLines; // Used to count number of lines
    static int count;
    static int seed = 5000;
        
    static double tf;
    static double score;
        
    static Iterator it;
    static Map.Entry pair;

    public static void main(String[] args) {

        wordFreqTable = new HashMap<String,HashMap<String,Double>>();
        sentFreq = new HashMap<String,Integer>();
        sent = new HashMap<String,Integer>();
        
        // Create directories.

        String[] dirNames = {testOut,trainOut,tfidfOut,statsOut};
        setupOutputDirs(dirNames);
        
        // First gather metrics, then write separate files for test data and training data.
        
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
        
            Random rand = new Random();
            String fileName;
            boolean rollNext = true;
            boolean newline;
            int roll = 0;
            int step = 1;

            File f = new File(input);
            File[] files = f.listFiles();

            allIDF = 0;
            allSent = 0;
            
            for(File fi : files) {

                fileName = "train-"+fi.getName();
            
                wordFreqTable.put(fileName,new HashMap<String,Double>());
                
                br = new BufferedReader(new FileReader(fi));
                test = new BufferedWriter(new FileWriter(testOut+"test-"+fi.getName()));
                train = new BufferedWriter(new FileWriter(trainOut+fileName));
   
                while((read = br.readLine())!=null) {
                    
                    // Every five items, we pick one line at random to be written to test-data.
                    // The other data will be written to a directory for training data.
                    
                    if(rollNext) {
                        roll = rand.nextInt(5)+1;
                        rollNext = false;
                    }
                    
                    if(step == roll) {
                    
                        test.write(read+"\n");
                    
                    } else {
                    
                        spl = read.split(" ");
                    
                        for(int i = 0; i < spl.length; i++) {

                            if(sent.get(spl[i]) == null) {
                            
                                sent.put(spl[i],1);
                            
                            } else {
                            
                                freq = sent.get(spl[i]);
                                freq++;
                                
                                sent.put(spl[i],freq);
                            
                            }
                            
                            allSent++;
                            
                            // Write to training data.
                            train.write(spl[i]);
                            
                            if(i < spl.length - 1) {
                                train.write(" ");
                            }
                        
                        }

                        train.write("\n");
                    
                        // Calculate frequencies for all words in the sentence.
                    
                        it = sent.entrySet().iterator();
                    
                        while (it.hasNext()) {
                    
                            pair = (Map.Entry)it.next();
                            key = (String)pair.getKey();
                    
                            tf = (int)pair.getValue() / ((double)allSent);

                            // Sum the frequencies of the words of a sentence, for a given file.
                            
                            if(wordFreqTable.get(fileName).get(key) == null) {
                     
                                wordFreqTable.get(fileName).put(key,tf);
  
                            } else {
                        
                                tf += wordFreqTable.get(fileName).get(key);
                                wordFreqTable.get(fileName).put(key,tf);

                            }
                            
                            // Calculate frequency for a word across all sentences
                            
                            if(sentFreq.get(key) == null) {
                            
                                sentFreq.put(key,1);
                                
                            } else {
                            
                                freq = sentFreq.get(key);
                                freq++;
                                sentFreq.put(key,freq);
                            
                            }
                            
                        }
                    
                        sent.clear();
                        allSent = 0;
                        allIDF++;
                    
                    }
                    
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
    
        HashSet<String> distinct = new HashSet<>();
    
        try {

            File f = new File(trainOut);
            File[] files = f.listFiles();

            // Used to store metrics
            
            stats = new HashMap<String, Integer>(files.length * 2);
            allWords = 0;
            allLines = 0;
            
            for(File fi : files) {
            
                br = new BufferedReader(new FileReader(fi));
                bw = new BufferedWriter(new FileWriter(tfidfOut+fi.getName()));
                
                wordFreq = wordFreqTable.get(fi.getName());

                count = 0;
                
                while((read = br.readLine())!=null) {
                    
                    spl = read.split(" ");
                    
                    for(int i = 0; i < spl.length; i++) {
                        
                        tf = wordFreq.get(spl[i]);
                        
                        score = tf * (int)Math.log10( ((double)allIDF) / (int)sentFreq.get(spl[i]) );
                        
                        if(score > 0.05) {
                        
                            bw.write(spl[i]+"\n");
                            
                        } else {
                        
                        
                        
                        }
                        
                        if(!distinct.contains(spl[i])) {
                            distinct.add(spl[i]);
                            allWords++;
                        }

                    }
                    
                    count++;
                    
                }
                
                // Save file stats.
        
                allLines += count;
                stats.put(fi.getName(),count);
                
                bw.close();
                br.close();
                
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
            mbw.write(allWords+"\n");
            mbw.write(allLines+"\n");
            mbw.close();
        
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    
    }

}
