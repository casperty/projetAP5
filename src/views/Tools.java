package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import models.Coord;
import models.Model;
/**
 * 
 * @author François Lamothe Guillaume Lecocq Alexandre Ravaux
 *
 */
public class Tools extends JDialog {

	private final int myWidth=200;
	private Model model;
	/**
	 * Créé la fenêtre "Tools"
	 * @param model
	 */
	public Tools(Model model){
		this.model=model;
		this.setTitle("Tools");
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dim.width/2-600/2)-myWidth-50, dim.height/2-600/2);
		this.setPreferredSize(new Dimension(myWidth,600));
		this.setResizable(false);
		
		JPanel cont = new JPanel();
		cont.setLayout(null);
		ArrayList<ToolButton> buttons = new ArrayList<ToolButton>();
		initButtons(buttons);
		
		for(ToolButton b : buttons){
			cont.add(b);
		}
		
		
		this.setContentPane(cont);
		
		this.pack();
		this.setVisible(true);
	}
	/**
	 * ajout des boutons à la fenêtre
	 * @param buttons
	 */
	public void initButtons(ArrayList<ToolButton> buttons){
		buttons.add(new ToolButton(model,0,new Coord(20,20),new Coord(64,64),new ImageIcon("res/CircleImg.png").getImage()));
		buttons.add(new ToolButton(model,1,new Coord(104,20),new Coord(64,64),new ImageIcon("res/RecImg.png").getImage()));
		buttons.add(new ToolButton(model,2,new Coord(20,104),new Coord(64,64),new ImageIcon("res/SelectImg.png").getImage()));
		buttons.add(new ToolButton(model,3,new Coord(104,104),new Coord(64,64),null));
	}
	
}
