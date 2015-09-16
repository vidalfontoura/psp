/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufpr.hp.protein.domain;

import java.util.Objects;


/**
 *
 * @author Vidal
 */
public class AminoAcid {
    
    private Point point;
    private AminoAcidType aminoAcidType;

    public AminoAcid(Point point, AminoAcidType aminoAcidType) {
        this.point = point;
        this.aminoAcidType = aminoAcidType;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public AminoAcidType getAminoAcidType() {
        return aminoAcidType;
    }

    public void setAminoAcidType(AminoAcidType aminoAcidType) {
        this.aminoAcidType = aminoAcidType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AminoAcid other = (AminoAcid) obj;
        if (!Objects.equals(this.point, other.point)) {
            return false;
        }
        if (this.aminoAcidType != other.aminoAcidType) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AminoAcid{" + "point=" + point + ", aminoAcidType=" + aminoAcidType + '}';
    }
    
    
    
   
    
    
            
    
}
