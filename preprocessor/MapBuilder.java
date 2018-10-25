//=================================================================================================
// Program		: PS4
// Class		: Builder.java
// Developer	: Christian Westbrook, Renae Fisher
// Abstract		: This class is used to build HashMaps that are used for Naive Bayes classification
//=================================================================================================

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class MapBuilder {
	
	private ArrayList<HashMap<String,Integer>> mapList;
    private int size = 2;
	private int seed = 5000;
	
	private int[][] metrics;
	private int n;
	private int v;
	
	private boolean debug = true;
	
	public MapBuilder(){

        // Access data from tokenizer
        File indir = new File("./tokenizer/train-data/");
		File[] files = indir.listFiles();

		// See if directories exist prior to writing data
		File dir = new File("./data/maps/");
            
        if(!dir.exists())
            dir.mkdir();
            
        dir = new File("./data/metrics/");
            
        if(!dir.exists())
            dir.mkdir();
            
        if(debug) {
        
            File nGramDir = new File("./ngrams/");
        
            if(!nGramDir.exists())
                nGramDir.mkdir();
        
        }
            
        // Cycle through data, building maps from positive, neutral, & negative files.
		
		String[] outputName;
		int ind = 0;
		n = 0;
        v = 0;
		
		for(File file : files) {
			
			outputName = file.getName().split("\\.");

			// Create maps & metrics for a partiuclar file
            mapList = new ArrayList<HashMap<String,Integer>>(size);
		
            for(int i = 0; i < size; i++) {
        
                mapList.add(i,new HashMap<String,Integer>(seed));
        
            }
            
            metrics = new int[size][2];
			
			mapBuilder(file, outputName[0]);

			// Write the maps to disk
			for(int i = 0; i < mapList.size(); i++) {
			
                writeMap(mapList.get(i),outputName[0],i);
                computeMetrics(metrics[i][0],metrics[i][1]);
                writeMetrics(metrics[i][0],metrics[i][1],outputName[0],i);
			
			}
			
			writeMetrics(n,v,"all",-1);

			ind++;
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
                                metrics[len][1]++; //V
                    
                            } else {
                    
                                freq = mapList.get(len).get(sb.toString());
                                freq++;
                                mapList.get(len).put(sb.toString(),freq);
                                metrics[len][0]++; //N
                    
                            }
                
                            if(debug) {
                        
                                bwTest[len].write(len+" "+sb.toString());
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
	
	private void writeMap(HashMap<String,Integer> hm, String outputName, int num) {
		
		try {

            File f = new File("./data/maps/"+outputName+"-"+(num+1)+".map");

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
	
	private void computeMetrics(int n2, int v2) {
		n += n2;
		v += v2;
	}
	
	private void writeMetrics(int n, int v, String outputName, int num) {
	
        try {

            // Write metrics to disk
			File mFile = new File("./data/metrics/"+outputName+"-metrics-"+(num+1)+".dat");
			mFile.createNewFile();
			FileWriter mfw = new FileWriter(mFile);
			BufferedWriter mbw = new BufferedWriter(mfw);
			mbw.write(n +","+ v + "\n");
			mbw.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	
	}
	
	public static void main(String[] args) {
		new MapBuilder();
	}

}
