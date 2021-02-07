package pl.umg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utils {

	public static String tryToReadLine(BufferedReader reader) {
		String line;
		try {
			line = reader.readLine();
			line = line.stripLeading();
		} catch (IOException e) {
			System.err.println("There was an error during reading of the file");
			return null;
		}
		return line;
	}

	public static Map<Integer, Problem> readProblemsFromFile(String fileLocation) {
		int numberOfProblems;
		Map<Integer, Problem> problems = new HashMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
			numberOfProblems = Integer.parseInt(reader.readLine().stripLeading());
			for (int index = 0; index < numberOfProblems; index++) {
				System.out.println("Read problem nr " + index);

				int numberOfAgents;
				int numberOfJobs;
				Map<Integer, Agent> agents;
				Map<Integer, Job> jobs;
				agents = new HashMap<>();
				jobs = new HashMap<>();
				String line;
				line = Utils.tryToReadLine(reader);
				System.out.println("Get the agents and jobs amount: " + line);
				assert line != null;
				numberOfAgents = Integer.parseInt(line.split(" ")[0]);
				numberOfJobs = Integer.parseInt(line.split(" ")[1]);

				int[][] costsOfAllocating = new int[numberOfAgents][numberOfJobs];
				int[][] resourcesConsumedInAllocating = new int[numberOfAgents][numberOfJobs];

				for (int i = 0; i < numberOfAgents; i++) {
					line = Utils.tryToReadLine(reader);
					System.out.println("Got line of job costs: " + line);
					for (int j = 0; j < numberOfJobs; j++) {
						assert line != null;
						costsOfAllocating[i][j] = Integer.parseInt(line.split(" ")[j]);
					}
				}
				for (int i = 0; i < numberOfAgents; i++) {
					line = Utils.tryToReadLine(reader);
					System.out.println("Got line of job resources: " + line);
					for (int j = 0; j < numberOfJobs; j++) {
						assert line != null;
						resourcesConsumedInAllocating[i][j] = Integer.parseInt(line.split(" ")[j]);
					}
				}

				line = Utils.tryToReadLine(reader);
				System.out.println("Got line of agents capacity: " + line);
				for (int i = 0; i < numberOfAgents; i++) {
					assert line != null;
					agents.put(i, new Agent(Integer.parseInt(line.split(" ")[i])));
				}

				for (int i = 0; i < numberOfJobs; i++) {
					Map<Agent, Integer> costsOfAllocatingMap = new HashMap<>();
					Map<Agent, Integer> resourcesConsumedInAllocatingMap = new HashMap<>();
					for (int j = 0; j < numberOfAgents; j++) {
						Agent agent = agents.get(j);
						costsOfAllocatingMap.put(agent, costsOfAllocating[j][i]);
						resourcesConsumedInAllocatingMap.put(agent, resourcesConsumedInAllocating[j][i]);
					}
					jobs.put(i, new Job(costsOfAllocatingMap, resourcesConsumedInAllocatingMap));
				}

				problems.put(index, new Problem(numberOfAgents, numberOfJobs, agents, jobs));
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			System.err.println("File was not found");
		} catch (IOException e) {
			System.err.println("Number of problems cannot be read correctly");
		}
		return problems;
	}
}
