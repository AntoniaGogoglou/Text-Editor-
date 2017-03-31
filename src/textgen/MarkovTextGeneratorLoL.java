package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		int i = sourceText.indexOf(' ');
		String firstWord = sourceText.substring(0, i);
		starter=firstWord;
		ListNode prevWordNode=new ListNode(firstWord);
		String[] arr = sourceText.split(" ");   
		
		for (int ss=1;ss<arr.length;ss++){   //for each word in the sourceText
			 String w = arr[ss];  //this is the current word!
			 if (!w.trim().isEmpty()){	 
			
			 ListNode wNode=new ListNode(w);
			 //System.out.println("prevWord is:"+prevWordNode.getWord());
			 //wordList.add(prevWordNode);
			 int flag=0;
			 int pos=-1;
			 for (ListNode word : wordList ){
				 if (word.getWord().equals(prevWordNode.getWord())){
					 flag=1; // it means the word w exists in the wordList
					 pos=wordList.indexOf(word);
				 }
			 }
			 if (flag==1){
				 System.out.println(prevWordNode.getWord());
				 //here is the problem
				 ListNode temp=wordList.get(pos);
				 temp.addNextWord(w);
			 }
			 else{
				 wordList.add(prevWordNode);
				 ListNode temp=wordList.get(wordList.indexOf(prevWordNode)); //where the newWord was placed in the wordList
				 temp.addNextWord(w);
			 }
			 prevWordNode=wNode;
			 }
			 if (ss==arr.length-1){ //if we reached the last word
				 int flag=0;
				 int pos=-1;
				 for (ListNode word : wordList ){
					 if (word.getWord().equals(prevWordNode.getWord())){
						 flag=1; // it means the word w exists in the wordList
						 pos=wordList.indexOf(word);
					 }
				 }
				 if (flag==1){
					 System.out.println(prevWordNode.getWord());
					 //here is the problem
					 ListNode temp=wordList.get(pos);
					 temp.addNextWord(starter);
				 }
				 else{
					 wordList.add(prevWordNode);
					 ListNode temp=wordList.get(wordList.indexOf(prevWordNode)); //where the newWord was placed in the wordList
					 temp.addNextWord(starter);
				 }
			 }
		 }
		
		//System.out.println(wordList.toString());
		
	}
	
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		String currWord=starter;
		ListNode currNode=new ListNode(currWord);
		List<ListNode> output=new LinkedList<ListNode>();;
		output.add(currNode);
		int countOutput=1;
		while (countOutput<numWords){
			int flag=-1;
			//System.out.println("currWord is "+currWord);
			//ListNode temp=wordList.get(wordList.indexOf(currNode));
			for (int i=0;i<wordList.size();i++){
				   //System.out.println(wordList.get(i).getWord()+currWord);
				   if (wordList.get(i).getWord().equals(currWord)){
				      flag=i;
				   }
			}
			ListNode temp=wordList.get(flag);
			//System.out.println(temp);
			String w=temp.getRandomNextWord(rnGenerator); //it is given in the constructor as input, so in main when we create a 
			// MarkonTextgeneratorLoL object we have defined this number
			ListNode wNode=new ListNode(w);
			output.add(wNode);
			currWord=w;
			
			countOutput++;
			
		}
		return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
			wordList = new LinkedList<ListNode>();
			starter = "";
			train(sourceText);
		
		
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "hi hi hi hello";
		System.out.println(textString2);
		gen.train(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString3 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString3);
		gen.retrain(textString3);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int min=0;
		int max=nextWords.size();
		int randomNum = generator.nextInt(max);
        String randomWord=nextWords.get(randomNum);
        System.out.println(randomWord);
	    return randomWord;
	    //return null;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


