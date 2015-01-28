import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;


/**
 * @author will
 *
 * Collages scrap images to try to make up the goal image
 */
public class Collager {

	private Set<Scrap> scraps;
	private BufferedImage goal;
	private int stickiness = 4; // how sticky the scraps are when evolving
	private int jumpiness = 50; // how jumpy the scraps can be

	public Collager(Set<BufferedImage> scrapImages, BufferedImage goal, int totalScraps ) {
		this.scraps = new HashSet<Scrap>();
		int copies = (int)Math.ceil(totalScraps/scrapImages.size());
		
		for (BufferedImage scrapImage : scrapImages){
			for (int i = 0; i < copies; i++){
				scraps.add(new Scrap(scrapImage));
			}
		}
		System.out.println(scraps.size() + " scraps in collage");
		this.goal = goal;
	}


	/**
	 * Evolves the scraps towards the goal image
	 */
	public void evolve() {
		for (Scrap scrap : scraps){
			double fitnessValue = scrap.evaluateFitness(goal);
			//System.out.println("fitness value: " + fitnessValue);
			scrap.evolve(fitnessValue, stickiness, jumpiness);
		}
	}


	public BufferedImage getCurrentEvolution() {
		BufferedImage collage = new BufferedImage(Main.GOAL_IMG_WIDTH, Main.GOAL_IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = collage.getGraphics();
		
		for (Scrap scrap : scraps){
			g.drawImage(scrap.getImage(), scrap.getPosition().x, scrap.getPosition().y, null);
			g.setColor(Color.BLACK);
			g.drawString(String.format("%4.2f", scrap.getCurrentFitness()), scrap.getPosition().x, scrap.getPosition().y);
		}
		return collage;
	}


	public boolean keepEvolving() {
		return true;
	}


	public void setStickiness(int stickiness) {
		this.stickiness = stickiness;		
	}	
	
	public void setJumpiness(int jumpiness){
		this.jumpiness = jumpiness;
	}
}
