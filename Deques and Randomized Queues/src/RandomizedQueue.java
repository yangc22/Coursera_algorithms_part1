import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] data;
	private int n;
	
	public RandomizedQueue() {
		data = (Item[]) new Object[2];
		n = 0;
	}
	
	public boolean isEmpty() {
		return (n == 0);
	}
	
    public int size() {
    	return n;
    }
    
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		if (n == data.length) {
			resize(2 * data.length);
		}
		data[n++] = item;
	}
	
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		int i = StdRandom.uniform(n);
		Item item = data[i];
		data[i] = data[--n];
		data[n] = null;
		if (n > 0 && n == (data.length / 4)) {
			resize(data.length / 2);
		}
		return item;
	}
	
	private void resize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < n; i++) {
			copy[i] = data[i];
		}
		data = copy;
	}
	
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		int i = StdRandom.uniform(n);
		return data[i];
	}
	
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		private Item[] list;
		private int num;
		
		public ListIterator() {
			list = (Item[]) new Object[n];
			num = n;
			for (int i = 0; i < n; i++){
				list[i] = data[i];
			}
		}
		
		public boolean hasNext() {
			return (num > 0);
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			int i = StdRandom.uniform(num);
			Item item = list[i];
			list[i] = list[--num];
			list[num] = null;
			return item;
		}
	}
	
	public static void main(String[] args) {
		RandomizedQueue<String> r = new RandomizedQueue<String>();
		r.enqueue("My");
		r.enqueue("name");
		r.enqueue("is");
		r.enqueue("Chaoyi Yang");
		for (String s : r) {
			StdOut.println(s);
		}
		for (String s : r) {
			StdOut.println(s);
		}
	}
}
