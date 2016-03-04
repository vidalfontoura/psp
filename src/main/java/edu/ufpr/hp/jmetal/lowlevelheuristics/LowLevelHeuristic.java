/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.hp.jmetal.lowlevelheuristics;

import java.util.LinkedList;
import java.util.Optional;

/**
 *
 *
 * @author Vidal
 */
public class LowLevelHeuristic {

    // Number of times that the heuristic has updated the best known solution
    private int cBest = 0;

    // Number of times that the heuristic has updated the current solution
    private int cCurrent = 0;

    // Number of times that the generated solution by the heuristic has been
    // accepted by the acceptance criterion
    private int cAccept = 0;

    // The average of the previous improvement strength of the heuristic no
    // espaço de busca
    private int cAva = 0;

    // The number of times that the heuristic has been ranked the first
    private int cR = 0;

    private FIFOFixedLengthQueue<Integer> rewardSlidingWindow;

    public LowLevelHeuristic(int slidingWindowSize) {
        rewardSlidingWindow = new FIFOFixedLengthQueue<Integer>(slidingWindowSize);

    }

    public int getMaxRewardFromSlidingWindow() {

        Optional<Integer> max = rewardSlidingWindow.stream().max((o1, o2) -> o1.compareTo(o2));
        if (max.isPresent()) {
            return max.get();
        }
        // If the time window is empty will return 0
        return 0;
    }

    public int getcCurrent() {

        return cCurrent;
    }

    public int getcBest() {

        return cBest;
    }

    public int getcAccept() {

        return cAccept;
    }

    public int getcAva() {

        return cAva;
    }

    public int getcR() {

        return cR;
    }

    public void updatecBest() {

        cBest++;
    }

    public void updatecCurrent() {

        cCurrent++;
    }

}


class FIFOFixedLengthQueue<E> extends LinkedList<E> {

    private int limit;

    public FIFOFixedLengthQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(E o) {

        boolean added = super.add(o);
        while (added && size() > limit) {
            super.remove();
        }
        return added;
    }
}
