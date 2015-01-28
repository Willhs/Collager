import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GUI {

	private CollagePanel collagePanel; 
	
	public GUI(BufferedImage goalImage, final Collager collager){
		
		int width = 1000, height = 500;
		
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		
		collagePanel = new CollagePanel(width, height, goalImage);
		
		JPanel UIPanel = new JPanel();
		UIPanel.setLayout(new BorderLayout());
		
		final JSlider stickiness = new JSlider(1, 7, 4);
		stickiness.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				collager.setStickiness(stickiness.getValue());
			}
		});
		UIPanel.add(stickiness, BorderLayout.WEST);
		
		final JSlider jumpiness = new JSlider(1, 100, 50);
		jumpiness.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				collager.setJumpiness(jumpiness.getValue());
			}
		});
		UIPanel.add(jumpiness, BorderLayout.EAST);
		
		frame.add(UIPanel, BorderLayout.SOUTH);
		
		frame.add(collagePanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void repaint(){
		collagePanel.repaint();
	}
	
	public void setImage(BufferedImage image){
		collagePanel.setImage(image);
	}
	
}
