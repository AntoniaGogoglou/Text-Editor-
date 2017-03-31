package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size=0;
		head=new LLNode<E>(null);
		tail=new LLNode<E>(null);
		head.next=tail;
		tail.prev=head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		LLNode<E> newEl=new LLNode<E>(element);
		if (size==0){
			head.next=newEl;
			newEl.prev=head;
			newEl.next=tail;
			tail.prev=newEl;
		}
		else{
			newEl.prev=tail.prev;
			tail.prev.next=newEl;
			tail.prev=newEl;
			newEl.next=tail;
		}
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) throws IndexOutOfBoundsException 
	{
		// TODO: Implement this method.
		LLNode<E> el=head.next;
		if (index<0 || index>=size || size==0){
			throw new IndexOutOfBoundsException("aa "+size);
		}
		else{
			for (int i=0;i<index;i++){
					el=el.next;
			}	
		}
		
		return el.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		LLNode<E> newEl=new LLNode<E>(element);
		if (index<0 || index>=size){
			throw new IndexOutOfBoundsException("Invalid index"+index+"while size is"+size);
		}
		if (size!=0){
			LLNode<E> el=head.next;
			for (int i=0;i<index;i++){
				el=el.next;
			} //at the end of this for loop the el object represents the element at index position
			
			newEl.next=el;
			newEl.prev=el.prev;
			el.prev.next=newEl;
			el.prev=newEl;			
		}
		else {
			head.next=newEl;
			newEl.prev=head;
			newEl.next=tail;
			tail.prev=newEl;
		}
		size++;
		
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		//LLNode<E> remEl=new LLNode<E>(element);
		if (index<0 || index>=size || size==0){
			throw new IndexOutOfBoundsException("Invalid index"+index+"while size is"+size);
		}
		else{
			LLNode<E> el=head.next;
			for (int i=0;i<index;i++){
				el=el.next;
			} //at the end of this for loop the el object represents the element at index position
			el.prev.next=el.next;
			el.next.prev=el.prev;
			E t=el.data;
			el.next=null;
			el.prev=null;
			size--;
			return t;
		}
		
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (index<0 || index>=size || size==0){
			throw new IndexOutOfBoundsException("Invalid index"+index+"while size is"+size);
		}
		else{
			LLNode<E> el=head.next;
			for (int i=0;i<index;i++){
				el=el.next;
			} //at the end of this for loop the el object represents the element at index position
			E t=el.data; //the old data
			el.data=element;
		
			return t;
		}
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	public LLNode(E e, LLNode<E> prev){
		this.data = e;
		this.prev=prev;
		this.next = null;
	}

}
