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

public class TFIDF {

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
    static int allLines = 0;
    static int wordCount = 0;
        
    static double tf;
    static double score;
        
    static Iterator it;
    static Map.Entry pair;

    public static void main(String[] args) {

        wordFreqTable = new HashMap<String,HashMap<String,Double>>();
        sentFreq = new HashMap<String,Integer>();
        sent = new HashMap<String,Integer>();
        
        // Create directories to separate test data from training data.
        
        File dir = new File("./test-data/");
            
        if(!dir.exists()) {
            dir.mkdir();
        }
        
        dir = new File("./train-data/");
            
        if(!dir.exists()) {
            dir.mkdir();
        }
        
        gatherMetrics();
   
        // We'll use this directory to write files that that don't contain elminated words.
   
        dir = new File("./tfidf-data/");
            
        if(!dir.exists()) {
            dir.mkdir();
        }

        writeNewFiles();
   
    }
    
    public static void gatherMetrics() {
    
        try {
        
            Random rand = new Random();
            String fileName;
            boolean rollNext = true;
            boolean newline;
            int roll = 0;
            int step = 1;

            File f = new File("./input-data/");
            File[] files = f.listFiles();

            for(File fi : files) {
            
                fileName = "train-"+fi.getName();
            
                wordFreqTable.put(fileName,new HashMap<String,Double>());
                
                br = new BufferedReader(new FileReader(fi));
                test = new BufferedWriter(new FileWriter("./test-data/test-"+fi.getName()));
                train = new BufferedWriter(new FileWriter("./train-data/"+fileName));
   
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
                            
                            wordCount++;
                            
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
                    
                            tf = (int)pair.getValue() / ((double)wordCount);

                            if(wordFreqTable.get(fileName).get(key) == null) {
                     
                                wordFreqTable.get(fileName).put(key,tf);
                                sentFreq.put(key,1);
                        
                            } else {
                        
                                tf += wordFreqTable.get(fileName).get(key);
                                wordFreqTable.get(fileName).put(key,tf);
                        
                                // Calculate frequency for a word in a given sentence.
                        
                                freq = sentFreq.get(key);
                                freq++;
                                sentFreq.put(key,freq);
                        
                            }
                        }
                    
                        sent.clear();
                        wordCount = 0;
                    
                        allLines++;
                    
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
    
        try {

            File f = new File("./train-data/");
            File[] files = f.listFiles();

            for(File fi : files) {
            
                br = new BufferedReader(new FileReader(fi));
                bw = new BufferedWriter(new FileWriter("./tfidf-data/"+fi.getName()));
                
                wordFreq = wordFreqTable.get(fi.getName());
                
                while((read = br.readLine())!=null) {
                    
                    spl = read.split(" ");
                    
                    for(int i = 0; i < spl.length; i++) {
                        
                        tf = wordFreq.get(spl[i]);
                        
                        score = tf * (int)Math.log10( ((double)allLines) / (int)sentFreq.get(spl[i]) );
                        
                        if(score > 0.05) {
                        
                            bw.write(spl[i]);
                            
                            if(i < spl.length - 1) {
                                bw.write(" ");
                            }
                            
                        }

                        //System.out.println(spl[i] + " " + score);
                        
                    }
                    
                    bw.write("\n");
                    
                }
                
                bw.close();
                br.close();
                
            }


        } catch (IOException e) {
    
            e.printStackTrace();
            System.exit(1);

        }

    }

}
