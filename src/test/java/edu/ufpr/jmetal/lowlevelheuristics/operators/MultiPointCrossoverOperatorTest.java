/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.jmetal.lowlevelheuristics.operators;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.MultiPointsCrossover;
import edu.ufpr.hp.protein.utils.MovementEnum;

/**
 *
 *
 * @author Vidal
 */
public class MultiPointCrossoverOperatorTest {

    private MultiPointsCrossover operator;
    private JMetalRandom random;

    @Before
    public void setup() {

        random = JMetalRandom.getInstance();
        random.setSeed(100);
        operator = new MultiPointsCrossover(1.0, random);
    }

    @Test
    public void testOperator19Genes() {

        SimpleIntegerProblem problem = new SimpleIntegerProblem(1, 19, 0, 4);
        DefaultIntegerSolution parent1 = new DefaultIntegerSolution(problem);
        DefaultIntegerSolution parent2 = new DefaultIntegerSolution(problem);

        MovementEnum[] actualParent1Moves = new MovementEnum[parent1.getNumberOfVariables()];
        for (int i = 0; i < parent1.getNumberOfVariables(); i++) {
            Integer variableValue = parent1.getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualParent1Moves[i] = moveFromInteger;
        }

        MovementEnum[] actualParent2Moves = new MovementEnum[parent2.getNumberOfVariables()];
        for (int i = 0; i < parent2.getNumberOfVariables(); i++) {
            Integer variableValue = parent2.getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualParent2Moves[i] = moveFromInteger;
        }

        List<IntegerSolution> offspring = operator.execute(Lists.newArrayList(parent1, parent2));

        String parent1Str = Arrays.toString(actualParent1Moves).replace(" ", "");
        System.out.println(parent1Str);

        String parent2Str = Arrays.toString(actualParent2Moves).replace(" ", "");
        System.out.println(parent2Str);

        MovementEnum[] actualOffspring1Moves = new MovementEnum[parent2.getNumberOfVariables()];
        for (int i = 0; i < offspring.get(0).getNumberOfVariables(); i++) {
            Integer variableValue = offspring.get(0).getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualOffspring1Moves[i] = moveFromInteger;
        }

        MovementEnum[] actualOffspring2Moves = new MovementEnum[parent2.getNumberOfVariables()];
        for (int i = 0; i < offspring.get(1).getNumberOfVariables(); i++) {
            Integer variableValue = offspring.get(1).getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualOffspring2Moves[i] = moveFromInteger;
        }

        String offspring1Str = Arrays.toString(actualOffspring1Moves).replace(" ", "");
        System.out.println(offspring1Str);

        String offspring2Str = Arrays.toString(actualOffspring2Moves).replace(" ", "");
        System.out.println(offspring2Str);

        Assert.assertEquals("[F,F,R,L,U,U,U,L,L,L,D,D,U,D,D,R,F,L,L]", parent1Str);
        Assert.assertEquals("[R,L,L,F,D,D,U,L,D,F,L,R,F,U,L,R,R,D,L]", parent2Str);

        Assert.assertEquals("[R,L,L,F,D,D,U,L,D,F,L,R,F,D,D,R,R,D,L]", offspring1Str);
        Assert.assertEquals("[F,F,R,L,U,U,U,L,L,L,D,D,U,U,L,R,F,L,L]", offspring2Str);

    }

    @Test
    public void testOperator40Genes() {

        SimpleIntegerProblem problem = new SimpleIntegerProblem(1, 40, 0, 4);
        DefaultIntegerSolution parent1 = new DefaultIntegerSolution(problem);
        DefaultIntegerSolution parent2 = new DefaultIntegerSolution(problem);

        MovementEnum[] actualParent1Moves = new MovementEnum[parent1.getNumberOfVariables()];
        for (int i = 0; i < parent1.getNumberOfVariables(); i++) {
            Integer variableValue = parent1.getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualParent1Moves[i] = moveFromInteger;
        }

        MovementEnum[] actualParent2Moves = new MovementEnum[parent2.getNumberOfVariables()];
        for (int i = 0; i < parent2.getNumberOfVariables(); i++) {
            Integer variableValue = parent2.getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualParent2Moves[i] = moveFromInteger;
        }

        List<IntegerSolution> offspring = operator.execute(Lists.newArrayList(parent1, parent2));

        String parent1Str = Arrays.toString(actualParent1Moves).replace(" ", "");
        System.out.println(parent1Str);

        String parent2Str = Arrays.toString(actualParent2Moves).replace(" ", "");
        System.out.println(parent2Str);

        MovementEnum[] actualOffspring1Moves = new MovementEnum[parent2.getNumberOfVariables()];
        for (int i = 0; i < offspring.get(0).getNumberOfVariables(); i++) {
            Integer variableValue = offspring.get(0).getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualOffspring1Moves[i] = moveFromInteger;
        }

        MovementEnum[] actualOffspring2Moves = new MovementEnum[parent2.getNumberOfVariables()];
        for (int i = 0; i < offspring.get(1).getNumberOfVariables(); i++) {
            Integer variableValue = offspring.get(1).getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualOffspring2Moves[i] = moveFromInteger;
        }

        String offspring1Str = Arrays.toString(actualOffspring1Moves).replace(" ", "");
        System.out.println(offspring1Str);

        String offspring2Str = Arrays.toString(actualOffspring2Moves).replace(" ", "");
        System.out.println(offspring2Str);

        Assert.assertEquals("[F,F,R,L,U,U,U,L,L,L,D,D,U,D,D,R,F,L,L,R,L,L,F,D,D,U,L,D,F,L,R,F,U,L,R,R,D,L,U,R]",
            parent1Str);
        Assert.assertEquals("[F,F,R,U,U,D,F,F,D,F,L,U,R,L,R,F,F,D,F,L,U,D,D,R,L,R,L,D,U,U,F,U,F,U,R,L,D,D,D,L]",
            parent2Str);

        Assert.assertEquals("[F,F,R,U,U,D,F,F,D,F,L,U,R,L,D,R,F,L,L,R,L,L,F,D,D,U,L,D,F,L,R,U,F,L,R,R,D,L,D,L]",
            offspring1Str);
        Assert.assertEquals("[F,F,R,L,U,U,U,L,L,L,D,D,U,D,R,F,F,D,F,L,U,D,D,R,L,R,L,D,U,U,F,F,U,U,R,L,D,D,U,R]",
            offspring2Str);

    }

    @Test
    public void testOperator100Genes() {

        SimpleIntegerProblem problem = new SimpleIntegerProblem(1, 100, 0, 4);
        DefaultIntegerSolution parent1 = new DefaultIntegerSolution(problem);
        DefaultIntegerSolution parent2 = new DefaultIntegerSolution(problem);

        MovementEnum[] actualParent1Moves = new MovementEnum[parent1.getNumberOfVariables()];
        for (int i = 0; i < parent1.getNumberOfVariables(); i++) {
            Integer variableValue = parent1.getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualParent1Moves[i] = moveFromInteger;
        }

        MovementEnum[] actualParent2Moves = new MovementEnum[parent2.getNumberOfVariables()];
        for (int i = 0; i < parent2.getNumberOfVariables(); i++) {
            Integer variableValue = parent2.getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualParent2Moves[i] = moveFromInteger;
        }

        List<IntegerSolution> offspring = operator.execute(Lists.newArrayList(parent1, parent2));

        String parent1Str = Arrays.toString(actualParent1Moves).replace(" ", "");
        System.out.println(parent1Str);

        String parent2Str = Arrays.toString(actualParent2Moves).replace(" ", "");
        System.out.println(parent2Str);

        MovementEnum[] actualOffspring1Moves = new MovementEnum[parent2.getNumberOfVariables()];
        for (int i = 0; i < offspring.get(0).getNumberOfVariables(); i++) {
            Integer variableValue = offspring.get(0).getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualOffspring1Moves[i] = moveFromInteger;
        }

        MovementEnum[] actualOffspring2Moves = new MovementEnum[parent2.getNumberOfVariables()];
        for (int i = 0; i < offspring.get(1).getNumberOfVariables(); i++) {
            Integer variableValue = offspring.get(1).getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualOffspring2Moves[i] = moveFromInteger;
        }

        String offspring1Str = Arrays.toString(actualOffspring1Moves).replace(" ", "");
        System.out.println(offspring1Str);

        String offspring2Str = Arrays.toString(actualOffspring2Moves).replace(" ", "");
        System.out.println(offspring2Str);

        Assert.assertEquals(
            "[F,F,R,L,U,U,U,L,L,L,D,D,U,D,D,R,F,L,L,R,L,L,F,D,D,U,L,D,F,L,R,F,U,L,R,R,D,L,U,R,F,F,R,U,U,D,F,F,D,F,L,U,R,L,R,F,F,D,F,L,U,D,D,R,L,R,L,D,U,U,F,U,F,U,R,L,D,D,D,L,U,U,L,L,R,U,R,D,L,L,U,L,F,L,R,R,R,U,F,D]",
            parent1Str);
        Assert.assertEquals(
            "[F,U,U,R,D,D,L,U,R,R,R,L,F,F,L,D,R,D,U,R,R,D,F,L,D,L,R,L,F,F,L,U,R,U,F,F,U,F,L,U,L,U,L,L,R,U,D,D,D,D,R,U,F,L,R,F,L,D,F,U,L,L,R,F,U,U,F,U,F,R,R,L,D,R,R,D,U,L,F,R,D,F,R,F,R,R,R,D,R,D,L,L,L,U,D,U,L,U,F,L]",
            parent2Str);

        Assert.assertEquals(
            "[F,U,R,L,U,U,U,L,L,L,D,L,F,F,D,R,F,L,L,R,L,L,F,D,D,L,R,L,F,L,R,F,U,L,R,F,U,F,U,R,F,F,R,U,U,D,F,F,D,F,L,U,R,L,R,F,F,D,F,L,U,D,D,R,L,R,L,D,U,U,F,U,F,U,R,L,D,D,D,R,D,F,R,F,R,R,R,D,R,D,L,L,L,U,D,U,L,U,F,L]",
            offspring1Str);
        Assert.assertEquals(
            "[F,F,U,R,D,D,L,U,R,R,R,D,U,D,L,D,R,D,U,R,R,D,F,L,D,U,L,D,F,F,L,U,R,U,F,R,D,L,L,U,L,U,L,L,R,U,D,D,D,D,R,U,F,L,R,F,L,D,F,U,L,L,R,F,U,U,F,U,F,R,R,L,D,R,R,D,U,L,F,L,U,U,L,L,R,U,R,D,L,L,U,L,F,L,R,R,R,U,F,D]",
            offspring2Str);

    }
}
