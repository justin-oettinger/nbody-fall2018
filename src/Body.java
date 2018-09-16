
public class Body {
	
	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;
	
	//Constructors
	/**
	 * Constructor to build a brand new Body object based on the parameters xp, yp, xv, yv, mass, and filename, which
	 * will be stored in the body's instance variables as seen above.
	 * @param xp
	 * @param yp
	 * @param xv
	 * @param yv
	 * @param mass
	 * @param filename
	 */
	public Body(double xp, double yp, double xv, double yv, double mass, String filename) {
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
	}
	/**
	 * Constructor to build a brand new Body object that is a copy of an existing Body object. It uses the getter
	 * methods implemented below to retrieve the private instance variables from body b, and stores those values in the
	 * instance variables for the new body.
	 * @param b
	 */
	public Body(Body b) {
		myXPos = b.getX();
		myYPos = b.getY();
		myXVel = b.getXVel();
		myYVel = b.getYVel();
		myMass = b.getMass();
		myFileName = b.getName();
	}
	
	//Getters
	/**
	 * Getter to retrieve private variable myXPos.
	 * @return
	 */
	public double getX() {
		return myXPos;
	}
	/**
	 * Getter to retrieve private variable myYPos.
	 * @return
	 */
	public double getY() {
		return myYPos;
	}
	/**
	 * Getter to retrieve private variable myXVel.
	 * @return
	 */
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Getter to retrieve private variable myYVel.
	 * @return
	 */
	public double getYVel() {
		return myYVel;
	}
	/**
	 * Getter to retrieve private variable myMass.
	 * @return
	 */
	public double getMass() {
		return myMass;
	}
	/**
	 * Getter to retrieve private variable myFileName.
	 * @return
	 */
	public String getName() {
		return myFileName;
	}
	
	//Methods
	/**
	 * This method calculates the distance between this body in question and body b, using the basic distance function.
	 * @param b
	 * @return
	 */
	public double calcDistance(Body b) {
		double dx2 = (myXPos - b.getX()) * (myXPos - b.getX());
		double dy2 = (myYPos - b.getY()) * (myYPos - b.getY());
		return Math.sqrt(dx2 + dy2);
	}
	/**
	 * This method uses basic mechanics equation for gravity to determine the gravitational force of a single planet b
	 * upon this planet upon which the method is being called. 
	 * @param b
	 * @return
	 */
	public double calcForceExertedBy(Body b) {
		double g = (6.67E-11);
		double r = this.calcDistance(b);
		return (g * ((myMass * b.getMass()) / (r * r)));
	}
	/**
	 * Using the result of calcForceExertedBy, this calculates the vector of gravitational force in the x plane on this
	 * body caused by body b.
	 * @param b
	 * @return
	 */
	public double calcForceExertedByX(Body b) {
		double f = this.calcForceExertedBy(b);
		double r = this.calcDistance(b);
		return (f * ((b.getX() - myXPos) / r));
	}
	/**
	 * Functionally identical to calcForceExertedByY, except with respect to the force in the y plane.
	 * @param b
	 * @return
	 */
	public double calcForceExertedByY(Body b) {
		double f = this.calcForceExertedBy(b);
		double r = this.calcDistance(b);
		return (f * ((b.getY() - myYPos) / r));
	}
	/**
	 * This method makes use of the calcForceExertedByX method to sum together the forces in the x plane exerted on
	 * this planet by all the planets in the bodies array, notably excluding the planet itself in the calculations (both
	 * for logical and mathematical reasons, since the planet cannot have a gravitational pull on itself nor can the r^2
	 * in the denominator of the force calculation come out to 0).
	 * @param bodies
	 * @return
	 */
	public double calcNetForceExertedByX(Body[] bodies) {
		double netForceX = 0;
		for(Body b : bodies) {
			if(! b.equals(this)) {
				netForceX += this.calcForceExertedByX(b);
			}
		}
		return netForceX;
	}
	/**
	 * Identical to the calcNetForceExertedByX method, but with regards to the forces in the y plane.
	 * @param bodies
	 * @return
	 */
	public double calcNetForceExertedByY(Body[] bodies) {
		double netForceY = 0;
		for(Body b : bodies) {
			if(! b.equals(this)) {
				netForceY += this.calcForceExertedByY(b);
			}
		}
		return netForceY;
	}
	/**
	 * This mutator updates the instance variables reflecting x and y positions and the x and y velocity vectors
	 * based on the time jump (deltaT) between updates and the results of the calcNetForce equations (xforce and yforce).
	 * @param deltaT
	 * @param xforce
	 * @param yforce
	 */
	public void update(double deltaT, double xforce, double yforce) {
		double ax = (xforce / myMass);
		double ay = (yforce / myMass);
		double nvx = (myXVel + (deltaT * ax));
		double nvy = (myYVel + (deltaT * ay));
		double nx = (myXPos + (deltaT * nvx));
		double ny = (myYPos + (deltaT * nvy));
		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
	}
	/**
	 * This is the simple method to draw the planet as it moves through the system, determined by the x and y positions
	 * and the picture drawn is drawn from the project files using the myFileName instance variable.
	 */
	public void draw() {
		StdDraw.picture(myXPos,  myYPos, "images/" + myFileName);
	}
	
}
