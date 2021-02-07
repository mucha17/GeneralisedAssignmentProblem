package pl.umg;

public class Assignment {
	private final Agent agent;
	private final Job job;

	public Assignment(Agent agent, Job job) {
		this.agent = agent;
		this.job = job;
	}

	public Agent getAgent() {
		return agent;
	}

	public Job getJob() {
		return job;
	}
}
