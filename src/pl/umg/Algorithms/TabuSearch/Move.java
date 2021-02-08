package pl.umg.Algorithms.TabuSearch;

public class Move {
	private int cadence;
	private final Pair pair;

	public Move(int cadence, Pair pair) {
		this.cadence = cadence;
		this.pair = pair;
	}

	public int getCadence() {
		return cadence;
	}

	public Pair getPair() {
		return pair;
	}

	public void setCadence(int cadence) {
		this.cadence = cadence;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Move) {
			return this.getPair().equals(((Move) obj).getPair());
		} else {
			return false;
		}
	}
}
