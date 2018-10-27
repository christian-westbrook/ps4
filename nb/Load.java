
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

public class Load {

    private HashMap<String,HashMap<String,Integer>> mapTable;
    private HashMap<String,Integer> hm;
    
    private FileInputStream fis;
    private ObjectInputStream ois;
    
    private String[] tmp;
    private String lable;
    
    private int n;
    private int v;
    
    private int seed = 10;

    public Load() {
        
        try {
            
            File indir = new File("./preprocessor/data/");
            File[] files = indir.listFiles();
            
            mapTable = new HashMap<String,HashMap<String,Integer>>(seed);
            
            for(File file : files) {
            
                tmp = file.getName().split("-");
                
                lable = tmp[1] + " " + tmp[2];
            
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                hm = (HashMap<String, Integer>)ois.readObject();
                ois.close();
            
                mapTable.put( lable, hm );
            
            }
            
            /*String[] metrics = bfr.readLine().split(",");

            n = Integer.parseInt(metrics[0]);
            v = Integer.parseInt(metrics[1]);

            unigramsN = Integer.parseInt(metrics[2]);
            unigramsV = Integer.parseInt(metrics[3]);

            bigramsN = Integer.parseInt(metrics[4]);
            bigramsV = Integer.parseInt(metrics[5]);

            trigramsN = Integer.parseInt(metrics[6]);
            trigramsV = Integer.parseInt(metrics[7]);*/
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    // Getters
    public HashMap<String,Integer> getHashMap(String lable) {
        return mapTable.get(lable);
    }
    
    public HashMap<String,Integer> getPosBMap() {
    
        return getHashMap("positive 2");
    }
    
    public HashMap<String,Integer> getNeuBMap() {
    
        return getHashMap("neutral 2");
    }
    
    public HashMap getNegBMap() {
    
        return getHashMap("negative 2");
    }
    
    /*
    public int getN() {
        return n;
    }

    public int getV() {
        return v;
    }
    */

}
