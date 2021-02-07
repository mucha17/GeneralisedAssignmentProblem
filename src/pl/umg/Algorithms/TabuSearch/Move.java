package pl.umg.Algorithms.TabuSearch;

public class Move {
	int cadence;
	Pair pair;

	public Move(Pair pair, int cadence) {
		this.pair = pair;
		this.cadence = cadence;
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
}
