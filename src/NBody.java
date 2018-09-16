	

/**
 * @author YOUR NAME THE STUDENT IN 201
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		Scanner s = new Scanner(new File(fname));
		s.nextInt();	//moving over the number of bodies
		double r = s.nextDouble();
		s.close();
		return r;	
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static Body[] readBodies(String fname) throws FileNotFoundException {
		
			Scanner s = new Scanner(new File(fname));
			int nb = s.nextInt(); // # bodies to be read
			Body[] bodies = new Body[nb];
			s.nextDouble();		//moving over the universe radius
			for(int i = 0; i < nb; i++) {
				double x = s.nextDouble();
				double y = s.nextDouble();
				double xv = s.nextDouble();
				double yv = s.nextDouble();
				double mass = s.nextDouble();
				String name = s.next();
				Body b = new Body(x, y, xv, yv, mass, name);
				bodies[i] = b;
			}
			s.close();
			return bodies;
	}
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 157788000.0;
		double dt = 25000.0;
		
		String fname= "./data/planets.txt";
		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		Body[] bodies = readBodies(fname);
		double radius = readRadius(fname);
		
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");
	
		for(double t = 0.0; t < totalTime; t += dt) {
			double[] xforces = new double[bodies.length];
			double[] yforces = new double[bodies.length];
			for(int i = 0; i < bodies.length; i++) {
				xforces[i] = bodies[i].calcNetForceExertedByX(bodies);
			}
			for(int i = 0; i < bodies.length; i++) {
				yforces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}
			for(int i = 0; i < bodies.length; i++) {
				bodies[i].update(dt, xforces[i], yforces[i]);
			}
			StdDraw.picture(0,0,"images/starfield.jpg");
			for(Body b : bodies) {
				b.draw();
			}
			StdDraw.show(10);
		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
