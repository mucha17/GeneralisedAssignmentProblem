package pl.umg;

import pl.umg.Algorithms.Fitness.GreedyFit;
import pl.umg.Algorithms.TabuSearch.SearchAlgorithm;
import pl.umg.Utils.TestDataReader;

import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// Source of test data: http://people.brunel.ac.uk/~mastjjb/jeb/orlib/gapinfo.html

		final int tolerance = 10;
		final int cadence = 10;
		final int tabuListSize = 5;
		final boolean aspirationCriteriaEnabled = true;
		final int selectedFile = 1; // in range 1..12
		final int selectedProblem = 1; // in range 1..5
		Map<Integer, Problem> problems = TestDataReader.readProblemsFromFile("resources/gap" + selectedFile + ".txt");

		Problem problem = problems.get(selectedProblem - 1);
		if (problem == null) {
			System.err.println("Selected problem in unavailable");
			return;
		}

		Solution initialSolution = GreedyFit.generateSolution(problem);
		SearchAlgorithm tabuSearch = new SearchAlgorithm(tolerance, cadence, tabuListSize, aspirationCriteriaEnabled);
		Solution acquiredSolution = tabuSearch.acquireSolution(initialSolution);

		System.out.println();
		System.out.println("Acquired vs initial solution");
		for (Agent agent : problem.getAgents()) {
			System.out.println("Agent " + agent.getIndex() + " has " + acquiredSolution.getAgentsAssignments(agent).size() + " assignments and had " + initialSolution.getAgentsAssignments(agent).size());
			System.out.println("His work costs equal " + acquiredSolution.getAgentsWorkCosts(agent) + " and was " + initialSolution.getAgentsWorkCosts(agent));
			System.out.println("His resources load equal " + acquiredSolution.getAgentsResourcesLoad(agent) + "/" + agent.getCapacity() + " and was " + initialSolution.getAgentsResourcesLoad(agent) + "/" + agent.getCapacity());
		}
		System.out.println("Work costs equal " + acquiredSolution.getTotalCost() + " and was " + initialSolution.getTotalCost());

	}
}
