package pr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import lr.LR;
import nb.NB;

public class PrecisionRecall {

	public static void main(String[] args) {
	
		NB n = new NB();
		LR l = new LR();
		int[][] nb = new int[3][3];
		int[][] lr = new int[3][3];
		
		fillMatrixes("../preprocessor/tokenizer/test-data/positive.txt",nb,lr,0);
		fillMatrixes("../preprocessor/tokenizer/test-data/neutral.txt",nb,lr,1);
		fillMatrixes("../preprocessor/tokenizer/test-data/negative.txt",nb,lr,2);

		calcPrecisionRecall(nb);
		calcPrecisionRecall(lr);
		
	}
	
	public static void fillMatrixes(String filename, int[][] cm1, int[][] cm2, int ind) {
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String read;
			String[] split;
			int res;
			
			while((read = br.readLine())!=null){
				
				split = read.split(" ");
				
				for(int i = 0; i < split.length; i++) {
					
					//res = nb.testCalc(split[i]);
					//cm1[res][ind]++;
					
					//res = lr.testCalc(split[i]);
					//cm2[res][ind]++;
					
				}
				
			}
			
		} catch(IOException e){
			
			e.printStackTrace();
			System.exit(1);
			
		}
		
	}
	
	public static void calcPrecisionRecall(int[][] cm) {
		
		double p;
		double r;
		
		for(int i = 0; i < cm.length; i++) {
			
			// calc for cm[i][i];
			
			p = 0.0;
			r = 0.0;
			
			for(int j = 0; j < cm[0].length; j++) {
				
				p += cm[i][j];
				
			}
			
			for(int k = 0; k < cm[0].length; k++) {
				
				r += cm[k][i];
				
			}
			
			System.out.println("Precision: "+((double)cm[i][i]) / p);
			System.out.println("Recall: "+((double)cm[i][i]) / r);
	
		}
		
	}

}
