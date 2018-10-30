package pr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import lr.LR;
import nb.NB;

public class PrecisionRecall {

    static NB n;
    static LR l;

	public static void main(String[] args) {
	
		n = new NB("cl");
		l = new LR("cl");
		int[][] nb = new int[3][3];
		int[][] lr = new int[3][3];
		
		fillMatrixes("../preprocessor/tokenizer/test-data/test-positive.txt",nb,lr,0);
		fillMatrixes("../preprocessor/tokenizer/test-data/test-neutral.txt",nb,lr,1);
		fillMatrixes("../preprocessor/tokenizer/test-data/test-negative.txt",nb,lr,2);

		System.out.print("NB: ");
		calcPrecisionRecall(nb);
		System.out.print("LR: ");
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
					
					res = n.testCalc(split[i]);
					cm1[res][ind]++;
					
					res = l.testCalc(split[i]);
					cm2[res][ind]++;
					
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
			
			if(i == 0) {
                System.out.print("Positive: ");
			} else if(i == 1) {
                System.out.print("Neutral: ");
			} else {
                System.out.print("Negative: ");
			}
			
			System.out.println("Precision: "+((double)cm[i][i]) / p);
			System.out.println("Recall: "+((double)cm[i][i]) / r);
	
		}
		
	}

}
