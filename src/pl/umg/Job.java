package pl.umg;

import java.util.Map;

public class Job {
	private final int index;
	private final Map<Agent, Integer> costsOfAllocating;
	private final Map<Agent, Integer> resourcesConsumedInAllocating;

	public Job(int index, Map<Agent, Integer> costsOfAllocating, Map<Agent, Integer> resourcesConsumedInAllocating) {
		this.index = index;
		this.costsOfAllocating = costsOfAllocating;
		this.resourcesConsumedInAllocating = resourcesConsumedInAllocating;
	}

	public int getIndex() {
		return index;
	}

	public Map<Agent, Integer> getCostsOfAllocating() {
		return costsOfAllocating;
	}

	public Map<Agent, Integer> getResourcesConsumedInAllocating() {
		return resourcesConsumedInAllocating;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Job) {
			return this.getIndex() == ((Job) obj).getIndex();
		} else {
			return false;
		}
	}
}
