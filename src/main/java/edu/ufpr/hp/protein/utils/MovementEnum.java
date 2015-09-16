/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufpr.hp.protein.utils;

/**
 *
 * @author Vidal
 */
public enum MovementEnum {
    
    F(0),U(1),D(2),L(3),R(4);
    private final int move;
    
    
    MovementEnum (int move) {
        this.move = move;
 
    } 

    public int getMove() {
        return move;
    }
    
}