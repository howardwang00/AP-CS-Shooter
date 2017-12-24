import java.awt.Color;
import java.awt.Graphics;
 
public class Projectile
{
    int x;
    int y;
    int width;
    int height;
	boolean visible = false;
	
    Color red;
	
	
    public Projectile(int x, int y)
    {
         
        this.x = x;
        this.y = y;
         
        this.width = 10;
        this.height = 10;
         
        this.red = new Color(255,0,0);
 
 
    }
	
    public void drawMe(Graphics g) {
		if(visible == true) {
			g.setColor(red);
			g.fillOval(x,y,width,height);
		}
		
    }
	
	public void moveRight() {
		if(visible == true) {
			x = x + 5;
		}
		if(x > 800) {
			x = 50;
			visible = false;
		}
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean getVisible() {
		return visible;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}