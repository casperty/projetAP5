package views;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import models.Model;

public class ViewSliders extends JPanel implements Observer, MouseListener{
	
	private Model m;
	
	private JLabel lr,lv,lb;
	private JSlider rs,vs,bs;
	
	public ViewSliders(Model m){
		this.m=m;
		m.addObserver(this);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		lr=new JLabel("R = ");
		lv=new JLabel("G = ");
		lb=new JLabel("B = ");
		
		ArrayList<JSlider> ss = new ArrayList<JSlider>();
		
		rs = new JSlider();
		ss.add(rs);
		vs = new JSlider();
		ss.add(vs);
		bs = new JSlider();
		ss.add(bs);
		
		for(JSlider s : ss){
			s.setMinimum(0);
			s.setMaximum(255);
			s.setMajorTickSpacing(0);
			s.setMinorTickSpacing(0);
			//s.setPaintLabels(true);
			s.setPaintTicks(true);
			s.addMouseListener(this);
		}
		
		add(lr);
		add(rs);
		
		JPanel rp = new JPanel();
		rp.add(lr);
		rp.add(rs);
		this.add(rp);
		
		JPanel vp = new JPanel();
		vp.add(lv);
		vp.add(vs);
		this.add(vp);
		
		JPanel bp = new JPanel();
		bp.add(lb);
		bp.add(bs);
		this.add(bp);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.rs.setValue(m.getR());
		this.vs.setValue(m.getV());
		this.bs.setValue(m.getB());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==rs){
			m.setR(((JSlider)rs).getValue());
		}else if(e.getSource()==vs){
			m.setV(((JSlider)vs).getValue());
		}else if(e.getSource()==bs){
			m.setB(((JSlider)bs).getValue());
		}
	}

//	@Override
//	public void stateChanged(ChangeEvent e) {
//		if(e.getSource()==rs){
//			m.setR(((JSlider)rs).getValue());
//		}else if(e.getSource()==vs){
//			m.setV(((JSlider)vs).getValue());
//		}else if(e.getSource()==bs){
//			m.setB(((JSlider)bs).getValue());
//		}
//	}

}
