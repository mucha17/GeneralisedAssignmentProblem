package pl.umg;

import java.util.Map;

public class Problem {
	private final int numberOfAgents;
	private final int numberOfJobs;
	private final Map<Integer, Agent> agents;
	private final Map<Integer, Job> jobs;

	public Problem(int numberOfAgents, int numberOfJobs, Map<Integer, Agent> agents, Map<Integer, Job> jobs) {
		this.numberOfAgents = numberOfAgents;
		this.numberOfJobs = numberOfJobs;
		this.agents = agents;
		this.jobs = jobs;
	}

	public int getNumberOfAgents() {
		return numberOfAgents;
	}

	public int getNumberOfJobs() {
		return numberOfJobs;
	}

	public Map<Integer, Agent> getAgents() {
		return agents;
	}

	public Map<Integer, Job> getJobs() {
		return jobs;
	}
}
