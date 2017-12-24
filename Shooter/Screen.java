import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//sound
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
 
public class Screen extends JPanel implements KeyListener {
 
    BufferedImage buffered;
    Projectile[] bullets;
    Ship s1;
	Star[] stars;
	Enemy[] enemiesLV1;
	Enemy[] enemiesLV2;
	Boss boss;
	
	int level = 1;
	boolean win = false;
	boolean lose = false;
	boolean restart = false;
	boolean[] destroyedlv1 = new boolean[3];
	boolean[] destroyedlv2 = new boolean[5];
	boolean bossDefeated = false;
	
	public BufferedImage spaceBackground;
	public BufferedImage galaxyBackground;
	public BufferedImage lv3;
	public BufferedImage end;
	
	boolean shipMoveUp = false;
	boolean shipMoveDown = false;
	boolean fire = false;
	boolean lv1Start = true;
	boolean lv2Start = false;
	boolean lv3Start = false;
	
	int firing = 0;	//timer between each bullet
	
    public Screen() {
         
        s1 = new Ship(50,300);
		bullets = new Projectile[100];
		for(int i = 0; i < bullets.length; i++) {
			bullets[i] = new Projectile(0, 0);
		}
		stars = new Star[150];
		for(int i = 0; i < stars.length; i++) {
			stars[i] = new Star();
		}
		enemiesLV1 = new Enemy[3];
		for(int i = 0; i < enemiesLV1.length; i++) {
			enemiesLV1[i] = new Enemy();
		}
		enemiesLV2 = new Enemy[5];
		for(int i = 0; i < enemiesLV2.length; i++) {
			enemiesLV2[i] = new Enemy();
		}
		boss = new Boss();
		
        //sets keylistener
        setFocusable(true);
		addKeyListener(this);
		//import images
		try
        {
			spaceBackground = ImageIO.read(new File("spaceBackground.jpg"));
			galaxyBackground = ImageIO.read(new File("galaxy.jpg"));
			lv3 = ImageIO.read(new File("lv3.jpg"));
			end = ImageIO.read(new File("endScreen.jpg"));
        } catch (IOException e) {}
		
    }
 
    public Dimension getPreferredSize() {
        //Sets the size of the panel
            return new Dimension(800,600);
    }
     
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
        //Create Buffered
        if( buffered == null ) {
            buffered = (BufferedImage)(createImage( getWidth(), getHeight() ) );
        }
         
        //Create a temporary graphics buffered to draw
        Graphics gBuff = buffered.createGraphics();
     
        //Clear everything
        //This draws the white box
        gBuff.setColor(Color.white);
        gBuff.fillRect(0, 0, 800, 600);
		
		//draw background
		if(level == 1 && win == false && s1.getLives() > 0) {
			gBuff.drawImage(spaceBackground, 0, 0, 800, 600, null);
		}
		else if(level == 2 && win == false && s1.getLives() > 0) {
			gBuff.drawImage(galaxyBackground, 0, 0, 800, 600, null);
		}
		else if(level == 3 && win == false && s1.getLives() > 0) {
			gBuff.drawImage(lv3, 0, 0, 800, 600, null);
		}
		
		//draw stars
		for(int i = 0; i < stars.length; i++) {
			stars[i].draw(gBuff);
		}
		
        //Draw ship
        s1.drawMe(gBuff);
         
        //Draw Projectile
		for(Projectile each : bullets) {
			each.drawMe(gBuff);
		}
        
		//draw enemies
		/*
		if(level == 1) {
			for(Enemy each : enemiesLV1) {
				each.drawMe(gBuff);
			}
		}
		else if(level == 2) {
			for(Enemy each : enemiesLV2) {
				each.drawMe(gBuff);
			}
		}
		else if(level == 3) {
			boss.drawMe(gBuff);
		}
		*/
		for(Enemy each : enemiesLV1) {
			each.drawMe(gBuff);
		}
		for(Enemy each : enemiesLV2) {
			each.drawMe(gBuff);
		}
		boss.drawMe(gBuff);
		
		if(win == true && s1.getLives() > 0) {
			gBuff.drawImage(end, 0, 0, 800, 600, null);
			Font arial2 = new Font("Arial", Font.PLAIN, 50);
			gBuff.setFont(arial2);
			gBuff.setColor(Color.white);
			gBuff.drawString("You Win!", 200, 250);
			gBuff.drawString("Press 'a' to restart", 200, 350);
		}
		else if(s1.getLives() < 1) {
			gBuff.drawImage(end, 0, 0, 800, 600, null);
			s1.setLives(0);
			lose = true;
			Font arial2 = new Font("Arial", Font.PLAIN, 50);
			gBuff.setFont(arial2);
			gBuff.setColor(Color.white);
			gBuff.drawString("You Lose :( ", 200, 250);
			gBuff.drawString("Press 'a' to restart", 200, 350);
			for(Enemy each : enemiesLV1) {
				each.setVisible(false);
			}
			for(Enemy each : enemiesLV1) {
				each.setVisible(false);
			}
			boss.setVisible(false);
		}
		else {
			lose = false;
		}
		
		Font arial = new Font("Arial", Font.PLAIN, 50);
		gBuff.setFont(arial);
		gBuff.setColor(Color.white);
		gBuff.drawString("Lives: " + s1.getLives(), 50, 50);
		gBuff.drawString("Level: " + level, 50, 100);
		if(level == 3 && s1.getLives() > 0) {
			gBuff.drawString("Boss Health: " + boss.getHealth(), 450, 50);
		}
		
        //draw the buffered image
        g.drawImage(buffered, 0, 0, null);
		
    } 
 
 
    public void animate() {
         
        while(true)
        {
            //wait for .01 second
            try {
                Thread.sleep(10);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
			
			if(restart == true) {
				s1.die(); 	//moves it back to the middle
				win = false;
				restart = false;
				lv1Start = true;
				level = 1;
				for(Enemy each : enemiesLV1) {
					each.setVisible(false);
				}
				boss.setVisible(false);
				for(int i = 0; i < destroyedlv1.length; i++) {
					destroyedlv1[i] = false;
				}
				for(int i = 0; i < destroyedlv2.length; i++) {
					destroyedlv2[i] = false;
				}
				bossDefeated = false;
				for(Projectile each : bullets) {
					each.setVisible(false);
				}
				s1.setLives(3);
			}
			
			if(shipMoveUp == true) {
				s1.moveUp();
			}
			else if(shipMoveDown == true) {
				s1.moveDown();
			}
			if(fire == true && firing == 0) {
				for(int i = 0; i < bullets.length; i++) {
					if(bullets[i].getVisible() == false) {
						bullets[i].setY(s1.getY() + s1.getHeight()/2);
						bullets[i].setX(s1.getX() + s1.getWidth());
						bullets[i].setVisible(true);
						firing = 30;
						fireSound();
						break;
					}
				}
			}
			if(firing > 0) {
				firing--;
			}
			for(Projectile each : bullets) {
				each.moveRight();
			}
			for(Star each : stars) {
				each.move();
			}
			if(lv1Start == true && lose == false) {
				boss.setVisible(false);
				lv1Start = false;
				for(int i = 0; i < enemiesLV2.length; i++) {
					enemiesLV2[i].setVisible(false);
				}
				for(int i = 0; i < destroyedlv1.length; i++) {
					destroyedlv1[i] = false;
				}
				for(int i = 0; i < destroyedlv2.length; i++) {
					destroyedlv2[i] = false;
				}
				for(Enemy each : enemiesLV1) {
					each.spawn();
				}
				lv2Start = false;
				level = 1;
			}
			else if(lv2Start == true && lose == false) {
				lv2Start = false;
				
				for(Enemy each : enemiesLV2) {
					each.spawn();
				}
			}
			else if(lv3Start == true && s1.getLives() > 0) {
				lv3Start = false;
				boss.spawn();
			}
			for(Enemy each : enemiesLV1) {
				for(int i = 0; i < bullets.length; i++) {
					each.checkCollision(bullets[i]);
				}
				each.checkCollision(s1);
				each.move(level);
			}
			for(Enemy each : enemiesLV2) {
				for(int i = 0; i < bullets.length; i++) {
					each.checkCollision(bullets[i]);
				}
				each.checkCollision(s1);
				each.move(level);
			}
			boss.checkCollision(s1);
			for(int i = 0; i < bullets.length; i++) {
				boss.checkCollision(bullets[i]);
			}
			boss.move();
			for(int i = 0; i < enemiesLV1.length; i++) {
				if(enemiesLV1[i].getVisible() == false) {
					destroyedlv1[i] = true;
				}
			}
			for(int i = 0; i < enemiesLV2.length; i++) {
				if(enemiesLV2[i].getVisible() == false && level == 2) {
					destroyedlv2[i] = true;
				}
			}
			if(boss.getVisible() == false && level == 3) {
				bossDefeated = true;
			}
			if(destroyedlv1[0] == true && destroyedlv1[1] == true && destroyedlv1[2] == true) {
				if(level == 1) {
					level = 2;
					lv2Start = true;
				}
			}
			if(level == 2 && destroyedlv2[0] == true && destroyedlv2[1] == true && destroyedlv2[2] == true && destroyedlv2[3] == true && destroyedlv2[4] == true) {
				level = 3;
				lv3Start = true;
			}
			if(bossDefeated == true) {
				win = true;
			}
			
            //repaint the graphics drawn
            repaint();
        }
		
    }
	
    //implement methods of the KeyListener
    public void keyPressed(KeyEvent e) {
 
 
        //key code
        //http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
		
        if (e.getKeyCode()==38) {	//Up Arrow
            shipMoveUp = true;       
        }
        else if (e.getKeyCode() == 40)//Down Arrow
        {
			shipMoveDown = true;
        }
		else if(e.getKeyCode() == 32) {	//space bar
			fire = true;
		}
		else if(e.getKeyCode() == 75) {	//k key = cheat key
			if(level == 1) {
				for(int i = 0; i < enemiesLV1.length; i++) {
					enemiesLV1[i].setVisible(false);
				}
			}
			else if(level == 2) {
				for(int i = 0; i < enemiesLV2.length; i++) {
					enemiesLV2[i].setVisible(false);
				}
			}
		}
		else if(e.getKeyCode() == 65) {	//a button
			if(lose == true || win == true) {
				restart = true;
			}
		}
		//System.out.println(e.getKeyCode());
        repaint();
 
    }
 
    public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==38) {	//Up Arrow
            shipMoveUp = false;       
        }
        else if (e.getKeyCode()==40)//Down Arrow
        {
			shipMoveDown = false;
        }
		else if(e.getKeyCode() == 32) {	//space bar
			fire = false;
		}
	}
    public void keyTyped(KeyEvent e) {}
	
	public void fireSound() {
		if(win == false && lose == false) {
			try {
			URL url = this.getClass().getClassLoader().getResource("laser.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();
			}
			catch (Exception exc) {
				exc.printStackTrace(System.out);
			}
		}
		
		
    }
}