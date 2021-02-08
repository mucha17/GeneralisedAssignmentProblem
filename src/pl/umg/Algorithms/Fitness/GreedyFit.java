package pl.umg.Algorithms.Fitness;

import pl.umg.*;

import java.util.Arrays;

public class GreedyFit {

	public static Solution generateSolution(Problem problem) {
		Solution solution = new Solution();
		boolean[] jobsAssigned = new boolean[problem.getNumberOfJobs()]; // automatically has false values
		Boolean[] canBeAssignedAnotherJob = new Boolean[problem.getNumberOfAgents()]; // has to be assigned true values
		Arrays.fill(canBeAssignedAnotherJob, true);
		while (Arrays.asList(canBeAssignedAnotherJob).contains(true)) {
			for (Agent agent : problem.getAgents()) {
				Job bestJob = null;
				canBeAssignedAnotherJob[agent.getIndex()] = false; // set to false so it can be checked against jobs
				for (Job job : problem.getJobs()) {
					if (!jobsAssigned[job.getIndex()]) {
						if (solution.isJobAssignableToAgent(job, agent)) {
							canBeAssignedAnotherJob[agent.getIndex()] = true;
						}
						int resource = job.getResourcesConsumedInAllocating().get(agent);
						int cost = job.getCostsOfAllocating().get(agent);
						if (bestJob == null) {
							bestJob = job;
						} else {
							int bjresource = bestJob.getResourcesConsumedInAllocating().get(agent);
							int bjcost = bestJob.getCostsOfAllocating().get(agent);
							if (bjcost / bjresource < cost / resource) {
								bestJob = job;
							}
						}
					}
				}
				if (bestJob != null && solution.addAssignment(new Assignment(agent, bestJob))) {
					Integer jobIndex = null;
					for (Job job : problem.getJobs()) {
						if (problem.getJobs().get(job.getIndex()).equals(bestJob)) {
							jobIndex = job.getIndex();
						}
					}
					if (jobIndex != null) {
						jobsAssigned[jobIndex] = true;
					}
				}
			}
		}
		return solution;
	}
}
