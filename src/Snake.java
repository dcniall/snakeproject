import java.util.HashMap;

/**
 * <p>
 * A Snake object which is used to describe a snake under the following attributes
 * <p>
 * The snake that this snake has it's head wrapped around {@link #getHead()}
 * The snake that this snake has it's tail wrapped around {@link #getTail()}
 * The name of this snake {@link #getName()}
 * The number of times this snake has sang {@link #getSingCount()}
 * <p>
 * Also allow's the following action's {@link #sing()}, {@link #unSing()}
 * 
 * 
 * @author Niall Deasy
 * @version 1.0
 */
public class Snake {


	private int head, tail;
	private String name;
	private int singCount;	
	private HashMap<Integer,Snake> snakes;
	/**
	 * <p>
	 * Constructs an empty snake object, setting the head and tail to null 
	 * <p>
	 * Used for constructing an empty snake for the root node
	 * @param name The name of the snake
	 * @see SnakeNode
	 * 
	 */
	public Snake(String name){
		this.name = name;
		this.head = -1;
		this.tail = -1;
		this.singCount = 0;
		this.snakes = new HashMap<Integer,Snake>();
	}
	/**
	 * <p>
	 * Constructs a snake object
	 * <p>
	 * Used for constructing an empty snake for the root node
	 * @param name The name of the snake
	 * @param tail The integer id of the snake that has this snake has it's tail wrapped around. 
	 * @param head The integer id of the snake that has this snake has it's head wrapped around. 
	 * @param snakes The HashMap of snakes, where each snake is mapped to an integer id, which represents its position in the csv file
	 * @see SnakeNode
	 * 
	 */
	public Snake(String name, int tail, int head, HashMap<Integer,Snake> snakes){
		this.name = name;
		this.singCount = 0;
		this.head = head;
		this.tail = tail;
		this.snakes = snakes;
	}
	/**<p>
	 * Get's the name of this snake as a String
	 * @return Name of the snake
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * <p>
	 * Get's the snake that this snake has it's tail wrapped around.
	 * @return  The snake that this snake has it's tail wrapped around.
	 * @see #getSnakeById
	 */
	public Snake getTail(){
		return getSnakeById(this.tail);
	}
	/**
	 * <p>
	 * Get's the snake that this snake has it's head wrapped around.
	 * @return The snake that this snake has it's head wrapped around.
	 * @see #getSnakeById
	 */
	public Snake getHead(){
		return getSnakeById(this.head);
	}
	/**
	 * <p>
	 * Decreases the sing count for this snake by one. Reverses the effect of a snake singing.
	 * <p>
	 * Used by the {@link SnakeTree.SnakeTreeIterator} to iterate up the tree to look for more possible solutions (Maximum Songs).
	 * <p>
	 * <b>General Practice</b>
	 * <br/>
	 * Iterate down - Snake.sing();<br/>
	 * Iterate up - Snake.unSing();
	 * 
	 */
	public void unSing(){
		this.singCount--;
	}
	/**
	 * <p>
	 * Gets the number of times that this snake has sang
	 * @return the number of times the snake has sang
	 * @see #sing
	 */
	public int getSingCount(){
		return this.singCount;
	}
	/**
	 * <p>
	 * Call's this snake to sing and return it's name. Increases the sing count by one.
	 * @return The name of the snake
	 */
	public String sing(){
		this.singCount++;
		return this.name;
	}
	/**
	 * <p>
	 * Get's the snake associated with the id.
	 * <p>
	 * The HashMap of snakes maps each line number that the snake was found from the CSV file to that snake.<br/>
	 * Where the first line in the CSV is 0
	 * @param the id of the snake / line it was described in the CSV 
	 * @return The snake associated with that id / line in the CSV 
	 */
	private Snake getSnakeById(int id){
		if(snakes!=null&&snakes.containsKey(id)){
			return snakes.get(id);
		}
		return null;
	}
}
