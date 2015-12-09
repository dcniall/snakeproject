

import java.util.HashSet;
import java.util.Iterator;
/**
 * <p>
 * SnakeNode is a Node object used by {@link SnakeTree} 
 * Each node contains a {@link Snake} object which can be resolved through {@link #getSnake()}
 *
 * @see Snake
 * @author Niall Deasy
 * @version 1.0
 */
public class SnakeNode{

	private Snake snake;
	private SnakeNode parent;
	private HashSet<SnakeNode> children;
	private boolean visited;

	/**
	 * <p>
	 * Constructor for SnakeNode.
	 * Creates a default node of type Root, with no parent.
	 * 
	 */
	public SnakeNode(){
		this.visited = false;
		this.parent = null;
		this.children = new HashSet<SnakeNode>();
		this.snake = new Snake("ROOT");
	}
	/**
	 * <p>
	 * Constructor for SnakeNode
	 * @param parent the parent node of this node
	 * @param snake the snake associated with this node
	 */
	public SnakeNode(SnakeNode parent, Snake snake){
		this.children = new HashSet<SnakeNode>();
		this.snake = snake;
		this.parent = parent;
		this.visited = false;
	}
	/**	 
	 * <p>
	 * Get's the number of child nodes within this snake node
	 * @return The amount of SnakeNode children contained within this SnakeNode
	 */
	public int getChildCount(){
		return this.children.size();
	}
	/**
	 * Determines if this node is the root node
	 * @return True if this node is the root node
	 */
	public boolean isRoot(){
		return this.parent==null;
	}
	/**
	 * Get's the parent node for this node if it exists.
	 * @return The parent node of for this node, null if no parent exist's
	 */
	public SnakeNode getParentNode(){
		return this.parent;
	}
	/**
	 * <p>
	 * Returns the next Available SnakeNode
	 * <p> 
	 * Depends on visited attribute {@link #isVisited()}, returning only child nodes which have
	 * not been marked as visited.
	 * <br/>
	 * @return The next available SnakeNode, or null if no valid node exists
	 * 
	 */
	public SnakeNode getNextChild(){
		Iterator<SnakeNode> snakeIt = children.iterator();
		if(children.isEmpty()){
			return null;
		}
		while(snakeIt.hasNext()){			
			SnakeNode node = snakeIt.next();
			if(!node.isVisited()){
				return node;
			}
		}
		return null;
	}
	/**
	 * <p>
	 * Marks the current node as visited
	 */
	public void setAsVisited(){
		this.visited = true;
	}
	/**
	 * <p>
	 * Determines if this node has been <b>marked</b> as visited.
	 * <p>
	 * See dependent method {@link #getNextChild()}
	 * 
	 * @return True if the node has been marked as visited
	 * @see #getNextChild()
	 * 
	 */
	public boolean isVisited(){
		return this.visited;
	}
	/**
	 * <p>
	 * Populates the children SnakeNodes from a set of snakes
	 * @param snakes the unique set of snakes
	 * 
	 */
	public void populateChildren(HashSet<Snake> snakes){
		children.clear();
		for(Snake snake:snakes){
			addChild(new SnakeNode(this,snake));
		}
	}
	/**
	 * <p>
	 * Removes a SnakeNode child from this node's children.
	 * 
	 * @param child the child node to be removed
	 * @return True if the node being removed exists
	 * 
	 */
	public boolean removeChild(SnakeNode child){
		return children.remove(child);
	}
	/**
	 * <p>
	 * Appends a SnakeNode to the children for this node
	 * 
	 * @param child the child node to be added
	 */
	public void addChild(SnakeNode child){
		children.add(child);
	}
	/**
	 * @return True if the current node contains child nodes
	 */
	public boolean hasChildren(){
		return !children.isEmpty();
	}
	/**
	 * <p>
	 * Returns the snake associated with this node
	 * 
	 * @return The Snake associated with this SnakeNode
	 * @see Snake
	 */
	public Snake getSnake(){
		return snake;
	}

};
