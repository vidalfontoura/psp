/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.hp.jmetal.ga.hyperheruistic;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.Operator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.hp.jmetal.lowlevelheuristics.SimpleLowLevelHeuristic;
import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.ExhaustiveSearchMutationOperator;
import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.LocalMoveOperator;
import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.LoopMoveOperator;
import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.MultiPointsCrossover;
import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.OppositeMoveOperator;
import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.SegmentMutationOperator;
import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.TwoPointsCrossover;
import edu.ufpr.hp.jmetal.problem.PSPProblem;

/**
 *
 *
 * @author Vidal
 */
public class GenerationalGeneticAlgorithmHHPSPProblemRunner {

    public static void main(String[] args) throws Exception {

        JMetalRandom.getInstance().setSeed(9);
        String aminoAcidSequence = "HHHHPHHPHHHHHPPHPPHHPPHPPPPPPHPPHPPPHPPHHPPHHHPH";
        int numberOfObjectives = 1;
        int numberOfVariables = aminoAcidSequence.length() - 1;
        int lowerLimit = 0;
        int upperLimit = 4;
        double alpha = 1.0;
        double beta = 3.0;

        int maxEvaluations = 1000000;
        int populationSize = 1000;

        String llhComparator = "ChoiceFunction";

        SequentialSolutionListEvaluator<IntegerSolution> evaluator =
            new SequentialSolutionListEvaluator<IntegerSolution>();

        Algorithm<IntegerSolution> algorithm;
        Problem pspProblem = new PSPProblem(numberOfObjectives, numberOfVariables, lowerLimit, upperLimit,
            aminoAcidSequence, alpha, beta);

        JMetalRandom jMetalRandom = JMetalRandom.getInstance();

        SelectionOperator<List<IntegerSolution>, IntegerSolution> selectionOperator =
            new BinaryTournamentSelection<IntegerSolution>();

        algorithm = new GenerationalGeneticAlgorithmHyperHeuristic(pspProblem, maxEvaluations, populationSize,
            createLowLevelHeuristics(aminoAcidSequence), selectionOperator, evaluator, llhComparator);

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

    private static List<SimpleLowLevelHeuristic> createLowLevelHeuristics(String aminoAcidSequence) {

        double alpha = 1.0;
        double beta = 0.05;

        JMetalRandom jMetalRandom = JMetalRandom.getInstance();
        Operator twoPointsCrossover = new TwoPointsCrossover(1.0, jMetalRandom);
        SimpleLowLevelHeuristic simpleLowLevelHeuristic0 =
            new SimpleLowLevelHeuristic(twoPointsCrossover, "TwoPoints", alpha, beta);

        Operator multiPointsCrossover = new MultiPointsCrossover(1.0, jMetalRandom);
        SimpleLowLevelHeuristic simpleLowLevelHeuristic1 =
            new SimpleLowLevelHeuristic(multiPointsCrossover, "MultiPoints", alpha, beta);

        Operator segmentMutation = new SegmentMutationOperator(1.0, jMetalRandom);
        SimpleLowLevelHeuristic simpleLowLevelHeuristic2 =
            new SimpleLowLevelHeuristic(segmentMutation, "SegmentMutation", alpha, beta);

        Operator localMove = new LocalMoveOperator(1.0, jMetalRandom);
        SimpleLowLevelHeuristic simpleLowLevelHeuristic3 =
            new SimpleLowLevelHeuristic(localMove, "LocalMove", alpha, beta);

        Operator loopMove = new LoopMoveOperator(1.0, jMetalRandom);
        SimpleLowLevelHeuristic simpleLowLevelHeuristic4 =
            new SimpleLowLevelHeuristic(loopMove, "LoopMove", alpha, beta);

        Operator opposite = new OppositeMoveOperator(1.0, jMetalRandom);
        SimpleLowLevelHeuristic simpleLowLevelHeuristic5 =
            new SimpleLowLevelHeuristic(opposite, "OppositeMove", alpha, beta);

        Operator exhaustive = new ExhaustiveSearchMutationOperator(1.0, jMetalRandom, aminoAcidSequence, 40);
        SimpleLowLevelHeuristic simpleLowLevelHeuristic6 =
            new SimpleLowLevelHeuristic(exhaustive, "OppositeMove", alpha, beta);

        return Lists.newArrayList(simpleLowLevelHeuristic0, simpleLowLevelHeuristic1, simpleLowLevelHeuristic2,
            simpleLowLevelHeuristic3, simpleLowLevelHeuristic4, simpleLowLevelHeuristic5, simpleLowLevelHeuristic6);

    }
}
