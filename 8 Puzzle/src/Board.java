import java.util.Arrays;
import java.util.Stack;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
	private final int[][] tiles;
	private final int size;
	
	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
    	if (blocks.length != blocks[0].length) {
    		throw new IllegalArgumentException("Illegal Input!");
    	}
    	size = blocks.length;
    	tiles = new int[size][size];
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			tiles[i][j] = blocks[i][j];
    		}
    	}
    }

    // board dimension n
    public int dimension() {
    	return size;
    }
    
    private int xyTo1D (int row, int col) {
    	return row * size + col + 1;
    }
    
    
    // number of blocks out of place
    public int hamming() {
    	int hamming = 0;
    	for (int i = 0; i < size - 1; i++) {
    		for (int j = 0; j < size; j++) {
    			if (tiles[i][j] != xyTo1D(i, j)) {
    				hamming++;
    			}
    		}
    	}
    	
    	for (int j = 0; j < size - 1; j++) {
    		if (tiles[size - 1][j] != xyTo1D(size - 1, j)) {
				hamming++;
			}
    	}
    	return hamming;
    }
    
    private int manhattan(int block, int row, int col) {
    	if (block != 0) {
    		int rowOfBlock = (block - 1) / size;
    		int colOfBlock = (block - 1) % size;
    		int manhattan = Math.abs(rowOfBlock - row) + Math.abs(colOfBlock - col);
    		return manhattan;
    	}
    	return 0;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
    	int manhattan = 0;
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			manhattan += manhattan(tiles[i][j], i, j);
    		}
    	}
    	return manhattan;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
    	for (int i = 0; i < size - 1; i++) {
    		for (int j = 0; j < size; j++) {
    			if (tiles[i][j] != xyTo1D(i, j)) {
    				return false;
    			}
    		}
    	}
    	
    	for (int j = 0; j < size - 1; j++) {
    		if (tiles[size - 1][j] != xyTo1D(size - 1, j)) {
				return false;
			}
    	}
    	return true;
    }
    
    private int[] findZero() {
    	int[] index = new int[2];
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			if (tiles[i][j] == 0) {
    				index[0] = i;
    				index[1] = j;
    			}
    		}
    	}
    	return index;
    }
    
    private int[][] copyTiles() {
    	int[][] twinArray = new int[size][size];
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			twinArray[i][j] = tiles[i][j];
    		}
    	}
    	return twinArray;
    }
    
    private Board upNieghbor() {
    	int[] zeroIndex = findZero();
    	int row = zeroIndex[0];
		int col = zeroIndex[1];
    	if (row == 0) {
    		return null;
    	} else {
    		int[][] twinArray = copyTiles();
    		int temp = twinArray[row - 1][col];
			twinArray[row - 1][col] = 0;
			twinArray[row][col] = temp;
			return new Board(twinArray);
    	}
    	
    }
    
    private Board downNieghbor() {
    	int[] zeroIndex = findZero();
    	int row = zeroIndex[0];
		int col = zeroIndex[1];
    	if (row == (size - 1)) {
    		return null;
    	} else {
    		int[][] twinArray = copyTiles();
    		int temp = twinArray[row + 1][col];
			twinArray[row + 1][col] = 0;
			twinArray[row][col] = temp;
			return new Board(twinArray);
    	}
    	
    }
    
    private Board leftNieghbor() {
    	int[] zeroIndex = findZero();
    	int row = zeroIndex[0];
		int col = zeroIndex[1];
    	if (col == 0) {
    		return null;
    	} else {
    		int[][] twinArray = copyTiles();
    		int temp = twinArray[row][col - 1];
			twinArray[row][col - 1] = 0;
			twinArray[row][col] = temp;
			return new Board(twinArray);
    	}
    	
    }
    
    private Board rightNieghbor() {
    	int[] zeroIndex = findZero();
    	int row = zeroIndex[0];
		int col = zeroIndex[1];
    	if (col == (size - 1)) {
    		return null;
    	} else {
    		int[][] twinArray = copyTiles();
    		int temp = twinArray[row][col + 1];
			twinArray[row][col + 1] = 0;
			twinArray[row][col] = temp;
			return new Board(twinArray);
    	}
    	
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
    	int[] zeroIndex = findZero();
    	int xIndex = StdRandom.uniform(size);
    	int yIndex = StdRandom.uniform(size);
    	while (xIndex == zeroIndex[0] && yIndex == zeroIndex[1]) {
    		xIndex = StdRandom.uniform(size);
        	yIndex = StdRandom.uniform(size);
    	}
    	int twinIndex = StdRandom.uniform(4);
    	int[] twinXYIndex = getTwinXYIndex(twinIndex, xIndex, yIndex);
    	while (!valid(twinXYIndex) || Arrays.equals(zeroIndex, twinXYIndex)) {
    		twinIndex = StdRandom.uniform(4);
        	twinXYIndex = getTwinXYIndex(twinIndex, xIndex, yIndex);
    	}
    	int[][] twinTiles = copyTiles();
    	int temp = twinTiles[xIndex][yIndex];
    	twinTiles[xIndex][yIndex] = twinTiles[twinXYIndex[0]][twinXYIndex[1]];
    	twinTiles[twinXYIndex[0]][twinXYIndex[1]] = temp;
    	return new Board(twinTiles);
    }
    
    private int[] getTwinXYIndex(int i, int xIndex, int yIndex) {
    	int[] twinIndex = new int[2];
    	if (i == 0) {
    		twinIndex[0] = xIndex;
    		twinIndex[1] = yIndex - 1;
    	} else if (i == 1) {
    		twinIndex[0] = xIndex;
    		twinIndex[1] = yIndex + 1;
    	} else if (i == 2) {
    		twinIndex[0] = xIndex - 1;
    		twinIndex[1] = yIndex;
    	} else if (i == 3) {
    		twinIndex[0] = xIndex + 1;
    		twinIndex[1] = yIndex;
    	}
    	return twinIndex;
    }
    
    private boolean valid(int[] index) {
    	if (index[0] < size && index[0] >= 0 && index[1] < size && index[1] >= 0) {
    		return true;
    	}
    	return false;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
    	if (y == this) {
    		return true;
    	}
        if (y == null) {
        	return false;
        }
        if (y.getClass() != this.getClass()) {
        	return false;
        }
        Board that = (Board) y;
        if (this.size != that.size) {
        	return false;
        }
        for (int i = 0; i < size; i++) {
        	for (int j = 0; j < size; j++) {
        		if (this.tiles[i][j] != that.tiles[i][j]) {
        			return false;
        		}
        	}
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
    	Stack<Board> s = new Stack<Board>();
    	Board u = upNieghbor();
    	Board d = downNieghbor();
    	Board l = leftNieghbor();
    	Board r = rightNieghbor();
    	if (u != null) {
    		s.add(u);
    	}
    	if (d != null) {
    		s.add(d);
    	}
    	if (l != null) {
    		s.add(l);
    	}
    	if (r != null) {
    		s.add(r);
    	}
    	return s;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
    	StringBuilder s = new StringBuilder();
        s.append(this.size + "\n");
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) {
    	int[][] a = new int[3][3];
    	a[0][0] = 8;
    	a[0][1] = 1;
    	a[0][2] = 3;
    	a[1][0] = 4;
    	a[1][1] = 2;
    	a[1][2] = 0;
    	a[2][0] = 7;
    	a[2][1] = 6;
    	a[2][2] = 5;
    	Board b = new Board(a);
    	StdOut.println(b.hamming());
    	StdOut.println(b.manhattan());
    	StdOut.println(b.size);
    	StdOut.println(b);
    	for (Board c : b.neighbors()) {
    		StdOut.println(c);
    	}
    	StdOut.println(b.twin());
    }
}
