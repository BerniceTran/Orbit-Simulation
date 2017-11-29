/*  Class: Physics 204
 *  Name: Bernice Tran
 *  Project: Simulating orbits by changing the exponent value of distance in Newton's Law of Universal Gravitation
 */

import java.awt.Color;

public class Planet
{
	public static final double G = 6.67e-11; //gravitational constant
	public static final double sunMass = 1.98e30;
	
	public double rx, ry; //Cartesian positions of the planet  
	public double vx, vy; //x and y component of the velocity of the planet 
	public double fx, fy; //x and y component of the gravitational force between the two planets
	public double mass; //mass of the planet
	Color color; //color of planet

	//creates and initializes a planet
	public Planet(double rx, double ry, double vx, double vy, double mass, Color color)
	{
		this.rx = rx;
		this.ry = ry;
		this.vx = vx;
		this.vy = vy;
		this.mass = mass;
		this.color = color;
	}
	
	//calculates the distance between sun and planet using Pythagorean theorem
	public double calculateDistanceTo(Planet p)
	{
		double x = rx - p.rx;
		double y = ry - p.ry;
		return Math.sqrt((x * x) + (y * y));
	}
	
	public void calculateForce(Planet b, double exponent) 
	{
		Planet a = this;
		double dx = b.rx - a.rx;
		double dy = b.ry - a.ry;
		double r = Math.sqrt(dx*dx + dy*dy);
		double F = (G * a.mass * b.mass) / (Math.pow(r,exponent));  
		a.fx += F * dx / r;  
		a.fy += F * dy / r;
	}
	
	//updates velocity and positions 
	public void update(double time)
	{
		vx += (fx/mass) * time; //f=ma -> f=m(v/t) -> v=f/m*t
		vy += (fy/mass) * time;
		rx += vx * time; //v=d/t -> d=vt
		ry += vy * time;
	}
	
	//sets the force to 0 for the next iteration
	public void resetForce()
	{
	    fx = 0.0;
	    fy = 0.0;
	}

	
}
