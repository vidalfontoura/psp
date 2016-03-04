/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.backtrack.test;

import com.google.common.collect.Lists;

import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 *
 *
 * @author Vidal
 */
public class Test {

    private static Stack<Point> positions = new Stack<Point>();
    private static Stack<Point> badPlace = new Stack<Point>();

    private static int[][] grid = new int[5][5];

    private static final Point[] MOVES =
        new Point[] { new Point(-1, 0), new Point(1, 0), new Point(0, -1), new Point(0, 1) };

    private static List<Point> MOVING = Lists.newArrayList(MOVES);

    static int count = 0;

    static int spaces = grid.length * grid.length;

    static int sequenceLength = 10;

    public static void initGrid() {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = -1;
            }
        }
    }

    public static void main(String[] args) {

        initGrid();
        boolean move = move(0, 0);
        printGrid();

    }

    public static boolean move(int x, int y) {

        grid[x][y] = count;
        count++;
        if (count == sequenceLength) {
            return true;
        }

        int newX = -1;
        int newY = -1;

        Random r = new Random();
        do {
            if (MOVING.size() == 0) {
                break;
            }
            int index = r.nextInt(MOVING.size());
            Point point = MOVING.remove(index);

            int xMove = (int) point.getX();
            int yMove = (int) point.getY();

            newX = x + xMove;
            newY = y + yMove;

            System.out.println(newX + "," + newY);
            if (newX < 0 || newY < 0 || newX >= grid.length || newY >= grid.length) {
                continue;
            }
            if (!isEmpty(newX, newY)) {
                continue;
            }
            // It was possible to set point
            if (move(newX, newY)) {
                MOVING = Lists.newArrayList(MOVES);
                return true;
            }

        } while (!MOVING.isEmpty());

        MOVING = Lists.newArrayList(MOVES);
        System.out.println(count);
        printGrid();

        grid[x][y] = -1;
        count--;

        System.out.println();
        return false;

    }

    public static boolean isEmpty(int x, int y) {

        int i = grid[x][y];
        return i == -1 ? true : false;
    }

    public static void printGrid() {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                int k = grid[i][j];
                System.out.print(k + "\t");
            }
            System.out.println();
        }
    }

}
