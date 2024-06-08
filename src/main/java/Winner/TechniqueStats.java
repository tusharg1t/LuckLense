package Winner;

public class TechniqueStats {
	String sequence;
	int winCount;
	String prediction;
	public TechniqueStats(String sequence, int winCount, String prediction) {
		super();
		this.sequence = sequence;
		this.winCount = winCount;
		this.prediction = prediction;
	}
	@Override
	public String toString() {
		return "TechniqueStats [sequence=" + sequence + ", winCount=" + winCount + ", prediction=" + prediction + "]";
	}
	
	
}
