/*  Class: Physics 204
 *  Name: Bernice Tran
 *  Project: Simulating orbits by changing the exponent value of distance in Newton's Law of Universal Gravitation
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class OrbitSimulation extends Applet implements ActionListener
{
	private static final double G = 6.67e-11; //gravitational constant
	private static final double sunMass = 1.98e30; //mass of the Sun
	private static final double earthMass = 5.97e24; //mass of Earth
	private double exponent = 2;
    private int N = 2;
    private Planet planets[]= new Planet[N];
//    public SpinnerNumberModel input;
//    public JSpinner inputSpinner;
    private TextField input;
    private Label label;
    private Button play;
    private Button stop;
    private boolean shouldrun = false;
    
    //first time applet is called this function will start 
    public void init()
    {
    	startPlanets(N);
        this.setSize(1100,800);
        setBackground(Color.black);
        play = new Button("Play");
        stop = new Button("Stop");
        label = new Label("Newton's Law of Universal Gravitation: F = ((G(m1)(m2))/r^2). "
        		+ "Enter an exponent value of the distance (r) to simulate the changes in orbit of the Earth around the Sun:");
        label.setForeground(Color.white);
        input = new TextField("2",5);
        play.addActionListener(this);
        stop.addActionListener(this);
        //input = new SpinnerNumberModel(2, 1.8, 2.2, 0.05);
        //inputSpinner = new JSpinner(input);
        add(label);
        //add(inputSpinner);
        add(input);
        add(play);
        add(stop);
    }
  
    //called when applet is terminated to stop the code
    public void stop()
    {
    	shouldrun = false;
    }
   
    //called by the applet initally
    public void paint(Graphics g)
    {
    	exponent = Double.parseDouble(input.getText());
    	g.translate(550,400);
    
    	if (shouldrun) 
    	{
    		for(int i = 0; i < N; i++)  
    		{
    			g.setColor(planets[i].color);
    			g.fillOval((int)(planets[i].rx/1e15),(int) (planets[i].ry/1e15),20,20);
    		}
    		for (int i = 0; i < N; i++) 
    		{
    			planets[i].resetForce();  
    			for (int j = 0; j < N; j++)
    			{
    				if (i != j) planets[i].calculateForce(planets[j],exponent);
    			}
    		}
    		for (int i = 0; i < N; i++) 
    		{
    			planets[i].update(365e7); //updates the position and velocity using timestep 
    		}
    		repaint();
    	}
    }
  
    //initializes the Sun and Earth with positions and circular velocities
    public void startPlanets(int N) 
	{
    	double rx = 1.49e17; //earth to sun distance
	    double ry = 1.49e17;
	    double r = Math.sqrt(rx*rx + ry*ry); 
	    double numerator = (G)*1e6*sunMass;  //
	    double magv = Math.sqrt(numerator/r);  
	    double angle = Math.atan(Math.abs(ry/rx));
	    double thetav = Math.PI/2-angle;
	    double vx = -1*Math.signum(ry)*Math.cos(thetav)*magv;
	    double vy = Math.signum(rx)*Math.sin(thetav)*magv;
	
	    planets[0]= new Planet(0,0,0,0,1e6*sunMass,Color.orange); //sun in the center with no velocity
	    planets[1]   = new Planet(rx, ry, vx, vy, earthMass, Color.blue);
	}
    
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Get label of the button clicked in event passed in 
	    if (e.getSource() == play)
	    {
	    	shouldrun=true;
	        startPlanets(2);
	        repaint();
	    }
	    else if (e.getSource() == stop) 
	        stop();
	}
	
}
