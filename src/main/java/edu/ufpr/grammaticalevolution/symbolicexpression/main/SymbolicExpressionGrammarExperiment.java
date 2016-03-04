package edu.ufpr.grammaticalevolution.symbolicexpression.main;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import edu.ufpr.grammaticalevolution.algorithm.impl.GrammaticalEvolutionAlgorithm;
import edu.ufpr.grammaticalevolution.mapper.impl.SymbolicExpressionGrammarMapper;
import edu.ufpr.grammaticalevolution.operator.crossover.SinglePointCrossoverVariableLength;
import edu.ufpr.grammaticalevolution.operator.mutation.DuplicationMutation;
import edu.ufpr.grammaticalevolution.operator.mutation.IntegerMutation;
import edu.ufpr.grammaticalevolution.operator.mutation.PruneMutation;
import edu.ufpr.grammaticalevolution.problem.SymbolicExpressionGrammarProblem;
import edu.ufpr.grammaticalevolution.solution.impl.VariableIntegerSolution;

/**
 *
 * @author thaina
 */
public class SymbolicExpressionGrammarExperiment {

    public static void main(String[] args) {

        // X - X * X / (X + X + (X * X))
        // 1 = 0,666666667
        // 2 = 1,5
        // 3 = 2,4
        // 4 = 3,333333333
        // 5 = 4,285714286
        List<Double> testCases = new ArrayList<>();
        testCases.add(1.0);
        testCases.add(2.0);
        testCases.add(3.0);
        testCases.add(4.0);
        testCases.add(5.0);
        testCases.add(6.0);
        testCases.add(7.0);
        testCases.add(8.0);
        testCases.add(9.0);
        testCases.add(10.0);

        String expectedFunction = "X - X * X / ((X + X) * X)";

        // JMetalRandom.getInstance().setSeed();

        SymbolicExpressionGrammarProblem problem =
            new SymbolicExpressionGrammarProblem("/symbolicexpression.bnf", 5, 20, testCases, expectedFunction);
        CrossoverOperator<VariableIntegerSolution> crossover = new SinglePointCrossoverVariableLength(1);
        MutationOperator<VariableIntegerSolution> mutation = new IntegerMutation(0.1);
        SelectionOperator<List<VariableIntegerSolution>, VariableIntegerSolution> selection =
            new BinaryTournamentSelection<VariableIntegerSolution>();

        GrammaticalEvolutionAlgorithm algorithm = new GrammaticalEvolutionAlgorithm(problem, 10000, 100, crossover,
            mutation, selection, new PruneMutation(0.01, 5), new DuplicationMutation(0.01),
            new SequentialSolutionListEvaluator<VariableIntegerSolution>());
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
        VariableIntegerSolution solution = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        SymbolicExpressionGrammarMapper mapper = new SymbolicExpressionGrammarMapper();
        mapper.loadGrammar("/symbolicexpression.bnf");

        System.out.println("Total time of execution: " + computingTime);
        System.out.println("Solution: " + solution.getObjective(0));
        System.out.println("Variables: " + mapper.interpret(solution));
    }
}
