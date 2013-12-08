import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Test extends JFrame  {
	
	public Test(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("test");
		this.setPreferredSize(new Dimension(400,400));
		
		JPanel p = new Colorwheel();
		
		this.setContentPane(p);
	}
	
	public class Colorwheel extends JPanel{
		
		private Image img;
		
		public Colorwheel(){
			
		}
		
		public void paintComponent(Graphics g){
			if(img==null){
				getImg();
			}
			g.drawImage(img,0, 0, this.getWidth(), this.getHeight(),null);
		}
		
		public void getImg(){
			int w = this.getWidth();
			int h = this.getHeight();
			img = createImage(w, h);
			Graphics g = img.getGraphics();
			for(int y=-(h/2);y<(h/2);y++){
				for(int x=-(w/2);x<(w/2);x++){
					
					float angle = (float) (Math.atan2(y, x) * 180/Math.PI);
					if(y<0){
						angle+=360;
					}
					float distance = (float) Math.sqrt((x*x)+(y*y));
					
					distance=(distance>(w/2))?0:distance/360;
					Color c = Color.getHSBColor(angle/360, distance, 1);
					g.setColor(c);
					g.fillRect(x+(w/2), y+(h/2), 2, 2);
				}
			}
		}
	}
	

	public static void main(String[] args) {
		Test t = new Test();
		t.pack();
		t.setVisible(true);
	}

}
