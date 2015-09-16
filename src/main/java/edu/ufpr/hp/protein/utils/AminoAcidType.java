/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufpr.hp.protein.utils;

import javafx.scene.paint.Color;

/**
 *
 * @author Vidal
 */
public enum AminoAcidType {
    H(Color.RED,Color.GREY),P(Color.WHITE, Color.GREY);
    
    private Color diffuseColor;
    private Color specularColor;

    AminoAcidType(Color diffuseColor, Color specularColor){
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public Color getSpecularColor() {
        return specularColor;
    }
    
    
    
   
}
