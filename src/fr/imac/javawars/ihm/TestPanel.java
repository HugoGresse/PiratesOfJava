package fr.imac.javawars.ihm;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestPanel  extends ZContainer {
	
	private Dimension dimension = new Dimension();
	private JLabel 	nombreMot;
	
	public TestPanel(Dimension dim){
		super(dim);
		initPanel();
	}
	
	protected JPanel getPanel(){
		return this.panel;
	}

	protected void initPanel() {
		JPanel head = new JPanel();
	}
	
}