/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.hp.jmetal.problem;

import com.google.common.collect.Lists;

import java.util.List;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import edu.ufpr.hp.protein.domain.AminoAcid;
import edu.ufpr.hp.protein.energy.EnergyFunction;
import edu.ufpr.hp.protein.utils.ProteinBuilder;

/**
 *
 *
 * @author Vidal
 */
public class PSPProblem extends AbstractIntegerProblem {

    private ProteinBuilder proteinBuilder;
    private String aminoAcidSequence;
    private double alpha;
    private double beta;

    private int evaluationCount = 0;

    private int generationsCount = 0;

    private double bestFitnessPerGen = -1.1;

    private double bestFitness = -1.1;

    private int bestFitnessCollisions = 0;

    private int populationSize = 100;

    public PSPProblem(int numberOfObjectives, int numberOfVariables, int lowerLimit, int upperLimit,
        String aminoAcidSequence, double alpha, double beta) {
        setNumberOfObjectives(numberOfObjectives);
        setNumberOfVariables(numberOfVariables);
        setNumberOfConstraints(0);
        setName("PSPProblem");

        List<Integer> lowerLimits = Lists.newArrayListWithExpectedSize(numberOfVariables);
        List<Integer> upperLimits = Lists.newArrayListWithExpectedSize(numberOfVariables);
        for (int i = 0; i < numberOfVariables; i++) {
            lowerLimits.add(lowerLimit);
            upperLimits.add(upperLimit);
        }
        setLowerLimit(lowerLimits);
        setUpperLimit(upperLimits);
        this.proteinBuilder = new ProteinBuilder();
        this.aminoAcidSequence = aminoAcidSequence;
        this.alpha = alpha;
        this.beta = beta;

    }

    public void evaluate(IntegerSolution solution) {

        // This 40 should not be necessary
        proteinBuilder.buildProteinAminoAcidList(solution, aminoAcidSequence, 40);
        List<AminoAcid> aminoAcidsList = proteinBuilder.getAminoAcidsList();

        int topologyContacts = EnergyFunction.getTopologyContacts(aminoAcidsList).size();
        int collisions = EnergyFunction.getCollisionsCount(aminoAcidsList);

        double fitness = calculateFitness(topologyContacts, collisions);

        solution.setObjective(0, fitness * -1);

        if (fitness > bestFitnessPerGen) {
            bestFitnessPerGen = fitness;
        }

        if (fitness > bestFitness) {
            bestFitness = fitness;
            bestFitnessCollisions = collisions;
        }

        if (evaluationCount % populationSize == 0) {
            System.out.print("Generation: " + generationsCount + "; ");
            System.out.println(bestFitnessPerGen + "; collisions: " + collisions);

            bestFitnessPerGen = -1.1;
            generationsCount++;
        }
        solution.setObjective(0, fitness * -1);
        evaluationCount++;
    }

    private double calculateFitness(int topologyContacts, int collisions) {

        double aux1 = topologyContacts * alpha;
        double aux2 = collisions * beta;
        return aux1 - aux2;
    }

}
