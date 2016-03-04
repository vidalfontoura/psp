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
public class OppositeMoveOperator implements MutationOperator<IntegerSolution> {

    private double mutationProbability;
    private JMetalRandom randomGenerator;

    public OppositeMoveOperator(double mutationProbability, JMetalRandom randomGenerator) {
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

            int startPoint = randomGenerator.nextInt(0, numberOfVariables - 1);
            int endPoint = startPoint;
            do {
                endPoint = randomGenerator.nextInt(0, numberOfVariables - 1);
            } while (startPoint == endPoint);

            int aux = -1;
            if (startPoint > endPoint) {
                aux = endPoint;
                endPoint = startPoint;
                startPoint = aux;
            }
            for (int i = startPoint; i < endPoint; i++) {
                int oldDirection = offspring.getVariableValue(i);
                int oppositeDirection = oldDirection;
                switch (oldDirection) {
                    case 0:
                        oppositeDirection = 0;
                        break;
                    case 1:
                        oppositeDirection = 2;
                        break;
                    case 2:
                        oppositeDirection = 1;
                        break;
                    case 3:
                        oppositeDirection = 4;
                        break;
                    case 4:
                        oppositeDirection = 3;
                        break;
                    default:
                        throw new JMetalException(
                            "The direction " + oldDirection + " isn't supported invalid parent chromossome");
                }
                offspring.setVariableValue(i, oppositeDirection);

            }

        }
        return offspring;

    }

}
