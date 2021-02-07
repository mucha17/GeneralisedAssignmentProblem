package pl.umg;

import pl.umg.Algorithms.Fitness.GreedyFit;
import pl.umg.Utils.TestDataReader;

import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// Source of test data: http://people.brunel.ac.uk/~mastjjb/jeb/orlib/gapinfo.html

		final int file = 1; // in range 1..12
		Map<Integer, Problem> problems = TestDataReader.readProblemsFromFile("resources/gap" + file + ".txt");

		Problem problem = problems.get(0);

		GreedyFit.assignJobsToAgents(problem);

		for(int key : problem.getAgents().keySet()) {
			System.out.println("Agent " + key + " has " + problem.getAgents().get(key).getAssignments().size() + " assignments");
		}

	}
}
