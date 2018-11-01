//===============================================================
// PROGRAM: LR.java
// ASSIGNMENT: Problem Set 4
// CLASS: Natural Language Processing
// DATE: Nov 1 2018
// AUTHOR: Renae Fisher, Anthony Todaro
// ABSTRACT: This class performs precision and recall calculations
//  on test data.
//===============================================================

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

        String files[] =
        {
        "../preprocessor/tokenizer/test-data/test-positive.txt",
        "../preprocessor/tokenizer/test-data/test-neutral.txt",
        "../preprocessor/tokenizer/test-data/test-negative.txt"
        };

		n = new NB("cl");
		int[][] nb = new int[3][3];

		for(int j = 0; j < files.length; j++) {
            fillMatrix(n,files[j],nb,j);
        }

        System.out.println("Naive Bayes:");
        calcPrecisionRecall(nb);

		int[][] lr = new int[3][3];
		l = new LR("cl");
		
		for(int j = 0; j < files.length; j++) {
            fillMatrix(l,files[j],lr,j);
        }
        
        System.out.println("Logistic Regression:");
        calcPrecisionRecall(lr);

	}
	
	public static void fillMatrix(NB n, String filename, int[][] cm, int ind) {
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String read;
			String[] split;
			int res;
			
			// res = predicted
			// ind = actual
			
			while((read = br.readLine())!=null){
				
				res = n.testCalc(read);
                cm[res][ind]++;
				
			}
			
		} catch(IOException e){
			
			e.printStackTrace();
			System.exit(1);
			
		}
		
	}
	
	public static void fillMatrix(LR l, String filename, int[][] cm, int ind) {
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String read;
			String[] split;
			int res;
			
			// res = predicted
			// ind = actual
			
			while((read = br.readLine())!=null){
				
				res = l.testCalc(read);
                cm[res][ind]++;
				
			}
			
		} catch(IOException e){
			
			e.printStackTrace();
			System.exit(1);
			
		}
		
	}
	
	public static void calcPrecisionRecall(int[][] cm) {
		
		double p;
		double r;
		
		for(int fix = 0; fix < cm.length; fix++) {
			
			// calc for cm[i][i];

			p = 0.0;
			r = 0.0;
			
			for(int ch = 0; ch < cm[0].length; ch++) {
				
				p += cm[fix][ch];
				
			}
			
			for(int ch = 0; ch < cm.length; ch++) {
				
				r += cm[ch][fix];
				
			}
			
			p = ((double)cm[fix][fix]) / p;
			r = ((double)cm[fix][fix]) / r;
			
			if(fix == 0) {
			
                System.out.println("  Positive");
                
			} else if(fix == 1) {
			
                System.out.println("  Neutral");
			
			} else {
			
                System.out.println("  Negative");
			
			}
			
			System.out.println("  P: " + String.format("%4.2f",p));
            System.out.println("  R: " + String.format("%4.2f",r));
	
		}
		
	}

	public static void printMatrix(int[][] matrix) {
	
        for(int rows = 0; rows < matrix.length; rows++) {
        
            for(int cols = 0; cols < matrix[0].length; cols++) {
            
                System.out.print(String.format("%4d ",matrix[rows][cols]));
            
            }
            
            System.out.println();
            
        }
        
        System.out.println();
	
	}
	
}
