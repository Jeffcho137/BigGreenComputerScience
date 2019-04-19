import java.util.ArrayList;
import java.util.List;

/**
 * A point quadtree: stores an element at a 2D position, 
 * with children at the subdivided quadrants
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * @author CBK, Spring 2016, explicit rectangle
 * @author CBK, Fall 2016, generic with Point2D interface
 * 
 */
public class PointQuadtree<E extends Point2D> {
	private E point;							// the point anchoring this node
	private int x1, y1;							// upper-left corner of the region
	private int x2, y2;							// bottom-right corner of the region
	private PointQuadtree<E> c1, c2, c3, c4;	// children

	/**
	 * Initializes a leaf quadtree, holding the point in the rectangle
	 */
	public PointQuadtree(E point, int x1, int y1, int x2, int y2) {
		this.point = point;
		this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
	}

	// Getters
	
	public E getPoint() {
		return point;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	/**
	 * Returns the child (if any) at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public PointQuadtree<E> getChild(int quadrant) {
		if (quadrant==1) return c1;
		if (quadrant==2) return c2;
		if (quadrant==3) return c3;
		if (quadrant==4) return c4;
		return null;
	}

	/**
	 * Returns whether or not there is a child at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public boolean hasChild(int quadrant) {
		return (quadrant==1 && c1!=null) || (quadrant==2 && c2!=null) || (quadrant==3 && c3!=null) || (quadrant==4 && c4!=null);
	}

	/**
	 * Inserts the point into the tree
	 */
	public void insert(E p2) {
		// TODO: YOUR CODE HERE
		//1 & 4
		if(p2.getX()>=point.getX()) {
			
			if(p2.getY() < point.getY()) {
				if(hasChild(1)) {
					c1.insert(p2);
				}
				else {
					c1 = new PointQuadtree<E>(p2, (int)getPoint().getX(), y1, x2, (int)getPoint().getY());
				}
			}
			
			else{
				if(hasChild(4)) {
					c4.insert(p2);
				}
				else c4 = new PointQuadtree<E>(p2, (int)getPoint().getX(), (int)getPoint().getY(), x2, y2);
			}
		}
		//2 & 3
		else { 
			if(p2.getY() < point.getY()) {
				if(hasChild(2)) {
					c2.insert(p2);
				}
				else {
					c2 = new PointQuadtree<E>(p2, x1, y1, (int)getPoint().getX(), (int)getPoint().getY());
				}
			}
			else if(p2.getX()<point.getX()) {
				if(hasChild(3)) {
					c3.insert(p2);
				}
				else {
					c3 = new PointQuadtree<E>(p2, x1, (int)getPoint().getY(), (int)getPoint().getX(), y2);
				}
			}
		}
		
	}
	
	/**
	 * Finds the number of points in the quadtree (including its descendants)
	 */
	public int size() {
		// TODO: YOUR CODE HERE
		return ((PointQuadtree<E>) point).allPoints().size();
	}
	
	/**
	 * Builds a list of all the points in the quadtree (including its descendants)
	 */
	public List<E> allPoints() {
		// TODO: YOUR CODE HERE
		//ArrayList<E> allPoints = new ArrayList<E>();
		List<E> allPoints = new ArrayList<E>();
		allPoints(allPoints, this);
		return allPoints;
	}	

	/**
	 * Uses the quadtree to find all points within the circle
	 * @param cx	circle center x
	 * @param cy  	circle center y
	 * @param cr  	circle radius
	 * @return    	the points in the circle (and the qt's rectangle)
	 */
	public List<E> findInCircle(double cx, double cy, double cr) {
		// TODO: YOUR CODE HERE
		/**
		 * To find all points within the circle (cx,cy,cr), stored in a tree covering rectangle (x1,y1)-(x2,y2)
		 * If the circle intersects the rectangle
		 * If the tree's point is in the circle, then the blob is a "hit" 
		 * For each quadrant with a child
		 * Recurse with that child
		 */
		List<E> allpoints =new ArrayList<E>();
		findInCircle(cx, cy, cr, allpoints, this);
		return allpoints;
	}

	// TODO: YOUR CODE HERE for any helper methods
	
	/**
	 * Helper method for findInCircle, uses circleIntersectsRectangle from geometry
	 * If  intersect, add to list else keep looping
	 * @param cx
	 * @param cy
	 * @param cr
	 * @param points
	 * @param tree
	 */
	public void findInCircle(double cx, double cy, double cr, List<E> points, PointQuadtree<E> tree) {
		if (Geometry.circleIntersectsRectangle(cx, cy, cr, (double) tree.x1, (double) tree.y1, (double) tree.x2, (double) tree.y2)) {
			if(Geometry.pointInCircle(tree.point.getX(),tree.point.getY(),cx,cy,cr))
				points.add(tree.point);
			for (int i = 1; i <= 4; i++)
				if (tree.getChild(i) != null)
					findInCircle(cx, cy, cr, points, tree.getChild(i));
		}
	}
	
	/**
	 * Helper method for allPoints: tests if point has children & adds to list recursively
	 * @param points
	 * @param tree
	 */
	public void allPoints(List<E> points, PointQuadtree<E> tree) {
		points.add(tree.point);
		for (int i = 1; i <= 4; i++)
			if (tree.getChild(i) != null)
				allPoints(points, getChild(i));
	}
}
