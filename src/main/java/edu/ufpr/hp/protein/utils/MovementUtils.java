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
public class MovementUtils {
    
    
    public static String inverterAxis(String eixo) {
        return eixo.charAt(1)=='+'?eixo.replaceFirst("\\+", "\\-"):eixo.replaceFirst("\\-", "\\+");
    }
    
}
