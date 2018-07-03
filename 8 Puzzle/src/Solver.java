import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private SearchNode initialSol;
	private SearchNode twinSol;
	
	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	if (initial == null) {
    		throw new IllegalArgumentException("Null Input!");
    	}
    	initialSol = new SearchNode(initial, 0);
    	Board twin = initial.twin();
    	twinSol = new SearchNode(twin, 0);
    	MinPQ<SearchNode> pQInitial = new MinPQ<SearchNode>();
    	pQInitial.insert(initialSol);
    	MinPQ<SearchNode> pQTwin = new MinPQ<SearchNode>();
    	pQTwin.insert(twinSol);
    	while ((!initialSol.board.isGoal()) && (!twinSol.board.isGoal())) {
    		initialSol = pQInitial.delMin();
    		for (Board b : initialSol.board.neighbors()) {
    			if (initialSol.pre == null || !b.equals(initialSol.pre.board)) {
    				SearchNode neighborNode = new SearchNode(b, initialSol.moves + 1, initialSol);
    				pQInitial.insert(neighborNode);
    			}
    		}
    		twinSol = pQTwin.delMin();
    		for (Board b : twinSol.board.neighbors()) {
    			if (initialSol.pre == null || !b.equals(twinSol.pre.board)) {
    				SearchNode neighborNode = new SearchNode(b, twinSol.moves + 1, twinSol);
    				pQTwin.insert(neighborNode);
    			}
    		}
    	}
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
    	return initialSol.board.isGoal();
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	if (isSolvable()) {
    		return initialSol.moves;
    	} else {
    		return -1;
    	}
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
    	if (isSolvable()) {
    		Stack<Board> sol = new Stack<Board>();
    		SearchNode solPath = initialSol;
    		while (solPath != null) {
    			sol.push(solPath.board);
    			solPath = solPath.pre;
    		}
    		return sol;
    	} else {
    		return null;
    	}
    }
    
    private class SearchNode implements Comparable<SearchNode> {
    	private SearchNode pre;
    	private int moves;
    	private int manhattan;
    	private Board board;
    	
    	public SearchNode(Board initial, int moves) {
    		initialization(initial, moves, null);
    	}
    	
    	public SearchNode(Board initial, int moves, SearchNode pre) {
    		initialization(initial, moves, pre);
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
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            StdOut.println(filename + ": " + solver.moves());
            for (Board b : solver.solution()) {
            	StdOut.println(b);
            }
        }
    }
}
