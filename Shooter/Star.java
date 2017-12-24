import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
 
public class Star {
    int x;
    int y;
	
    Color yellow = new Color(255, 255, 0);
    public Star() {
		
        this.x = (int)(Math.random() * 800);
        this.y = (int)(Math.random() * 600);
    }
	
    public void draw(Graphics g) {
		Color yellow = new Color(255, 255, 0);
		g.setColor(yellow);
		g.fillOval(x, y, 3, 3);
    }
	
	public void move() {
		x--;
		if(x < -10) {
			x = 801;
			y = (int)(Math.random() * 600);
		}
	}
	
}