package gui;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class CollagePanel extends JPanel{

	private BufferedImage evolvedImage;
	private BufferedImage goalImage;
	private final int WIDTH, HEIGHT;
	
	public CollagePanel(int width, int height, BufferedImage goalImage){
		this.WIDTH = width;
		this.HEIGHT = height;
		this.goalImage = goalImage;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (evolvedImage != null){
			g.drawImage(goalImage, 0, 0, goalImage.getWidth(), goalImage.getHeight(), null);
			g.drawImage(evolvedImage, goalImage.getWidth(), 0, evolvedImage.getWidth(), evolvedImage.getHeight(), null);
		}
		else {
			g.drawString("image is null", WIDTH/2 - 20, HEIGHT/2);
		}
	}

	public void setImage(BufferedImage evolvedImage) {
		this.evolvedImage = evolvedImage;
	}
	
}
