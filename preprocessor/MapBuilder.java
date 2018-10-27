//=================================================================================================
// Program		: PS4
// Class		: Builder.java
// Developer	: Christian Westbrook, Renae Fisher
// Abstract		: This class is used to build HashMaps that are used for Naive Bayes classification
//=================================================================================================

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
import java.util.Stack;

public class MapBuilder {
	
	private ArrayList<HashMap<String,Integer>> mapList;
	private HashMap<String, Integer> stats;
    //private Stack<String> fileStack;
    //private Stack<Integer> lineStack;
	
    private int size = 2;
	private int seed = 5000;

	private int[][] classMetrics;
	private int[][] allMetrics;

    private int allLines;

	// Prior = Lines with class c / All lines
	
	private boolean debug = true;

	public MapBuilder(){

        try {
        
            // Load stats from tokenizer & create stacks for metric processing

            FileInputStream fis = new FileInputStream(new File("./tokenizer/stats/stats.map"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            stats = (HashMap<String, Integer>)ois.readObject();
            ois.close();

            //fileStack = new Stack<>();
            //lineStack = new Stack<>();
            
            // See if directories exist prior to writing data
        
            String[] d = {"./data/","./data/maps","./data/metrics/"};
            setupOutputDirs(d);

            if(debug) {
        
                File nGramDir = new File("./ngrams/");
        
                if(!nGramDir.exists())
                    nGramDir.mkdir();
        
            }
        
            // Access data from tokenizer
            
            File indir = new File("./tokenizer/train-data/");
            File[] files = indir.listFiles();
            
            // Cycle through data, building maps from positive, neutral, & negative files.
		
            String[] outputName;
            int ind = 0;
            allMetrics = new int[size][3];
            allLines = 0;
        
            for(File file : files) {
        
                // Create maps for a partiuclar file
                
                mapList = new ArrayList<HashMap<String,Integer>>(size);
		
                for(int i = 0; i < size; i++) {
        
                    mapList.add(i,new HashMap<String,Integer>(seed));
        
                }
            
                // Create metrics for a particular file
                
                classMetrics = new int[size][3];
			
                // Build maps
			
                outputName = file.getName().split("\\.");
                mapBuilder(file, outputName[0]);

                // Write the maps to disk, along with metrics
                
                for(int i = 0; i < mapList.size(); i++) {

                    writeMap(mapList.get(i), outputName[0]+"-"+(i+1) );
                    
                    classMetrics[i][2] = stats.get(file.getName());
                    
                    writeMetrics(classMetrics[i], outputName[0]+"-metrics-"+(i+1) );
			
                }

                // Add up line count for all files.

                allLines += stats.get(file.getName());

                //fileStack.push(file.getName());
                //lineStack.push(stats.get(file.getName()));

                ind++;
                
            }
            
            // Write "global" metrics, such as n, v, & prior.

            for(int i = 0; i < size; i++) {
            
                allMetrics[i][2] = allLines;
                writeMetrics(allMetrics[i], "train-all-metrics-"+(i+1) );
            
            }
        
        } catch(Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

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
            
            // Use a queue to store previous words.
            Queue<String> q = new LinkedList<>();

            int freq;

			// Iterate through lines in the tokens file
			String line = "";
			while((line = br.readLine()) != null) {

                if(line.length() > 0) {
                    
                    q.add(line.toLowerCase());
                    
                    // Discard words at the head of the queue if we exceed the size
                    if(q.size() > size) {
            
                        q.remove();
            
                    }
            
                    // Start to save ngrams when we have enough to build a full one
                    if(q.size() == size) {
                
                        StringBuilder sb = new StringBuilder();
                        int len = 0;
                
                        for(String w : q) {
                
                            sb.append(w);
                    
                            if(len < q.size() - 1) {
                                sb.append(" ");
                            }
                
                            if(mapList.get(len).get(sb.toString()) == null) {

                                mapList.get(len).put(sb.toString(),1);
                                
                                classMetrics[len][1]++; //V
                                allMetrics[len][1]++;
                    
                            } else {
                    
                                freq = mapList.get(len).get(sb.toString());
                                freq++;
                                mapList.get(len).put(sb.toString(),freq);

                            }
                            
                            classMetrics[len][0]++; //N
                            allMetrics[len][0]++;
                
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

    /*
	public void TFIDF(HashMap<String, Integer> hm) {
	
        Iterator it = hm.entrySet().iterator();
        int freq;
        int score;
        
        // STORE THEM, USE QUARTILES TO FIND MIDDLE ZONE?
        // Occurence of term i in a document j * log( number of documents in collection / number of documents containing i )
        
        while(it.hasNext()) {
        
            Map.Entry pair = (Map.Entry)it.next();
            
            freq = (int) pair.getValue();
            
            //score = hm.get(pair.getKey()) * 
            
            
        }

	}*/

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
		new MapBuilder();
	}

}
