/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.jmetal.lowlevelheuristics.operators;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.SegmentMutationOperator;
import edu.ufpr.hp.protein.utils.MovementEnum;

/**
 *
 *
 * @author Vidal
 */
public class SegmentMutationOperatorTest {

    private SegmentMutationOperator operator;
    private JMetalRandom random;

    @Before
    public void setup() {

        random = JMetalRandom.getInstance();
        random.setSeed(100);
        operator = new SegmentMutationOperator(1.0, random);
    }

    /**
     * <pre>
     *F,F,L,L,U,U,U,L,L,L,D,D,U,D,D,L,F,L,L,
     *F,F,L,L,U,U,D,D,U,D,F,L,L,D,D,L,F,L,L,
     *             ------------
     * </pre>
     */
    @Test
    public void testOperator19Genes() {

        SimpleIntegerProblem problem = new SimpleIntegerProblem(1, 19, 0, 4);
        DefaultIntegerSolution parent1 = new DefaultIntegerSolution(problem);

        MovementEnum[] actualParentMoves = new MovementEnum[parent1.getNumberOfVariables()];
        for (int i = 0; i < parent1.getNumberOfVariables(); i++) {
            Integer variableValue = parent1.getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualParentMoves[i] = moveFromInteger;
        }

        IntegerSolution offspring = operator.execute(parent1);
        MovementEnum[] actualOffspringMoves = new MovementEnum[offspring.getNumberOfVariables()];
        for (int i = 0; i < offspring.getNumberOfVariables(); i++) {
            Integer variableValue = offspring.getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualOffspringMoves[i] = moveFromInteger;
        }
        String parent = Arrays.toString(actualParentMoves).replace(" ", "");
        System.out.println(parent);

        String offspringStr = Arrays.toString(actualOffspringMoves).replace(" ", "");
        System.out.println(offspringStr);

        Assert.assertEquals("[F,F,R,L,U,U,U,L,L,L,D,D,U,D,D,R,F,L,L]", parent);

        Assert.assertEquals("[F,F,R,L,U,U,D,D,U,D,F,L,R,D,D,R,F,L,L]", offspringStr);

    }
}
