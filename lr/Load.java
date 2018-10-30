package lr;

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

    private HashMap<String,Double> positive;
    private HashMap<String,Double> neutral;
    private HashMap<String,Double> negative;

    private FileInputStream fis;
    private ObjectInputStream ois;
    private BufferedReader br;
    
    private int seed = 30;
    
    String path1 = "../preprocessor/data/lr_maps/";
	String path2 = "./preprocessor/data/lr_maps/";
	
	String directory = path2;

    public Load() {
        
        try {
            
            fis = new FileInputStream(directory+"positive.map");
            ois = new ObjectInputStream(fis);
            positive = (HashMap<String,Double>)ois.readObject();
            ois.close();
            
            fis = new FileInputStream(directory+"neutral.map");
            ois = new ObjectInputStream(fis);
            neutral = (HashMap<String,Double>)ois.readObject();
            ois.close();
            
            fis = new FileInputStream(directory+"negative.map");
            ois = new ObjectInputStream(fis);
            negative = (HashMap<String,Double>)ois.readObject();
            ois.close();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    // Getters
    public HashMap<String,Double> getPosFeat() {
        return positive;
    }
    
    public HashMap<String,Double> getNeuFeat() {
        return neutral;
    }
    
    public HashMap<String,Double> getNegFeat() {
        return negative;
    }

}
