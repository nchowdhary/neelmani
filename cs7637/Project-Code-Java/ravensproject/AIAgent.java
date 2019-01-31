package ravensproject;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



public class AIAgent {
	
	
	
  /**
   * This method solves the AI RPM 2x2 as well as 3x3 Matrices assuming verbal information is provided. It returns the best possible solution(according to it's "thinking").	
   * @param problem
   * @return best matching option NUMBER (according to it's "thinking").	
   */
   public int solveUsingVerbalInformation(RavensProblem problem) {

		if ("2x2".equalsIgnoreCase(problem.getProblemType())) { // 2 by 2 RPMs

			return solve2by2ProblemUsingVerbalInfo(problem);
		}

		else if ("3x3".equalsIgnoreCase(problem.getProblemType())) {

			return solve2by2ProblemUsingVerbalInfo(problem);
			// System.out.println(problem.getName() + " has " + problem.getFigures().size() + " elements and is a 3x3 problem which is currently not supported. Hence, this problem will be skipped.");
			// return -1;
		} else {
			return -1;

		}
	}

	/**
	 * This method is a placeholder that will attempt to Solve the RPM matrices based on Visual Information provided.
	 * @param problem
	 * @return  best matching option NUMBER (according to it's "thinking").	
	 */
	public int solveUsingVisualInformation(RavensProblem problem) {

		int solution = -1;

		System.err.println("Problem " + problem.getName() + " does not provide verbal information, which is currently not supported. Hence -1 will be returned");

		return solution;
	}

	
	/**
	   * This method solves the AI RPM 2x2 only assuming verbal information is provided. It returns the best possible solution(according to it's "thinking").	
	   * @param problem
	   * @return best matching option NUMBER (according to it's "thinking").	
	   */
	private int solve2by2ProblemUsingVerbalInfo(RavensProblem problem) {

		int solution = -1;
		/* create a sorted map that contains only problems */
		Map<String, RavensFigure> problemMap = new TreeMap<String, RavensFigure>();
		
		/* create a sorted map that contains only options that will be tested*/
		Map<String, RavensFigure> optionsMap = new TreeMap<String, RavensFigure>();
		
		for(String figureName : problem.getFigures().keySet()) {
			
			if(isAlphabetic(figureName)) {
				problemMap.put(figureName, problem.getFigures().get(figureName));
			}else if(isNumeric(figureName)) {
				optionsMap.put(figureName, problem.getFigures().get(figureName));
			}
		}
		
		
		solution = findBestMatchingOption(problemMap, optionsMap);
		System.out.println("*** " + problem.getName() + " Solution = " + solution);	
		

		return solution;
	}


	private int findBestMatchingOption(Map<String, RavensFigure> problemMap,
			Map<String, RavensFigure> optionsMap) {
		
		int expectedNumberOfCountInSolution = getExpectedNumberOfCountInSolution(problemMap);
		
		int weightAtoB = calculateWeightForAToBTransformation(problemMap.get("A"), problemMap.get("B"));
		System.out.println("weightAtoB = " + weightAtoB);
		
		Set<String> optionsKey = optionsMap.keySet();
		for (String option : optionsKey) {
		
			int weightCtoOption = calculateWeightForCToOptionTransformation(problemMap.get("C"), optionsMap.get(option));
			
			System.out.println("weight C to " + option + " = " + weightCtoOption);
			
			if(weightAtoB == weightCtoOption) {
				
				
				return Integer.parseInt(option);
			}
		}
		
		
		return -1;
		
	}


	/**
	 * This method returns the expected number of objects in the desired solution based on the pattern between A, B and C. 
	 * For eg, if A has 1 object, B has 1 object, C has 1 object, then Solution should also be having 1 object.
	 * @param numberOfObjectsInA
	 * @param numberOfObjectsInB
	 * @param numberOfObjectsInC
	 * @return
	 */
	private int getExpectedNumberOfCountInSolution(Map<String, RavensFigure> problemMap) {
		
		int expectedNumberOfCountInSolution = -1;
		
		int numberOfObjectsInA = problemMap.get("A").getObjects().keySet().size();
		int numberOfObjectsInB = problemMap.get("B").getObjects().keySet().size();
		int numberOfObjectsInC = problemMap.get("C").getObjects().keySet().size();
		
		if(numberOfObjectsInA == numberOfObjectsInB) {
			
			expectedNumberOfCountInSolution = numberOfObjectsInC;
		}else if(numberOfObjectsInA != numberOfObjectsInB) {
		
			expectedNumberOfCountInSolution = numberOfObjectsInC + (numberOfObjectsInB - numberOfObjectsInA); // for now, the AI Agent thinks that the number of Objects in Solution will be having the same difference with the count in C as B has to A.
		}
		
		System.out.println("numberOfObjectsInA = " + numberOfObjectsInA + ", numberOfObjectsInB = " + numberOfObjectsInB + ", numberOfObjectsInC = " + numberOfObjectsInC + ", expectedNumberOfCountInSolution = " + expectedNumberOfCountInSolution);
		
		return expectedNumberOfCountInSolution; // this will never happen as previous if-else block will return a non-negative number	
	}
	
	

	private int calculateWeightForAToBTransformation(RavensFigure ravensFigureA, RavensFigure ravensFigureB) {
	
		
		// {a=RavensObject [name=a, attributes={shape=square, size=very large, fill=yes}]}
		// {b=RavensObject [name=b, attributes={shape=square, size=very large, fill=yes}]}
		
		int weight = 0;
		
		
		Map<String, String> attributeMapForA = ravensFigureA.getObjects().get(ravensFigureA.getObjects().keySet().iterator().next()).getAttributes();
		Map<String, String> attributeMapForB = ravensFigureB.getObjects().get(ravensFigureB.getObjects().keySet().iterator().next()).getAttributes();
			
		Set<String> attributeNames = attributeMapForA.keySet();
		for (String attributeName : attributeNames) {
			
			weight += getWeight(attributeName, attributeMapForA.get(attributeName), attributeMapForB.get(attributeName));
		}
		
		return weight;
		
		
	}
	
	
	private int calculateWeightForCToOptionTransformation(RavensFigure ravensFigureC, RavensFigure ravensFigureOption) {
	
		
		// {a=RavensObject [name=a, attributes={shape=square, size=very large, fill=yes}]}
		// {b=RavensObject [name=b, attributes={shape=square, size=very large, fill=yes}]}
		
		int weight = 0;

		System.out.println(ravensFigureC);
		System.out.println(ravensFigureOption);
		
		
		Map<String, String> attributeMapForC = ravensFigureC.getObjects().get(ravensFigureC.getObjects().keySet().iterator().next()).getAttributes();
		Map<String, String> attributeMapForOption = ravensFigureOption.getObjects().get(ravensFigureOption.getObjects().keySet().iterator().next()).getAttributes();
			
		Set<String> attributeNames = attributeMapForC.keySet();
		for (String attributeName : attributeNames) {
			
			weight += getWeight(attributeName, attributeMapForC.get(attributeName), attributeMapForOption.get(attributeName));
		}
		
		return weight;
		
		
	}


	private boolean isAlphabetic(String figureName) {
		
		if(figureName.matches("[A-Z]"))
			return true;
		else
			return false;
	}

	private boolean isNumeric(String figureName) {
		
		if(figureName.matches("[1-9]"))
			return true;
		else
			return false;
	}
	
	
	
	private int getWeight(String attributeName, String leftSide, String rightSide ) {
		
		int weight = 0;
	
		
		if ("shape".equalsIgnoreCase(attributeName)) {
			
			weight += matchShape(leftSide, rightSide);
		}
	
		if("size".equalsIgnoreCase(attributeName)) {
			
			weight += matchSize(leftSide, rightSide);
		}
		
		if("fill".equalsIgnoreCase(attributeName)) {
			
			weight += matchFill(leftSide, rightSide);
		}
		
		return weight;

		
	}


	private int matchSize(String leftSide, String rightSide) {

		if (leftSide.equalsIgnoreCase(rightSide)) {
			return 5;
		}

		return 0;
	}

	private int matchShape(String leftSide, String rightSide) {

		if (leftSide.equalsIgnoreCase(rightSide)) {
			return 5;
		}

		return 0;

	}

	
	private int matchFill(String leftSide, String rightSide) {

		if (leftSide.equalsIgnoreCase(rightSide)) {
			return 5;
		}

		return 0;

	}
}
