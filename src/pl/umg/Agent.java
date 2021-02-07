package pl.umg;

import java.util.ArrayList;
import java.util.List;

public class Agent {
	private final int capacity;
	private final List<Job> assignments;

	public Agent(int capacity) {
		this.capacity = capacity;
		assignments = new ArrayList<>();
	}

	public List<Job> getAssignments() {
		return assignments;
	}

	public boolean isAssignable(int extraLoad) {
		int load = this.getResourcesLoad();
		return (load + extraLoad) < this.capacity;
	}

	public boolean addAssignment(Job job) {
		if (isAssignable(job.getResourcesConsumedInAllocating().get(this))) {
			return this.assignments.add(job);
		} else {
			return false;
		}
	}

	public boolean removeAssignment(Job job) {
		return this.assignments.remove(job);
	}

	public int getWorkCosts() {
		int costs = 0;
		for (Job assignment : assignments) {
			costs += assignment.getCostsOfAllocating().get(this);
		}
		return costs;
	}

	public int getResourcesLoad() {
		int load = 0;
		for (Job assignment : assignments) {
			load += assignment.getResourcesConsumedInAllocating().get(this);
		}
		return load;
	}

	@Override
	public String toString() {
		String toView = "Agent has " + getResourcesLoad() + "/" + capacity + "\nAssignments:\n";
		for(Job assignment : assignments) {
			toView += "Cost: " + assignment.getCostsOfAllocating().get(this) + ", resources: " + assignment.getResourcesConsumedInAllocating().get(this) + "\n";
		}
		return toView;
	}
}
