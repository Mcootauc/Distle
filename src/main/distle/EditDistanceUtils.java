package main.distle;

import java.util.*;

public class EditDistanceUtils {
    
    /**
     * Returns the completed Edit Distance memoization structure, a 2D array
     * of ints representing the number of string manipulations required to minimally
     * turn each subproblem's string into the other.
     * 
     * @param s0 String to transform into other
     * @param s1 Target of transformation
     * @return Completed Memoization structure for editDistance(s0, s1)
     */
    public static int[][] getEditDistTable (String s0, String s1) {
    	int[][] memoizTable = new int[s0.length() + 1][s1.length() + 1];
    	String strArray0[] = s0.split("");
        String strArray1[] = s1.split("");
        int minVal = 0;
    	//Fills the gutters of the rows 
    	for (int i = 0; i < s0.length() + 1; i++) {
            memoizTable[i][0] = i;
        }
    	//Fills the gutters of the cols
        for (int j = 0; j < s1.length() + 1; j++) {
            memoizTable[0][j] = j;
        }
        //Goes through whole table from left to right and top down
        //r and c start at 1,1 so it doesn't start from the gutter
        for(int r = 1; r < s0.length() + 1; r++) {
            for(int c = 1; c < s1.length() + 1; c++) {
	        //Insertion
		minVal = memoizTable[r][c-1] + 1;
		//Compares Deletion 
		minVal = Math.min(memoizTable[r-1][c] + 1, minVal);
		//Compares Replacement 
		minVal = Math.min(memoizTable[r-1][c-1] + (!(strArray0[r-1].equals(strArray1[c-1])) ? 1 : 0), minVal);
		//Checks and compares Transposition
		if (r >= 2 && c >= 2 && strArray0[r-1].equals(strArray1[c-2]) && strArray1[c-1].equals(strArray0[r-2])) {
		    minVal = Math.min(memoizTable[r-2][c-2] + 1, minVal);
		}
		//Sets the min value
		    memoizTable[r][c] = minVal;
        	}
        }
    	return memoizTable;
    }
    
    /**
     * Returns one possible sequence of transformations that turns String s0
     * into s1. The list is in top-down order (i.e., starting from the largest
     * subproblem in the memoization structure) and consists of Strings representing
     * the String manipulations of:
     * <ol>
     *   <li>"R" = Replacement</li>
     *   <li>"T" = Transposition</li>
     *   <li>"I" = Insertion</li>
     *   <li>"D" = Deletion</li>
     * </ol>
     * In case of multiple minimal edit distance sequences, returns a list with
     * ties in manipulations broken by the order listed above (i.e., replacements
     * preferred over transpositions, which in turn are preferred over insertions, etc.)
     * @param s0 String transforming into other
     * @param s1 Target of transformation
     * @param table Precomputed memoization structure for edit distance between s0, s1
     * @return List that represents a top-down sequence of manipulations required to
     * turn s0 into s1, e.g., ["R", "R", "T", "I"] would be two replacements followed
     * by a transposition, then insertion.
     */
    public static List<String> getTransformationList (String s0, String s1, int[][] table) {
        List<String> finalList = new ArrayList<String>();
        int r = s0.length();
        int c = s1.length();
        getTransformationListHelper (s0, s1, table, r, c, finalList);
        return finalList;
    }
    
    public static List<String> getTransformationListHelper (String s0, String s1, int[][] table, int r, int c, List<String> list) {
    	String strArray0[] = s0.split("");
        String strArray1[] = s1.split("");
        //Check Base Case
        if (table[r][c] == 0) {
	    return list;
        //Check Match
        } else if (c >= 1 && r >= 1 && strArray0[r-1].equals(strArray1[c-1])) {
            getTransformationListHelper(s0, s1, table, r-1, c-1, list);
        //Check Replacement
        } else if (c >= 1 && r >= 1 && table[r][c] - 1 == table[r-1][c-1]) {
            list.add("R");
            getTransformationListHelper(s0, s1, table, r-1, c-1, list);
        //Check Transposition
        } else if (r >= 2 && c >= 2 && strArray0[r-1].equals(strArray1[c-2]) && strArray1[c-1].equals(strArray0[r-2])) {
            list.add("T");
            getTransformationListHelper(s0, s1, table, r-2, c-2, list);
        //Check Insertion
        } else if (c >= 1 && table[r][c] - 1 == table[r][c-1]) {
            list.add("I");
            getTransformationListHelper(s0, s1, table, r, c-1, list);
        //Check Deletion
        } else if (r >= 1 && table[r][c] - 1 == table[r-1][c]) {
            list.add("D");
            getTransformationListHelper(s0, s1, table, r-1, c, list);
        }
        return null;
    }
    
    /**
     * Returns the edit distance between the two given strings: an int
     * representing the number of String manipulations (Insertions, Deletions,
     * Replacements, and Transpositions) minimally required to turn one into
     * the other.
     * 
     * @param s0 String to transform into other
     * @param s1 Target of transformation
     * @return The minimal number of manipulations required to turn s0 into s1
     */
    public static int editDistance (String s0, String s1) {
        if (s0.equals(s1)) { return 0; }
        return getEditDistTable(s0, s1)[s0.length()][s1.length()];
    }
    
    /**
     * See {@link #getTransformationList(String s0, String s1, int[][] table)}.
     */
    public static List<String> getTransformationList (String s0, String s1) {
        return getTransformationList(s0, s1, getEditDistTable(s0, s1));
    }

}