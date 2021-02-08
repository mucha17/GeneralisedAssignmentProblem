package pl.umg;

import pl.umg.Algorithms.Fitness.GreedyFit;
import pl.umg.Algorithms.TabuSearch.SearchAlgorithm;
import pl.umg.Utils.TestDataReader;

import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// Source of test data: http://people.brunel.ac.uk/~mastjjb/jeb/orlib/gapinfo.html

		final int tolerance = 20;
		final int cadence = 10;
		final int tabuListSize = 50;
		final boolean aspirationCriteriaEnabled = true;
		final int file = 1; // in range 1..12
		Map<Integer, Problem> problems = TestDataReader.readProblemsFromFile("resources/gap" + file + ".txt");

		Problem problem = problems.get(0);
		if(problem == null) {
			System.out.println("Selected problem in unavailable");
			return;
		}

		Solution solution = GreedyFit.generateSolution(problem);

		System.out.println("Initial solution");
		for (Agent agent : problem.getAgents()) {
			System.out.println("Agent " + agent.getIndex() + " has " + solution.getAgentsAssignments(agent).size() + " assignments");
			System.out.println("His work costs: " + solution.getAgentsWorkCosts(agent));
			System.out.println("His resources load: " + solution.getAgentsResourcesLoad(agent) + "/" + agent.getCapacity());
		}
		System.out.println("Work costs equal " + solution.getTotalCost());
		System.out.println();

		SearchAlgorithm tabuSearch = new SearchAlgorithm(tolerance, cadence, tabuListSize, aspirationCriteriaEnabled);
		solution = tabuSearch.acquireSolution(solution);

		System.out.println();
		System.out.println("Constructed solution");
		for (Agent agent: problem.getAgents()) {
			System.out.println("Agent " + agent.getIndex() + " has " + solution.getAgentsAssignments(agent).size() + " assignments");
			System.out.println("His work costs: " + solution.getAgentsWorkCosts(agent));
			System.out.println("His resources load: " + solution.getAgentsResourcesLoad(agent) + "/" + agent.getCapacity());
		}
		System.out.println("Work costs equal " + solution.getTotalCost());

	}
}
