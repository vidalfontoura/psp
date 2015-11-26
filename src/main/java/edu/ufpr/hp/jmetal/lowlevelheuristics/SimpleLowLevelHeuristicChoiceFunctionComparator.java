package edu.ufpr.hp.jmetal.lowlevelheuristics;

import java.util.Comparator;

public class SimpleLowLevelHeuristicChoiceFunctionComparator implements Comparator<SimpleLowLevelHeuristic> {

    @Override
    public int compare(SimpleLowLevelHeuristic o1, SimpleLowLevelHeuristic o2) {

        if (o1.getChoiceFunction() > o2.getChoiceFunction()) {
            return -1;
        } else if (o1.getChoiceFunction() < o2.getChoiceFunction()) {
            return 1;
        } else {
            return 0;
        }
    }

}
