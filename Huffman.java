package cs10.ps3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 
 * 
 *
 */

public class Huffman {

	/**
	 * Creates and returns a hashmap with the frequency of each character 
	 * try catch to check if file is there and input.read() is there
	 * 
	 * if it finds the char, increase its frequency by 1
	 * if the char isn't already in the hashmap, add it to the hashmap with a frequency of 1
	 * @param pathName
	 * @return map with frequencies
	 * @throws IOException
	 */
	public static Map<Character, Integer> frequencyTable(String pathName) throws IOException{
		Map<Character, Integer> freq = new HashMap<Character, Integer>();
		BufferedReader input = new BufferedReader(new FileReader(pathName));
		try {
			int next = input.read();
			while(next != -1) { //when next = -1, reaches end of file
				if(freq.containsKey((char) next)) { //if it finds the character, increment value by 1
					freq.put((char) next, freq.get((char) next)+1);
				}
				else { //if it doesn't already exist, add to tree with frequency of 1
					freq.put((char) next, 1);
				}
				next = input.read();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			input.close(); //close file
		}
		return freq;
	}

	/**
	 * Combines characters into a Tree
	 * Puts tree into priority queue and returns priority queue
	 * @param freq
	 * @return ordered -- priority queue
	 */
	public static PriorityQueue<Tree> createPriorityQueue(Map<Character, Integer> freq){
		PriorityQueue<Tree> ordered = new PriorityQueue<Tree>(new TreeComparator());
		for(Character character : freq.keySet()) { //goes through all of the keys in freq
			Tree tree = new Tree(character, freq.get(character));
			ordered.add(tree);
		}
		return ordered;
	}

	/**
	 * creates tree from the priority queue
	 * T1 and T2 are the first and second Trees in the queuedTrees priority queue
	 * create a new tree whose key is null, value is T1 value + T2 value, and left and right branches are T1 and T2
	 * put that back in the queue and continue doing that until there's only one thing left (big combined tree)
	 * @param queuedTrees
	 * @return queuedTrees.peek() which will return the first (and only) item in the priority queue, the big tree
	 */
	public static Tree createTree(PriorityQueue<Tree> queuedTrees) {
		while(queuedTrees.size() > 1) {
			Tree T1 = queuedTrees.poll();
			Tree T2 = queuedTrees.poll();
			Tree newTree = new Tree(null, T1.getValue()+T2.getValue(), T1, T2); // combines trees
			queuedTrees.add(newTree);
		}
		return queuedTrees.peek(); //return big tree
	}

	/**
	 * only traverses once by using recursion
	 * calls helper on both children
	 * @param tree
	 * @return map with all codes and characters
	 */
	public static Map<Character,String> compressor(Tree tree){
		Map<Character,String> map = new HashMap<Character,String>();
		if(tree==null) return map; //handles empty files
		if(tree.hasLeft()) map.putAll(compressor(tree.getLeft(),"0"));
		if(tree.hasRight()) map.putAll(compressor(tree.getRight(),"1"));
		if(tree.isLeaf()) map.put(tree.getKey(),"0");
		return map;
	}
	/**
	 * helper function
	 * @param tree
	 * @param keeps track of unique code for char
	 * @return map with all codes and characters
	 */
	public static Map<Character,String> compressor(Tree tree, String code){
		Map<Character,String> map = new HashMap<Character,String>();
		if(tree.hasLeft()) map.putAll(compressor(tree.getLeft(),code+"0"));
		if(tree.hasRight()) map.putAll(compressor(tree.getRight(),code+"1"));
		if(tree.isLeaf()) map.put(tree.getKey(),code+"");
		return map;
	}

	/**
	 * reads in file and writes compressed version
	 * @param inPath
	 * @param outPath
	 * @param codeMap
	 * @throws IOException
	 */
	public static void compress(String inPath, String outPath, Map<Character,String> codeMap) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(inPath));
		BufferedBitWriter output = new BufferedBitWriter(outPath);
		try { 
			int current = input.read();
			while(current!=-1) { //stops when at end of file
				for(char i : codeMap.get((char) current).toCharArray()) {
					output.writeBit(i=='1'); //breaks the code into an array that can be iterated through
				}
				current = input.read();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			input.close();
			output.close();
		}
	}

	/**
	 * reads through the compressed file and uses the tree to decompress
	 * @param inPath
	 * @param outPath
	 * @param tree
	 * @throws IOException
	 */
	public static void decompress(String inPath, String outPath, Tree tree) throws IOException{
		BufferedBitReader input = new BufferedBitReader(inPath);
		BufferedWriter output = new BufferedWriter(new FileWriter(outPath)); 
		boolean bit = false;
		Tree traversal = tree; //used to traverse down the tree
		try { 
			if(tree!=null) { //handles empty files
				if(input.hasNext()) bit = input.readBit();
				while(input.hasNext()) { //runs until the end of the file
					traversal = tree;
					while(!traversal.isLeaf()&&input.hasNext()) {
						if(bit) traversal = traversal.getRight();
						else traversal = traversal.getLeft();
						bit = input.readBit();
					}
					if(input.hasNext())output.write((char)traversal.getKey()); //needed so that it does not attempt to read when at end
				}
				//gets the last character
				if(bit) traversal = traversal.getRight();
				else traversal = traversal.getLeft();
				if(traversal!=null)output.write((char)traversal.getKey());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			input.close();
			output.close();
		}
	}



	public static void main(String[] args) throws IOException {
		//test cases
//		String inPath = "src/PS3/test";
//		String compressedPath = "src/PS3/testCompressed";
//		String outPath = "src/PS3/testUncompressed";
//		String inPath = "src/PS3/testa";
//		String compressedPath = "src/PS3/testaCompressed";
//		String outPath = "src/PS3/testaUncompressed";
//		String inPath = "src/PS3/testb";
//		String compressedPath = "src/PS3/testbCompressed";
//		String outPath = "src/PS3/testbUncompressed";
		
//		String inPath = "src/PS3/USConstitution.txt";
//		String compressedPath = "src/PS3/USConstitutionCompressed.txt";
//		String outPath = "src/PS3/USConstitutionUncompressed.txt";
		String inPath = "src/cs10.ps3/WarAndPeace.txt";
		String compressedPath = "src/cs10.ps3/WarAndPeaceCompressed.txt";
		String outPath = "src/cs10.ps3/WarAndPeaceUncompressed.txt";
		try {
			Tree tree = createTree(createPriorityQueue(frequencyTable(inPath)));
			compress(inPath, compressedPath, compressor(tree));
			decompress(compressedPath, outPath, tree);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
