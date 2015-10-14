package edu.ufpr.grammaticalevolution.algorithm;

import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;

import edu.ufpr.grammaticalevolution.operator.mutation.DuplicationMutation;
import edu.ufpr.grammaticalevolution.operator.mutation.PruneMutation;
import edu.ufpr.grammaticalevolution.solution.impl.VariableIntegerSolution;

/**
 * Created by ajnebro on 26/10/14.
 */
public abstract class AbstractGrammaticalEvolutionAlgorithm<S extends VariableIntegerSolution, R>
    extends AbstractGeneticAlgorithm<VariableIntegerSolution, R> {

    protected PruneMutation pruneMutationOperator;
    protected DuplicationMutation duplicationMutationOperator;

}
