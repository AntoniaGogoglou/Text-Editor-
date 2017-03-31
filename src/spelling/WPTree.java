/**
 * 
 */
package spelling;

//import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * WPTree implements WordPath by dynamically creating a tree of words during a Breadth First
 * Search of Nearby words to create a path between two words. 
 * 
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class WPTree implements WordPath {

	// this is the root node of the WPTree
	private WPTreeNode root;
	// used to search for nearby Words
	private NearbyWords nw; 
	
	// This constructor is used by the Text Editor Application
	// You'll need to create your own NearbyWords object here.
	public WPTree () {
		this.root = null;
		// TODO initialize a NearbyWords object
		 Dictionary d = new DictionaryHashSet();
		 DictionaryLoader.loadDictionary(d, "data/dict.txt");
		 this.nw = new NearbyWords(d);
	}
	
	//This constructor will be used by the grader code
	public WPTree (NearbyWords nw) {
		this.root = null;
		this.nw = nw;
	}
	
	// see method description in WordPath interface
	/*@author Antonia Gogoglou, AUTH
	 * *
	 */
	public List<String> findPath(String word1, String word2) 
	{
	    // TODO: Implement this method.
		/** Return a path from word1 to word2 through dictionary words with
		 *  the restriction that each step in the path can involve only a
		 *  single character mutation  */
		List<WPTreeNode> queue = new LinkedList<WPTreeNode>();     // String to explore
		HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same  // string multiple times
		
		WPTreeNode w1=new WPTreeNode(word1,null); //I make root the word1!
		this.root=w1;
		//WPTreeNode w2=new WPTreeNode(word2,root); 
		List<WPTreeNode> retList = new LinkedList<WPTreeNode>();   // words to return
		 
		System.out.println("I am in");
		// insert first node
		queue.add(root);
		visited.add(word1);
					
		// TODO: Implement the remainder of this method, see assignment for algorithm
		while (!queue.isEmpty() && !visited.contains(word2)) {
			System.out.println("I am in2");
			WPTreeNode temp=queue.get(0);
			queue.remove(0);
			System.out.println("I am in3");
			List<String> neighbors=nw.distanceOne(temp.toString(), true);
			for(int index = 0; index < neighbors.size(); index++){
				System.out.println("I am in3");
				System.out.println(index+neighbors.get(index));
				if (!visited.contains(neighbors.get(index))){
					WPTreeNode neighNode=new WPTreeNode(neighbors.get(index),temp); //the neighboring node has parent the temp node
					queue.add(0, neighNode);
					temp.addChild(neighbors.get(index));
					visited.add(neighbors.get(index));
					System.out.println("I added this word to queue:"+" "+neighbors.get(index));
					if (neighbors.get(index).equals(word2)){
						//WPTreeNode neighNode=new WPTreeNode(neighbors.get(index),temp)
						return temp.buildPathToRoot();
					}
				}
			}
		}
		// the path from the final word (word2) to the root
	    return new LinkedList<String>();
	}
	
	// Method to print a list of WPTreeNodes (useful for debugging)
	private String printQueue(List<WPTreeNode> list) {
		String ret = "[ ";
		
		for (WPTreeNode w : list) {
			ret+= w.getWord()+", ";
		}
		ret+= "]";
		return ret;
	}
	
}

/* Tree Node in a WordPath Tree. This is a standard tree with each
 * node having any number of possible children.  Each node should only
 * contain a word in the dictionary and the relationship between nodes is
 * that a child is one character mutation (deletion, insertion, or
 * substitution) away from its parent
*/
class WPTreeNode {
    
    private String word;
    private List<WPTreeNode> children;
    private WPTreeNode parent;
    
    /** Construct a node with the word w and the parent p
     *  (pass a null parent to construct the root)  
	 * @param w The new node's word
	 * @param p The new node's parent
	 */
    public WPTreeNode(String w, WPTreeNode p) {
        this.word = w;
        this.parent = p;
        this.children = new LinkedList<WPTreeNode>();
    }
    
    /** Add a child of a node containing the String s
     *  precondition: The word is not already a child of this node
     * @param s The child node's word
	 * @return The new WPTreeNode
	 */
    public WPTreeNode addChild(String s){
        WPTreeNode child = new WPTreeNode(s, this);
        this.children.add(child);
        return child;
    }
    
    /** Get the list of children of the calling object
     *  (pass a null parent to construct the root)  
	 * @return List of WPTreeNode children
	 */
    public List<WPTreeNode> getChildren() {
        return this.children;
    }
   
    /** Allows you to build a path from the root node to 
     *  the calling object
     * @return The list of strings starting at the root and 
     *         ending at the calling object
	 */
    public List<String> buildPathToRoot() {
        WPTreeNode curr = this;
        List<String> path = new LinkedList<String>();
        while(curr != null) {
            path.add(0,curr.getWord());
            curr = curr.parent; 
        }
        return path;
    }
    
    /** Get the word for the calling object
     *
	 * @return Getter for calling object's word
	 */
    public String getWord() {
        return this.word;
    }
    
    /** toString method
    *
	 * @return The string representation of a WPTreeNode
	 */
    public String toString() {
        String ret = "Word: "+word+", parent = ";
        if(this.parent == null) {
           ret+="null.\n";
        }
        else {
           ret += this.parent.getWord()+"\n";
        }
        ret+="[ ";
        for(WPTreeNode curr: children) {
            ret+=curr.getWord() + ", ";
        }
        ret+=(" ]\n");
        return ret;
    }

}

