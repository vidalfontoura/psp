/*
 */
package edu.ufpr.hp.protein.utils;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;

import edu.ufpr.hp.protein.domain.AminoAcid;
import edu.ufpr.hp.protein.domain.Point;
import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;

/**
 *
 *
 * @author Vidal
 */
public class ProteinBuilder {

    private static final String ERROR_MSG =
        "Error the solution has %s variables and the sequence has %s. The solution must have the sequence - 1 variables";

    private List<Point3D> aminoAcidsCordinates;
    private List<AminoAcid> aminoAcidsList;

    public void buildProteinAminoAcidList(IntegerSolution solution, String sequence, int aminoAcidDistance) {

        int numberOfVariables = solution.getNumberOfVariables();
        int numberOfAminoAcids = sequence.length();

        if (numberOfVariables != numberOfAminoAcids - 1) {
            throw new JMetalException(String.format(ERROR_MSG, numberOfVariables, numberOfAminoAcids));
        }

        aminoAcidsCordinates = new ArrayList<Point3D>();
        aminoAcidsList = new ArrayList<>();

        AminoAcidType[] aminoAcidsType = AminoAcidSequenceUtils.fromStringSequence(sequence);
        // adding first amino acid
        int x = 0;
        int y = 0;
        int z = 0;

        String lookingAxis = "Z+";
        String headAxis = "Y+";
        Point3D rotate = Rotate.Y_AXIS;

        // addAminoAcidToView(x, y, z, aminoAcidsType[0], moleculeXform, false,
        // null, null);
        aminoAcidsCordinates.add(new Point3D(x, y, z));
        aminoAcidsList.add(new AminoAcid(new Point(x, y, z), aminoAcidsType[0] == AminoAcidType.H
            ? edu.ufpr.hp.protein.domain.AminoAcidType.H : edu.ufpr.hp.protein.domain.AminoAcidType.P, 0));

        for (int i = 0; i < numberOfVariables; i++) {
            MovementEnum move = MovementEnum.getMoveFromInteger(solution.getVariableValue(i));
            String newLookingToAxis = lookingAxis;
            String newHeadAxis = headAxis;
            switch (move) {
                case F: {
                    newLookingToAxis = lookingAxis;
                    newHeadAxis = headAxis;
                    switch (lookingAxis) {
                        case "Z+": {
                            z = z + aminoAcidDistance;
                            rotate = Rotate.X_AXIS;
                            break;
                        }
                        case "Z-": {
                            z = z - aminoAcidDistance;
                            rotate = Rotate.X_AXIS;
                            break;
                        }
                        case "Y+": {
                            y = y + aminoAcidDistance;
                            rotate = Rotate.Y_AXIS;
                            break;
                        }
                        case "Y-": {
                            y = y - aminoAcidDistance;
                            rotate = Rotate.Y_AXIS;
                            break;
                        }
                        case "X+": {
                            x = x + aminoAcidDistance;
                            rotate = Rotate.Z_AXIS;
                            break;
                        }
                        case "X-": {
                            x = x - aminoAcidDistance;
                            rotate = Rotate.Z_AXIS;
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
                            y = y + aminoAcidDistance;
                            rotate = Rotate.Y_AXIS;
                            break;
                        case "Y-":
                            y = y - aminoAcidDistance;
                            rotate = Rotate.Y_AXIS;
                            break;
                        case "X-":
                            x = x - aminoAcidDistance;
                            rotate = Rotate.Z_AXIS;
                            break;
                        case "X+":
                            x = x + aminoAcidDistance;
                            rotate = Rotate.Z_AXIS;
                            break;
                        case "Z+":
                            z = z + aminoAcidDistance;
                            rotate = Rotate.X_AXIS;
                            break;
                        case "Z-":
                            z = z - aminoAcidDistance;
                            rotate = Rotate.X_AXIS;
                            break;
                    }
                    break;

                }
                case D: {
                    newLookingToAxis = MovementUtils.inverterAxis(headAxis);
                    newHeadAxis = lookingAxis;
                    switch (headAxis) {
                        case "Y+":
                            y = y - aminoAcidDistance;
                            rotate = Rotate.Y_AXIS;
                            break;
                        case "Y-":
                            y = y + aminoAcidDistance;
                            rotate = Rotate.Y_AXIS;
                            break;
                        case "X-":
                            x = x + aminoAcidDistance;
                            rotate = Rotate.Z_AXIS;
                            break;
                        case "X+":
                            x = x - aminoAcidDistance;
                            rotate = Rotate.Z_AXIS;
                            break;
                        case "Z+":
                            z = z - aminoAcidDistance;
                            rotate = Rotate.X_AXIS;
                            break;
                        case "Z-":
                            z = z + aminoAcidDistance;
                            rotate = Rotate.X_AXIS;
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
                                    x = x + aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "X-";
                                    x = x - aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;

                                case "X+":
                                    newLookingToAxis = "Y-";
                                    y = y - aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Y+";
                                    y = y + aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                            }
                            break;
                        case "Z-":
                            switch (headAxis) {
                                case "Y+":
                                    newLookingToAxis = "X-";
                                    x = x - aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "X+";
                                    x = x + aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;

                                case "X-":
                                    newLookingToAxis = "Y-";
                                    y = y - aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;

                                case "X+":
                                    newLookingToAxis = "Y+";
                                    y = y + aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;

                            }
                            break;
                        case "Y+":
                            switch (headAxis) {
                                case "X+":
                                    newLookingToAxis = "Z+";
                                    z = z + aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Z-";
                                    z = z - aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Z+":
                                    newLookingToAxis = "X-";
                                    x = x - aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "X+";
                                    x = x + aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                            }
                            break;
                        case "Y-":
                            switch (headAxis) {
                                case "X+":
                                    newLookingToAxis = "Z-";
                                    z = z - aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Z+";
                                    z = z + aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Z+":
                                    newLookingToAxis = "X+";
                                    x = x + aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "X-";
                                    x = x - aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                            }
                            break;
                        case "X+":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "Y+";
                                    y = y + aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "Y-";
                                    y = y - aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Y+":
                                    newLookingToAxis = "Z-";
                                    z = z - aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "Z+";
                                    z = z + aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                            }
                            break;
                        case "X-":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "Y-";
                                    y = y - aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "Y+";
                                    y = y + aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Y+":
                                    newLookingToAxis = "Z+";
                                    z = z + aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "Z-";
                                    z = z - aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
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
                                    x = x - aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "X+";
                                    x = x + aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "X+":
                                    newLookingToAxis = "Y+";
                                    y = y + aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Y-";
                                    y = y - aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                            }
                            break;
                        case "Z-":
                            switch (headAxis) {
                                case "Y+":
                                    newLookingToAxis = "X+";
                                    x = x + aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "X-";
                                    x = x - aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "X+":
                                    newLookingToAxis = "Y-";
                                    y = y - aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Y+";
                                    y = y + aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                            }
                            break;
                        case "Y+":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "X+";
                                    x = x + aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "X-";
                                    x = x - aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "X+":
                                    newLookingToAxis = "Z-";
                                    z = z - aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Z+";
                                    z = z + aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                            }
                            break;
                        case "Y-":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "X-";
                                    x = x - aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "X+";
                                    x = x + aminoAcidDistance;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "X+":
                                    newLookingToAxis = "Z+";
                                    z = z + aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Z-";
                                    z = z - aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                            }
                            break;
                        case "X+":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "Y-";
                                    y = y - aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "Y+";
                                    y = y + aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Y+":
                                    newLookingToAxis = "Z+";
                                    z = z + aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "Z-";
                                    z = z - aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                            }
                            break;
                        case "X-":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "Y+";
                                    y = y + aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "Y-";
                                    y = y - aminoAcidDistance;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Y+":
                                    newLookingToAxis = "Z-";
                                    z = z - aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "Z+";
                                    z = z + aminoAcidDistance;
                                    rotate = Rotate.X_AXIS;
                                    break;

                            }
                            break;
                    }
                }

            }
            lookingAxis = newLookingToAxis;
            headAxis = newHeadAxis;
            aminoAcidsCordinates.add(new Point3D(x, y, z));
            aminoAcidsList
                .add(new AminoAcid(new Point(x / aminoAcidDistance, y / aminoAcidDistance, z / aminoAcidDistance),
                    aminoAcidsType[i + 1] == AminoAcidType.H ? edu.ufpr.hp.protein.domain.AminoAcidType.H
                        : edu.ufpr.hp.protein.domain.AminoAcidType.P,
                    i));
        }
    }

    public List<Point3D> getAminoAcidsCordinates() {

        return aminoAcidsCordinates;
    }

    public List<AminoAcid> getAminoAcidsList() {

        return aminoAcidsList;
    }

}
