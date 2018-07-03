import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	
   private Point[] p;
   private LineSegment[] ls;
   private int num;
	
   public BruteCollinearPoints(Point[] points) {
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
       for (int i = 0; i < p.length; i++) {
    	   	for (int j = i + 1; j < p.length; j++) {
	    		   	for (int k = j + 1; k < p.length; k++) {
		    			   	for (int l = k + 1; l < p.length; l++) {
		    			   		if ((p[i].slopeTo(p[j]) == p[i].slopeTo(p[k])) && ((p[i].slopeTo(p[k]) == p[i].slopeTo(p[l])))) {
		    			   			lSTem.add(new LineSegment(p[i], p[l]));
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
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}