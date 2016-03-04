package edu.ufpr.hp.jmetal.lowlevelheuristics;

import java.util.Comparator;

public class SimpleLowLevelHeuristicComparatorFactory {

    public static final String NAME_COMPARATOR = "Name";
    public static final String CHOICE_FUNCTION_COMPARATOR = SimpleLowLevelHeuristic.CHOICE_FUNCTION;

    // public static final String MULTI_ARMED_BANDIT_COMPARATOR =
    // LowLevelHeuristic.MULTI_ARMED_BANDIT;
    public static final String RANDOM_COMPARATOR = SimpleLowLevelHeuristic.RANDOM;

    public static Comparator<SimpleLowLevelHeuristic> createComparator(String name) {

        switch (name) {
            case CHOICE_FUNCTION_COMPARATOR:
                return new SimpleLowLevelHeuristicChoiceFunctionComparator();
            case RANDOM_COMPARATOR:
                return new SimpleLowLevelHeuristicRandomComparator();
            // case NAME_COMPARATOR:
            // return new LowLevelHeuristicNameComparator();
            // case MULTI_ARMED_BANDIT_COMPARATOR:
            // return new LowLevelHeuristicMultiArmedBanditComparator();
            default:
                return null;
        }
    }

}
