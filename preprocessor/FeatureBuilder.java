import java.io.IOException;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;

public class FeatureBuilder {

	static HashMap<String,Double> positive;
	static HashMap<String,Double> neutral;
	static HashMap<String,Double> negative;
	
	static int seed = 1000;

	public static void main(String[] args) {
	
		//pos.txt
		//good 5.0
		
		FileOutputStream fos;
		ObjectOutputStream foos;
		File f;
	
        positive = new HashMap<String,Double>(seed);
        neutral = new HashMap<String,Double>(seed);
        negative = new HashMap<String,Double>(seed);
	
		buildFeature(positive,"./features/positive.txt");
		buildFeature(neutral,"./features/neutral.txt");
		buildFeature(negative,"./features/negative.txt");
		
		// Write them to disk.
	
        try {
        
            f = new File("./data/lr_maps/");
            
            if(!f.exists())
                f.mkdir();
                
            f = new File("./data/lr_maps/positive.map");
            f.createNewFile();
            fos = new FileOutputStream(f);
            foos = new ObjectOutputStream(fos);
            foos.writeObject(positive);
            foos.close();
            
            f = new File("./data/lr_maps/neutral.map");
            f.createNewFile();
            fos = new FileOutputStream(f);
            foos = new ObjectOutputStream(fos);
            foos.writeObject(neutral);
            foos.close();
            
            f = new File("./data/lr_maps/negative.map");
            f.createNewFile();
            fos = new FileOutputStream(f);
            foos = new ObjectOutputStream(fos);
            foos.writeObject(negative);
            foos.close();
            
        
        } catch(IOException e) {
        
            e.printStackTrace();
            System.exit(1);
        
        }
	
	}
	
	public static void buildFeature(HashMap<String,Double> hm, String filename) {
	
		BufferedReader br;
		String read;
		String[] split;
		double weight;
	
		try {
		
			br = new BufferedReader(new FileReader(filename));
			
			while((read = br.readLine())!=null) {

				split = read.split(" ");
				
				weight = Double.parseDouble(split[1]);
				
				hm.put(split[0],weight);
			
			}
		
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	
	}

}
