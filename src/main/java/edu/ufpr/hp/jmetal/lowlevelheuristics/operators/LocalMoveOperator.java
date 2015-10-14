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
 * Troca a direções de dois genes aleatórios consecutivos. Existem alguma
 * condições a serem satisfeitas. Exemplo: As novas direções não podem criar
 * movimentos redundantes. Este operador introduz um “movimento de esquina”.
 * Mencionam que este operador foi introduzido por outro trabalho o qual,
 * utiliza uma buscal local utilizando um parâmetro T que serve para ajustar a
 * aceitação de bad moves ou não (possibilitando balancear convergência e
 * diversidade).
 *
 * @author vfontoura
 * 
 *         TODO: Verifiy Accpetance criterion
 * 
 */
public class LocalMoveOperator implements MutationOperator<IntegerSolution> {

    private double mutationProbability;
    private JMetalRandom randomGenerator;

    public LocalMoveOperator(double mutationProbability, JMetalRandom randomGenerator) {
        this.mutationProbability = mutationProbability;
        this.randomGenerator = randomGenerator;
    }

    public IntegerSolution execute(IntegerSolution source) {

        if (source == null) {
            throw new JMetalException("Null parameter");
        }
        return applyLocalMove(mutationProbability, source);

    }

    public IntegerSolution applyLocalMove(double probability, IntegerSolution source) {

        IntegerSolution offspring = (IntegerSolution) source.copy();

        if (randomGenerator.nextDouble() < probability) {
            int numberOfVariables = offspring.getNumberOfVariables();

            int localMovePoint1 = randomGenerator.nextInt(0, numberOfVariables - 1);
            int localMovePoint2 = localMovePoint1++;

            Integer variableValue1 = offspring.getVariableValue(localMovePoint1);
            Integer variableValue2 = offspring.getVariableValue(localMovePoint2);

            offspring.setVariableValue(localMovePoint1, variableValue2);
            offspring.setVariableValue(localMovePoint2, variableValue1);

        }
        return offspring;

    }

}
