package pl.umg;

import pl.umg.TabuSearch.SearchAlgorithm;

import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// Source of test data: http://people.brunel.ac.uk/~mastjjb/jeb/orlib/gapinfo.html

		final int file = 1; // in range 1..12
		Map<Integer, Problem> problems = Utils.readProblemsFromFile("resources/gap" + file + ".txt");


	}
}
