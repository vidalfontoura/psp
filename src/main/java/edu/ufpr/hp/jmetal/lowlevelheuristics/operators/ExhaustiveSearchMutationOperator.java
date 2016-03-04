/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.hp.jmetal.lowlevelheuristics.operators;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.hp.protein.domain.AminoAcid;
import edu.ufpr.hp.protein.domain.TopologyContact;
import edu.ufpr.hp.protein.energy.EnergyFunction;
import edu.ufpr.hp.protein.utils.ProteinBuilder;

/**
 *
 *
 * @author Vidal
 */
public class ExhaustiveSearchMutationOperator implements MutationOperator<IntegerSolution> {

    private double mutationProbability;
    private JMetalRandom randomGenerator;
    private String aminoAcidSequence;
    private int aminoAcidDistance;

    private ProteinBuilder proteinBuilder = new ProteinBuilder();

    public ExhaustiveSearchMutationOperator(double mutationProbability, JMetalRandom randomGenerator,
        String aminoAcidSequence, int aminoAcidDistance) {
        this.mutationProbability = mutationProbability;
        this.randomGenerator = randomGenerator;
        this.aminoAcidSequence = aminoAcidSequence;
        this.aminoAcidDistance = aminoAcidDistance;
    }

    public IntegerSolution execute(IntegerSolution source) {

        if (source == null) {
            throw new JMetalException("Null parameter");
        }
        return applySegmentMutation(mutationProbability, source);
    }

    private IntegerSolution applySegmentMutation(double probability, IntegerSolution source) {

        Map<Integer, Integer> directionsFitness = new TreeMap<>((o1, o2) -> o2.compareTo(o1));
        IntegerSolution offspring = (IntegerSolution) source.copy();

        if (randomGenerator.nextDouble() < probability) {
            int numberOfVariables = offspring.getNumberOfVariables();

            int gene = randomGenerator.nextInt(0, numberOfVariables - 1);

            int newDirection = -1;
            for (int i = 0; i <= 4; i++) {
                switch (i) {
                    case 0: {
                        newDirection = 0;
                        break;
                    }
                    case 1: {
                        newDirection = 1;
                        break;
                    }
                    case 2: {
                        newDirection = 2;
                        break;
                    }
                    case 3: {
                        newDirection = 3;
                        break;
                    }
                    case 4: {
                        newDirection = 4;
                        break;
                    }

                }

                offspring.setVariableValue(gene, newDirection);
                proteinBuilder.buildProteinAminoAcidList(offspring, aminoAcidSequence, aminoAcidDistance);
                List<AminoAcid> aminoAcidsList = proteinBuilder.getAminoAcidsList();
                Set<TopologyContact> topologyContacts = EnergyFunction.getTopologyContacts(aminoAcidsList);
                int fitness = topologyContacts.size();

                directionsFitness.put(fitness, newDirection);

            }

            Integer bestFitness = directionsFitness.keySet().iterator().next();
            Integer direction = directionsFitness.get(bestFitness);
            offspring.setVariableValue(gene, direction);
        }

        return offspring;

    }

}
