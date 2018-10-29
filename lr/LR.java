import java.util.HashMap;

public class LR {
	
	static HashMap<String, Double> positive;
	static HashMap<String, Double> neutral;	
	static HashMap<String, Double> negative;
	
	public static STO calc(STO sto) {
		
		sto.setPos(result(1, positive, sto.getInput()));
		sto.setNeg(result(-1, negative, sto.getInput()));
		sto.setNeu(result(0, neutral, sto.getInput()));
		
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
	
	public double result(int selection, HashMap<String, Double> hm, String input) {

		double res = 0.0;

		double a;
		double b;
		double c;
		
		double max;
		
		a = res(positve,input);
		b = res(neutral,input);
		c = res(negative,input);
		
		double d = a + b + c;

		
		if(selection == 0) {
			
			return (Math.exp(b) / Math.exp(d));
			
		} else if(selection == 1) {
			
			return (Math.exp(a) / Math.exp(d));
			
		} else {
			
			return (Math.exp(c) / Math.exp(d));
			
		}
	
}
	
	private double res(HashMap<String, Double> hm, String input) {
		
		double result = 0.0;
		
		String[] split = input.split(" ");
		
		for(int i = 0; i < split.length; i++) {
			
			if(hm.get(split[i]) != null) {
				
				result += hm.get(split[i]);
				
			}
			
		}
		
		return result;
		
	}
}
