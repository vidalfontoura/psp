/*
 */
package edu.ufpr.jmetal.lowlevelheuristics.operators;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.hp.jmetal.lowlevelheuristics.operators.ExhaustiveSearchMutationOperator;
import edu.ufpr.hp.protein.domain.AminoAcid;
import edu.ufpr.hp.protein.domain.TopologyContact;
import edu.ufpr.hp.protein.energy.EnergyFunction;
import edu.ufpr.hp.protein.utils.MovementEnum;
import edu.ufpr.hp.protein.utils.ProteinBuilder;

/**
 *
 *
 * @author Vidal TODO: Don't forget to fix this Test and check if it is working
 *         properly
 */
public class ExhaustiveSearchMutationOperatorTest {

    private ExhaustiveSearchMutationOperator operator;
    private JMetalRandom random;
    private String aminoAcidSequence = "HHHPPHHPHHHHHHHHHPPH";
    private ProteinBuilder proteinBuilder;

    @Before
    public void setup() {

        random = JMetalRandom.getInstance();
        random.setSeed(2);
        operator = new ExhaustiveSearchMutationOperator(1.0, random, aminoAcidSequence, 40);
        proteinBuilder = new ProteinBuilder();
    }

    @Test
    public void testOperator1() {

        SimpleIntegerProblem problem = new SimpleIntegerProblem(1, 19, 0, 4);
        DefaultIntegerSolution solution = new DefaultIntegerSolution(problem);

        MovementEnum[] actualParentMoves = new MovementEnum[solution.getNumberOfVariables()];
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            Integer variableValue = solution.getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualParentMoves[i] = moveFromInteger;
        }

        String parent = Arrays.toString(actualParentMoves).replace(" ", "");
        System.out.println(parent);

        proteinBuilder.buildProteinAminoAcidList(solution, aminoAcidSequence, 40);
        List<AminoAcid> aminoAcidsList = proteinBuilder.getAminoAcidsList();

        Set<TopologyContact> topologyContacts = EnergyFunction.getTopologyContacts(aminoAcidsList);
        int collisionsCount = EnergyFunction.getCollisionsCount(aminoAcidsList);
        int fitness = topologyContacts.size();
        System.out.println("fitness: " + fitness);
        System.out.println("collisions: " + collisionsCount);

        IntegerSolution execute = operator.execute(solution);

        MovementEnum[] actualOffspringMoves = new MovementEnum[execute.getNumberOfVariables()];
        for (int i = 0; i < execute.getNumberOfVariables(); i++) {
            Integer variableValue = execute.getVariableValue(i);
            MovementEnum moveFromInteger = MovementEnum.getMoveFromInteger(variableValue);
            actualOffspringMoves[i] = moveFromInteger;
        }

        String offSpring = Arrays.toString(actualOffspringMoves).replace(" ", "");
        System.out.println(offSpring);

        proteinBuilder.buildProteinAminoAcidList(execute, aminoAcidSequence, 40);
        aminoAcidsList = proteinBuilder.getAminoAcidsList();
        topologyContacts = EnergyFunction.getTopologyContacts(aminoAcidsList);

        collisionsCount = EnergyFunction.getCollisionsCount(aminoAcidsList);
        fitness = topologyContacts.size();

        System.out.println("fitness: " + fitness);
        System.out.println("collisions: " + collisionsCount);

    }
}
