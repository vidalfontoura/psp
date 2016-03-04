/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.hp.jmetal.lowlevelheuristics.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 *
 *
 * @author Vidal
 */
public class TwoPointsCrossover implements CrossoverOperator<IntegerSolution> {

    private double crossoverProbability;
    private JMetalRandom randomGenerator;

    public TwoPointsCrossover(double crossoverProbability, JMetalRandom randomGenerator) {
        this.crossoverProbability = crossoverProbability;
        this.randomGenerator = randomGenerator;
    }

    public List<IntegerSolution> execute(List<IntegerSolution> solutions) {

        if (solutions == null) {
            throw new JMetalException("Null parameter");
        } else if (solutions.size() != 2) {
            throw new JMetalException("There must be two parents instead of " + solutions.size());
        }

        return doCrossover(crossoverProbability, solutions.get(0), solutions.get(1));
    }

    public List<IntegerSolution> doCrossover(double probability, IntegerSolution parent1, IntegerSolution parent2) {

        IntegerSolution offspring1 = (IntegerSolution) parent2.copy();
        IntegerSolution offspring2 = (IntegerSolution) parent1.copy();

        if (randomGenerator.nextDouble() < probability) {

            int numberOfVariables = parent1.getNumberOfVariables();
            int numberOfCrossPoints = 2;

            List<Integer> crossoverPoints = new ArrayList<>(numberOfCrossPoints);

            for (int i = 0; i < numberOfCrossPoints; i++) {
                int crosspoint = randomGenerator.nextInt(0, numberOfVariables - 1);
                if (!crossoverPoints.contains(crosspoint)) {
                    crossoverPoints.add(crosspoint);
                }

            }

            Collections.sort(crossoverPoints);

            int startPoint = 0;
            boolean exchangeValues = true;
            for (int j = 0; j < crossoverPoints.size(); j++) {

                Integer point = crossoverPoints.get(j);
                for (int i = startPoint; i < point; i++) {
                    Integer variableValue1 = parent1.getVariableValue(i);
                    Integer variableValue2 = parent2.getVariableValue(i);

                    if (exchangeValues) {
                        offspring1.setVariableValue(i, variableValue2);
                        offspring2.setVariableValue(i, variableValue1);
                    } else {
                        offspring1.setVariableValue(i, variableValue1);
                        offspring2.setVariableValue(i, variableValue2);
                    }

                }
                exchangeValues = exchangeValues ? false : true;
                startPoint = point;
            }

        }

        List<IntegerSolution> offspring = new ArrayList<>(2);
        offspring.add(offspring1);
        offspring.add(offspring2);
        return offspring;
    }

}
