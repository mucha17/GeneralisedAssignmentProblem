package pl.umg;

import java.util.Map;

public class Job {
	private final Map<Agent, Integer> costsOfAllocating;
	private final Map<Agent, Integer> resourcesConsumedInAllocating;

	public Job(Map<Agent, Integer> costsOfAllocating, Map<Agent, Integer> resourcesConsumedInAllocating) {
		this.costsOfAllocating = costsOfAllocating;
		this.resourcesConsumedInAllocating = resourcesConsumedInAllocating;
	}

	public Map<Agent, Integer> getCostsOfAllocating() {
		return costsOfAllocating;
	}

	public Map<Agent, Integer> getResourcesConsumedInAllocating() {
		return resourcesConsumedInAllocating;
	}
}
