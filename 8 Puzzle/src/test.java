import java.util.Arrays;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class test {

	public static void main(String[] args) {
		MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
		int[][] a = new int[3][3];
		int[][] b = new int[3][3];
		int[][] c = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				a[i][j] = i * 3 + j + 1;
				b[i][j] = i * 3 + j + 1;
				c[i][j] = i * 3 + j + 1;
			}
		}
		a[2][2] = 0;
		b[2][2] = 6;
		b[1][2] = 0;
		c[2][2] = 3;
		c[0][2] = 0;
		
		Board aa = new Board(a);
		Board bb = new Board(b);
		Board cc = new Board(c);
		SearchNode aaa = new SearchNode(aa, 0);
		SearchNode bbb = new SearchNode(bb, 0);
		SearchNode ccc = new SearchNode(cc, 0);
		pq.insert(ccc);
		pq.insert(bbb);
		pq.insert(aaa);
		StdOut.println(pq.delMin().board.manhattan());
		StdOut.println(pq.delMin().board.manhattan());
		StdOut.println(pq.delMin().board.manhattan());
		int[] d = {1, 2};
		int[] e = {1, 2};
		StdOut.println(Arrays.equals(d, e));
	}
	
	private static class SearchNode implements Comparable<SearchNode> {
    	private SearchNode pre;
    	private int moves;
    	private int manhattan;
    	private Board board;
		
    	public SearchNode(Board initial, int moves) {
    		initialization(initial, moves, null);
    	}
    	
    	
    	private void initialization(Board initial, int moves, SearchNode pre) {
    		this.board = initial;
    		this.moves = moves;
    		this.manhattan = initial.manhattan();
    		this.pre = pre;
    	}
    	
		public int compareTo(SearchNode that) {
			if ((manhattan + moves) < (that.manhattan + that.moves)) {
				return -1;
			} else if ((manhattan + moves) > (that.manhattan + that.moves)) {
				return +1;
			} else {
				return 0;
			}
		}
    }

}
