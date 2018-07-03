import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trialNumber;
	private final double mean;
	private final double stddev;
	
	public PercolationStats(int n, int trials) {
	    if (n <= 0 || trials <= 0) {
	    	throw new IllegalArgumentException("The input should be non-negative!");
	    }
	    int gridSize = n;
	    trialNumber = trials;
	    double[] data = new double[trialNumber];
	    
	    for (int i = 0; i < trialNumber; ++i) {
	    	Percolation p = new Percolation(gridSize);
	    	while (!p.percolates()) {
	    		int row = StdRandom.uniform(gridSize) + 1;
	    		int col = StdRandom.uniform(gridSize) + 1;
	    		if (!p.isOpen(row, col)) {
	    			p.open(row, col);
	    		}
	    	}
	    	double threshold = 1.0 * p.numberOfOpenSites() / (gridSize * gridSize);
	    	data[i] = threshold;
	    }
	    mean = StdStats.mean(data);
	    if (trialNumber == 1) {
	        stddev = Double.NaN;
	    } else {
	    	stddev = StdStats.stddev(data);
	    }
	}
	
   public double mean() {
	   return this.mean;
   }
   
   public double stddev() {
       return this.stddev;
   }
   
   public double confidenceLo() {
	   double confidenceLo = this.mean - 1.96 * this.stddev / Math.sqrt(trialNumber);
	   return confidenceLo;
   }
   
   public double confidenceHi() {
	   double confidenceHi = this.mean + 1.96 * this.stddev / Math.sqrt(trialNumber);
	   return confidenceHi;
   }
   
   public static void main(String[] args) {
	   int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);
       PercolationStats p = new PercolationStats(n, t);
       double mean = p.mean();
       double stddev = p.stddev();
       double cL = p.confidenceLo();
       double cH = p.confidenceHi();
       StdOut.println("mean \t= " + mean);
       StdOut.println("stddev \t= " + stddev);
       StdOut.println("95% confidence interval = [" + cL + ", " + cH + "]");
   }
}