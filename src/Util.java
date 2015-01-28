

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Util {
	public static BufferedImage readImage(File resource)
			throws IOException {

		BufferedImage image = ImageIO.read(resource);

		if (image == null) {
			// file wasn't an image.
			throw new IOException();
		}
		image = createCompatibleImage(image);
		return image;
	}

	/**
	 * I believe this does: Converts a bufferedimage into a format which can be
	 * read faster on the current machine Found here:
	 * http://stackoverflow.com/questions
	 * /6319465/fast-loading-and-drawing-of-rgb-data-in-bufferedimage But that
	 * was originally from another question (which I couldn't find).
	 *
	 * @param image
	 * @return a better bufferedimage
	 */
	public static BufferedImage createCompatibleImage(BufferedImage image) {

		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		BufferedImage newImage = gc.createCompatibleImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return newImage;
	}
	
	/**
	 * http://stackoverflow.com/a/9417836/1696114
	 * @param img
	 * @param newW
	 * @param newH
	 * @return
	 */
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}
	
	/**
	 * http://stackoverflow.com/questions/2103368/color-logic-algorithm
	 * @param c1
	 * @param c2
	 * @return the difference between the colours
	 */
	public static double colourDistance(Color c1, Color c2)
	{
	    double rmean = ( c1.getRed() + c2.getRed() )/2;
	    int r = c1.getRed() - c2.getRed();
	    int g = c1.getGreen() - c2.getGreen();
	    int b = c1.getBlue() - c2.getBlue();
	    double weightR = 2 + rmean/256;
	    double weightG = 4.0;
	    double weightB = 2 + (255-rmean)/256;
	    return (Math.sqrt(weightR*r*r + weightG*g*g + weightB*b*b)/3)/256;
	}
	
}

