package ravensproject;

import java.util.HashMap;

// Uncomment these lines to access image processing.
//import java.awt.Image;
//import java.io.File;
//import javax.imageio.ImageIO;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures: public Agent()
 * public char Solve(RavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent {
	/**
	 * The default constructor for your Agent. Make sure to execute any processing
	 * necessary before your Agent starts solving problems here.
	 * 
	 * Do not add any variables to this signature; they will not be used by main().
	 * 
	 */

	AIAgent aiAgent = new AIAgent();
	
	public Agent() {

		
	}

	/**
	 * The primary method for solving incoming Raven's Progressive Matrices. For
	 * each problem, your Agent's Solve() method will be called. At the conclusion
	 * of Solve(), your Agent should return an int representing its solution to the
	 * question: 1, 2, 3, 4, 5, or 6. Strings of these ints are also the Names of
	 * the individual RavensFigures, obtained through RavensFigure.getName(). Return
	 * a negative number to skip a problem.
	 * 
	 * Make sure to return your solution *as an integer* at the end of Solve().
	 * Returning your solution as a string may cause your program to crash.
	 * 
	 * @param problem
	 *            the RavensProblem your agent should solve
	 * @return your Agent's solution to this problem
	 */
	public int Solve(RavensProblem problem) {

		if (problem.hasVerbal()) {

			return aiAgent.solveUsingVerbalInformation(problem);
		} else if (problem.hasVisual()) {

			return aiAgent.solveUsingVisualInformation(problem);

		} else {

			System.err.println("Neither Visual, Nor Verbal information received. The problem will be skipped.");
			return -1;
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	int solveUsingVerbalInformationOld(RavensProblem problem) {

		int solution = -1;

		HashMap<String, RavensFigure> ravensFigureMap = problem.getFigures(); // create Raven's Figure map from the
																				// problem

		/*
		 * 2 by 2 RPM problem have 9 Ravens Figure and 3 by 3 RPM have 16 Ravens Figure.
		 * Find the total number of Ravens Figure and put check accordingly.
		 */

		if ("2x2".equalsIgnoreCase(problem.getProblemType())) { // 2 by 2 RPMs

			RavensFigure ravensFigure = ravensFigureMap.get("A");
			HashMap<String, RavensObject> ravensObjectMap = ravensFigure.getObjects(); // get the Ravens Figure from the
																						// problem figures

			int numberOfObjects = ravensObjectMap.size(); // number of objects in Figure A

			if (numberOfObjects == 1) {
				solution = OneObject(problem); // 2 by 2 RPM with one objects per figure
				if (solution == 0) { // if solution = 0 from the method set it to -1 to skip the problem
					solution = -1;
				}

			} else {
				System.err.println("Number of objects in problem " + problem.getName()
						+ " is more than 1 which is currently not supported. Hence -1 will be returned");
				solution = -1;
			}
		}

		else if ("3x3".equalsIgnoreCase(problem.getProblemType())) {

			System.out.println(problem.getName() + " has " + ravensFigureMap.size()  + " elements and is a 3x3 problem which is currently not supported. Hence, this problem will be skipped.");
			solution = -1;
		}

		return solution;

	}
	
	

	
	

	/*
	 * This method solves for 2 by 2 RPM that has one object in a figure using
	 * verbal approach
	 */

	private int OneObject(RavensProblem problem) {
		TwoByTwoWithOneObject oneObject = new TwoByTwoWithOneObject(problem);
		return oneObject.oneObjectRPM();
	}

}
