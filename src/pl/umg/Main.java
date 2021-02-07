package pl.umg;

import pl.umg.Utils.TestDataReader;

import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// Source of test data: http://people.brunel.ac.uk/~mastjjb/jeb/orlib/gapinfo.html

		final int file = 1; // in range 1..12
		Map<Integer, Problem> problems = TestDataReader.readProblemsFromFile("resources/gap" + file + ".txt");


	}
}
