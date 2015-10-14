/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.jmetal.lowlevelheuristics.operators;

import com.google.common.collect.Lists;

import java.util.List;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

/**
 *
 *
 * @author Vidal
 */
public class SimpleIntegerProblem extends AbstractIntegerProblem {

    public SimpleIntegerProblem(int numberOfObjectives, int numberOfVariables, int lowerLimit, int upperLimit) {
        setNumberOfObjectives(numberOfObjectives);
        setNumberOfVariables(numberOfVariables);
        setNumberOfConstraints(0);
        setName("SimpleIntegerProblem");

        List<Integer> lowerLimits = Lists.newArrayListWithExpectedSize(numberOfVariables);
        List<Integer> upperLimits = Lists.newArrayListWithExpectedSize(numberOfVariables);
        for (int i = 0; i < numberOfVariables; i++) {
            lowerLimits.add(lowerLimit);
            upperLimits.add(upperLimit);
        }
        setLowerLimit(lowerLimits);
        setUpperLimit(upperLimits);

    }

    public void evaluate(IntegerSolution solution) {

    }

}
