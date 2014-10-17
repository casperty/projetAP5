package views;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import models.Coord;
import models.Model;
/**
 * 
 * @author Francois Lamothe Guillaume Leccoq Alexandre Ravaux
 *	Fenetre d'outil.
 */
public class Tools extends JDialog {

	private final int myWidth=200;
	private Model model;
	/**
	 * Crée une nouvelle fenetre d'outil.
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
	 * Initialise les boutons présents dans la fenêtre
	 * @param buttons
	 */
	public void initButtons(ArrayList<ToolButton> buttons){
		buttons.add(new ToolButton(model,Model.OVAL,new Coord(20,20),new Coord(64,64),new ImageIcon("res/Circle.png").getImage(),"Oval"));
        buttons.add(new ToolButton(model,Model.RECTANGLE,new Coord(104,20),new Coord(64,64),new ImageIcon("res/Rectangle.png").getImage(),"Rectangle"));
        buttons.add(new ToolButton(model,Model.SELECT,new Coord(20,104),new Coord(64,64),new ImageIcon("res/SelectHand.png").getImage(),"Select"));
        buttons.add(new ToolButton(model,Model.LINE,new Coord(104,104),new Coord(64,64),new ImageIcon("res/Line.png").getImage(),"Line"));
        buttons.add(new ToolButton(model,Model.POLYGON,new Coord(20,188),new Coord(64,64),new ImageIcon("res/Polygon.png").getImage(),"Polygone"));
		buttons.add(new ToolButton(model,Model.FILL,new Coord(104,188),new Coord(64,64),new ImageIcon("res/ColorBucket.png").getImage(),"Fill"));
		buttons.add(new ToolButton(model,Model.RESIZE,new Coord(20,272),new Coord(64,64),new ImageIcon("res/Resize.png").getImage(),"Resize"));
		buttons.add(new ToolButton(model,Model.IMAGE,new Coord(104,272),new Coord(64,64),new ImageIcon("res/Picture.png").getImage(),"Image"){
			@Override
			public void mousePressed(MouseEvent arg0) {
				model.setTool(getId());
				model.setImgPath(ImgChooser.getPath());
			}
		});
	}
	
}
