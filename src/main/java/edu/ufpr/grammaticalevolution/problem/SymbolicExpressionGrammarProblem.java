package edu.ufpr.grammaticalevolution.problem;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import edu.ufpr.grammaticalevolution.mapper.impl.SymbolicExpressionGrammarMapper;
import edu.ufpr.grammaticalevolution.solution.impl.VariableIntegerSolution;

/**
 *
 * @author thaina
 */
public class SymbolicExpressionGrammarProblem extends AbstractGrammaticalEvolutionProblem<String> {

    private final List<Double> testCases;
    private final String expectedFunction;

    private double bestFitness = Double.MAX_VALUE;
    private String bestSolution = "";
    private int generations = 0;
    private int populationSize = 100;
    private int evaluations = 0;

    public SymbolicExpressionGrammarProblem(String file, int minCondons, int maxCondons, List<Double> testCases,
        String expectedFunction) {
        super(minCondons, maxCondons, new SymbolicExpressionGrammarMapper(), file);
        this.testCases = testCases;
        this.expectedFunction = expectedFunction;
        setNumberOfObjectives(1);
    }

    @Override
    public void evaluate(VariableIntegerSolution solution) {

        String function = mapper.interpret(solution.getVariables());
        double fitness = calculate(function);

        if (fitness < bestFitness) {
            bestFitness = fitness;
            bestSolution = function;

        }

        if (evaluations % populationSize == 0) {
            System.out.print("Generation: " + generations + "; ");
            System.out.println("Best fitness found so far: " + bestFitness + "; " + bestSolution);

            generations++;
        }

        solution.setObjective(0, fitness);
        evaluations++;
    }

    public double calculate(String function) {

        double fitness = 0;
        try {

            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");

            for (Double testCase : testCases) {
                String genFunction = function.replaceAll("X", testCase.toString());
                String expFunction = expectedFunction.replaceAll("X", testCase.toString());
                double result = (double) engine.eval(genFunction);
                double expectedResult = (double) engine.eval(expFunction);
                double diff = Math.abs(expectedResult - result);
                fitness += diff;
            }

        } catch (ScriptException ex) {
            Logger.getLogger(SymbolicExpressionGrammarProblem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fitness;
    }
}
