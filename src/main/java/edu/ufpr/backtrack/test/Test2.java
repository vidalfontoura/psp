/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.backtrack.test;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.ufpr.hp.protein.utils.MovementEnum;
import edu.ufpr.hp.protein.utils.MovementUtils;

/**
 *
 *
 * @author Vidal
 */
public class Test2 {

    private static int[][][] grid = new int[10][10][10];

    private static List<MovementEnum> MOVE = Lists.newArrayList(MovementEnum.values());

    private static List<MovementEnum> solutionMoves = Lists.newArrayList();

    static int count = 0;

    static int sequenceLength = 30;

    static Random r;

    public static void initGrid() {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                for (int t = 0; t < grid[i][j].length; t++) {
                    grid[i][j][t] = -1;
                }

            }
        }
    }

    public static void printGrid() {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                for (int t = 0; t < grid[i][j].length; t++) {
                    int value = grid[i][j][t];
                    if (value != -1) {
                        System.out.println(i + "," + j + "," + t + "=" + value);
                    }

                }

            }
        }

    }

    public static void main(String[] args) {

        for (int i = 0; i < 1; i++) {

            MOVE = Lists.newArrayList(MovementEnum.values());
            r = new Random(i);
            count = 0;
            solutionMoves = Lists.newArrayList();

            initGrid();

            // adding first amino acid

            String lookingAxis = "Z+";
            String headAxis = "Y+";

            boolean move = move(0, 0, 0, lookingAxis, headAxis);
            printGrid();

            Collections.reverse(solutionMoves);

            System.out.println(solutionMoves.toString());
        }

    }

    public static boolean move(int x, int y, int z, String lookingAxis, String headAxis) {

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
            if (MOVE.size() == 0) {
                break;
            }
            int index = r.nextInt(MOVE.size());
            MovementEnum movement = MOVE.remove(index);

            Object[] coordinates = fromMoveCodeToCoordinates(lookingAxis, headAxis, movement, x, y, z);

            int xMove = (int) coordinates[0];
            int yMove = (int) coordinates[1];
            int zMove = (int) coordinates[2];

            System.out.println(xMove + "," + yMove + "," + zMove);

            lookingAxis = (String) coordinates[3];
            headAxis = (String) coordinates[4];

            newX = xMove;
            newY = yMove;
            newZ = zMove;

            // System.out.println(newX + "," + newY + "," + newZ);
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

            MOVE = Lists.newArrayList(MovementEnum.values());
            // It was possible to set point
            if (move(newX, newY, newZ, lookingAxis, headAxis)) {

                solutionMoves.add(movement);

                return true;
            }

        } while (!MOVE.isEmpty());

        lookingAxis = localLookingAxis;
        headAxis = localHeadAxis;

        // System.out.println("Count: " + count);
        MOVE = Lists.newArrayList(MovementEnum.values());

        grid[x][y][z] = -1;
        count--;

        return false;

    }

    public static boolean isEmpty(int x, int y, int z) {

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

    // public static void printGrid() {
    //
    // for (int i = 0; i < grid.length; i++) {
    // for (int j = 0; j < grid.length; j++) {
    // int k = grid[i][j];
    // System.out.print(k + "\t");
    // }
    // System.out.println();
    // }
    // }

}
