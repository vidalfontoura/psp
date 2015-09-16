/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufpr.hp.protein.domain;

/**
 *
 * @author Vidal
 */
/**
 *
 *
 * @author user
 */
public class TopologyContact {

   private AminoAcid aminoAcid1;
           
   private AminoAcid aminoAcid2;

    public TopologyContact(AminoAcid aminoAcid1, AminoAcid aminoAcid2) {
        this.aminoAcid1 = aminoAcid1;
        this.aminoAcid2 = aminoAcid2;
    }
   
   
   

    public AminoAcid getAminoAcid1() {
        return aminoAcid1;
    }

    public void setAminoAcid1(AminoAcid aminoAcid1) {
        this.aminoAcid1 = aminoAcid1;
    }

    public AminoAcid getAminoAcid2() {
        return aminoAcid2;
    }

    public void setAminoAcid2(AminoAcid aminoAcid2) {
        this.aminoAcid2 = aminoAcid2;
    }
   
   
          

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((aminoAcid1 == null) ? 0 : aminoAcid1.hashCode() + aminoAcid1.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TopologyContact other = (TopologyContact) obj;

        if (this.aminoAcid1.equals(other.aminoAcid1) && this.aminoAcid2.equals(other.aminoAcid2)) {
            return true;
        }
        if (this.aminoAcid2.equals(other.aminoAcid1) && this.aminoAcid1.equals(other.aminoAcid2)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "TopologyContact{" + "aminoAcid1=" + aminoAcid1 + ", aminoAcid2=" + aminoAcid2 + '}';
    }
    
    

}