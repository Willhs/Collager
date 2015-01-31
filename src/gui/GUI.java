package gui;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import collager.Collager;
import evolver.EvolveListener;


public class GUI implements EvolveListener{

	private CollagePanel collagePanel;
	private Collager collager;
	
	public GUI(BufferedImage goalImage, final Collager collager){
				
		int width = 1000, height = 500;
		this.collager = collager;
		
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		
		collagePanel = new CollagePanel(width, height, goalImage);

		// ============ TODO: Use Java 8 to try lambdas ===============
		
		JPanel UIPanel = new JPanel();
		UIPanel.setLayout(new BorderLayout());
		
		final JSlider stickiness = new JSlider(1, 7, 4);
		stickiness.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				collager.setStickiness(stickiness.getValue());
			}
		});
		UIPanel.add(makeSliderPanel("stickiness", stickiness), BorderLayout.WEST);
		
		final JSlider jumpiness = new JSlider(1, 100, 50);
		jumpiness.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				collager.setJumpiness(jumpiness.getValue());
			}
		});
		UIPanel.add(makeSliderPanel("jumpiness", jumpiness), BorderLayout.EAST);
		
		final JSlider speed = new JSlider(1, 100, 50);
		speed.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e){				
				collager.setSpeed(speed.getValue());
			}
		});
		
		UIPanel.add(makeSliderPanel("speed", speed), BorderLayout.CENTER);
		
		frame.add(UIPanel, BorderLayout.SOUTH);
		
		frame.add(collagePanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private JPanel makeSliderPanel(String name, JSlider slider){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel(name, SwingConstants.CENTER), BorderLayout.NORTH);
		panel.add(slider, BorderLayout.SOUTH);
		return panel;
	}
	
	public void repaint(){
		collagePanel.repaint();
	}
	
	public void setImage(BufferedImage image){
		collagePanel.setImage(image);
	}

	@Override
	public void notifyEvolved() {
		collagePanel.setImage(collager.getCurrentEvolution());
		repaint();
	}
	
}
