package edu.ufpr.grammaticalevolution.operator.crossover;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.grammaticalevolution.solution.impl.VariableIntegerSolution;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 *
 *          This class implements a single point crossover operator.
 */
public class SinglePointCrossoverVariableLength implements CrossoverOperator<VariableIntegerSolution> {

    private double crossoverProbability;
    private JMetalRandom randomGenerator;

    /**
     * Constructor
     */
    public SinglePointCrossoverVariableLength(double crossoverProbability) {
        if (crossoverProbability < 0) {
            throw new JMetalException("Crossover probability is negative: " + crossoverProbability);
        }
        this.crossoverProbability = crossoverProbability;
        randomGenerator = JMetalRandom.getInstance();
    }

    /* Getter */
    public double getCrossoverProbability() {

        return crossoverProbability;
    }

    @Override
    public List<VariableIntegerSolution> execute(List<VariableIntegerSolution> solutions) {

        if (solutions == null) {
            throw new JMetalException("Null parameter");
        } else if (solutions.size() != 2) {
            throw new JMetalException("There must be two parents instead of " + solutions.size());
        }

        return doCrossover(crossoverProbability, solutions.get(0), solutions.get(1));
    }

    /**
     * Perform the crossover operation.
     *
     * @param probability Crossover setProbability
     * @param parent1 The first parent
     * @param parent2 The second parent
     * @return An array containing the two offspring
     */
    public List<VariableIntegerSolution> doCrossover(double probability, VariableIntegerSolution parent1,
                                                     VariableIntegerSolution parent2) {

        VariableIntegerSolution offspring1 = parent1.copy();

        VariableIntegerSolution offspring2 = parent2.copy();

        if (randomGenerator.nextDouble() < probability) {
            offspring1.clearVariables();
            offspring2.clearVariables();
            // 1. Get the total number of bits
            int totalNumberOfGenesParent1 = parent1.getNumberOfVariables();
            int totalNumberOfGenesParent2 = parent2.getNumberOfVariables();

            // 2. Calculate the point to make the crossover
            int crossoverPointParent1 = randomGenerator.nextInt(0, totalNumberOfGenesParent1 - 1);
            int crossoverPointParent2 = randomGenerator.nextInt(0, totalNumberOfGenesParent2 - 1);

            for (int i = 0; i < crossoverPointParent1; i++) {
                offspring1.addVariable(parent1.getVariableValue(i));
            }
            for (int i = 0; i < crossoverPointParent2; i++) {
                offspring2.addVariable(parent2.getVariableValue(i));
            }
            for (int i = crossoverPointParent1; i < totalNumberOfGenesParent1; i++) {
                offspring2.addVariable(parent1.getVariableValue(i));
            }
            for (int i = crossoverPointParent2; i < totalNumberOfGenesParent2; i++) {
                offspring1.addVariable(parent2.getVariableValue(i));
            }
        }

        List<VariableIntegerSolution> offspring = new ArrayList<>(2);
        offspring.add(offspring1);
        offspring.add(offspring2);
        return offspring;
    }
}
