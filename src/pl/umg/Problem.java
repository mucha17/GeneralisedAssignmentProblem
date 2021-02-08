package pl.umg;

import java.util.List;

public class Problem {
	private final int numberOfAgents;
	private final int numberOfJobs;
	private final List<Agent> agents;
	private final List<Job> jobs;

	public Problem(int numberOfAgents, int numberOfJobs, List<Agent> agents, List<Job> jobs) {
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

	public List<Agent> getAgents() {
		return agents;
	}

	public List<Job> getJobs() {
		return jobs;
	}

}
