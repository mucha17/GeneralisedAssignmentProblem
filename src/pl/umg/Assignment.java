package pl.umg;

public class Assignment implements Comparable<Assignment> {
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

	@Override
	public int compareTo(Assignment o) {
		return Double.compare((double) this.getJob().getCostsOfAllocating().get(this.getAgent()) / this.getJob().getResourcesConsumedInAllocating().get(this.getAgent()), (double) o.getJob().getCostsOfAllocating().get(o.getAgent()) / o.getJob().getResourcesConsumedInAllocating().get(o.getAgent()));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Assignment) {
			return (this.getJob().equals(((Assignment) obj).getJob())) && (this.getAgent().equals(((Assignment) obj).getAgent()));
		} else {
			return false;
		}
	}
}
