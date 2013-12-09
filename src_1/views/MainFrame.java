package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import models.Circle;
import models.ColorModel;
import models.Coord;
import models.Forme;
import models.Model;

public class MainFrame extends JFrame implements Observer{
	
	private DrawArea drawArea;
	private Tools tools;
	private Model model;
	private ColorChooser colorChooser;
	
	public MainFrame(){
		model=new Model();
		model.addObserver(this);
		this.setTitle("AFG");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-600/2, dim.height/2-600/2);
		this.setPreferredSize(new Dimension(600,600));
		this.setMinimumSize(new Dimension(400,400));
		
		tools=new Tools();
		colorChooser = new ColorChooser(model);
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		
		
		initMenu();
		
		
		JScrollPane scrollPane = new JScrollPane();
		JPanel drawAreaCont = new JPanel();
		drawAreaCont.setBackground(Color.GRAY);
		drawAreaCont.setLayout(new GridBagLayout());
		drawArea = new DrawArea();
		drawAreaCont.add(drawArea);
		
		scrollPane.add(drawAreaCont);
		scrollPane.setViewportView(drawAreaCont);
		p.add(scrollPane);
		
		
		
		this.setContentPane(p);
		
		
		Circle c = new Circle(25);
		c.setPos(new Coord(50,50));
		c.setColor(new ColorModel(255, 0, 0, 255));
		c.setFill(true);
		model.addForme(c);
	}
	
	public void initMenu(){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		menuBar.add(file);
		
		JMenuItem open = new JMenuItem ("Open");
		file.add(open);
		
		JMenuItem save = new JMenuItem ("Save");
		file.add(save);
		
		JMenuItem Quit = new JMenuItem ("Quit");
		file.add(Quit);
		
		JMenu Edit = new JMenu("Edit");
		menuBar.add(Edit);
		
		JMenuItem Undo = new JMenuItem ("Undo");
		Edit.add(Undo);
		
		JMenuItem Redo = new JMenuItem ("Redo");
		Edit.add(Redo);
		
		setJMenuBar(menuBar);
	}
	
	
	public static void main(String[] args){
		MainFrame m = new MainFrame();
		m.pack();
		m.setVisible(true);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("up");
		for(Forme f : model.getFormes()){
			drawArea.addForme(f);
		}
	}

}
