/*
 * Copyright 2015, Charter Communications,  All rights reserved.
 */
package edu.ufpr.jmetal.custom.operators;

import org.uma.jmetal.operator.LocalSearchOperator;
import org.uma.jmetal.solution.Solution;

/**
 *
 *
 * @author Vidal
 */
public class LocalMoveOperator implements LocalSearchOperator<Solution<Integer>> {

	public Solution<Integer> execute(Solution<Integer> source) {

		int numberOfVariables = source.getNumberOfVariables();

		return null;
	}

	public int getEvaluations() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.uma.jmetal.operator.LocalSearchOperator#getNumberOfImprovements()
	 */
	public int getNumberOfImprovements() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.uma.jmetal.operator.LocalSearchOperator#
	 * getNumberOfNonComparableSolutions()
	 */
	public int getNumberOfNonComparableSolutions() {
		// TODO Auto-generated method stub
		return 0;
	}

}
