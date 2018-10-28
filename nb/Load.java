
//=================================================================================================
// Program		: PS4
// Class		: Load.java
// Developer	: Renae Fisher
// Abstract		: 
//=================================================================================================

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.ArrayList;

public class Load {

    private HashMap<String,HashMap<String,Integer>> mapTable;
    private HashMap<String,Integer> hm;

    private HashMap<String,ArrayList<Integer>> metricTable;
    private ArrayList<Integer> metricList;
    
    private String[] tmp;
    private String lable;
    private String read;
    
    private FileInputStream fis;
    private ObjectInputStream ois;
    private BufferedReader br;
    
    private int seed = 30;

    public Load() {
        
        try {
            
            File indir = new File("../preprocessor/data/maps/");
            File[] files = indir.listFiles();
            
            mapTable = new HashMap<String,HashMap<String,Integer>>(seed);
            
            for(File file : files) {
            
                tmp = file.getName().split("-|\\.");
                
                lable = tmp[1] + " " + tmp[2];

                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                hm = (HashMap<String, Integer>)ois.readObject();
                ois.close();
            
                mapTable.put( lable, hm );
            
            }
            
            indir = new File("../preprocessor/data/metrics/");
            files = indir.listFiles();
            
            metricTable = new HashMap<String,ArrayList<Integer>>();
            
            for(File file : files) {
                
                tmp = file.getName().split("-|\\.");
                lable = tmp[1] + " " + tmp[3];

                br = new BufferedReader(new FileReader(file));
            
                read = br.readLine();
                tmp = read.split(",");

                metricList = new ArrayList<Integer>(3);
                metricList.add(0,Integer.parseInt(tmp[0]));
                metricList.add(1,Integer.parseInt(tmp[1]));
                metricList.add(2,Integer.parseInt(tmp[2]));
                
                metricTable.put( lable, metricList );
                
                br.close();
            
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    // Getters
    public HashMap<String,Integer> getHashMap(String lable) {
        return mapTable.get(lable);
    }
    
    public ArrayList<Integer> getArrayList(String lable) {
        return metricTable.get(lable);
    }
    
    public HashMap<String,Integer> getPosUMap() {
        return getHashMap("positive 1");
    }
    
    public HashMap<String,Integer> getNeuUMap() {
        return getHashMap("neutral 1");
    }
    
    public HashMap getNegUMap() {
        return getHashMap("negative 1");
    }

    // Return V, the number of distinct words, & the number of lines in the original file.

    public int getAllV() {
        return getArrayList("all 1").get(1);
    }
    
    public int getAllSent() {
        return getArrayList("all 1").get(2);
    }
    
    // Return all N just for Unigrams.

    public int getPosUniN() {
        return getArrayList("positive 1").get(0);
    }

    public int getNeuUniN() {
        return getArrayList("neutral 1").get(0);
    }

    public int getNegUniN() {
        return getArrayList("negative 1").get(0);
    }

    // Return sentence count.

    public int getPosSent() {
        return getArrayList("positive 1").get(2);
    }
    
    public int getNeuSent() {
        return getArrayList("neutral 1").get(2);
    }
    
    public int getNegSent() {
        return getArrayList("negative 1").get(2);
    }
    
}
