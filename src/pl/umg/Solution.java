package pl.umg;

import java.util.ArrayList;
import java.util.List;

public class Solution {

	private final List<Assignment> assignments;

	public Solution() {
		this.assignments = new ArrayList<>();
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public int getTotalCost() {
		int totalCost = 0;
		for (Assignment assignment : assignments) {
			Job job = assignment.getJob();
			Agent agent = assignment.getAgent();
			totalCost += job.getCostsOfAllocating().get(agent);
		}
		return totalCost;
	}

	public List<Assignment> getAgentsAssignments(Agent selectedAgent) {
		List<Assignment> agentsAssignments = new ArrayList<>();
		for (Assignment assignment : assignments) {
			Job job = assignment.getJob();
			Agent agent = assignment.getAgent();
			if (agent == selectedAgent) {
				agentsAssignments.add(assignment);
			}
		}
		return agentsAssignments;
	}

	public int getAgentsWorkCosts(Agent selectedAgent) {
		int costs = 0;
		for (Assignment assignment : assignments) {
			Job job = assignment.getJob();
			Agent agent = assignment.getAgent();
			if (agent == selectedAgent) {
				costs += job.getCostsOfAllocating().get(agent);
			}
		}
		return costs;
	}

	public int getAgentsResourcesLoad(Agent selectedAgent) {
		int load = 0;
		for (Assignment assignment : assignments) {
			Job job = assignment.getJob();
			Agent agent = assignment.getAgent();
			if (agent == selectedAgent) {
				load += job.getResourcesConsumedInAllocating().get(agent);
			}
		}
		return load;
	}

	public boolean isJobAssignableToAgent(Job selectedJob, Agent selectedAgent) {
		int currentLoad = getAgentsResourcesLoad(selectedAgent);
		int extraLoad = selectedJob.getResourcesConsumedInAllocating().get(selectedAgent);
		return (currentLoad + extraLoad) < selectedAgent.getCapacity();
	}

	public boolean addAssignment(Assignment assignment) {
		if(isJobAssignableToAgent(assignment.getJob(), assignment.getAgent())) {
			return assignments.add(assignment);
		} else {
			return false;
		}
	}

	public boolean removeAssignment(Assignment assignment) {
		return assignments.remove(assignment);
	}
}
