package pl.umg.Utils;

import pl.umg.Assignment;
import pl.umg.Solution;

public class Cloner {

	public static Solution cloneSolution(Solution solution) {
		Solution clonedSolution = new Solution();
		for (Assignment assignment : solution.getAssignments()) {
			clonedSolution.addAssignment(new Assignment(assignment.getAgent(), assignment.getJob()));
		}
		return clonedSolution;
	}
}
