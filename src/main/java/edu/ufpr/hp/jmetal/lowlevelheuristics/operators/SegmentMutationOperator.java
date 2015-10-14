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
 * Segment Mutation (SMUT): Muda um numero randomico de genes consecutivos (5 a
 * 7) para novas direções. Este operador introduz grandes mudanças na
 * conformação, e tem uma grande probabilidade de criar colisões, portanto um
 * mecanismo de reparação é aplicado no filho gerado caso necessário.
 *
 *
 * 
 * @author Vidal
 */
public class SegmentMutationOperator implements MutationOperator<IntegerSolution> {

    private double mutationProbability;
    private JMetalRandom randomGenerator;

    public SegmentMutationOperator(double mutationProbability, JMetalRandom randomGenerator) {
        this.mutationProbability = mutationProbability;
        this.randomGenerator = randomGenerator;
    }

    public IntegerSolution execute(IntegerSolution source) {

        if (source == null) {
            throw new JMetalException("Null parameter");
        }
        return applySegmentMutation(mutationProbability, source);
    }

    public IntegerSolution applySegmentMutation(double probability, IntegerSolution source) {

        IntegerSolution offspring = (IntegerSolution) source.copy();

        if (randomGenerator.nextDouble() < probability) {
            int numberOfVariables = offspring.getNumberOfVariables();

            int startPoint = randomGenerator.nextInt(0, numberOfVariables - 1);
            int numberOfGenes = randomGenerator.nextInt(5, 7);
            int endPoint = startPoint + numberOfGenes;

            for (int i = startPoint; i < endPoint; i++) {

                int oldDirection = offspring.getVariableValue(i);
                int newDirection = oldDirection;
                do {
                    newDirection = randomGenerator.nextInt(0, 4);
                } while (oldDirection == newDirection);

                offspring.setVariableValue(i, newDirection);
            }

        }
        return offspring;

    }

}
