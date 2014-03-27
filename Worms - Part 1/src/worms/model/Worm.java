package worms.model;
 
/**
 * A class of worms involving a position consisting of an X- and Y-coordinate, an angle,
 * a radius, a mass, action points (current and maximum)
 *
 * @version 1.0
 * @Author Ruben Schroyen & Ralph Vancampenhoudt
 * 
 * @invar isValidRadius(getRadius())
 * 
 * @invar isValid
 */
 
import java.lang.Math;

import be.kuleuven.cs.som.annotate.Basic;
import worms.util.Util;
 
public class Worm {                    
	
	
         /**
         *   The X-coordinate for the worm in meters
         **/
	     private double posX;
	
	     
	     /**
	     *   The Y-coordinate for the worm in meters
	     **/
	     private double posY;
	     
	     
	     /**
	      *  The angle the worm is facing in radial
	      **/
	     private double angle;
	     
	     
	     /**
	      *   The radius of a worm in meter
	      */
	     private double radius;
	     
	     
	     /**
	      *   The minimal radius a worm has to be in meter (0.25m)
	      */
	     public final double minRadius = 0.25;
	     
	     
	     /**
	      *   The mass a worm has in kilogram
	      */
	     private double mass;
	     
	     
	     /**
	      *   The density a worm has (1062kg/m³)
	      */
	     private static final double density = 1062;

	     
	     /**
	      *   The current amount of action points a worm has
	      */
	     private int currentAP;
	     
	     
	     /**
	      *   The maximum amount of action points a worm has
	      */
	     private int maxAP;

	     
	     
	     /**
	      *   The name a worm has been given
	      */
	     private String name;
	     
	     
	     /**
	      *   The earth acceleration (9.80665)
	      */
         private final double g = 9.80665;
	     
	     
         /**
	      *   The force with which a worm jumps
	      */
	     private double force;
	     
	     
	     /**
	      *   The velocity with which a worm jumps
	      */
	     private double velocity;
	     
	     
	     /**
	      *   The distance a worm jumps in meter
	      */
	     private double distance;
	     
	     
	     /**
	      *   The time a worm is in the air
	      */
	     private double time;

         
	     /**
	      * Creates the worm with some initialization parameters.
	      * 
	      * @param x
	      * 			creates the worm in a position on the X-axis of the world
	      * 
	      * @param y
	      * 			creates the worm in a position on the Y-axis of the world
	      * 
	      * @param angle
	      * 			creates the worm with an initial angle of direction
	      *
	      * @param radius
	      * 			creates the worm with an initial radius
	      * 
	      * @param name 
	      * 			creates the worm with a given name
	      * 
	      * @pre given radius must be valid
	      * 		| isValidRadius(radius)
	      * 
	      * @pre given name must be valid
	      * 		| isValidName(name)
	      * 
	      * @pre given orientation is valid
	      * 		| isValidAngle(angle)
	      * 
	      * @effect X-coordinate is set to given value
	      * 		| new.getPosX() == x
	      * 
	      * @effect Y-coordinate is set to given value
	      * 		| new.getPosY() == y
	      * 
	      * @effect radius of the worm is set to given value
	      * 		| new.getRadius() == radius
	      * 
	      * @effect name of the worm is set to the given string if name is valid
	      * 		| new.getName() == name
	      * 
	      * @effect number of action points is set to the maximum number of action points a worm can have 
	      * 		| new.getCurrentAP() == this.getMaxAP
	      * 
	      * @throws IllegalArgumentExeption if the name is not allowed.
	      * 		| !isValidName(name)
	      * 
	      * @throws IllegalArgumentExeption if the X or Y coordinate is not valid, this is done by the method validX or validY.
	      * 		| isValidX(X)   
	      * 		| isValidY(Y)
	      * 
	      * @throws  Illegal argument if radius or angle invalid
	      */ 
        public Worm(double x, double y, double radius, double angle, String name)
        {
                this.setPosX(x);
                this.setPosY(y);
                this.setRadius(radius);
                this.setAngle(angle);
                this.setName(name);
                this.setCurrentAP((int)Math.round(this.getMass()));
        }
        
        
        public int calculateApCostMove (int nbSteps) {
               
                int cost = (int) Math.ceil((Math.abs(Math.cos(this.getAngle())) + 4*Math.abs(Math.sin(this.getAngle())))*nbSteps);
                return cost;
        }
       
        
        
        public int calculateApCostTurn (double angle){
        	int cost; 
        	if (Util.fuzzyLessThanOrEqualTo(Math.abs(angle), Math.PI))
        		cost = (int) Math.ceil(Math.abs(angle)*60/(2*Math.PI));
        		else
        			cost = (int) Math.ceil(Math.abs(angle)*60/(2*Math.PI));      	
                
        	return cost;
        }
       
                       
       
				        
				        public boolean inspectMovement (int nbSteps) {
				                        if (nbSteps < 0)
				                        	return false;
				                        if (calculateApCostMove(nbSteps) <= this.getCurrentAP())
				                        	return true;
				                        else
				                        	return false;
				                        
				                       
				        }
				       
				       
				        public boolean inspectTurn(double angle) {
				        	if (this.getCurrentAP() >= calculateApCostTurn(angle))
				        		return true;
				        	else
				        		return false;
				        }            
                       
                        
                        //Inspect Radius
                        public boolean isValidRadius(double radius) {
                                if ((radius <= this.getRadius()) && (Double.POSITIVE_INFINITY != radius))
                                        return true;
                                else return false;
                        }
                       
                        
                        //Inspect Name
                        public boolean InspectName(String name) {
                                if (name.length() < 2)
                                        return false;
                               
                                else if (!Character.isUpperCase(name.charAt(0)))
                                        return false;
                               
                                else if (!name.matches("[a-zA-Z'\" ]*"))
                                                return false;
                               
                                else return true;
                               
                        }
 
                       
                       
                        //Inspect Position
                        public boolean InspectPosition() {
                                if ((this.getPosX() != Double.NEGATIVE_INFINITY) && (this.getPosY() != Double.NEGATIVE_INFINITY) && (this.getPosX() != Double.POSITIVE_INFINITY) && (this.getPosY() != Double.POSITIVE_INFINITY))
                                        return true;
                                else return false;
                        }
                       
                        
                       
                        //Inspect Mass
                        public boolean InspectMass(){
                                if (density * (4/3) * Math.PI * Math.pow(minRadius,3) <= this.getMass())
                                        return true;
                                else return false;
                        }
                       
                        
                       
                        //Inspect Action Points
                        public boolean InspectActionPoints() {
                                if ((0 <= this.getCurrentAP()) && (this.getCurrentAP() <= this.getMaxAP()))
                                        return true;
                                else return false;
                        }
 
                       
                       
                       
        /**
         * The next thing in line are the methods for the actual gameplay, namely the method to move the worm, to turn the worm,
         * to jump with the worm and the actual physics calculations of JumpStep and JumpTime.
         * These methods will not return any values, but will change the given values into new ones.
         */
                       
                        //Methods for gameplay
                       
                        /**
                         * The method to move the worm into a certain new position if he has enough action points to use.
                         *
                         * @param nbSteps
                         *  	The amount of steps a worm can take with his current amount of action points
                         *              
                         * @throws IllegalArgumentException
                         * 		If the worm does not have enough AP left to do this step
                         * 		| !isValidMovement()
                         *
                         * @post
                         *      If the worm has more than 0 action points, he can take a step to the direction he is facing. The cost of this step is calculated by the fact that
                         *      1 step in the X-direction costs him 1 action point and 1 step in the Y-direction costs him 4 action points.
                         *      Every time a step is taken, the parameter 'nbSteps' increases by 1 and the position gets changed and the action points get reduced.
                         *			| new.getPosX() == posX
                         *			| new.getPosY() == posY
                         *			| new.getCurrentAP() == currentAP
                         *
                         */
                       
                        //Move
                        public void Move(int nbSteps)  throws IllegalArgumentException {
                                this.setPosX(getPosX() + (nbSteps * getRadius()) * Math.cos(this.getAngle()));
                                this.setPosY(getPosY() + (nbSteps * getRadius()) * Math.sin(this.getAngle()));
                                this.setCurrentAP( this.getCurrentAP() - calculateApCostMove(nbSteps) );
                                
                                if(inspectMovement(nbSteps) != true)                                    
                                    throw new IllegalArgumentException("You cannot do another step, you don't have enough Action Points");
                               
                       }
                       
                        /**
                         * The method to turn the worm to a certain new direction if he has enough action points to do so.
                         *
                         * @param newangle
                         *  	The new angle a worm gets after changing it in game. This new angle is used to calculate the cost of this turn
                         * 
                         * @throws IllegalArgumentException
                         * 		If the worm does not have enough AP to do this turn
                         *      | !isValidTurn()
                         *
                         * @post
                         *      If the worm has enough AP to do so, he will turn and change his angle to the new angle, reducing his AP by the calculated amount in calculateApCostTurn.
                         *      To make sure that a worm also loses AP when the new angle is negative, the absolute value of this angle-difference is taken to calculate its cost.
                         *      | new.getAngle() == angle  
                         *      | new.getCurrentAP() == currentAP   
                         */
                       
                        //Turn
                        public void Turn(double newangle) throws IllegalArgumentException {
                                if (inspectTurn(newangle) != true )
                                        throw new IllegalArgumentException("Insufficient Action Points");
                                       
                                this.angle = this.getAngle() + newangle;
                                this.currentAP = this.getCurrentAP() - calculateApCostTurn(Math.abs(newangle - this.angle));    
                                       
                        }
                       
                        //Jump
                        public void Jump() {
                        	
	                            double [] newPosition = this.JumpStep(this.JumpTime());
	                            this.setPosX(newPosition[0]);
	                            this.setPosY(newPosition[1]);
	                            this.setCurrentAP(0);
	                            }
       
                       
                        
                       
                        //JumpTime
                        public double JumpTime(){
                        	if (this.getCurrentAP() != 0){
                            	
                            	this.setForce((5 * this.getCurrentAP()) + (this.getMass() * this.g));
                                this.setVelocity(this.getForce()/this.getMass()*0.5);
                                this.setDistance( (Math.pow(this.getVelocity(), 2) * Math.sin(2*this.getAngle()) ) / this.g);
                                this.setTime(this.getDistance() / (this.getVelocity() * Math.cos(this.getAngle()) ) );
                                return this.getTime();
                                
                                }
                        else {
                        	return 0.0;
                        }
                        }

                       
                        
                        //JumpStep
                        public double[] JumpStep(double Delta_T){
                                             		
                            double velocity_x = this.getVelocity() * Math.cos(this.getAngle());
                            double velocity_y = this.getVelocity() * Math.sin(this.getAngle());
                            double x = this.getPosX() + (velocity_x * Delta_T);
                            double y = this.getPosY() + (velocity_y * Delta_T - 0.5*this.g*Math.pow(Delta_T, 2));
                            double jumpstep[] = new double[] {x,y};
                            
                            if (Util.fuzzyLessThanOrEqualTo(0, this.getAngle())
                				&& (Util.fuzzyLessThanOrEqualTo(this.getAngle(), Math.PI)))
                            	return jumpstep;
                            
                            else return new double[] {this.getPosX(),this.getPosY()};
                        
                        }
                        
                       
                       
                        /**
                         * Last, we put together all the set and get methods to retrieve the values of our parameters declared in the beginning
                         */
                       
                        //Set and Get Methods
                       
                        //Get Max_AP
                        /**
                         * This method recalls the value of the maximum amount of AP a worm can have, namely the mass a worm has rounded upwards to the next integer
                         * 
                         * @return Max_AP
                         * 		Equal to this.getMass() rounded upwards to the next integer
                         */
                        public int getMaxAP() {
                                return maxAP = (int)Math.ceil(this.getMass());
                        }
                       
                        
                        //Set and Get Angle
                        /**
                         * This method recalls the value of the angle the worm is facing
                         * 
                         * @return angle
                         */
                        public double getAngle() {
                                return angle;
                        }
                        
                        /**
                         * This method sets the value for the angle a worm is facing. If the angle surpasses the value of PI, the angle gets reset by extracting PI
                         * 
                         * @param angle
                         * 		The angle of direction a worm is facing
                         * 
                         * @effect 
                         * 		Sets the value of the worms angle to a newly calculated or given angle
                         */
                        public void setAngle(double angle) {
                        	if (angle > Math.PI) 
                        		this.angle = angle - Math.PI;
                        	else if (angle < -Math.PI)
                        		this.angle = angle + Math.PI;
                        	else this.angle = angle;
                        }
                       
                       
                        //Set and Get Radius
                        /**
                         * This method recalls the value of the radius of a worm
                         * 
                         * @return radius
                         */
                        public double getRadius() {
                                return radius;
                        }
                        
                        /**
                         * This method sets the value for the radius of the worm
                         * 
                         * @param radius
                         * 		The radius a worm has been given in meters
                         * 
                         * @effect 
                         * 		Sets the value of the worms radius to a newly calculated or given radius
                         */
                        public void setRadius(double radius) {
                                this.radius = radius;
                        }

                        
                        //Get Mass
                        /**
                         * This method recalls the value of the mass a worm has, equal to density*(4/3)*Math.PI*Math.pow(getRadius(), 3)
                         * 
                         * @return mass
                         * 		This mass is equal to density*(4/3)*Math.PI*Math.pow(getRadius(), 3) and therefore we have shortened the code by just putting the formula
                         */
                        public double getMass() {
                                return density*(4/3)*Math.PI*Math.pow(getRadius(), 3);
                        }
                      
                       
                        //Set and Get Pos_X
                        /**
                         * This method recalls the X-position of the worm
                         */
                        @Basic
                        public double getPosX() {
                                return posX;
                        }
                        
                        /**
                         * This method sets the value for the X-position of the worm
                         * 
                         * @param pos_x
                         * 		The X-position of the worm in meters
                         * 
                         * @effect 
                         * 		Sets the value of the worms X-position to a newly calculated or given X-position
                         */
                        public void setPosX(double x) {
                                this.posX = x;
                        }
                       
                       
                        //Set and Get Pos_Y
                        /**
                         * This method recalls the Y-position of the worm
                         * 
                         * @return pos_y
                         */
                        public double getPosY() {
                                return posY;
                        }
                        
                        /**
                         * This method sets the value for the Y-position of the worm
                         * 
                         * @param pos_y
                         * 		The Y-position of the worm in meters
                         * 
                         * @effect 
                         * 		Sets the value of the worms Y-position to a newly calculated or given Y-position
                         */
                        public void setPosY(double y) {
                                this.posY = y;
                        }
                       
                       
                        //Set and Get Current_AP
                        /**
                         * This method recalls the current amount of AP a worm has
                         * 
                         * @return current_AP
                         * 
                         */
                        public int getCurrentAP() {
                                return currentAP;
                        }
                        
                        /**
                         * This method sets the value for the current amount of AP a worm has
                         * 
                         * @param current_AP
                         * 		The current amount of AP a worm has
                         * 
                         * @effect 
                         * 		Sets the value of the worms current AP to the newly calculated or given current AP
                         */
                        public void setCurrentAP(int currentAP) {
                                this.currentAP = currentAP;
                        }
                       
                       
                        //Set and Get Name
                        /**
                         * This method recalls the given name of the worm
                         * 
                         * @return name
                         */
                        public String getName() {
                                return name;
                        }
                        
                        /**
                         * This method sets the name of the worm
                         * 
                         * @param name
                         * 		The name a worm is given
                         * 
                         * @throws IllegalArgumentException
                         * 		If the name of the worm does not follow his constraints of being longer than 2 characters, starting with an uppercase character and consisting
                         * 		only of letters, single or double quotes and spaces
                         * 
                         * @effect 
                         * 		Sets the value of the worms name to a newly given name if this name is valid
                         */
                        public void setName (String name) throws IllegalArgumentException {
                                        if (InspectName(name) == false)
                                                throw new IllegalArgumentException("Your name has some invalid characters included");
                                this.name = name;
                        }                      
                       
                        //Set and Get Force
                        /**
                         * This method recalls the force with which a worm jumps
                         * 
                         * @return force
                         */
                        public double getForce() {
                                return force;
                        }
                        
                        /**
                         * This method sets the value for the force with which a worm jumps
                         * 
                         * @param Force
                         * 		The force with which a worm jumps
                         * 
                         * @effect 
                         * 		Sets the value of the force with which a worm jumps to the newly calculated or given force
                         */
                        public void setForce(double force) {
                                this.force = force;
                        }
                       
                       
                        //Set and Get Velocity
                        /**
                         * This method recalls the value of the velocity with which a worm jumps
                         * 
                         * @return velocity
                         */
                        public double getVelocity() {
                                return velocity;
                        }
                        
                        /**
                         * This method sets the value for the velocity with which a worm jumps
                         * 
                         * @param velocity
                         * 		the velocity with which a worm jumps
                         * 
                         * @effect 
                         * 		Sets the value of velocity with which a worm jumps to the newly calculated or given velocity
                         */
                        public void setVelocity(double velocity) {
                                this.velocity = velocity;
                        }
                       
                       
                        //Set and Get Distance
                        /**
                         * This method recalls the value of the distance a worm jumps
                         * 
                         * @return distance
                         */
                        public double getDistance() {
                                return distance;
                        }
                        
                        /**
                         * This method sets the value for the distance a worm jumps
                         * 
                         * @param distance
                         * 		the distance a worm jumps
                         * 
                         * @effect 
                         * 		Sets the value of distance a worm jumps to the newly calculated or given distance
                         */
                        public void setDistance(double distance) {
                                this.distance = distance;
                        }
                       
                       
                        //Set and Get Time
                        /**
                         * This method recalls the value of the time a worm is in the air while jumping
                         * 
                         * @return time
                         */
                        public double getTime() {
                                return time;
                        }
                        
                        /**
                         * This method sets the value for the time a worm is in the air while jumping
                         * 
                         * @param time
                         * 		The time a worm is in the air while jumping
                         * 
                         * @effect 
                         * 		Sets the value of the time a worm is in the air while jumping to the newly calculated or given time
                         */
                        public void setTime(double time) {
                                this.time = time;
                        }
                        }