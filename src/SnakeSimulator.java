
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * <p>
 * The SnakeSimulator is used to determine the maximum MPRS-length song for a given set of snakes.
 * <p>
 * To run the simulation, call {@link #runSimulation()}
 * The maximum MPRS-length song can be found by calling {@link #getMaximumSong()}
 * 
 * @see #runSimulation()
 * @see #getMaximumSong()
 * 
 * @author Niall Deasy
 * @version 1.0
 */
public class SnakeSimulator {
	
	private SnakeTree songSearchTree;
	private LinkedList<String> currentSolution;
	private LinkedList<String> maximumSolution;
	private HashMap<Integer,Snake> snakes;
	private final int MPRS;
	/**
	 * <p>
	 * Constructor for SnakeSimulator 
	 * @param snakes A HashMap of the snakes to be simulated
	 * @param MPRS the maximum repeats per snake
	 * 
	 */
	public SnakeSimulator(HashMap<Integer,Snake> snakes, int MPRS){
		this.MPRS = MPRS;
		this.snakes = snakes;
		songSearchTree = new SnakeTree(snakes);
		currentSolution = new LinkedList<String>();
		maximumSolution = new LinkedList<String>();
		songSearchTree.initialize();
	}
	/**
	 * Run's the current simulation. Calculates the largest N-MPRS song for the given snakes. 
	 * <p>
	 * Iterates through the current {@link SnakeTree} using a {@link SnakeTree.SnakeTreeIterator} in order
	 * to find the largest song. The largest song is returned through {@link #getMaximumSong()}.
	 * 
	 * @see SnakeTree.SnakeTreeIterator
	 * @see SnakeTree
	 * 
	 */
	public void runSimulation(){
		SnakeTree.SnakeTreeIterator it = songSearchTree.getIterator();
		
		while(it.hasNext()){
			SnakeNode currentNode = it.next();

			if(!currentNode.isVisited()){
				currentNode.getSnake().sing();
				if(!currentNode.isRoot()){
					currentNode.populateChildren(getAvailableSingingSnakes(currentNode.getSnake()));
					currentSolution.add(currentNode.getSnake().getName());
				}
			}
			currentNode.setAsVisited();
			if(currentNode.getNextChild()==null){
				if(currentSolution.size()>=maximumSolution.size()){
					maximumSolution.clear();
					maximumSolution.addAll(currentSolution);
				}
				if(!currentSolution.isEmpty()){
					currentSolution.removeLast();
				}
				currentNode.getSnake().unSing();
				it.remove();
			}
		}		
	}
	/**
	 * <p>
	 * Print's the result of this snake simulator
	 * <p>
	 * <b>Example Result:</b>
	 * <br/>
	 * A maximal length 1-MRPS song for this 3-snake ensemble has 6 sings:
	 * <br/>
	 * Rod, Jane, Rod, Jane, Freddy, Freddy.
	 * 
	 */
	public void printResult(){
		LinkedList<String> song = getMaximumSong();

		System.out.println("A maximal length "+MPRS+"-MRPS song for this "+snakes.size()+
				"-snake ensemble has "+song.size()+" sings:");
		for(int i=0;i<song.size();i++){
			System.out.print(song.get(i));
			if(i==song.size()-1){
				System.out.println(".");
			}else {
				System.out.print(", ");
			}
		}
	}
	/**
	 * @return the maximum song as a list of snake names
	 * @see LinkedList
	 */
	public LinkedList<String> getMaximumSong(){
		return maximumSolution;
	}
	/**
	 * <p>
	 * The method used for determining which snakes can sing directly after the current snake. 
	 * Makes use of the HashSet collection in order to eliminate duplicate snakes from being returned.
	 * 
	 * @param currentSnake the current snake
	 * @return the HashSet of snakes which are allowed to sing  
	 * @see HashSet
	 * @see Snake
	 */
	private HashSet<Snake> getAvailableSingingSnakes(Snake currentSnake){
		HashSet<Snake> nextAvailableSingingSnakes = new HashSet<Snake>();

		for(Snake snake:snakes.values()){
			if(snake.getTail()!=null&&snake.getTail().getName()==currentSnake.getName()&&snake.getSingCount()<=MPRS){
				nextAvailableSingingSnakes.add(snake);
			}
		}
		if(currentSnake.getHead()!=null&&currentSnake.getHead().getSingCount()<=MPRS){
			nextAvailableSingingSnakes.add(currentSnake.getHead());
		}
		return nextAvailableSingingSnakes;
	}

}
