import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		RandomizedQueue<String> q = new RandomizedQueue<String>(); 
		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			q.enqueue(s);
		}
		for (int i = 0; i < n; i++) {
			StdOut.println(q.dequeue());
		}
	}

}
