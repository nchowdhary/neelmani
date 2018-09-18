package ravensproject;

import java.util.HashMap;

import edu.gatech.cs7637.nchowdhary.ThreeByThree;
import edu.gatech.cs7637.nchowdhary.TwoByTwoOne;
import edu.gatech.cs7637.nchowdhary.TwoByTwoTwo;
import edu.gatech.cs7637.nchowdhary.VisualApproach;

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
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(RavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent {
    
	
	HashMap<String, RavensFigure> ravensFigureMap;
	private int response;
	private RavensFigure ravensFigureMapA;
	private HashMap<String, RavensObject> ravensObjectMap;
	
	/**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
    public Agent() {
        
    	
    	
    }
    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return an int representing its
     * responsewer to the question: 1, 2, 3, 4, 5, or 6. Strings of these ints 
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName(). Return a negative number to skip a problem.
     * 
     * Make sure to return your responsewer *as an integer* at the end of Solve().
     * Returning your responsewer as a string may cause your program to crash.
     * @param problem the RavensProblem your agent should solve
     * @return your Agent's responsewer to this problem
     */
    public int Solve(RavensProblem problem) {
       
    	System.out.println("**Neel's AI Agent will try to now solve : " + problem.getName());

    	
			response = VisualSolution(problem);
			if (response == 0) {
				response = -1;
			}
		
		return response;
	}

	/*
	 * This method solves for 2 by 2 RPM that has one object in a figure using verbal approach
	 */

	private int OneObject(RavensProblem problem) {
		TwoByTwoOne oneObject = new TwoByTwoOne(problem);
		return oneObject.oneObjectRPM();
	}

	/*
	 * This method solves for 2 by 2 RPM that has two objects in a figure using verbal approach
	 */

	private int TwoObjects(RavensProblem problem) {
		TwoByTwoTwo twoObjects = new TwoByTwoTwo(problem);
		return twoObjects.twoObjectRPM();
	}
	
	/*
	 * This method solves for 3 by 3 RPM using verbal approach
	 */
	
	private int ThreeByThree (RavensProblem problem) {
		ThreeByThree threebythree = new ThreeByThree(problem);
		return threebythree.ThreeByThreeRPM();
	}
	
	/*
	 * This method solves for 3 by 3 RPM using visual approach
	 */
	
	private int VisualSolution(RavensProblem problem) {
		VisualApproach visualRPMs = new VisualApproach(problem);
		return visualRPMs.VisualApproachResults();
	}
}