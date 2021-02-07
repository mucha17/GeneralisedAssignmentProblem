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
		int load = 0;
		for (Job assignment : assignments) {
			load += assignment.getResourcesConsumedInAllocating().get(this);
		}
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
}
