package views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.management.ObjectName;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import models.Forme;
import models.Model;
/**
 * Fenetre de gestion de la profondeur des objets.
 * @author Francois
 *
 */
public class ShapeManager extends JDialog implements Observer, MouseListener{

	private final int myWidth=200;
	private Model model;
	private String[] objNames={""};
	private JList objList;
	private JPanel infoBar;
	private JPanel up,down;
	
	/**
	 * Crée un nouveau Shape Manager.
	 * @param model
	 */
	public ShapeManager(Model model	){
		this.model=model;
		model.addObserver(this);
		this.setTitle("Shape Manager");
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dim.width/2+600/2)+50, (dim.height/2));
		this.setPreferredSize(new Dimension(myWidth,300));
		this.setResizable(false);
		
		JPanel cont = new JPanel();
		cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));
		
		objList = new JList (objNames);
		objList.addMouseListener(this);
		JScrollPane scrollpane= new JScrollPane(objList);
		
		cont.add(scrollpane);
		
		infoBar=new JPanel();
		infoBar.setLayout(new BoxLayout(infoBar, BoxLayout.X_AXIS));
		infoBar.setMinimumSize(new Dimension(1,25));
		infoBar.setMaximumSize(new Dimension(20000,25));
		infoBar.setPreferredSize(new Dimension(2, 25));
		
		up=new mButton(new ImageIcon("res/btnup.png").getImage());
		up.setMaximumSize(new Dimension(20,20));
		up.setPreferredSize(new Dimension(20, 20));
		up.addMouseListener(this);
		down=new mButton(new ImageIcon("res/btndwn.png").getImage());
		down.setMaximumSize(new Dimension(20,20));
		down.setPreferredSize(new Dimension(20, 20));
		down.addMouseListener(this);
		
		infoBar.add(up);
		infoBar.add(down);
		
		cont.add(infoBar);
		
		this.setContentPane(cont);
		
		
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		ArrayList<String> l = new ArrayList<String>();
		for(Forme f : model.getFormes()){
			l.add(f.getDeep()+": "+f.toString().substring(0, f.toString().indexOf("/")));
		}
		objNames=l.toArray(new String[l.size()]);
		objList.setListData(objNames);
		
		int i=0;
		Forme sel=null;
		for(Forme f : model.getFormes()){
			if(f.isSelect()){
				i++;
				sel=f;
			}
		}
		if(sel!=null){
			int id;
			for(id=0;id<model.getFormes().size();id++){
				if(model.getFormes().get(id)==sel) break;
			}
			if(i==1) objList.setSelectedIndex(id);
		}
		
	}
	
	public class mButton extends JPanel{
		
		private Image img;
		
		public mButton(Image img){
			this.img=img;
			repaint();
		}
		
		public void paintComponent(Graphics g){
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(objNames.length==0 || objNames[0].equals("")) return;
		if(e.getSource()==objList){
			model.selectByDeep(getSelectedDeep());
		}else if(e.getSource()==up){
			model.up(getSelectedDeep());
		}else{
			model.down(getSelectedDeep());
		}
	}
	
	/**
	 * Permet de recuperer la profondeur de l'objet selectionner dans la liste.
	 * @return	profondeur d'un objet
	 */
	public int getSelectedDeep(){
		if(objList.getSelectedValue()==null) return -1;
		String s = ((String) objList.getSelectedValue()).substring(0, ((String) objList.getSelectedValue()).indexOf(":"));
		return Integer.parseInt(s);
	}
	
}
