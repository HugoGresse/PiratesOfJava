package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestPanel  extends ZContainer implements PanelInterface {
	
	private Dimension dimension = new Dimension();
	private JLabel 	life;
	
	public TestPanel(Dimension dim){
		super(dim);
		initPanel();
	}
	
	public JPanel getPanel(){
		return this.panel;
	}
	
	protected void initPanel() {
		this.life = new JLabel("fffffffffffffffffff");
		this.life.setPreferredSize(new Dimension(400, 60));
		this.life.setForeground(Color.blue);
		
		this.panel.add(life);
		System.out.println("TestPanel init done !");
	}
	
	public void update(String nlife) {
		this.life.setText(nlife);
	}
}