package pl.umg.Algorithms.TabuSearch;

import pl.umg.Job;

public class Pair {
	Job element1;
	Job element2;

	public Pair(Job element1, Job element2) {
		this.element1 = element1;
		this.element2 = element2;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			Pair pair = (Pair) obj;
			return (this.element1 == pair.element1 && this.element2 == pair.element2) || (this.element1 == pair.element2 && this.element2 == pair.element1);
		} else {
			return false;
		}
	}
}
