package pl.umg.Algorithms.TabuSearch;

import pl.umg.Assignment;

public class Pair {
	private final Assignment element1;
	private final Assignment element2;

	public Pair(Assignment element1, Assignment element2) {
		this.element1 = element1;
		this.element2 = element2;
	}

	public Assignment getElement1() {
		return element1;
	}

	public Assignment getElement2() {
		return element2;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair) {
			return (this.getElement1().equals(((Pair) obj).getElement1()) && this.getElement2().equals(((Pair) obj).getElement2())) || (this.getElement1().equals(((Pair) obj).getElement2()) && this.getElement2().equals(((Pair) obj).getElement1()));
		} else {
			return false;
		}
	}
}
