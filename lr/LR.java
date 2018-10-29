import java.util.HashMap;

public class LR {
	
	static HashMap<String, Double> positive;
	static HashMap<String, Double> neutral;	
	static HashMap<String, Double> negative;
	
	public static STO calc(STO sto) {
		
		sto.setPos(calc(bigramPos, sto.getInput()));
		sto.setNeg(calc(bigramNeg, sto.getInput()));
		sto.setNeu(calc(bigramNeu, sto.getInput()));
		
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
	
	private double calc(HashMap<String, Double> hm, String input) {
		
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
