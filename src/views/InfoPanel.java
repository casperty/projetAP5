package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Coord;
import models.Model;
/**
 * 
 * @author Franï¿½ois Lamothe Guillaume Leccoq Alexandre Ravaux
 * Informations affichées en bas de la fenêtre principale
 *
 */
public class InfoPanel extends JPanel implements Observer {
	
	private Model model;
	private JLabel info,size,coord;
	private Zoom zoom;
	private DrawArea drawArea;

	public InfoPanel(Model model,DrawArea drawArea){
		this.drawArea=drawArea;
		this.setMinimumSize(new Dimension(1,25));
		this.setMaximumSize(new Dimension(20000,25));
		this.setPreferredSize(new Dimension(2, 25));//dans un boxlayout avec en parametre y_axis, la largeur est ignoré ?
		
		setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx=6;
		info=new JLabel("Info");
		add(info,c);
		c.weightx=1;
		size=new JLabel(model.getAreaSz().toString());
		size.setHorizontalAlignment(JLabel.CENTER);
		add(size,c);
		c.weightx=1;
		coord=new JLabel("Coord");
		coord.setHorizontalAlignment(JLabel.CENTER);
		Dimension dim = new Dimension(50,15);
		coord.setMinimumSize(dim);
		coord.setMaximumSize(dim);
		coord.setPreferredSize(dim);
		add(coord,c);
		c.weightx=1;
		zoom=new Zoom(drawArea);
		add(zoom,c);
		
		for(Component comp : this.getComponents()){
			if(comp == getComponent(getComponentCount()-1)) continue;
			((JComponent)comp).setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		}
		
	}
	
	public void setMse(Coord c){
		coord.setText(c.toString());
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		size.setText(model.getAreaSz().toString());
	}

	public void setZoom(float zoom) {
		System.out.println(zoom);
		this.zoom.setZoom((int)(zoom*100));
	}
}
