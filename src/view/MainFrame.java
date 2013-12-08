package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	
	public MainFrame(){
		this.setTitle("AFG");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(400,400));
		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		
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
		
		
		
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1;
		c.gridheight=1;
		c.gridwidth=1;
		c.weighty=1;
		p.add(new Outils(),c);
		c.weightx=4;
		c.weighty=4;
		c.gridwidth=GridBagConstraints.REMAINDER;
		p.add(new Dessin(),c);
		
		this.setContentPane(p);
		
	}
	
	public static void main(String[] args) {
		MainFrame m = new MainFrame();
		m.pack();
		m.setVisible(true);
	}

}
