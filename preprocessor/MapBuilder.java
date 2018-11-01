//===============================================================
// PROGRAM: MapBuilder.java
// ASSIGNMENT: Problem Set 4
// CLASS: Natural Language Processing
// DATE: Nov 1 2018
// AUTHOR: Renae Fisher
// ABSTRACT: This class builds maps from tokens that were generated
//  by the TFIDF program. It uses HashMaps to calculate frequencies 
//  from n-grams, which are determined by the variable size.
//  This program also gathers metrics from the TF-IDF application, 
//  it calcuates some others, then it packages them for the runtime
//  application.
//===============================================================

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;

public class MapBuilder {
	
	private String input = "./tokenizer/tfidf-data/";
	
	private ArrayList<HashMap<String,Integer>> mapList;
	private HashMap<String, Integer> stats;

    private int size = 1;
	private int seed = 5000;

	private int[][] classMetrics;
	private int[][] allMetrics;

    private int allLines;
    private int distinctWords;

	static private boolean debug = false;

	public MapBuilder(){

        try {
        
            // Load data calculated during tokenization.
            // We'll use this for tf-idf, or for metrics in further calculations.

            FileInputStream fis = new FileInputStream(new File("./tokenizer/stats/stats.map"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            stats = (HashMap<String, Integer>)ois.readObject();
            ois.close();
            
            BufferedReader br = new BufferedReader(new FileReader("./tokenizer/stats/stats.dat"));
            distinctWords = Integer.parseInt(br.readLine());
            allLines = Integer.parseInt(br.readLine());
            br.close();
            
            // See if directories exist prior to writing data
        
            String[] d = {"./data/","./data/maps","./data/metrics/"};
            setupOutputDirs(d);

            if(debug) {
        
                File f = new File("./ngrams/");
        
                if(!f.exists())
                    f.mkdir();
        
            }
        
            // Access data from input & begin to build maps.
            
            File indir = new File(input);
            File[] files = indir.listFiles();
            
            String[] outputName;

            for(File file : files) {
        
                // Create a new list of maps, the length of size, the number of ngrams.
                
                mapList = new ArrayList<HashMap<String,Integer>>(size);
		
                for(int i = 0; i < size; i++) {
                    mapList.add(i,new HashMap<String,Integer>(seed));
                }

                // Build maps.
			
                outputName = file.getName().split("\\.");
                
                mapBuilder(file, outputName[0]);

                // Create an array of metrics for each map.
                
                classMetrics = new int[size][2];
                
                for(int i = 0; i < mapList.size(); i++) {

                    writeMap( mapList.get(i), outputName[0]+"-"+(i+1) );
                    classMetrics[i][0] = stats.get(file.getName()+"-n");
                    classMetrics[i][1] = stats.get(file.getName()+"-s");
                    writeMetrics( classMetrics[i], outputName[0]+"-metrics-"+(i+1) );
			
                }
                
            }
            
            // Write "all" metrics, such as n, v, & total sentence count.
            // Sentence count is used for prior.

            allMetrics = new int[1][2];
            allMetrics[0][0] = distinctWords;
            allMetrics[0][1] = allLines;
            writeMetrics(allMetrics[0], "train-all-metrics-"+(1) );
        
        } catch(Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

	}
	
	private void buildDirectories() {
	
	}

    private void mapBuilder(File tokens, String outputName) {

		try {

			// Open the tokens file
			
			BufferedReader br = new BufferedReader(new FileReader(tokens));
			BufferedWriter bwTest[] = null;
			
			if(debug) {  
                bwTest = new BufferedWriter[size];
                
                for(int i = 0; i < bwTest.length; i++) {
                    bwTest[i] = new BufferedWriter(new FileWriter("./ngrams/"+outputName+"-"+(i+1)+".txt"));
                }
			}
            
            // Use a queue to store a section of words. Iterate through the lines of the token file.
            
            Queue<String> q = new LinkedList<>();
            int freq;

			String line = "";
			while((line = br.readLine()) != null) {

                if(line.length() > 0) {
      
                    q.add(line.toLowerCase());
                    
                    // Discard words at the head of the queue if we exceed the n-gram size.
                    
                    if(q.size() > size) {
            
                        q.remove();
            
                    }
            
                    // Start to save ngrams when we have enough to build a full one.
                    
                    if(q.size() == size) {
                
                        StringBuilder sb = new StringBuilder();
                        int len = 0; // Refers to n-gram, unigrams start at 0.
                
                        for(String w : q) {
                
                            sb.append(w);
                    
                            if(len < q.size() - 1) {
                                sb.append(" ");
                            }
                
                            if(mapList.get(len).get(sb.toString()) == null) {

                                mapList.get(len).put(sb.toString(),1);
                    
                            } else {
                    
                                freq = mapList.get(len).get(sb.toString());
                                freq++;
                                mapList.get(len).put(sb.toString(),freq);

                            }
                
                            if(debug) {
                                bwTest[len].write(sb.toString());
                                bwTest[len].write("\n");
                            }

                            len++;
                
                        }
 
                    }
                }
                
            }
    
            br.close();
    
            if(debug) {
                for(int i = 0; i < bwTest.length; i++) {
                    bwTest[i].close();
                }
            }
    
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    
    }

	private void writeMap(HashMap<String,Integer> hm, String outputName) {

		try {

            File f = new File("./data/maps/"+outputName+".map");

            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream foos = new ObjectOutputStream(fos);
            foos.writeObject(hm);
            foos.close();

		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}

	private void writeMetrics(int[] metrics, String outputName) {

        try {

            // Write class metrics to disk
            
			File mFile = new File("./data/metrics/"+outputName+".dat");
			mFile.createNewFile();
			FileWriter mfw = new FileWriter(mFile);
			BufferedWriter mbw = new BufferedWriter(mfw);
			
			for(int i = 0; i < metrics.length; i++) {
			
                mbw.write(metrics[i]+",");
			
			}
			
			mbw.close();

		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}

	private static void setupOutputDirs(String[] dirNames) {
 
        for(int i = 0; i < dirNames.length; i++) {
         
            File dir = new File(dirNames[i]);
            
            if(!dir.exists()) {
                dir.mkdir();
            }
            
        }
        
    }

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
	
        new MapBuilder();

	}

}
