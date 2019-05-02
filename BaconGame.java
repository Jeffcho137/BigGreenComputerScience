package cs10.ps4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BaconGame {
	public static void main(String[] args) {
		//Variables
		Scanner scan = new Scanner(System.in);
		char[] input;
		Boolean run = true;
		String center = "Kevin Bacon"; //can be changed but starts as Kevin Bacon
		String line;

		//maps to chart id and movie/actor relations
		HashMap<Integer, String> actorsI = new HashMap<Integer, String>();
		HashMap<String, Integer> actorsS = new HashMap<String, Integer>();
		HashMap<Integer, String> moviesI = new HashMap<Integer, String>();
		HashMap<String, Integer> moviesS = new HashMap<String, Integer>();
		HashMap<Integer, HashSet<Integer>> movAct = new HashMap<Integer, HashSet<Integer>>();

		//paths to files
		String actorsPath = "src/testing/actors.txt";
		String moviesPath = "src/testing/movies.txt";
		String movactPath = "src/testing/movie-actors.txt";
		//		String actorsPath = "src/PS4/actorsTest.txt";
		//		String moviesPath = "src/PS4/moviesTest.txt";
		//		String movactPath = "src/PS4/movie-actorsTest.txt";

		//major graphs
		Graph<String, String> universe = new AdjacencyMapGraph<String, String>();
		Graph<String, String> bfs = new AdjacencyMapGraph<String, String>();

		//filling out info
		//reader needs try catch
		BufferedReader file;
		try {
			file = new BufferedReader(new FileReader(actorsPath));
			line = file.readLine();
			while(line != null) { //when next = null, end of file
				actorsI.put(Integer.valueOf(line.split("\\|")[0]), line.split("\\|")[1]);
				actorsS.put(line.split("\\|")[1], Integer.valueOf(line.split("\\|")[0]));
				universe.insertVertex(line.split("\\|")[1]);
				line = file.readLine();
			}
			file = new BufferedReader(new FileReader(moviesPath));
			line = file.readLine();		
			while(line != null) { //when next = null, end of file
				moviesI.put(Integer.valueOf(line.split("\\|")[0]), line.split("\\|")[1]);
				moviesS.put(line.split("\\|")[1], Integer.valueOf(line.split("\\|")[0]));
				line = file.readLine();
			}
			file = new BufferedReader(new FileReader(movactPath));
			line = file.readLine();
			while(line != null) { //when next = null, end of file
				Integer mov = Integer.valueOf(line.split("\\|")[0]);
				Integer act = Integer.valueOf(line.split("\\|")[1]);
				if(movAct.containsKey(mov)) movAct.get(mov).add(act);
				else {
					movAct.put(mov, new HashSet<Integer>());
					movAct.get(mov).add(Integer.valueOf(line.split("\\|")[1]));
				}
				line = file.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//add edges in universe
		for(Integer i : movAct.keySet()) {
			for(Integer j : movAct.get(i)) {
				for(Integer k : movAct.get(i)) {
					universe.insertUndirected(actorsI.get(j), actorsI.get(k), moviesI.get(i));
				}
			}
		}

		//try catch for bfs
		try {
			bfs = BaconGraphLib.bfs(universe,center);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//list of commands
		System.out.println("Commands:");
		System.out.println("c <#>: list top (positive number) or bottom (negative) <#> centers of the universe, sorted by average separation");
		System.out.println("d <low> <high>: list actors sorted by degree, with degree between low and high");
		System.out.println("i: list actors with infinite separation from the current center");
		System.out.println("p <name>: find path from <name> to current center of the universe");
		System.out.println("s <low> <high>: list actors sorted by non-infinite separation from the current center, with separation between low and high");
		System.out.println("u <name>: make <name> the center of the universe");
		System.out.println("q: quit game");
		while(run) {
			//prompt user for input
			System.out.println();
			System.out.println(center + " game >");
			input = scan.nextLine().toCharArray();

			if(input[0]=='q') run = false; //q: quit game
			
			else if(input[0]=='i') {//i: list actors with infinite separation from the current center
				System.out.println("Actors with infinite separation from current center:");
				for(String i : BaconGraphLib.missingVertices(universe, bfs)) System.out.println(i); //only need missing verticies
			}
			
			else if(input[0]=='u') {//u <name>: make <name> the center of the universe
				char[] temp = new char[input.length-2];
				for(int i = 2;i<input.length;i++) temp[i-2] = input[i];
//				System.out.println(input);
//				System.out.println(temp);
				String name = new String(temp);
				System.out.println(name + " is now the center of the universe.");
				if(universe.hasVertex(name))
					try {
						center = name;
						bfs=BaconGraphLib.bfs(universe,center); //swap bfs's source
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else System.out.println("Name invalid");
			}
			
			else if(input[0]=='p') {//p <name>: find path from <name> to current center of the universe
				char[] temp = new char[input.length-2];
				for(int i = 2 ; i<input.length ; i++) temp[i-2] = input[i];
				String name = new String(temp);
				if(universe.hasVertex(name)) {
					List<String> path = BaconGraphLib.getPath(bfs, name); //just use get path and print info
					System.out.println(name+"'s number is "+(path.size()-1));
					for(int i = 0 ; i<path.size()-1 ; i++) {
						System.out.println(path.get(i)+" appeared in ["+universe.getLabel(path.get(i), path.get(i+1))+"] with "+path.get(i+1));
					}
				}
				else System.out.println("Name invalid");
			}
			
			else if(input[0]=='c') {//c <#>: list top (positive number) or bottom (negative) <#> centers of the universe, sorted by average separation
				HashMap<String, Integer> avgSep = new HashMap<String, Integer>();
				char[] temp = new char[input.length-2];
				String name;
				for(int i = 2 ; i<input.length ; i++) temp[i-2] = input[i];
				int num = Integer.parseInt(String.valueOf(temp));
				//need to measure and compare separation 
				if(num!=0) {
					//comparator
					PriorityQueue<String> avgOrder = new PriorityQueue<String>(new Comparator<String>() {
						public int compare(String s1, String s2) {
							return (int) ( (num/Math.abs(num)) * avgSep.get(s2)-avgSep.get(s1)); //changes whether number is pos or neg
						}
					});
					for(String actor : actorsS.keySet()) {
						try {
							avgSep.put(actor, (int)BaconGraphLib.averageSeparation(BaconGraphLib.bfs(universe, actor), actor)); //add to priority queue
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						avgOrder.add(actor);
					}
					if(num>0) System.out.println("The top centers are:");
					else System.out.println("The bottom centers are");
					while(!avgOrder.isEmpty()) {
						name = avgOrder.poll();
						System.out.println(name+" has an average seperation of "+avgSep.get(name));
					}
				}
			}
			
			//very similar to one above
			//very similar to one above
			else if(input[0]=='d') {//d <low> <high>: list actors sorted by degree, with degree between low and high
				HashMap<String, Integer> degree = new HashMap<String, Integer>();
				String[] elements = new String(input).split(" ");
				String name;
				int low = Integer.parseInt(elements[1]);
				int high = Integer.parseInt(elements[2]);
				PriorityQueue<String> degOrder = new PriorityQueue<String>(new Comparator<String>() {
					public int compare(String s1, String s2) {
						return (int) (degree.get(s2)-degree.get(s1));
					}
				});
				for(String s : actorsS.keySet()) {
					if(bfs.hasVertex(s)) {
						if(bfs.inDegree(s)+bfs.outDegree(s)>=low&& bfs.inDegree(s)+bfs.outDegree(s)<=high) {
							degree.put(s, bfs.inDegree(s)+bfs.outDegree(s));
						}
					}
				}
				for(String s : degree.keySet()) {
					degOrder.add(s);
				}
				System.out.println("The order of degrees between "+low+" and "+high+" are");
				while(!degOrder.isEmpty()) {
					name = degOrder.poll();
					System.out.println(name+" has a degree of "+degree.get(name));
				}
			}

			//very similar to one above
			//very similar to one above
			else if(input[0]=='s') {//s <low> <high>: list actors sorted by non-infinite separation from the current center, with separation between low and high
				HashMap<String, Integer> seperation = new HashMap<String, Integer>();
				String[] elements = new String(input).split(" ");
				String name;
				int low = Integer.parseInt(elements[1]);
				int high = Integer.parseInt(elements[2]);
				int length;
				PriorityQueue<String> sepOrder = new PriorityQueue<String>(new Comparator<String>() {
					public int compare(String s1, String s2) {
						return (int) (seperation.get(s2)-seperation.get(s1));
					}
				});
				for(String s : actorsS.keySet()) {
					if(BaconGraphLib.getPath(bfs,s)!=null) {
						if(BaconGraphLib.getPath(bfs, s).size()>=low
								&&BaconGraphLib.getPath(bfs, s).size()<=high) {
							seperation.put(s, BaconGraphLib.getPath(bfs, s).size());
						}
					}
				}
				for(String s : seperation.keySet()) {
					sepOrder.add(s);
				}
				System.out.println("The order of speration between "+low+" and "+high+" are");
				while(!sepOrder.isEmpty()) {
					name = sepOrder.poll();
					System.out.println(name+" has a seperation of "+seperation.get(name));
				}

			}
			else System.out.println("Command not understood, try again!");
		}
		System.exit(0); //end program
	}
}
