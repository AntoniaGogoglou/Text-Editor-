package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author Antonia
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		String word2=word.toLowerCase();
		char[] charOfWord=word2.toCharArray();
		//TrieNode nod=new TrieNode(word2);
		TrieNode curr=root;
		int count=0;
		while (curr!=null && count<charOfWord.length){
			if ((count==charOfWord.length-1) && (curr.getChild(charOfWord[count])!=null)) {//means I am at the end of the wordtoAdd and the curr has not become null yet,
				//indicating that the wordtoAdd exists!
				if (curr.getChild(charOfWord[count]).endsWord()){
					return false;
				}else {
					curr.getChild(charOfWord[count]).setEndsWord(true);
					return true;
					
				}
			}
			if (curr.getChild(charOfWord[count])!=null) {
				//curr=curr.getChild(charOfWord[count]); //if in the text of curr you found the character you were looking for
					//then follow the link to the next TrieNode that corresponds to that character
				curr=curr.getChild(charOfWord[count]);
			}else{  //means I didn't find link to the charOfWord[count] character
				TrieNode newNode=curr.insert(charOfWord[count]); //add the link to the character of charOfWord[count]
				curr=newNode;
				//check if I reached the end of the word I want to add
				if (count==charOfWord.length-1){
					newNode.setEndsWord(true);
				}
			}
			count++;
		}
		return true;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
		//printTree();
		traverseNodes(root);
		return size;
	}
	
	public void traverseNodes(TrieNode curr){
 		if (curr == null) 
 			return;
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			if (next.endsWord()){
 				size++;
 				//System.out.println(size);
 			}
 			traverseNodes(next);
 		}
 	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
		//TODO: Implement this method.
				String word2=s.toLowerCase();
				char[] charOfWord=word2.toCharArray();
				TrieNode nod=new TrieNode(word2);
				TrieNode curr=root;
				//for (char cc : charOfWord){
				int count=0;
				while (curr!=null && count<charOfWord.length){
					if ((count==charOfWord.length-1) && (curr.getChild(charOfWord[count])!=null)) {//means I am at the end of the wordtoAdd and the curr has not become null yet,
						//indicating that the wordtoAdd exists!
						if (curr.getChild(charOfWord[count]).endsWord()){
							return true;
						}
					}
					if (curr.getChild(charOfWord[count])!=null) {
						//curr=curr.getChild(charOfWord[count]); //if in the text of curr you found the character you were looking for
							//then follow the link to the next TrieNode that corresponds to that character
						curr=curr.getChild(charOfWord[count]);
					}
					count++;
				}
				return false;
		
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
    	 String word2=prefix.toLowerCase();
		 char[] charOfWord=word2.toCharArray();
		 TrieNode curr=root;
		 int flagAtRoot=-1;   //we need this in case the prefix is at the root and has length=0
		 //printTree();
		 int count=0;
		 List<String> Completions=new ArrayList<String>();
		 // two cases: one the prefix is in the root or below! basically the first case is for when the prefix is ""
		 if (root.getText().equals(prefix)){
			 //System.out.println("found it at the root"+count);
			 flagAtRoot=0;
			 while (curr!=null && count<charOfWord.length || flagAtRoot==0){
				 //System.out.println("curr is:"+curr.getText()+" "+"count is:"+count);
				 if ((count==charOfWord.length-1) || flagAtRoot==0) {//means I am at the end of the wordtoAdd and the curr has not become null yet,
					//indicating that the prefix exists!
					Queue<TrieNode> q=new LinkedList<TrieNode>();
					q.add(curr);
					
					while (!q.isEmpty() && (Completions.size()<numCompletions)){
						TrieNode temp=q.remove();
						if (temp.endsWord()){
							Completions.add(temp.getText());
							//System.out.println(temp.getText()+Completions.size());
						}
						for (Character cc : temp.getValidNextCharacters()){
							q.add(temp.getChild(cc));
						}
					}
	                return Completions;
				}
				if (curr.getChild(charOfWord[count])!=null) {
					 //then follow the link to the next TrieNode that corresponds to that character
					curr=curr.getChild(charOfWord[count]);
				}
				count++;
		   	}
			return Completions;
		 }else{
		 
			 while (curr!=null && count<charOfWord.length){
				 //System.out.println("curr is:"+curr.getText()+" "+"count is:"+count+" "+charOfWord.length);
				 if ((count==charOfWord.length-1)) {//means I am at the end of the wordtoAdd and the curr has not become null yet,
					 //indicating that the prefix exists!
					 Queue<TrieNode> q=new LinkedList<TrieNode>();
					 if (curr.getChild(charOfWord[count])==null){return Completions;}
					 else{
						 q.add(curr.getChild(charOfWord[count]));
				
						 while (!q.isEmpty() && (Completions.size()<numCompletions)){
							 TrieNode temp=q.remove();
							 //System.out.println("temp is:"+temp.getText());
							 if (temp.endsWord()){
								 Completions.add(temp.getText());
								 //System.out.println(temp.getText()+Completions.size());
							 }
							 for (Character cc : temp.getValidNextCharacters()){
								 q.add(temp.getChild(cc));
							 }
						 }
						 return Completions;
					 }
				 }
				 if (curr.getChild(charOfWord[count])!=null) {
					 //then follow the link to the next TrieNode that corresponds to that character
					 curr=curr.getChild(charOfWord[count]);
				 }
				 count++;
			 }return Completions;
		} 
         //return Completions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}