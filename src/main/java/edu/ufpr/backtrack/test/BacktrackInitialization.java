/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.backtrack.test;

import com.google.common.collect.Lists;

import java.util.List;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.hp.jmetal.problem.PSPProblem;
import edu.ufpr.hp.protein.energy.EnergyFunction;
import edu.ufpr.hp.protein.utils.MovementEnum;
import edu.ufpr.hp.protein.utils.MovementUtils;
import edu.ufpr.hp.protein.utils.ProteinBuilder;

/**
 *
 *
 * @author vfontoura
 */
public class BacktrackInitialization {

    private int[][][] grid;

    private List<MovementEnum> movements;

    private int count;

    private int sequenceLength;

    private JMetalRandom random;

    private List<MovementEnum> solutionMoves;

    private Problem<IntegerSolution> problem;

    public BacktrackInitialization(JMetalRandom random, Problem<IntegerSolution> problem, String sequence) {

        this.sequenceLength = sequence.length();
        this.random = random;
        this.problem = problem;
    }

    public List<IntegerSolution> createPopulationAsIntegerSolution(int amountOfSolutions) {

        List<IntegerSolution> population = Lists.newArrayListWithExpectedSize(amountOfSolutions);

        List<List<MovementEnum>> listMovementEnum = this.createPopulationAsListMovementEnum(amountOfSolutions);

        for (List<MovementEnum> movementEnum : listMovementEnum) {
            IntegerSolution solution = problem.createSolution();
            for (int j = 0; j < movementEnum.size(); j++) {
                int move = movementEnum.get(j).getMove();
                solution.setVariableValue(j, move);
            }
            population.add(solution);
        }

        return population;
    }

    public List<List<MovementEnum>> createPopulationAsListMovementEnum(int amountOfSolutions) {

        List<List<MovementEnum>> population = Lists.newArrayListWithExpectedSize(amountOfSolutions);

        for (int i = 0; i < amountOfSolutions; i++) {

            this.movements = Lists.newArrayList(MovementEnum.values());
            this.count = 0;
            this.grid = createGrid(sequenceLength);
            this.solutionMoves = Lists.newArrayList();

            String lookingAxis = "Z+";
            String headAxis = "Y+";

            int startPoint = grid.length / 2;

            move(startPoint, startPoint, startPoint, lookingAxis, headAxis);

            population.add(solutionMoves);

        }
        return population;
    }

    private boolean move(int x, int y, int z, String lookingAxis, String headAxis) {

        String localLookingAxis = lookingAxis;
        String localHeadAxis = headAxis;

        grid[x][y][z] = count;
        count++;
        if (count == sequenceLength) {
            return true;
        }

        int newX = -1;
        int newY = -1;
        int newZ = -1;

        do {
            if (movements.size() == 0) {
                break;
            }
            int index = random.nextInt(0, movements.size() - 1);
            MovementEnum movement = movements.remove(index);

            Object[] coordinates = fromMoveCodeToCoordinates(lookingAxis, headAxis, movement, x, y, z);

            int xMove = (int) coordinates[0];
            int yMove = (int) coordinates[1];
            int zMove = (int) coordinates[2];

            lookingAxis = (String) coordinates[3];
            headAxis = (String) coordinates[4];

            newX = xMove;
            newY = yMove;
            newZ = zMove;

            if (newX < 0 || newY < 0 || newX >= grid.length || newY >= grid.length || newZ < 0 || newZ >= grid.length) {
                lookingAxis = localLookingAxis;
                headAxis = localHeadAxis;
                continue;
            }
            if (!isEmpty(newX, newY, newZ)) {
                lookingAxis = localLookingAxis;
                headAxis = localHeadAxis;
                continue;
            }

            movements = Lists.newArrayList(MovementEnum.values());
            // It was possible to set point
            if (move(newX, newY, newZ, lookingAxis, headAxis)) {

                solutionMoves.add(movement);

                return true;
            }

        } while (!movements.isEmpty());

        lookingAxis = localLookingAxis;
        headAxis = localHeadAxis;

        movements = Lists.newArrayList(MovementEnum.values());

        grid[x][y][z] = -1;
        count--;

        return false;

    }

    public boolean isEmpty(int x, int y, int z) {

        int i = grid[x][y][z];
        return i == -1 ? true : false;
    }

    public static Object[] fromMoveCodeToCoordinates(String lookingAxis, String headAxis, MovementEnum movement, int x,
                                                     int y, int z) {

        int DISTANCE = 1;

        String newLookingToAxis = lookingAxis;
        String newHeadAxis = headAxis;

        switch (movement) {
            case F: {
                newLookingToAxis = lookingAxis;
                newHeadAxis = headAxis;
                switch (lookingAxis) {
                    case "Z+": {
                        z = z + DISTANCE;
                        break;
                    }
                    case "Z-": {
                        z = z - DISTANCE;
                        break;
                    }
                    case "Y+": {
                        y = y + DISTANCE;
                        break;
                    }
                    case "Y-": {
                        y = y - DISTANCE;
                        break;
                    }
                    case "X+": {
                        x = x + DISTANCE;
                        break;
                    }
                    case "X-": {
                        x = x - DISTANCE;
                        break;
                    }
                }
            }
                break;
            case U: {
                newLookingToAxis = headAxis;
                newHeadAxis = MovementUtils.inverterAxis(lookingAxis);
                switch (headAxis) {
                    case "Y+":
                        y = y + DISTANCE;
                        break;
                    case "Y-":
                        y = y - DISTANCE;
                        break;
                    case "X-":
                        x = x - DISTANCE;
                        break;
                    case "X+":
                        x = x + DISTANCE;
                        break;
                    case "Z+":
                        z = z + DISTANCE;
                        break;
                    case "Z-":
                        z = z - DISTANCE;
                        break;
                }
                break;

            }
            case D: {
                newLookingToAxis = MovementUtils.inverterAxis(headAxis);
                newHeadAxis = lookingAxis;
                switch (headAxis) {
                    case "Y+":
                        y = y - DISTANCE;
                        break;
                    case "Y-":
                        y = y + DISTANCE;
                        break;
                    case "X-":
                        x = x + DISTANCE;
                        break;
                    case "X+":
                        x = x - DISTANCE;
                        break;
                    case "Z+":
                        z = z - DISTANCE;
                        break;
                    case "Z-":
                        z = z + DISTANCE;
                        break;
                }
                break;
            }
            case L: {
                newHeadAxis = headAxis;
                switch (lookingAxis) {
                    case "Z+":
                        switch (headAxis) {
                            case "Y+":
                                newLookingToAxis = "X+";
                                x = x + DISTANCE;
                                break;
                            case "Y-":
                                newLookingToAxis = "X-";
                                x = x - DISTANCE;
                                break;

                            case "X+":
                                newLookingToAxis = "Y-";
                                y = y - DISTANCE;
                                break;
                            case "X-":
                                newLookingToAxis = "Y+";
                                y = y + DISTANCE;
                                break;
                        }
                        break;
                    case "Z-":
                        switch (headAxis) {
                            case "Y+":
                                newLookingToAxis = "X-";
                                x = x - DISTANCE;
                                break;
                            case "Y-":
                                newLookingToAxis = "X+";
                                x = x + DISTANCE;
                                break;

                            case "X-":
                                newLookingToAxis = "Y-";
                                y = y - DISTANCE;
                                break;

                            case "X+":
                                newLookingToAxis = "Y+";
                                y = y + DISTANCE;
                                break;

                        }
                        break;
                    case "Y+":
                        switch (headAxis) {
                            case "X+":
                                newLookingToAxis = "Z+";
                                z = z + DISTANCE;
                                break;
                            case "X-":
                                newLookingToAxis = "Z-";
                                z = z - DISTANCE;
                                break;
                            case "Z+":
                                newLookingToAxis = "X-";
                                x = x - DISTANCE;
                                break;
                            case "Z-":
                                newLookingToAxis = "X+";
                                x = x + DISTANCE;
                                break;
                        }
                        break;
                    case "Y-":
                        switch (headAxis) {
                            case "X+":
                                newLookingToAxis = "Z-";
                                z = z - DISTANCE;
                                break;
                            case "X-":
                                newLookingToAxis = "Z+";
                                z = z + DISTANCE;
                                break;
                            case "Z+":
                                newLookingToAxis = "X+";
                                x = x + DISTANCE;
                                break;
                            case "Z-":
                                newLookingToAxis = "X-";
                                x = x - DISTANCE;
                                break;
                        }
                        break;
                    case "X+":
                        switch (headAxis) {
                            case "Z+":
                                newLookingToAxis = "Y+";
                                y = y + DISTANCE;
                                break;
                            case "Z-":
                                newLookingToAxis = "Y-";
                                y = y - DISTANCE;
                                break;
                            case "Y+":
                                newLookingToAxis = "Z-";
                                z = z - DISTANCE;
                                break;
                            case "Y-":
                                newLookingToAxis = "Z+";
                                z = z + DISTANCE;
                                break;
                        }
                        break;
                    case "X-":
                        switch (headAxis) {
                            case "Z+":
                                newLookingToAxis = "Y-";
                                y = y - DISTANCE;
                                break;
                            case "Z-":
                                newLookingToAxis = "Y+";
                                y = y + DISTANCE;
                                break;
                            case "Y+":
                                newLookingToAxis = "Z+";
                                z = z + DISTANCE;
                                break;
                            case "Y-":
                                newLookingToAxis = "Z-";
                                z = z - DISTANCE;
                                break;
                        }
                        break;
                }
            }
                break;
            case R: {
                newHeadAxis = headAxis;
                switch (lookingAxis) {
                    case "Z+":
                        switch (headAxis) {
                            case "Y+":
                                newLookingToAxis = "X-";
                                x = x - DISTANCE;
                                break;
                            case "Y-":
                                newLookingToAxis = "X+";
                                x = x + DISTANCE;
                                break;
                            case "X+":
                                newLookingToAxis = "Y+";
                                y = y + DISTANCE;
                                break;
                            case "X-":
                                newLookingToAxis = "Y-";
                                y = y - DISTANCE;
                                break;
                        }
                        break;
                    case "Z-":
                        switch (headAxis) {
                            case "Y+":
                                newLookingToAxis = "X+";
                                x = x + DISTANCE;
                                break;
                            case "Y-":
                                newLookingToAxis = "X-";
                                x = x - DISTANCE;
                                break;
                            case "X+":
                                newLookingToAxis = "Y-";
                                y = y - DISTANCE;
                                break;
                            case "X-":
                                newLookingToAxis = "Y+";
                                y = y + DISTANCE;
                                break;
                        }
                        break;
                    case "Y+":
                        switch (headAxis) {
                            case "Z+":
                                newLookingToAxis = "X+";
                                x = x + DISTANCE;
                                break;
                            case "Z-":
                                newLookingToAxis = "X-";
                                x = x - DISTANCE;
                                break;
                            case "X+":
                                newLookingToAxis = "Z-";
                                z = z - DISTANCE;
                                break;
                            case "X-":
                                newLookingToAxis = "Z+";
                                z = z + DISTANCE;
                                break;
                        }
                        break;
                    case "Y-":
                        switch (headAxis) {
                            case "Z+":
                                newLookingToAxis = "X-";
                                x = x - DISTANCE;
                                break;
                            case "Z-":
                                newLookingToAxis = "X+";
                                x = x + DISTANCE;
                                break;
                            case "X+":
                                newLookingToAxis = "Z+";
                                z = z + DISTANCE;
                                break;
                            case "X-":
                                newLookingToAxis = "Z-";
                                z = z - DISTANCE;
                                break;
                        }
                        break;
                    case "X+":
                        switch (headAxis) {
                            case "Z+":
                                newLookingToAxis = "Y-";
                                y = y - DISTANCE;
                                break;
                            case "Z-":
                                newLookingToAxis = "Y+";
                                y = y + DISTANCE;
                                break;
                            case "Y+":
                                newLookingToAxis = "Z+";
                                z = z + DISTANCE;
                                break;
                            case "Y-":
                                newLookingToAxis = "Z-";
                                z = z - DISTANCE;
                                break;
                        }
                        break;
                    case "X-":
                        switch (headAxis) {
                            case "Z+":
                                newLookingToAxis = "Y+";
                                y = y + DISTANCE;
                                break;
                            case "Z-":
                                newLookingToAxis = "Y-";
                                y = y - DISTANCE;
                                break;
                            case "Y+":
                                newLookingToAxis = "Z-";
                                z = z - DISTANCE;
                                break;
                            case "Y-":
                                newLookingToAxis = "Z+";
                                z = z + DISTANCE;
                                break;

                        }
                        break;
                }
            }

        }
        lookingAxis = newLookingToAxis;
        headAxis = newHeadAxis;

        Object[] array = new Object[] { x, y, z, lookingAxis, headAxis };
        return array;
    }

    public int[][][] createGrid(int size) {

        int[][][] grid = new int[size][size][size];

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                for (int z = 0; z < grid.length; z++) {
                    grid[x][y][z] = -1;
                }
            }
        }
        return grid;

    }

    public static void main(String[] args) {

        JMetalRandom random = JMetalRandom.getInstance();
        random.setSeed(1448654320573l);

        String aminoAcidSequence = "HHPPHHHPHHPHHPHPHPHPHHPPHHHPPHHHPPHHHPHHPHHPHPHPHPHHPPHHHPPH";

        Problem<IntegerSolution> pspProblem =
            new PSPProblem(1, aminoAcidSequence.length() - 1, 0, 4, aminoAcidSequence, 1.0, 0.0005);
        BacktrackInitialization backtrackInitialization =
            new BacktrackInitialization(random, pspProblem, aminoAcidSequence);

        List<IntegerSolution> population = backtrackInitialization.createPopulationAsIntegerSolution(100);

        ProteinBuilder proteinBuilder = new ProteinBuilder();

        for (IntegerSolution solution : population) {

            proteinBuilder.buildProteinAminoAcidList(solution, aminoAcidSequence, 1);
            System.out.println(EnergyFunction.getCollisionsCount(proteinBuilder.getAminoAcidsList()) + ":" + solution);

        }

    }

}
