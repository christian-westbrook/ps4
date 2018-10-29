package nb;

public class STO 
{
	private String input;
	private String classifier;
	private double pos;
	private double neg;
	private double neu;
	
	public STO()
	{
		this.input = new String();
		this.classifier = new String();
	}

	public String getInput() 
	{
		return input;
	}

	public void setInput(String input) 
	{
		this.input = input;
	}

	public String getClassifier() 
	{
		return classifier;
	}

	public void setClassifier(String classifier) 
	{
		this.classifier = classifier;
	}

	public double getPos() 
	{
		return pos;
	}

	public void setPos(double pos) 
	{
		this.pos = pos;
	}

	public double getNeg() 
	{
		return neg;
	}

	public void setNeg(double neg) 
	{
		this.neg = neg;
	}
	
	public double getNeu() 
	{
		return neu;
	}

	public void setNeu(double neu) 
	{
		this.neu = neu;
	}
}
