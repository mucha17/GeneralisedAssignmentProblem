package pl.umg;

public class Agent {
	private final int index;
	private final int capacity;

	public Agent(int index, int capacity) {
		this.index = index;
		this.capacity = capacity;
	}

	public int getIndex() {
		return index;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Agent) {
			return this.getIndex() == ((Agent) obj).getIndex();
		}
		return false;
	}
}
