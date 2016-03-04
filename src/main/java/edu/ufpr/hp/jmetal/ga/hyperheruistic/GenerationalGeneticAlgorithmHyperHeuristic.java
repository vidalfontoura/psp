package edu.ufpr.hp.jmetal.ga.hyperheruistic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.comparator.ObjectiveComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.hp.jmetal.lowlevelheuristics.SimpleLowLevelHeuristic;
import edu.ufpr.hp.jmetal.lowlevelheuristics.SimpleLowLevelHeuristicComparatorFactory;

/**
 * @author vfontoura
 */
public class GenerationalGeneticAlgorithmHyperHeuristic
    extends AbstractGeneticAlgorithm<IntegerSolution, IntegerSolution> {

    private Comparator<IntegerSolution> comparator;

    private Comparator<SimpleLowLevelHeuristic> lowlevelHeuristicComparator;
    private int maxEvaluations;
    private int populationSize;
    private int evaluations;

    private Problem<IntegerSolution> problem;

    private SolutionListEvaluator<IntegerSolution> evaluator;

    private List<SimpleLowLevelHeuristic> lowLevelHeuristics;

    /**
     * Constructor
     */
    public GenerationalGeneticAlgorithmHyperHeuristic(Problem<IntegerSolution> problem, int maxEvaluations,
        int populationSize, List<SimpleLowLevelHeuristic> lowLevelHeuristics,
        SelectionOperator<List<IntegerSolution>, IntegerSolution> selectionOperator,
        SolutionListEvaluator<IntegerSolution> evaluator, String llhComparator) {
        this.problem = problem;
        this.maxEvaluations = maxEvaluations;
        this.populationSize = populationSize;
        this.lowLevelHeuristics = lowLevelHeuristics;
        this.selectionOperator = selectionOperator;

        this.evaluator = evaluator;

        comparator = new ObjectiveComparator<IntegerSolution>(0);
        lowlevelHeuristicComparator = SimpleLowLevelHeuristicComparatorFactory.createComparator(llhComparator);
    }

    @Override
    protected boolean isStoppingConditionReached() {

        return (evaluations >= maxEvaluations);
    }

    @Override
    protected List<IntegerSolution> createInitialPopulation() {

        List<IntegerSolution> population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            IntegerSolution newIndividual = problem.createSolution();
            population.add(newIndividual);
        }
        return population;
    }

    @Override
    protected List<IntegerSolution> replacement(List<IntegerSolution> population,
                                                List<IntegerSolution> offspringPopulation) {

        Collections.sort(population, comparator);
        offspringPopulation.add(population.get(0));
        offspringPopulation.add(population.get(1));
        Collections.sort(offspringPopulation, comparator);
        offspringPopulation.remove(offspringPopulation.size() - 1);
        offspringPopulation.remove(offspringPopulation.size() - 1);

        return offspringPopulation;
    }

    @Override
    protected List<IntegerSolution> reproduction(List<IntegerSolution> matingPopulation) {

        List<IntegerSolution> offspringPopulation = new ArrayList<>(matingPopulation.size() + 2);
        for (int i = 0; i < populationSize; i += 2) {
            List<IntegerSolution> parents = new ArrayList<>(2);
            parents.add(matingPopulation.get(i));
            parents.add(matingPopulation.get(i + 1));

            SimpleLowLevelHeuristic lowLevelHeuristic = getApplyingHeuristic(lowlevelHeuristicComparator);

            List<IntegerSolution> offspring = lowLevelHeuristic.execute(parents);
            offspringPopulation.add(offspring.get(0));

            if (offspring.size() > 1) {
                offspringPopulation.add(offspring.get(1));
            } else {
                offspringPopulation.add(parents.get(1));
            }

            lowLevelHeuristic.updateScore(parents, offspringPopulation);

            for (SimpleLowLevelHeuristic heuristic : lowLevelHeuristics) {
                if (!heuristic.equals(lowLevelHeuristic)) {
                    heuristic.notExecuted();
                }
            }

        }
        return offspringPopulation;
    }

    @Override
    protected List<IntegerSolution> selection(List<IntegerSolution> population) {

        List<IntegerSolution> matingPopulation = new ArrayList<>(population.size());
        for (int i = 0; i < populationSize; i++) {
            IntegerSolution solution = selectionOperator.execute(population);
            matingPopulation.add(solution);
        }

        return matingPopulation;
    }

    @Override
    protected List<IntegerSolution> evaluatePopulation(List<IntegerSolution> population) {

        population = evaluator.evaluate(population, problem);

        return population;
    }

    @Override
    public IntegerSolution getResult() {

        Collections.sort(getPopulation(), comparator);
        return getPopulation().get(0);
    }

    @Override
    public void initProgress() {

        evaluations = populationSize;
    }

    @Override
    public void updateProgress() {

        evaluations += populationSize;
    }

    private SimpleLowLevelHeuristic getApplyingHeuristic(Comparator<SimpleLowLevelHeuristic> comparator) {

        // if
        // (getInputParameter("heuristicFunction").equals(LowLevelHeuristic.RANDOM)
        // || comparator == null) {
        // return lowLevelHeuristics.get(PseudoRandom.randInt(0,
        // lowLevelHeuristics.size() - 1));
        // } else {
        if (comparator != null) {
            // Choice
            List<SimpleLowLevelHeuristic> allLowLevelHeuristics = new ArrayList<>(lowLevelHeuristics);
            Collections.sort(allLowLevelHeuristics, lowlevelHeuristicComparator);
            List<SimpleLowLevelHeuristic> applyingHeuristics = new ArrayList<>();

            // Find the best tied heuristics
            Iterator<SimpleLowLevelHeuristic> iterator = allLowLevelHeuristics.iterator();
            SimpleLowLevelHeuristic heuristic;
            SimpleLowLevelHeuristic nextHeuristic = iterator.next();
            do {
                heuristic = nextHeuristic;
                applyingHeuristics.add(heuristic);
            } while (iterator.hasNext() && comparator.compare(heuristic, nextHeuristic = iterator.next()) == 0);

            return applyingHeuristics.get(JMetalRandom.getInstance().nextInt(0, applyingHeuristics.size() - 1));
            // }
        } else {
            // Random
            return lowLevelHeuristics.get(JMetalRandom.getInstance().nextInt(0, lowLevelHeuristics.size() - 1));
        }
    }
}
