package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controllers.MenuListener;
import models.ColorModel;
import models.Coord;
import models.Forme;
import models.Model;
/**
 * 
 * @author François Lamothe Guillaume Leccoq Alexandre Ravaux
 * Classe principale de l'application
 *
 */
public class MainFrame extends JFrame{
	
	public final static boolean DEBUG=true;
	
	private DrawArea drawArea;
	private Tools tools;
	private Model model;
	private ColorChooser colorChooser;
	private ShapeManager layers;
	private JPanel drawAreaCont;
	private JScrollPane scrollPane;
	
	public MainFrame(){
		model=new Model();
		
		//modif ?
		model.setAreaSz(new Coord(500,500));
		
		this.setTitle("AFG");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-600/2, dim.height/2-600/2);
		this.setPreferredSize(new Dimension(600,600));
		this.setMinimumSize(new Dimension(400,400));
		
		//looknfeel
		try {
			
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		
		tools=new Tools(model);
		colorChooser = new ColorChooser(model);
		layers=new ShapeManager(model);
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		initMenu();
		scrollPane = new JScrollPane();
		drawAreaCont = new JPanel();
		drawAreaCont.setBackground(Color.GRAY);
		drawAreaCont.setLayout(new GridBagLayout());
		drawArea = new DrawArea(model,this,model.getAreaSz());
		drawAreaCont.add(drawArea);
		
		scrollPane.add(drawAreaCont);
		scrollPane.setViewportView(drawAreaCont);
		p.add(scrollPane);
		
		container.add(p);
		InfoPanel infop=new InfoPanel(model,drawArea);
		container.add(infop);
		drawArea.setInfoPanel(infop);
		this.setContentPane(container);
		
	}
	
	public void initMenu(){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		menuBar.add(file);
		
		JMenuItem newCanvas = new JMenuItem ("New");
		file.add(newCanvas);
		
		JMenuItem open = new JMenuItem ("Open");
		file.add(open);
		
		JMenuItem save = new JMenuItem ("Save");
		file.add(save);
		
		JMenu export = new JMenu ("Export");
		file.add(export);
		
		JMenuItem exportSVG = new JMenuItem ("Export to SVG");
		export.add(exportSVG);
		
		JMenuItem exportJPG = new JMenuItem ("Export to JPG");
		export.add(exportJPG);
		
		JMenuItem quit = new JMenuItem ("Quit");
		file.add(quit);
		
		JMenu Edit = new JMenu("Edit");
		menuBar.add(Edit);
		
		JMenuItem Undo = new JMenuItem ("Undo");
		Edit.add(Undo);
		
		JMenuItem Redo = new JMenuItem ("Redo");
		Edit.add(Redo);
		
		JMenuItem Clear = new JMenuItem ("Clear all");
		Edit.add(Clear);
		
		/* les evenements, raccourcis clavier et tooltips */
		
		ActionListener listener = new MenuListener(this, model);
		
		/* File */
		
		//new canvas
		newCanvas.addActionListener(listener);
		newCanvas.setMnemonic('N');
		newCanvas.setAccelerator(KeyStroke.getKeyStroke (KeyEvent.VK_N,InputEvent.CTRL_MASK));
		//open
		open.addActionListener(listener);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke (KeyEvent.VK_O,InputEvent.CTRL_MASK));
		
		//save
		save.addActionListener(listener);
		save.setMnemonic('S');
		save.setAccelerator(KeyStroke.getKeyStroke (KeyEvent.VK_S,InputEvent.CTRL_MASK));
		
		//export
		exportSVG.addActionListener(listener);
		exportSVG.setMnemonic('E');
		exportSVG.setAccelerator(KeyStroke.getKeyStroke (KeyEvent.VK_E,InputEvent.CTRL_MASK));
		
		exportJPG.addActionListener(listener);
		exportJPG.setMnemonic('E');
		exportJPG.setAccelerator(KeyStroke.getKeyStroke (KeyEvent.VK_E,InputEvent.SHIFT_MASK));
		
		//quitter le logiciel
		quit.addActionListener(listener);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke (KeyEvent.VK_Q,InputEvent.CTRL_MASK));
		
		/* Edit */
		//Clear.addActionListener(listener);
		
		Undo.addActionListener(listener);
		Undo.setMnemonic('Z');
		Undo.setAccelerator(KeyStroke.getKeyStroke (KeyEvent.VK_Z,InputEvent.CTRL_MASK));
		
		Redo.addActionListener(listener);
		Redo.setMnemonic('Y');
		Redo.setAccelerator(KeyStroke.getKeyStroke (KeyEvent.VK_Y,InputEvent.CTRL_MASK));
		
		Clear.addActionListener(listener);
		Clear.setMnemonic('L');
		Clear.setAccelerator(KeyStroke.getKeyStroke (KeyEvent.VK_L,InputEvent.CTRL_MASK));
		
		/* les tooltips */
		newCanvas.setToolTipText("Nouveau dessin");
		open.setToolTipText("Ouvrir un dessin");
		save.setToolTipText("Enregistrer en AFG");
		export.setToolTipText("Exporter en SVG ou JPG");
		quit.setToolTipText("Quitter le logiciel");
		Undo.setToolTipText("Annuler la dernière action");
		Redo.setToolTipText("Refaire la dernière action");
		
		setJMenuBar(menuBar);
		
	}
	
	public static void main(String[] args){
		SplashScreen sp = new SplashScreen(2);
		MainFrame m = new MainFrame();
		m.pack();
		m.setVisible(true);
		sp.setVisible(false);
		sp=null;
	}
}
