/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.hp.jmetal.lowlevelheuristics.operators;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 *
 *
 * @author Vidal
 */
public class LoopMoveOperator implements MutationOperator<IntegerSolution> {

    private double mutationProbability;
    private JMetalRandom randomGenerator;

    public LoopMoveOperator(double mutationProbability, JMetalRandom randomGenerator) {
        this.mutationProbability = mutationProbability;
        this.randomGenerator = randomGenerator;
    }

    public IntegerSolution execute(IntegerSolution source) {

        if (source == null) {
            throw new JMetalException("Null parameter");
        }
        return applyLooplMove(mutationProbability, source);

    }

    public IntegerSolution applyLooplMove(double probability, IntegerSolution source) {

        IntegerSolution offspring = (IntegerSolution) source.copy();

        if (randomGenerator.nextDouble() < probability) {
            int numberOfVariables = offspring.getNumberOfVariables();

            int localMovePoint1 = randomGenerator.nextInt(0, numberOfVariables - 1);
            int localMovePoint2 = localMovePoint1 + 5;

            Integer variableValue1 = offspring.getVariableValue(localMovePoint1);
            Integer variableValue2 = offspring.getVariableValue(localMovePoint2);

            offspring.setVariableValue(localMovePoint1, variableValue2);
            offspring.setVariableValue(localMovePoint2, variableValue1);

        }
        return offspring;

    }
}
