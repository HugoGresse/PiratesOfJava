package fr.imac.javawars.player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.engine.Projectile;
import fr.imac.javawars.engine.Tower;
import fr.imac.javawars.engine.TowerBombe;
import fr.imac.javawars.engine.TowerBounce;
import fr.imac.javawars.engine.TowerFreeze;
import fr.imac.javawars.engine.TowerLaser;
import fr.imac.javawars.engine.TowerMachinegun;
import fr.imac.javawars.engine.TowerMissile;
import fr.imac.javawars.engine.TowerPoison;
import fr.imac.javawars.engine.TowerSniper;

/**
 * Class TowersLayer: manage & display towers 
 */
public class TowersLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage bufferedImage = null;
	private boolean isEmptyBufferedImage;
	
	private Image freezeImg;
	private Image laserImg;
	private Image missileImg;
	private Image machineGunImg;
	private Image bombImg;
	private Image bounceImg;
	private Image sniperImg;
	private Image poisonImg;
	
	private Image freezeProjectile;
	private Image laserProjectile;
	private Image missileProjectile;
	private Image machineGunProjectile;
	private Image bombProjectile;
	private Image bounceProjectile;
	private Image sniperProjectile;
	private Image poisonProjectile;
	
	/**
	 * Constructor
	 */
	TowersLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);	
		//getting images for towers
		try {
			this.freezeImg = ImageIO.read(new File("res/img/freeze.png"));
			this.laserImg = ImageIO.read(new File("res/img/laser.png"));
			this.missileImg = ImageIO.read(new File("res/img/missile.png"));
			this.machineGunImg = ImageIO.read(new File("res/img/machineGun.png"));
			this.bombImg = ImageIO.read(new File("res/img/bomb.png"));
			this.bounceImg = ImageIO.read(new File("res/img/bounce.png"));
			this.sniperImg = ImageIO.read(new File("res/img/sniper.png"));
			this.poisonImg = ImageIO.read(new File("res/img/poison.png"));
			
			this.freezeProjectile = ImageIO.read(new File("res/img/projectiles/proj-freeze.png"));
			this.laserProjectile = ImageIO.read(new File("res/img/projectiles/proj-laser.png"));
			this.missileProjectile = ImageIO.read(new File("res/img/projectiles/proj-missile.png"));
			this.machineGunProjectile = ImageIO.read(new File("res/img/projectiles/proj-machineGun.png"));
			this.bombProjectile = ImageIO.read(new File("res/img/projectiles/proj-bomb.png"));
			this.bounceProjectile = ImageIO.read(new File("res/img/projectiles/proj-bounce.png"));
			this.sniperProjectile = ImageIO.read(new File("res/img/projectiles/proj-sniper.png"));
			this.poisonProjectile = ImageIO.read(new File("res/img/projectiles/proj-poison.png"));

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bufferedImage =  new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		isEmptyBufferedImage = true;
	}

	/**
	 * Painting layer (display towers)
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(!isEmptyBufferedImage)
			g.drawImage(bufferedImage, 0, 0, null);
		else {
			drawBufferedImage();
			g.drawImage(bufferedImage, 0, 0, null);
		}

	}
	

	/**
	 * Draw the new stuff on a buffered image insteed of the default graphics
	 */
	public void drawBufferedImage(){
		//System.out.println("Draw TowersLayersBuffer");
		bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = bufferedImage.createGraphics();
		
		//Antialiasing ON
		((Graphics2D)  g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//getting towers from engine
		CopyOnWriteArrayList<Tower> towers = JavaWars.getDispatcher().getTowers();
		Iterator<Tower> it = towers.iterator();
		Iterator<Map.Entry<Integer, Projectile>> itProjec;
		
		//Display Tower and projectiles if any
		while(it.hasNext()){
			Tower t = it.next();
			
			Image icon = null;
			Image iconProjectile = null;
			
			//changing image for each type
			if(t.getClass() == TowerFreeze.class){ icon = freezeImg; iconProjectile = freezeProjectile; }
			else if(t.getClass() == TowerLaser.class){ icon = laserImg; iconProjectile = laserProjectile; }
			else if(t.getClass() == TowerMissile.class){ icon = missileImg; iconProjectile = missileProjectile; }
			else if(t.getClass() == TowerMachinegun.class){ icon = machineGunImg; iconProjectile = machineGunProjectile; }
			else if(t.getClass() == TowerBombe.class){ icon = bombImg; iconProjectile = bombProjectile; }
			else if(t.getClass() == TowerBounce.class){ icon = bounceImg; iconProjectile = bounceProjectile; }
			else if(t.getClass() == TowerSniper.class){ icon = sniperImg; iconProjectile = sniperProjectile; }
			else if(t.getClass() == TowerPoison.class){ icon = poisonImg; iconProjectile = poisonProjectile; }
			
			g.drawImage(icon, (int)(t.getPosition().getX()-12.5),(int)(t.getPosition().getY()-12.5), 25,25, null);
			
			// draw projectiles if any
			
			itProjec = t.getProjectiles().entrySet().iterator();
			while(itProjec.hasNext()){
				Projectile p = itProjec.next().getValue();
				g.drawImage(iconProjectile, (int)(p.getPosition().getX()-7.5),(int)(p.getPosition().getY()-7.5), 15,15, null);
			}//end projectile
			
			
		} // end while tower
		isEmptyBufferedImage = false;
		
		//After the calculation of "new" image, we display it
		repaint();
	}
	
	/**
	 * Create a tower/send info to dispatcher
	 */
	public void createTower(int x, int y, String type){	
		Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
		
		ActionTowerCreate myAction = null;
		
		if( type.equals("freeze")) {
			myAction = new ActionTowerCreate(player,new TowerFreeze(new Point(x, y), player));
		} else if(type.equals("laser")){
			myAction = new ActionTowerCreate(player,new TowerLaser(new Point(x, y), player));
		} else if(type.equals("missile")){
			myAction = new ActionTowerCreate(player,new TowerMissile(new Point(x, y), player));
		} else if(type.equals("machinegun")){
			myAction = new ActionTowerCreate(player,new TowerMachinegun(new Point(x, y), player));
		} else if(type.equals("bomb")){
			myAction = new ActionTowerCreate(player,new TowerBombe(new Point(x, y), player));
		} else if(type.equals("bounce")){
			myAction = new ActionTowerCreate(player,new TowerBounce(new Point(x, y), player));
		} else if(type.equals("sniper")){
			myAction = new ActionTowerCreate(player,new TowerSniper(new Point(x, y), player));
		} else if(type.equals("poison")){
			myAction = new ActionTowerCreate(player,new TowerPoison(new Point(x, y), player));
		} else {
			System.out.println("Erreur Grave - tour inconnue - TowersLayers. Action not created");
			return;
		}

		JavaWars.getDispatcher().addAction(myAction);
		
		repaint();
	}

	/**
	 * setter
	 * @param img : img to put instead of the current one
	 */
	public void setBufferedImage(BufferedImage img){
		this.bufferedImage = img;
	}

}