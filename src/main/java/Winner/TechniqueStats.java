package Winner;

public class TechniqueStats {
	String sequence;
	int winCount;
	String prediction;
	int winSum;
	public TechniqueStats(String sequence, int winCount, String prediction, int winSum) {
		super();
		this.sequence = sequence;
		this.winCount = winCount;
		this.prediction = prediction;
		this.winSum = winSum;
	}
	@Override
	public String toString() {
		return "TechniqueStats [sequence=" + sequence + ", winCount=" + winCount + ", prediction=" + prediction+ ", winSum=" + winSum + "]";
	}
	
	
}
