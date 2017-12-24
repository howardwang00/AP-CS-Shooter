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

public class Enemy
{
	public BufferedImage enemy;
    int x;
    int y;
    int width;
    int height;
	
	boolean visible;
	boolean up = true;
    public Enemy()
    {
        this.x = 800;
        this.y = (int)(Math.random() * 500 + 10);
        this.width = 75;
        this.height = 50;
		this.visible = false;
		try
        {
			enemy = ImageIO.read(new File("enemy.jpg"));
        } catch (IOException e) {}
    }
	
    public void drawMe(Graphics g)
    {
		if(visible) {
			g.drawImage(enemy, x, y, width, height, null);
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
		x = (int)(Math.random() * 200 + 800);
	}
	public void move(int level) {
		if(visible == true) {
			x = x - level;
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
	public void checkCollision(Projectile p) {
		int pX = p.getX();
		int pY = p.getY();
		int pWidth = p.getWidth();
		int pHeight = p.getHeight();
		
		if(visible == true && p.getVisible() == true && pX + pWidth > x && pX <= x + width && pY + pHeight >= y && pY <= y + height) {
			//System.out.println("Enemy Hit");
			visible = false;
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
			visible = false;
			s.die();
			explosion();
		}
		else if(visible == true && x < -50) {
			visible = false;
			s.die();
			explosion();
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