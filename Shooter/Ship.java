import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ship {
    int x;
    int y;
	int height;
	int width;
    Color blue;
	
	int lives = 3;
	
	public BufferedImage ship;
	
    public Ship(int x, int y)
    {
         
        this.x = x;
        this.y = y;
		this.height = 50;
		this.width = 100;
         
        this.blue = new Color(0,0,255);
		try
        {
			ship = ImageIO.read(new File("spaceship.jpg"));
        } catch (IOException e) {}
    }
	
    public void drawMe(Graphics g) {
        g.drawImage(ship, x, y, width, height, null);
    }
     
    public void moveUp() {
		if(y > 0) {
			y = y - 3;
		}
    }
     
    public void moveDown() {
		if(y < 600 - height) {
			y = y + 3;
		}
    }
    public int getX() {
        return x;
    }
    public int getY()
    {
        return y;
    }
	public int getLives() {
		return lives;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public void setLives(int num) {
		lives = num;
	}
	public void die() {
		y = 300;
		lives--;
	}
}