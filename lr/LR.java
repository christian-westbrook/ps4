package lr;

import java.util.HashMap;

public class LR {
	
	static HashMap<String, Double> positive;
	static HashMap<String, Double> neutral;	
	static HashMap<String, Double> negative;
	
	public LR() {
	
        Load l = new Load();
        
        positive = l.getPosFeat();
        neutral = l.getNeuFeat();
        negative = l.getNegFeat();
	
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

        double alpha = 3.0;
        double w = 0.0;
		
		double[] tmp = res(positive,input);
		double a = tmp[0];
		w += tmp[1];
		
		tmp = res(neutral,input);
		double b = tmp[0];
		w+= tmp[1];
		
		tmp = res(negative,input);
		double c = tmp[0];
		w += tmp[1];
		
		double d = a + b + c;

		if(selection == 0) {
			
			res = Math.log10(Math.exp(a) / Math.exp(d)) + (alpha * w);
			
		} else if(selection == 1) {
			
			res = Math.log10(Math.exp(b) / Math.exp(d)) + (alpha * w);
			
		} else {
			
			res = Math.log10(Math.exp(c) / Math.exp(d)) + (alpha * w);
			
		}
		
		return res;
	
    }
	
	private static double[] res(HashMap<String, Double> hm, String input) {
		
		double[] result = new double[2];
		
		String[] split = input.split(" ");
		
		for(int i = 0; i < split.length; i++) {
			
			if(hm.get(split[i]) != null) {
				
				result[0] += hm.get(split[i]);
				result[1] += Math.pow(hm.get(split[i]),2);
				
			}
			
		}
		
		return result;
		
	}

	// Used for debugging / testing via command line.
	
	public static void getAllProb(String input) {
    
		double a = result(0, input);
		double b = result(1, input);
		double c = result(2, input);
    
		System.out.println("Pos: "+a);
		System.out.println("Neu: "+b);
		System.out.println("Neg: "+c);
		
		System.out.print("Sentiment is ");
    
		if(a > b && a > c) {
			System.out.println("Positive");
		} else if(c > a && c > b) {
			System.out.println("Negative");
		} else {
			System.out.println("Neutral");
		}
    
	}
	
}
