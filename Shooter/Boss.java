import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//sound
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class Boss
{
	public BufferedImage boss;
    int x;
    int y;
    int width;
    int height;
	
	int health;
	boolean visible;
	boolean up = true;
	
    public Boss()
    {
        this.x = 800;
        this.y = (int)(Math.random() * 500 + 10);
        this.width = 150;
        this.height = 100;
		this.visible = false;
		
		try
        {
			boss = ImageIO.read(new File("boss.jpg"));
        } catch (IOException e) {}
    }
	
    public void drawMe(Graphics g)
    {
		if(visible) {
			g.drawImage(boss, x, y, width, height, null);
		}
    }
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean bool) {
		visible = bool;
	}
	public void spawn() {
		visible = true;
		x = 800;
		health = 20;
	}
	public void move() {
		if(visible == true) {
			x = x - 1;
			if((int)(Math.random() * 100 + 1) > 99) {
				if(up == true) {
					up = false;
				}
				else {
					up = true;
				}
			}
			if(y < 0) {
				up = false;
			}
			else if(y > 600 - height) {
				up = true;
			}
			if(up == true) {
				y--;
			}
			else {
				y++;
			}
		}
			
		//System.out.println(up);
	}
	public int getHealth() {
		return health;
	}
	public void checkCollision(Projectile p) {
		int pX = p.getX();
		int pY = p.getY();
		int pWidth = p.getWidth();
		int pHeight = p.getHeight();
		
		if(visible == true && p.getVisible() == true && pX + pWidth > x && pX <= x + width && pY + pHeight >= y && pY <= y + height) {
			//System.out.println("Boss Hit");
			health--;
			if(health < 1) {
				visible = false;
			}
			p.setVisible(false);
			explosion();
		}
		
	}
	public void checkCollision(Ship s) {
		//also checks collision against the left wall
		int sX = s.getX();
		int sY = s.getY();
		int sWidth = s.getWidth();
		int sHeight = s.getHeight();
		
		if(visible == true && sX + sWidth > x && sX <= x + width && sY + sHeight >= y && sY <= y + height) {
			s.die();
			explosion();
			x = 800;
		}
		else if(visible == true && x < 0) {
			s.die();
			explosion();
			x = 800;
		}
	}
	public void explosion() {
		
		try {
			URL url = this.getClass().getClassLoader().getResource("explosion.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();
         }
         catch (Exception exc) {
			 exc.printStackTrace(System.out);
         }
    }
 
}