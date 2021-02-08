package pl.umg.Algorithms.TabuSearch;

import pl.umg.Agent;
import pl.umg.Assignment;
import pl.umg.Job;
import pl.umg.Solution;
import pl.umg.Utils.Cloner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchAlgorithm {

	private final int cadence; // how many iterations a move stays tabu
	private final int size; // how many moves can be tabu at the same time
	private final int tolerance; // how many moves can be done before stopping the algorithm
	private final boolean aspirationCriteriaEnabled;
	private final List<Move> tabuList;

	public SearchAlgorithm(int tolerance, int cadence, int size, boolean aspirationCriteriaEnabled) {
		this.tolerance = tolerance;
		this.cadence = cadence;
		this.size = size;
		this.aspirationCriteriaEnabled = aspirationCriteriaEnabled;
		this.tabuList = new ArrayList<>();
	}

	public Solution acquireSolution(Solution initialSolution) {
		// main algorithm method - to be called
		Solution bestSolution = Cloner.cloneSolution(initialSolution);
		Solution solution = Cloner.cloneSolution(initialSolution);
		int currentTolerance = tolerance;
		while (currentTolerance > 0) {
			constructNewSolution(solution);
			if (bestSolution.getTotalCost() < solution.getTotalCost()) {
				bestSolution = Cloner.cloneSolution(solution);
				System.out.println("BS size: " + bestSolution.getAssignments().size());
				currentTolerance = tolerance;
			} else {
				currentTolerance--;
				System.out.println("Tolerance left: " + currentTolerance);
			}
			updateCadencesOfTabuMoves();
			System.out.println("Current total cost: " + solution.getTotalCost());
		}
		return bestSolution;
	}

	public void constructNewSolution(Solution solution) {
		Assignment worstAssignment = findWorstAssignmentThatCanBeReassigned(solution);
		if (worstAssignment == null) {
			System.out.println("No moves available at this moment.");
			updateCadencesOfTabuMoves();
			return;
		}
		List<Assignment> applicableAssignments = new ArrayList<>();
		for (Assignment assignment : solution.getAssignments()) {
			Agent selectedAgent = worstAssignment.getAgent();
			Job selectedJob = worstAssignment.getJob();
			Agent targetAgent = assignment.getAgent();
			Job targetJob = assignment.getJob();
			if (!targetAgent.equals(selectedAgent) && !targetJob.equals(selectedJob)) {
				int newSelectedAgentLoad = solution.getAgentsResourcesLoad(selectedAgent) - selectedJob.getResourcesConsumedInAllocating().get(selectedAgent) + targetJob.getResourcesConsumedInAllocating().get(selectedAgent);
				int newTargetAgentLoad = solution.getAgentsResourcesLoad(targetAgent) - targetJob.getResourcesConsumedInAllocating().get(targetAgent) + selectedJob.getResourcesConsumedInAllocating().get(targetAgent);
				if (newSelectedAgentLoad <= selectedAgent.getCapacity() && newTargetAgentLoad <= targetAgent.getCapacity()) {
					applicableAssignments.add(assignment);
				}
			}
		}
		// sort the collection of applicable moves from the best to the worst
		Collections.sort(applicableAssignments);
		Collections.reverse(applicableAssignments);
		System.out.println("Got " + applicableAssignments.size() + " applicable assignments");
		for (Assignment applicableAssignment : applicableAssignments) {
			Move move = new Move(cadence, new Pair(worstAssignment, applicableAssignment));
			if (!isMoveTabu(move)) {
				Agent firstAgent = worstAssignment.getAgent();
				Job firstJob = worstAssignment.getJob();
				Agent secondAgent = applicableAssignment.getAgent();
				Job secondJob = applicableAssignment.getJob();

				if (!solution.removeAssignment(worstAssignment)) {
					System.out.println("[ERROR] Deletion of worst assignment should always work");
				}
				if (!solution.removeAssignment(applicableAssignment)) {
					System.out.println("[ERROR] Deletion of applicable should always work");
				}
				if (!solution.addAssignment(new Assignment(firstAgent, secondJob))) {
					System.out.println("[ERROR] This should be possible (free capacity: " + (firstAgent.getCapacity() - solution.getAgentsResourcesLoad(firstAgent)) + ", required capacity: " + secondJob.getResourcesConsumedInAllocating().get(firstAgent) + ")");
				}
				if (!solution.addAssignment(new Assignment(secondAgent, firstJob))) {
					System.out.println("[ERROR] This should be possible (free capacity: " + (secondAgent.getCapacity() - solution.getAgentsResourcesLoad(secondAgent)) + ", required capacity: " + firstJob.getResourcesConsumedInAllocating().get(secondAgent) + ")");
				}

				// make move tabu
				if (addToTabuList(move)) {
					System.out.println("New move is tabu");
				} else {
					System.out.println("Move not made tabu due to lack of space");
				}
				updateCadencesOfTabuMoves();
				return;
			} else if (aspirationCriteriaEnabled) {
				Agent firstAgent = worstAssignment.getAgent();
				Job firstJob = worstAssignment.getJob();
				Agent secondAgent = applicableAssignment.getAgent();
				Job secondJob = applicableAssignment.getJob();
				Solution temporarySolution = Cloner.cloneSolution(solution);
				temporarySolution.removeAssignment(worstAssignment);
				temporarySolution.removeAssignment(applicableAssignment);
				temporarySolution.addAssignment(new Assignment(firstAgent, secondJob));
				temporarySolution.addAssignment(new Assignment(secondAgent, firstJob));
				if (temporarySolution.getTotalCost() > solution.getTotalCost()) {
					System.out.println("Aspiration Criteria is met, breaking tabu and making the move");
					if (!solution.removeAssignment(worstAssignment)) {
						System.out.println("[ERROR] Deletion of worst assignment should always work");
					}
					if (!solution.removeAssignment(applicableAssignment)) {
						System.out.println("[ERROR] Deletion of applicable should always work");
					}
					if (!solution.addAssignment(new Assignment(firstAgent, secondJob))) {
						System.out.println("[ERROR] This should be possible (free capacity: " + (firstAgent.getCapacity() - solution.getAgentsResourcesLoad(firstAgent)) + ", required capacity: " + secondJob.getResourcesConsumedInAllocating().get(firstAgent) + ")");
					}
					if (!solution.addAssignment(new Assignment(secondAgent, firstJob))) {
						System.out.println("[ERROR] This should be possible (free capacity: " + (secondAgent.getCapacity() - solution.getAgentsResourcesLoad(secondAgent)) + ", required capacity: " + firstJob.getResourcesConsumedInAllocating().get(secondAgent) + ")");
					}
				}
				updateCadencesOfTabuMoves();
				return;
			}
			System.out.println("Move restricted due to tabu");
		}
		updateCadencesOfTabuMoves();
	}

	private Assignment findWorstAssignmentThatCanBeReassigned(Solution solution) {
		Assignment worstAssignment = null;
		for (Assignment selectedAssignment : solution.getAssignments()) {
			for (Assignment targetAssigment : solution.getAssignments()) {
				Agent selectedAgent = selectedAssignment.getAgent();
				Job selectedJob = selectedAssignment.getJob();
				Agent targetAgent = targetAssigment.getAgent();
				Job targetJob = targetAssigment.getJob();
				if (!selectedAgent.equals(targetAgent) && !selectedJob.equals(targetJob)) {
					int newSelectedAgentLoad = solution.getAgentsResourcesLoad(selectedAgent) - selectedJob.getResourcesConsumedInAllocating().get(selectedAgent) + targetJob.getResourcesConsumedInAllocating().get(selectedAgent);
					int newTargetAgentLoad = solution.getAgentsResourcesLoad(targetAgent) - targetJob.getResourcesConsumedInAllocating().get(targetAgent) + selectedJob.getResourcesConsumedInAllocating().get(targetAgent);
					if (newSelectedAgentLoad <= selectedAgent.getCapacity() && newTargetAgentLoad <= targetAgent.getCapacity()) {
						if (worstAssignment == null) {
							worstAssignment = selectedAssignment;
						} else if ((double) selectedAssignment.getJob().getCostsOfAllocating().get(selectedAssignment.getAgent()) / (double) selectedAssignment.getJob().getResourcesConsumedInAllocating().get(selectedAssignment.getAgent()) < ((double) worstAssignment.getJob().getCostsOfAllocating().get(worstAssignment.getAgent()) / (double) worstAssignment.getJob().getResourcesConsumedInAllocating().get(worstAssignment.getAgent()))) {
							worstAssignment = selectedAssignment;
						}
					}
				}
			}
		}
		return worstAssignment;
	}

	public boolean isMoveTabu(Move selectedMove) {
		for (Move move : this.tabuList) {
			if (move.equals(selectedMove)) {
				return true;
			}
		}
		return false;
	}

	public boolean addToTabuList(Move move) {
		if (tabuList.size() < size) {
			if (tabuList.contains(move)) {
				return false;
			} else {
				move.setCadence(cadence);
				tabuList.add(move);
				return true;
			}
		} else {
			return false;
		}
	}

	public void updateCadencesOfTabuMoves() {
		for (Move move : tabuList) {
			int cadence = move.getCadence() - 1;
			move.setCadence(cadence);
		}
	}

}
