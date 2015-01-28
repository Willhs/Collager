import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Main {

	public static final int GOAL_IMG_WIDTH = 500;
	public static final int GOAL_IMG_HEIGHT = 500;

	public static void main(String[] args){
		
		// Choose options for the collage
		int[] options = requestOptions();
		int totalScraps = options[0];
		int sizeScraps = options[1];
		
		// get files from user
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose collage images");
		chooser.setMultiSelectionEnabled(true);
		chooser.setCurrentDirectory(new File("pics"));
		if (chooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
			System.exit(0);
		}
		File[] files = chooser.getSelectedFiles();

		Set<BufferedImage> scrapImages = new HashSet<BufferedImage>();
		
		for (File file : files){
			try {
				BufferedImage image = Util.createCompatibleImage((Util.readImage(file)));
				image = Util.resize(image, sizeScraps, sizeScraps);
				scrapImages.add(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		chooser.setMultiSelectionEnabled(false);
		chooser.setDialogTitle("Choose goal image");
		if (chooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
			System.exit(0);
		}
		File file = chooser.getSelectedFile();
		BufferedImage goalImage = null;
				
		try {
			goalImage = Util.createCompatibleImage((Util.readImage(file)));
			goalImage = Util.resize(goalImage, GOAL_IMG_WIDTH, GOAL_IMG_HEIGHT);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		// ----- IMPORTANT STUFF ------
		
		Collager collager = new Collager(scrapImages, goalImage, totalScraps);
		
		// GUI
		GUI gui = new GUI(goalImage, collager);
		
		// Keep evolving and displaying current evolution
		while (collager.keepEvolving()){
			collager.evolve();
			gui.setImage(collager.getCurrentEvolution());
			gui.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
	
	public static int[] requestOptions(){
		JTextField numScraps = new JTextField(Integer.toString(500));
	    JTextField sizeScraps = new JTextField(Integer.toString(15));

	    JPanel myPanel = new JPanel();
	    myPanel.add(new JLabel("number of scraps:"));
	    myPanel.add(numScraps);
	    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	    myPanel.add(new JLabel("size:"));
	    myPanel.add(sizeScraps);

	    int result = JOptionPane.showConfirmDialog(null, myPanel, 
	             "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.CANCEL_OPTION){
	    	System.exit(0);
	    }
		return new int[]{
				Integer.parseInt(numScraps.getText()),
				Integer.parseInt(sizeScraps.getText())
		};
	}
}
