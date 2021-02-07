package pl.umg.Algorithms.Fitness;

import pl.umg.Agent;
import pl.umg.Job;
import pl.umg.Problem;

import java.util.Arrays;

public class GreedyFit {

	public static void assignJobsToAgents(Problem problem) {
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
						if (agent.isAssignable(job.getResourcesConsumedInAllocating().get(agent))) {
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
				if (bestJob != null && agent.addAssignment(bestJob)) {
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
	}
}
