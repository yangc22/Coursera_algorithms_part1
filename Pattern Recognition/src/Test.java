import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.StdOut;

public class Test {

	public static void main(String[] args) {

	    Point p1 = new Point(1000, 7000);
	    Point p2 = new Point(25000, 31000);
	    Map<Point, Double> detector = new TreeMap<Point, Double>();
	    detector.put(p2, p1.slopeTo(p2));
	    Point p3 = new Point(11000, 17000);
	    StdOut.println(detector.containsKey(p2));
	    StdOut.println(detector.get(p2) == p3.slopeTo(p2));
	}

}
