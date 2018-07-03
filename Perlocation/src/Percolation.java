import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private int overallSize;
	private int gridSize;
	private boolean[][] gridStat;
	private int virtualTop;
	private int virtualBottom;
	private int numberOfOpenSites;
	private boolean topStat;
	private boolean bottomStat;
	
    public Percolation(int n) {
    	if (n <= 0) {
    		throw new IllegalArgumentException("The grid size should be positive!");
    	} else {
    		numberOfOpenSites = 0;
    		gridSize = n;
    		overallSize = n * n;
    		virtualTop = 0;
    		virtualBottom = overallSize + 1;
	        grid = new WeightedQuickUnionUF(overallSize + 2);
	        gridStat = new boolean[n][n];
	        for (int i = 1; i < gridSize + 1; ++i) {
	        	grid.union(virtualTop, xyTo1D(1, i));
	        	grid.union(virtualBottom, xyTo1D(gridSize, i));
	        }
    	}
    }
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
	    	validate(row, col);
	        numberOfOpenSites++;
	        gridStat[row - 1][col - 1] = true;
	        if (row == 1) {
	        	topStat = true;
	        } 
	        if (row == gridSize) {
	        	bottomStat = true;
	        }
	        connectUp(row, col);
	        connectDown(row, col);
	        connectLeft(row, col);
	        connectRight(row, col);
        }
    }
    
   public boolean isOpen(int row, int col) {
	   validate(row, col);
	   return gridStat[row - 1][col - 1];
   }
   
   public boolean isFull(int row, int col) {
	   validate(row, col);
	   return (grid.connected(xyTo1D(row, col), virtualTop) && isOpen(row, col));
   }
   
   public     int numberOfOpenSites() {
	   return numberOfOpenSites;
   }
   
   public boolean percolates() {
       return (grid.connected(virtualTop, virtualBottom) && topStat && bottomStat);
   }
   
   private int xyTo1D(int row, int col) {
	   int index = (row - 1) * gridSize + col;
	   return index;
   }
   
   private void connectUp(int row, int col) {
	   if (row - 1 >= 1) {
		   if (isOpen(row - 1, col)) {
			   grid.union(xyTo1D(row, col), xyTo1D(row - 1, col));
		   }
	   }
   }
   
   private void connectDown(int row, int col) {
	   if (row + 1 <= gridSize) {
		   if (isOpen(row + 1, col)) {
			   grid.union(xyTo1D(row, col), xyTo1D(row + 1, col));
		   }
	   }
   }
   
   private void connectLeft(int row, int col) {
	   if (col - 1 >= 1) {
		   if (isOpen(row, col - 1)) {
			   grid.union(xyTo1D(row, col), xyTo1D(row, col - 1));
		   }
	   }
   }
   
   private void connectRight(int row, int col) {
	   if (col + 1 <= gridSize) {
		   if (isOpen(row, col + 1)) {
			   grid.union(xyTo1D(row, col), xyTo1D(row, col + 1));
		   }
	   }
   }
   
   private void validate(int row, int col) {
	   if (row <= 0 || row > gridSize) {
       	throw new IllegalArgumentException("Row index is out of bound!");
       } else if (col <= 0 || col > gridSize) {
       	throw new IllegalArgumentException("Column index is out of bound!");
       }
   }

   public static void main(String[] args) {
       Percolation p = new Percolation(3);
       // System.out.println(p.percolates());
       // System.out.println(p.isFull(1, 1));
       p.open(1, 1);
       p.open(2, 1);
       p.open(3, 1);
       p.open(2, 1);
       p.open(3, 1);
       System.out.println(p.isFull(3, 1));
       System.out.println(p.numberOfOpenSites());
       System.out.println(p.percolates());
   }
}