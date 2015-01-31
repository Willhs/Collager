package collager;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;


/**
 * @author will
 * A scrap image at a position on the collage
 * used to make up part of the goal image of a collage
 */
public class Scrap {

	private BufferedImage image;
	private Point pos;
	private double currentFitness; 
	
	public Scrap(BufferedImage image){
		this.image = image;
		this.pos = getRandomStartPos();
	}	

	/**
	 * Determines how fit this scrap is in it's current position 
	 * @param goal
	 * @return
	 */
	public double evaluateFitness(BufferedImage goal){
		long startTime = System.currentTimeMillis(); // for timing
		
		double totalColourDist = 0;
		
		for (int r = 0; r < image.getHeight(); r++){
			for (int c = 0; c < image.getWidth(); c++){				
				Color scrapPixel = new Color(image.getRGB(c,r), true);
				Color goalPixel = new Color(goal.getRGB(pos.x + c, pos.y + r), true);
				double scrapAlpha = (double)scrapPixel.getAlpha()/255; // between 0 and 1 (0 = transparent)
				
				// transpareny reduces difference
				double colourDist = Util.colourDistance(scrapPixel, goalPixel) * scrapAlpha;							
				
				totalColourDist += colourDist;				
				
				/*System.out.println("---- Pixel [" + (pos.x + c) + ", " + (pos.y + r) + "]");
				System.out.println("goal colour: "+ goalPixel.getRed() + " " + goalPixel.getGreen() + " " + goalPixel.getBlue() +" "+ scrapPixel.getAlpha());
				System.out.println("scrap colour: "+ scrapPixel.getRed() + " " + scrapPixel.getGreen() + " " + scrapPixel.getBlue() +" "+  scrapPixel.getAlpha());
				System.out.println("colourDist: "+ colourDist);*/
			}
		}		
		
		int pixelsCompared = image.getHeight() * image.getWidth();
		double fitnessValue = 1-(totalColourDist / pixelsCompared);
		
		currentFitness = fitnessValue;		
		return fitnessValue;
	}
	
	/**
	 * Puts the scrap in a different position based on it's current success
	 * @param fitnessValue
	 */
	public void evolve(double fitnessValue, int stickiness, int jumpiness) {
		if (fitnessValue < 0 || fitnessValue > 1){
			throw new IllegalArgumentException("fitness value should be in range 0 - 1 but was " + fitnessValue);
		}
		
		// proportional to the image size and a scaling factor
		double difference = 1-fitnessValue;
		double exp = Math.pow(difference, -stickiness);		
		
		int moveX = (int) Math.round(Math.pow(Math.random(), exp) * jumpiness * (Math.random() >= 0.5 ? 1 : -1));
		int moveY = (int) Math.round(Math.pow(Math.random(), exp) * jumpiness * (Math.random() >= 0.5 ? 1 : -1));
		
		//System.out.println("moveX: " +moveX + " moveY: " + moveY);
		
		// get new positions, making sure to stay within the bounds of the goal image
		int newX = Math.max(0, Math.min(Main.GOAL_IMG_WIDTH - image.getWidth(), pos.x + moveX));
		int newY = Math.max(0, Math.min(Main.GOAL_IMG_HEIGHT - image.getHeight(), pos.y + moveY));
		
		pos.setLocation(newX, newY);
	}
	
	private Point getRandomStartPos() {
		
		int posX = (int)(Math.random() * Main.GOAL_IMG_WIDTH);
		int posY = (int)(Math.random() * Main.GOAL_IMG_HEIGHT);
		
		// ensure the point stays inside the image frame
		int newX = Math.max(0, Math.min(Main.GOAL_IMG_WIDTH - image.getWidth(), posX)); 
		int newY = Math.max(0, Math.min(Main.GOAL_IMG_HEIGHT - image.getHeight(), posY));
		return new Point(newX, newY);
	}

	public Point getPosition() {
		return pos;
	} 
	
	public BufferedImage getImage(){
		return image;
	}

	public double getCurrentFitness() {
		return currentFitness;
	}
	
}
