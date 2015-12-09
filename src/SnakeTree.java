
import java.util.HashMap;
import java.util.Iterator;

/**
 * <p>
 * SnakeTree is a Tree made up of {@link SnakeNode} nodes 
 * Each node contains a {@link Snake} object.
 * <p>
 * The tree can be iterated with a {@link SnakeTreeIterator}, returned by {@link #getIterator()}
 * 
 * @see Snake
 * @see SnakeNode
 * @see SnakeTreeIterator
 *
 * @author Niall Deasy
 * @version 1.0
 */
public class SnakeTree {

	private SnakeNode rootNode;
	private HashMap<Integer,Snake> snakes;
	/**
	 * <p>
	 * Constructor for SnakeTree.
	 * <p>
	 * Set's the root node to an empty Snake object.
	 * @param snakes The HashMap of snakes maps each line number that the snake was found from the CSV file to that snake.
	 * 
	 */
	public SnakeTree(HashMap<Integer,Snake> snakes){
		this.snakes = snakes;
		rootNode = new SnakeNode();
	}
	/**
	 * <p>
	 * Initializes the current tree by populating the root node with a child node for 
	 * each available Snake.
	 * @see	SnakeNode
	 */
	public void initialize(){
		for(Snake snake:snakes.values()){
			rootNode.addChild(new SnakeNode(rootNode, snake));
		}
	}
	/**
	 * <p>
	 * Returns the root SnakeNode for the current tree
	 * 
	 * @return	The root SnakeNode for the current tree
	 * @see	SnakeNode
	 */
	public SnakeNode getRoot(){
		return rootNode;
	}
	/**
	 * <p>
	 * Returns a SnakeTreeIterator, which can be used to iterate through the enclosing Snake Tree
	 * @return	SnakeTreeIterator for the current tree
	 * @see	SnakeTreeIterator
	 */
	SnakeTreeIterator getIterator(){
		return new SnakeTreeIterator();
	}
	/**
	 * <p>
	 * The SnakeTreeIterator is an iterator that can be used to iterate through each the SnakeNode nodes
	 * within the SnakeTree. 
	 *
	 * @see	Iterator
	 * @see	SnakeNode
	 */
	public class SnakeTreeIterator implements Iterator<SnakeNode>{

		private SnakeNode currentNode;
		private boolean initialized;

		public SnakeTreeIterator(){
			currentNode = rootNode;
			initialized = false;
		}
		/**
		 * <p>
		 * Determines if the iterator has a next available node in the next iteration step.
		 * @return True if the iteration has more elements to iterate.
		 */
		@Override
		public boolean hasNext() {
			return currentNode!=null;
		}
		/**
		 * <p>
		 * 
		 * @return The next SnakeNode element in the iteration.
		 * @see	SnakeNode
		 */
		@Override
		public SnakeNode next() {
			if(!initialized){
				initialized = true;
				return currentNode;
			}
			currentNode = currentNode.getNextChild();
			return currentNode;
		}
		/**
		 * <p>
		 * Removes from the SnakeTree collection the last SnakeNode element returned by this iterator (optional operation), and
		 * set's the next node to the removed node's parent node.
		 * <p>
		 * Used when a node is reached in the iteration that has no more children nodes available. We remove this node in 
		 * order to iterate back up the tree to any other child nodes that have not yet been iterated. 
		 */
		@Override
		public void remove() {
			SnakeNode removedNode = currentNode;
			currentNode = currentNode.getParentNode();
			if(currentNode!=null){
				currentNode.removeChild(removedNode);
			}
			initialized = false;
		}
	};
}
