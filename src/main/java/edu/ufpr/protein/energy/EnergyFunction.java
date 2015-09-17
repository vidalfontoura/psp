/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufpr.protein.energy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.ufpr.hp.protein.domain.AminoAcid;
import edu.ufpr.hp.protein.domain.AminoAcidType;
import edu.ufpr.hp.protein.domain.Point;
import edu.ufpr.hp.protein.domain.TopologyContact;

/**
 *
 * @author Vidal
 */
public class EnergyFunction {
    
    
    
    public static int[][][] get3dGrid(List<AminoAcid> aminoAcidsPoints) {
        int size = aminoAcidsPoints.size();
        int[] minCordinates = getMinCoordinates(aminoAcidsPoints);
        int[][][] grid = new int[size][size][size];
        for (int i = 0; i < aminoAcidsPoints.size(); i++) {
            int x = aminoAcidsPoints.get(i).getPoint().getX() - (minCordinates[0]);
            int y = aminoAcidsPoints.get(i).getPoint().getY() - (minCordinates[1]);
            int z = aminoAcidsPoints.get(i).getPoint().getZ() - (minCordinates[2]);
            grid[x][y][z] = i;
        }
        return grid;
    }
    
    public static Set<TopologyContact> getTopologyContacts(List<AminoAcid> aminoAcids) {
        Set<TopologyContact> topologyContacts = new HashSet<>();
        int index = 0;
        int[] minCordinates = getMinCoordinates(aminoAcids);
        int[][][] grid = get3dGrid(aminoAcids);
        for (int i = 0; i < aminoAcids.size(); i++) {
            
            int x = aminoAcids.get(i).getPoint().getX() - (minCordinates[0]);
            int y = aminoAcids.get(i).getPoint().getY() - (minCordinates[1]);
            int z = aminoAcids.get(i).getPoint().getZ() - (minCordinates[2]);
            
            if (aminoAcids.get(i).getAminoAcidType().equals(AminoAcidType.P)) {
                continue;
            }
            if (y + 1 < grid.length) {
                index = grid[x][y + 1][z];
                // test up
                if (isTopologicalContact(i, index, aminoAcids)) {
                    topologyContacts.add(new TopologyContact(aminoAcids.get(i), aminoAcids.get(index)));
                }
            }
            if (x + 1 < grid.length) {
                // test right
                index = grid[x + 1][y][z];
                if (isTopologicalContact(i, index, aminoAcids)) {
                    topologyContacts.add(new TopologyContact(aminoAcids.get(i), aminoAcids.get(index)));
                }
            }
            if (y - 1 >= 0) {
                // test down
                index = grid[x][y - 1][z];
                if (isTopologicalContact(i, index, aminoAcids)) {
                    topologyContacts.add(new TopologyContact(aminoAcids.get(i), aminoAcids.get(index)));
                }
            }
            if (x - 1 >= 0) {
                // test back
                index = grid[x-1][y][z];
                if (isTopologicalContact(i, index, aminoAcids)) {
                    topologyContacts.add(new TopologyContact(aminoAcids.get(i), aminoAcids.get(index)));
                }
            }
            
             if (z + 1 < grid.length) {
                // test front
                index = grid[x][y][z+1];
                if (isTopologicalContact(i, index, aminoAcids)) {
                    topologyContacts.add(new TopologyContact(aminoAcids.get(i), aminoAcids.get(index)));
                }
            }
             
             if (z - 1 >= 0) {
                // test back
                index = grid[x][y][z-1];
                if (isTopologicalContact(i, index, aminoAcids)) {
                    topologyContacts.add(new TopologyContact(aminoAcids.get(i), aminoAcids.get(index)));
                }
            }

        }
        return topologyContacts;
    
    }
    
    public static int[] getMinCoordinates(List<AminoAcid> aminoAcidsPoints) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        for(int i=0; i<aminoAcidsPoints.size(); i++) {
            if (minX >= aminoAcidsPoints.get(i).getPoint().getX()) {
                minX = aminoAcidsPoints.get(i).getPoint().getX();
            }
            
            if (minY >= aminoAcidsPoints.get(i).getPoint().getY()) {
                minY = aminoAcidsPoints.get(i).getPoint().getY();
            }
            
            if (minZ >= aminoAcidsPoints.get(i).getPoint().getZ()) {
                minZ = aminoAcidsPoints.get(i).getPoint().getZ();
            }
        }
        return new int[]{minX,minY,minZ};
    }
    
    public static boolean isTopologicalContact(int i, int index, List<AminoAcid> aminoAcids) {

        if (i != index + 1 && i != index - 1 && index != -1) {

            return aminoAcids.get(index).getAminoAcidType().equals(AminoAcidType.H);
        }
        return false;
    }
    
    public static int getCollisionsCount(List<AminoAcid> aminoAcids) {

        Set<Point> pointsSet = new HashSet<>();
        int count = 0;
        for (int i = 0; i < aminoAcids.size(); i++) {
            boolean added = pointsSet.add(aminoAcids.get(i).getPoint());
            if (!added) {
                count++;
            }
        }
        return count;
    }

}
