package fr.imac.javawars.player;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class BottomBar: create bottom bar of the interface
 *  
 */
public class BottomBar extends JPanel{
	//swing needed
	private static final long serialVersionUID = 1L;
	
	private JButton pause = new JButton("||");
	private JButton speed = new JButton(">>");
	
	/** constructor */
	public BottomBar(){
		JPanel wrapperButtons = new JPanel();
		JPanel wrapperCommands = new JPanel();
		
		//organizing content
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST,wrapperCommands);
		this.add(BorderLayout.EAST,wrapperButtons);
		
		//put pause and speed buttons
		pause.setPreferredSize(new Dimension(82,65));
		speed.setPreferredSize(new Dimension(83,65));
		wrapperButtons.add(pause);
		wrapperButtons.add(speed);
		
		// buttons listener
		pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	// TODO : tell engine to stop the game
            }
        });
		
		speed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	// TODO : tell engine to speed up the game
            }
        });
	}

}
