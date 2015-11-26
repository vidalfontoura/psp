/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.hp.jmetal.ga;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.LoopMoveOperator;
import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.TwoPointsCrossover;
import edu.ufpr.hp.jmetal.problem.PSPProblem;

/**
 *
 *
 * @author Vidal
 */
public class GenerationalGeneticAlgorithmPSPProblemRunner {

    public static void main(String[] args) throws Exception {

        JMetalRandom.getInstance().setSeed(2);
        // String aminoAcidSequence =
        // "HPPHHHPPHHHPPHHPHHHHHPPPHHHHPPHHHHHHHPHHHHPP";
        String aminoAcidSequence = "HHHHPHHPHHHHHPPHPPHHPPHPPPPPPHPPHPPPHPPHHPPHHHPH";
        int numberOfObjectives = 1;
        int numberOfVariables = aminoAcidSequence.length() - 1;
        int lowerLimit = 0;
        int upperLimit = 4;
        double alpha = 1.0;
        double beta = 1.0;

        int maxEvaluations = 200000;
        int populationSize = 1000;

        Algorithm<IntegerSolution> algorithm;
        Problem pspProblem = new PSPProblem(numberOfObjectives, numberOfVariables, lowerLimit, upperLimit,
            aminoAcidSequence, alpha, beta);

        JMetalRandom jMetalRandom = JMetalRandom.getInstance();

        CrossoverOperator<IntegerSolution> crossoverOperator = new TwoPointsCrossover(1, jMetalRandom);
        MutationOperator<IntegerSolution> mutationOperator = new LoopMoveOperator(1, jMetalRandom);
        SelectionOperator<List<IntegerSolution>, IntegerSolution> selectionOperator =
            new BinaryTournamentSelection<IntegerSolution>();

        algorithm = new GeneticAlgorithmBuilder<IntegerSolution>(pspProblem, crossoverOperator, mutationOperator)
            .setPopulationSize(populationSize).setMaxEvaluations(maxEvaluations).setSelectionOperator(selectionOperator)
            .build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

        IntegerSolution solution = algorithm.getResult();
        List<IntegerSolution> population = new ArrayList<>(1);
        population.add(solution);

        long computingTime = algorithmRunner.getComputingTime();

        new SolutionSetOutput.Printer(population).setSeparator("\t")
            .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
            .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv")).print();

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    }
}
