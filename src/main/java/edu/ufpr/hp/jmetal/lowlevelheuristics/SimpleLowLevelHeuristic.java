package edu.ufpr.hp.jmetal.lowlevelheuristics;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.Operator;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.DominanceComparator;

public class SimpleLowLevelHeuristic implements Operator<List<IntegerSolution>, List<IntegerSolution>> {

    public static final String CHOICE_FUNCTION = "ChoiceFunction";
    public static final String RANDOM = "Random";

    private Operator operator;

    private double score;
    private int elapsedTime;

    private double alpha;
    private double beta;

    private int numberOfTimesApplied;

    private String name;

    public SimpleLowLevelHeuristic(Operator<IntegerSolution, IntegerSolution> operator, String name, double alpha,
        double beta) {
        this.operator = operator;
        this.alpha = alpha;
        this.beta = beta;
        this.score = 1.0;
        this.elapsedTime = 0;
        this.name = name;
    }

    public List<IntegerSolution> execute(List<IntegerSolution> source) {

        if (operator instanceof MutationOperator) {
            IntegerSolution solution = source.get(0);
            return Lists.newArrayList(applyMutationOperator(solution));
        } else if (operator instanceof CrossoverOperator) {
            return applyCrossoverOperator(source);

        }
        // TODO: Fix error message
        throw new JMetalException("Error pica");
    }

    private IntegerSolution applyMutationOperator(IntegerSolution solution) {

        IntegerSolution offspring = (IntegerSolution) operator.execute(solution);
        executed();
        return offspring;
    }

    private List<IntegerSolution> applyCrossoverOperator(List<IntegerSolution> solutions) {

        List<IntegerSolution> offspring = (List<IntegerSolution>) operator.execute(solutions);
        executed();
        return offspring;
    }

    public void executed() {

        this.numberOfTimesApplied++;
        updateElapsedTime(true);
    }

    public void notExecuted() {

        updateElapsedTime(false);
    }

    private void updateElapsedTime(boolean executed) {

        this.elapsedTime = (executed) ? 0 : this.elapsedTime + 1;
    }

    public void updateScore(List<IntegerSolution> parents, List<IntegerSolution> offsprings) {

        Comparator<Solution> comparator = new DominanceComparator();
        score = 0.0;
        for (Solution parent : parents) {
            for (Solution offspring : offsprings) {
                score += (comparator.compare(parent, offspring) + 1.0) / 2.0;
            }
        }
        score /= ((double) parents.size() * (double) offsprings.size());
    }

    public double getChoiceFunction() {

        return (alpha * score) + (beta * elapsedTime);
    }

    public void reinitialize() {

        this.score = 1;
        this.elapsedTime = 0;
        this.numberOfTimesApplied = 0;
    }

}
