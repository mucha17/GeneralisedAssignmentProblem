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
			for (int agentIndex : problem.getAgents().keySet()) {
				Agent agent = problem.getAgents().get(agentIndex);
				Job bestJob = null;
				canBeAssignedAnotherJob[agentIndex] = false; // set to false so it can be checked against jobs
				for (int jobIndex : problem.getJobs().keySet()) {
					if (!jobsAssigned[jobIndex]) {
						Job job = problem.getJobs().get(jobIndex);
						if (solution.isJobAssignableToAgent(job, agent)) {
							canBeAssignedAnotherJob[agentIndex] = true;
						}
						int resource = job.getResourcesConsumedInAllocating().get(agent);
						int cost = job.getCostsOfAllocating().get(agent);
						if (bestJob == null) {
							bestJob = job;
						} else {
							int bjresource = bestJob.getResourcesConsumedInAllocating().get(agent);
							int bjcost = bestJob.getCostsOfAllocating().get(agent);
							if (bjcost/bjresource < cost/resource) {
								bestJob = job;
							}
						}
					}
				}
				if (bestJob != null && solution.addAssignment(new Assignment(agent, bestJob))) {
					Integer jobIndex = null;
					for (int key : problem.getJobs().keySet()) {
						if (problem.getJobs().get(key).equals(bestJob)) {
							jobIndex = key;
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
