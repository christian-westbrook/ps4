//===============================================================
// PROGRAM: LR.java
// ASSIGNMENT: Problem Set 4
// CLASS: Natural Language Processing
// DATE: Nov 1 2018
// AUTHOR: Renae Fisher, Anthony Todaro
// ABSTRACT: This class performs calculations to classify a space
//  delimited sentence.
//===============================================================

package lr;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class LR {
	
	static HashMap<String, Double> positive;
	static HashMap<String, Double> neutral;	
	static HashMap<String, Double> negative;
	static double wSQ;
	
	public LR(String access) {
		
		String head = "";
		
		if(access.equals("cl"))
			head = "..";
		else if(access.equals("web"))
			head = ".";
	
        Load l = new Load(head);
        
        positive = l.getPosFeat();
        neutral = l.getNeuFeat();
        negative = l.getNegFeat();

        wSQ = getWSQ(positive);
        wSQ += getWSQ(neutral);
        wSQ += getWSQ(negative);
        
	}
	
	public static STO calc(STO sto) {
		
		sto.setPos(result(0, sto.getInput()));
		sto.setNeu(result(1, sto.getInput()));
		sto.setNeg(result(2, sto.getInput()));
		
		sto.setClassifier(getClass(sto));
		
		return sto;
	}
	
	private static String getClass(STO sto) {
		
		if(sto.getPos() > sto.getNeu() && sto.getPos() > sto.getNeg()) {
			
			return "Positive";
			
		} else if(sto.getNeg() > sto.getPos() && sto.getNeg() > sto.getNeu()) {
			
			return "Negative";
			
		} else {
			
			return "Neutral";
			
		}
	}
	
	public static double result(int selection, String input) {

		double res = 0.0;
        double alpha = 0.013;
		
		double a = res(positive,input);
		double b = res(neutral,input);
		double c = res(negative,input);
		double d = a + b + c;

		if(selection == 0) {
			
			res = Math.log10(Math.exp(a) / Math.exp(d)) - (alpha * wSQ);
			
		} else if(selection == 1) {
			
			res = Math.log10(Math.exp(b) / Math.exp(d)) - (alpha * wSQ);
			
		} else {
			
			res = Math.log10(Math.exp(c) / Math.exp(d)) - (alpha * wSQ);
			
		}
		
		return res;
	
    }
	
	private static double res(HashMap<String, Double> hm, String input) {
		
		double result = 0.0;
		
		String[] split = input.split(" ");
		String tmp;
		
		for(int i = 0; i < split.length; i++) {
			
			tmp = split[i].toLowerCase();
			
			if(hm.get(tmp) != null) {
				result += hm.get(tmp);
			}
			
		}
		
		return result;
		
	}
	
	private double getWSQ(HashMap<String,Double> hm) {
	
        double res = 0;
	
        Iterator it = hm.entrySet().iterator();
        Map.Entry pair;
                    
        while (it.hasNext()) {
                    
            pair = (Map.Entry)it.next();
            res += Math.pow((double)pair.getValue(),2);
                            
        }
        
        return res;
	
	}

	// Used for debugging / testing via command line.
	
	public static void getAllProb(String input) {
    
		double a = result(0, input);
		double b = result(1, input);
		double c = result(2, input);
    
		System.out.println("  Pos: "+a);
		System.out.println("  Neu: "+b);
		System.out.println("  Neg: "+c);
		
		System.out.print("  Sentiment is ");
    
		if(a > b && a > c) {
			System.out.println("Positive");
		} else if(b > a && b > c) {
			System.out.println("Neutral");
		} else {
			System.out.println("Negative");
		}
    
	}
	
	public static int testCalc(String input) {
    
		double a = result(0, input);
		double b = result(1, input);
		double c = result(2, input);

		if(a > b && a > c) {
			return 0;
		} else if(b > a && b > c) {
			return 1;
		} else {
			return 2;
		}
    
	}
	
}
