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
public class AminoAcidSequenceUtils {
    
    
    
    public static  AminoAcidType[] fromStringSequence(String sequence) {
        AminoAcidType[] aminoAcidsType = new AminoAcidType[sequence.length()];
        for(int i=0; i<sequence.length(); i++) {
             aminoAcidsType[i] = getAminoAcidType(sequence.charAt(i));
        }
        return aminoAcidsType;
    }
    
    public static AminoAcidType getAminoAcidType(char aminoAcid) {
        switch(aminoAcid) {
            case 'H': {
                return AminoAcidType.H;
            }
            case 'P': {
                return AminoAcidType.P;
            }
            default: {
                throw new IllegalArgumentException("Amino acid "+aminoAcid+" not supported");
            }
        
        }
    }
    
}
