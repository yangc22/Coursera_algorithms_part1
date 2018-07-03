import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
	private Node last;
	private int n;
	
	private class Node {
	    private Item item;
	    private Node next;
	    private Node pre;
	}
	
	public Deque() {
        first = null;
        last = null;
        n = 0;
    }
	
	public boolean isEmpty() {
		return (n == 0);
	}
	
	public int size() {
		return n;
	}
	
	public void addFirst(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		if (isEmpty()) {
			last = first;
		} else {
			first.next = oldFirst;
			oldFirst.pre = first;
		}
		n++;
	}
	
	public void addLast(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		Node oldLast = last;
		last = new Node();
		last.item = item;
		if (isEmpty()) {
			first = last;
		} else {  
			last.pre = oldLast;
			oldLast.next = last;
		}
		n++;
	}
	
	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item item = first.item;
		Node oldFirst = first;
		first = first.next;
		n--;
		if (isEmpty()) {
			last = first;
		} else {
		    first.pre = null;
		    oldFirst.next = null;
		}
		return item;
	}
	
	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item item = last.item;
		Node oldLast = last;
		last = last.pre;
		n--;
		if (isEmpty()) {
			first = last;
		} else {
		    last.next = null;
		    oldLast.pre = null;
		}
		return item;
	}
	
	/* public String toString() {
		Node current = first;
		String s = "[ ";
		while (current != null) {
			s = s + current.item + " ";
			current = current.next;
		}
		s += "]";
		return s;
	} */
	
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		private Node current = first;
		
		public boolean hasNext() { return current != null; }
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	public static void main(String[] args)  {
		Deque<Integer> q = new Deque<Integer>();
		q.addFirst(0);
		StdOut.println(q.removeLast());
		/* q.addFirst(1);
		StdOut.println(q.removeFirst());
		q.addLast(1);
		StdOut.println(q.toString());
		q.addLast(2);
		StdOut.println(q.toString());
		q.removeFirst();
		StdOut.println(q.toString());
		q.addFirst(4);
		StdOut.println(q.toString());
		q.addFirst(5);
		StdOut.println(q.toString());
		q.addFirst(6);
		StdOut.println(q.toString());
		q.addLast(7);
		StdOut.println(q.toString());
		q.addLast(8);
		StdOut.println(q.toString());
		StdOut.println(q.removeLast());
		StdOut.println(q.toString());
		StdOut.println(q.removeFirst());
		StdOut.println(q.toString()); */
	}
}
