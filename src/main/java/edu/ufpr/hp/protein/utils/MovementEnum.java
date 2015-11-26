/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package edu.ufpr.hp.protein.utils;

import org.uma.jmetal.util.JMetalException;

/**
 *
 * @author Vidal
 */
public enum MovementEnum {

    F(0), U(1), D(2), L(3), R(4);

    private final int move;
    private static final String UNSUPPORTED_MOVEMENT_MSG = "The % movement is unsupported";

    MovementEnum(int move) {
        this.move = move;

    }

    public int getMove() {

        return move;
    }

    public static MovementEnum getMoveFromInteger(int move) {

        switch (move) {
            case 0:
                return F;
            case 1:
                return U;
            case 2:
                return D;
            case 3:
                return L;
            case 4:
                return R;
        }
        throw new JMetalException(String.format(UNSUPPORTED_MOVEMENT_MSG, move));
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {

        return "MovementEnum." + this.name();
    }

}
