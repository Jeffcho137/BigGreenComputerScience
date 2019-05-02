package cs10.ps3;

import java.util.Comparator;

/**
 * 
 * Compares the values of 2 trees
 * -1 if a is less than b
 * 1 if a is greater than b
 * 0 if they're the same
 * 
 * Uses created Tree class which is mostly BST but changed it to Character, Integer pairs
 * added getValue and getKey methods as well
 *
 */

public class TreeComparator implements Comparator<Tree>{
	@Override
	public int compare(Tree a, Tree b) {
		// TODO Auto-generated method stub
		if(a.getValue() > b.getValue()) {
			return 1;
		}
		if(a.getValue() < b.getValue()) {
			return -1;
		}
		return 0;
	}
}
