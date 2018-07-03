import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	
	private Point[] p;
	private int num;
	private LineSegment[] ls;
	
	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException("The input can not be null!");
	    }
	    p = new Point[points.length];
	    for (int i = 0; i < points.length; i++) {
	    	if (points[i] == null) {
	    			throw new IllegalArgumentException("There is null element in the input!");
	    	}
	    	p[i] = points[i];
	    }
	    Arrays.sort(p);
	    for (int i = 0; i < p.length - 1; i++) {
	    	if (p[i].compareTo(p[i + 1]) == 0) {
	    		throw new IllegalArgumentException("There are repeated points in the input!");
	    	}
	    }
	    ArrayList<LineSegment> lSTem = new ArrayList<LineSegment>();
	    Map<Point, TreeSet<Point>> detector = new TreeMap<Point, TreeSet<Point>>();
	    for (int i = 0; i < p.length - 3; i++) {
	    	Arrays.sort(p);
	    	Arrays.sort(p, i + 1, p.length, p[i].slopeOrder());
	    	// StdOut.println(p[i]);
	    	for (int j = i + 1; j < p.length - 2; j++) {
	    		// StdOut.println(j + " " + p[j]);
	    		if (p[i].slopeTo(p[j]) == p[i].slopeTo(p[j + 1])) {
	    			int k = 2;
	    			j++;
	    			// StdOut.println("  " + j + " " + p[j]);
	    			while ((j + 1) < p.length && p[i].slopeTo(p[j]) == p[i].slopeTo(p[j + 1])) {
	    				j++;
	    				k++;
	    				// StdOut.println("  " + j + " " + p[j]);
	    			}
	    			if (k >= 3) {
	    				/* StdOut.println(i);
	    				StdOut.println(j);
	    				StdOut.println(k);
	    				StdOut.println(p[i]);
	    				StdOut.println(p[j - k]);
	    				StdOut.println(p[j - k + 1]);
	    				StdOut.println(p[j - k + 2]);
	    				StdOut.println(p[j - k + 3]); */
	    				/* boolean tester = true;
	    				for (int l = 0; l < i; l++) {
	    					if (p[i].slopeTo(p[j]) == p[l].slopeTo(p[j])) {
	    						tester = false;
	    					}
	    				}
	    				if (tester) {
	    					lSTem.add(new LineSegment(p[i], p[j]));
	    				} */
	    				boolean tester = true;
	    				if (detector.containsKey(p[j])) {
	    					for (Point pp : detector.get(p[j])) {
	    						if (pp.slopeTo(p[j]) == p[i].slopeTo(p[j])) {
	    							tester = false;
	    						}
	    					}
	    				}
	    				if(tester) {
	    					lSTem.add(new LineSegment(p[i], p[j]));
	    					if (!detector.containsKey(p[j])) {
	    						detector.put(p[j], new TreeSet<Point>());
	    					}
	    					detector.get(p[j]).add(p[i]);
	    				}
	    			}
	    		}
	    	}
	    }
	    ls = new LineSegment[lSTem.size()];
	    num = 0;
	    for (LineSegment l : lSTem) {
	    	ls[num++] = l;
	    }
	}
	
	public           int numberOfSegments() {
		return num;
	}
	
	public LineSegment[] segments() {
		LineSegment[] lSCopy = new LineSegment[ls.length];
		   for (int i = 0; i < ls.length; i++) {
			   lSCopy[i] = ls[i];
		   }
		   return lSCopy;
	}
	
	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
