package fr.imac.javawars.engine;

import java.awt.Color;
//import java.awt.Point;
import java.awt.image.*;
import javax.imageio.*;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

/**
 * Class Map : create map with : image (gif or png), xml file or random
 * 
 * * * * Image map
 * Wall : Black
 * Path : White
 * Neutral Bases : Grey
 * Players' Bases : 4 distincts colors (with R, G & B differents)
 * 
 * 
 * * * * Legends XML
 * Wall --> -2
 * Path --> -1
 * Neutral Bases --> 0
 * Players' Bases --> >= 1
 * 
 */

// TODO make a real map with swing

public class Ground {

	
	private int bitMap[][];
	
	
	private ArrayList<Point> centerBases = new ArrayList<Point>();
	
    public static final int WHITE=255;
    public static final int BLACK=0;
    public static final int WIN_HEIGHT=500;
    public static final int WIN_WITDH=700;
	public static final int RADIUS=25;
	
	//test arthur
	//nbPlayers calculated from the number of bases for players at the beginning.
	private int numberOfPlayers;

	/** Constructor to generate Random Map
	 * 
	 */
    public Ground(){
    	// Map Random
    	bitMap = new int[WIN_HEIGHT][WIN_WITDH];
    	numberOfPlayers = 4;
    	
    	initGroundPath();
    	generateBasesPlayer();
    	generateBasesNeutral(10);
    	generateWall();
    	extendPathBases();
    	onePixelPerBase();
    	
    	saveAsXML(bitMap, "map/randomMap_1");
    }
    
    /** Constructor to generate Map by file (Text or Image)
     * 
     * @param file
     */
	public Ground(String file) {
		
		// Search extension of file
		String[] ext = file.split("\\.");
		
		// IF it is an image (gif or png)
		if (ext[1].toString().equals("gif") || ext[1].toString().equals("png") ){
			generateGroundByImg(file);
			saveAsXML(bitMap, ext[0].toString());
			// Generation Map with XML
		} 
		
		// IF it is a text file (xml)
		else if (ext[1].toString().equals("xml")){
			System.out.println("XML");
			generateGroundByXML(file);
		} 
		
		// IF it is a text file (xml)
		else {
			System.err.println("Fichier image de la map invalide, utiliser .gif, .png, .xml ou rien pour une génération aléatoire" );
		}
		
		
	}
	
	public void printGround(){
		int i=0;
		int j=0;
			
			//Create the map table
			while(i<bitMap.length){
				// column to column
				j=0;
			    	while(j<bitMap[0].length){
			    		// row to row
			    		
			    		System.out.print(bitMap[i][j]);
			    		
			    		j++;	   
			    	}
			    	System.out.println(" ");
			    i++;
			}
	}
	
	private int getGroundPosition(int x, int y){
		return bitMap[x][y];
	}

	
	public void generateGroundByXML(String file){
		
	}
	
	/** Generate a bitMap by a File
	 * 
	 * @param file
	 */
	public void generateGroundByImg(String file){
		
		LinkedList<Color> playerColor = new LinkedList<Color>();
		
		BufferedImage mapImg;
		try {
			mapImg = ImageIO.read(new File(file));
			bitMap = new int[mapImg.getWidth()][mapImg.getHeight()];
		}
		catch (IOException e) {
			mapImg = null;
			System.err.println("Fichier image de la map invalide" );
		}
		
		int i=0;
		int j=0;
		
		//Create the map table
		while(i<mapImg.getHeight()){
			// column to column
			j=0;
		    	while(j<mapImg.getWidth()){
		    		// row to row
		    		Color c = new Color(mapImg.getRGB(j,i));
		    		bitMap[i][j]=rgbToInt(c, playerColor);
		    		
		    		j++;	   
		    	}
		    i++;
		}
	}
	
	/** Save bitMap in XML file
	 * 
	 * @param bitMap
	 * @param File
	 */
	public static void saveAsXML(int bitMap[][], String File){
	    String nameFile = File+".xml";
	    try{
	      PrintWriter out  = new PrintWriter(new FileWriter(nameFile));
	      int i, j;
	      for (i = 0; i < bitMap.length; i++) {
	    	  j = 0;
	    	  for (j = 0; j< bitMap[0].length; j++)
	    		  out.print(bitMap[i][j] + " " );
	    	  out.println(" ");
	      }
	      out.close();
	    }
	    catch(Exception e){
	      e.printStackTrace();
	    }
	  }
	
	/** Convert Color in "Wall", "Path", "Player's Bases" or "Neutral Bases"
	 * 
	 * @param c
	 * 			Color in the image
	 * @return value of Color
	 */
	 private int rgbToInt(Color c,  LinkedList<Color> playerColor){

 		int r = c.getRed();
 		int g = c.getGreen();
 		int b = c.getBlue();
 		
		 if(r==g && g==b){
			 if (r == BLACK)
				 return -2;
			 else if (r == WHITE )
				 return -1;
			 else 
				 return 0;
		 }
		 else {
			 int i = 1;
			 // LinkedList Empty, add first value
			 if (playerColor.isEmpty()){
				 playerColor.add(c);
				 return i;
			 }
			 else {
				 // Search if color already exists in LinkedList
				 for(Color color: playerColor){
					 if (c.equals(color))
						 return i;
					 ++i;
				 }
				 // If color doesn't exist, add value at end of LinkedList
				 playerColor.add(c);
				 //test arthur
				 this.numberOfPlayers = playerColor.size();
				 
				 return i;
			 }
			 
		 }
	 }
	 
	 /** Init all the map at 0 --> all path
	  * 
	  * @param bitMap
	  */
	 public void initGroundPath(){
		int i=0;
		int j=0;
			
			//Create the map table
			while(i<bitMap.length){
				// column to column
				j=0;
			    	while(j<bitMap[0].length){
			    		// row to row
			    		
			    		bitMap[i][j]=-1;
			    		
			    		j++;	   
			    	}
			    i++;
			}
	 }
	 
	 
	 /** Generate Player's Bases
	  * 
	  * @param nbBases
	  * @param bitMap
	  */
	 public void generateBasesPlayer(){	
		 ArrayList<Base> bases = new ArrayList<Base>();
		 
		 
		 
		 
		 
	    	Random rnd = new Random();
	    	int oX, oY;
		 for (int i=1; i<= numberOfPlayers; ++i){
			 
			 do {
				switch(i){
					case 1:
						oX = rnd.nextInt(WIN_WITDH-(2*(RADIUS+1)+WIN_WITDH/2))+(RADIUS+1);
					 	oY = rnd.nextInt(WIN_HEIGHT-(2*(RADIUS+1)+WIN_HEIGHT/2))+(RADIUS+1);	
					break;
					case 2:
						oX = rnd.nextInt(WIN_WITDH-(2*(RADIUS+1)+WIN_WITDH/2))+((RADIUS+1)+WIN_WITDH/2);
					 	oY = rnd.nextInt(WIN_HEIGHT-(2*(RADIUS+1)+WIN_HEIGHT/2))+(RADIUS+1);
					break;
					case 3:
						oX = rnd.nextInt(WIN_WITDH-(2*(RADIUS+1)+WIN_WITDH/2))+(RADIUS+1);
					 	oY = rnd.nextInt(WIN_HEIGHT-(2*RADIUS+WIN_HEIGHT/2))+(RADIUS+WIN_HEIGHT/2);	
					break;
					case 4:
						oX = rnd.nextInt(WIN_WITDH-(2*(RADIUS+1)+WIN_WITDH/2))+((RADIUS+1)+WIN_WITDH/2);
					 	oY = rnd.nextInt(WIN_HEIGHT-(2*RADIUS+WIN_HEIGHT/2))+(RADIUS+WIN_HEIGHT/2);
					break;
					default:
						oX = rnd.nextInt(WIN_WITDH-2*RADIUS)+RADIUS;
					 	oY = rnd.nextInt(WIN_HEIGHT-2*RADIUS)+RADIUS;	
					break;
				}		 
			 } while(bitMap[oY][oX]!= -1 || !checkSpaceBases(oX, oY, RADIUS, bitMap) );

			 generateCircleInPixel(RADIUS, oX, oY, bitMap, i);
			// TODO Create class Bases
			 
			 //Point p = new Point(oX, oY);
			 //Base b = new Base(p, RADIUS);
			 //bases.add(b);
			 //centerBases.add(p);
		 }
		 
		 //JavaWars.getEngine().setBases(bases);
	 }
	 
	 /** Generate neutral Bases
	  * 
	  * @param nbBases
	  * @param bitMap
	  */
	 
	 public void generateBasesNeutral(int nbBases){	    	
	    	Random rnd = new Random();
	    	int oX, oY, rayon;
	    	
		 for (int i=1; i<= nbBases; ++i){
			 rayon = rnd.nextInt(RADIUS-1)+1;
			 do {
				
				oX = rnd.nextInt(WIN_WITDH-2*rayon)+rayon;
			 	oY = rnd.nextInt(WIN_HEIGHT-2*rayon)+rayon;		 
			 } while(bitMap[oY][oX]!=-1 || !checkSpaceBases(oX, oY, rayon, bitMap) );

			 generateCircleInPixel(rayon, oX, oY, bitMap, 0);
			 Point p = new Point(oX, oY);
			 centerBases.add(p);
		 }
	 }
	 
	 /** Create wall in the bitMap
	  * 
	  * @param bitMap
	  */
	 
	 public void generateWall(){
		 Random rnd = new Random();
		 int disposition;
		 int rayon;
		 int j=0;
		 int oX, oY, rndX, rndY;
		 
		 // Create wall around the window
	    	while(j<bitMap[0].length){
	    		
	    		disposition = rnd.nextInt(10);
	    		rayon = rnd.nextInt(RADIUS)+20;
	    		
	    		//Left wall
	    		if (disposition>2 && bitMap[0][j]==-1){
	    			generateCircleInPixel(rayon, 0, j, bitMap, -2);
	   		 	}
	    		
	    		// Top wall
	    		if (disposition>2 && bitMap[0][j]==-1){
	    			generateCircleInPixel(rayon, j, 0, bitMap, -2);
	   		 	}
	    		// Bottom wall
	    		if (disposition>2 && bitMap[WIN_HEIGHT-1][j]==-1){
	    			generateCircleInPixel(rayon, j, WIN_HEIGHT-1, bitMap, -2);
	   		 	}
	    		j++;	   
	    	}
	    	j=0;
	    	while(j<bitMap.length){
	    		disposition = rnd.nextInt(10);
	    		rayon = rnd.nextInt(RADIUS)+5;
		    	if (disposition>2 && bitMap[j][WIN_HEIGHT-1]==-1){
		    		
	    			generateCircleInPixel(rayon,WIN_WITDH-1, j, bitMap, -2);
	   		 	}
		    	j++;
	    	}
	    	
	    	// Create wall in the windows
	    	for (int i = 0; i<5; ++i) {
	    		rayon = rnd.nextInt(RADIUS)+5;
	    		
	    		oX = rnd.nextInt(WIN_HEIGHT-2*RADIUS)+RADIUS;
		 		oY = rnd.nextInt(WIN_WITDH-2*RADIUS)+RADIUS;	
		 		
		 		for (int l =0; l<5; ++l) {
		 			rndX = rnd.nextInt(rayon);
		 			rndY = rnd.nextInt(rayon);
		 			generateCircleInPixel(rayon, oX+rndX, oY+rndY, bitMap, -2);
		 		}
	    	}

	 }
	 

	 /** Create an extend circle around Bases
	  * 
	  * @param bitMap
	  */
	 public void extendPathBases(){
		 for (int i =0; i<centerBases.size(); ++i){
			 generateCircleInPixel(RADIUS+20, (int)centerBases.get(i).getX(), (int)centerBases.get(i).getY(), bitMap, -1);
		 }
	 }
	 
	 
	 public void onePixelPerBase(){
		 for (int i =0; i<centerBases.size(); ++i){
			 
		 }
	 }
	 
	 
	 /** Create a circle in BitMap with origin, radius
	  * 
	  * @param rayon
	  * @param oX
	  * @param oY
	  * @param bitMap
	  * @param value
	  */
	 
	 public void generateCircleInPixel(int rayon, int oX, int oY, int bitMap[][], int value){	
	    	int x, y;
	    	for (x=oX-(rayon); x<=oX+(rayon); x++){
	    		for (y=oY-(rayon); y<=oY+(rayon); y++) {
		    		if( ( (x-oX)*(x-oX) + (y-oY)*(y-oY) ) <= rayon*rayon && x>=0 && y>=0 && x<WIN_WITDH && y<WIN_HEIGHT && bitMap[y][x]<0){
		        		bitMap[y][x]=value;
		        	}
	    		}
	    	}
	 }
	 
	 /** Check if enough space to create this circle
	  * 
	  * @param oX
	  * @param oY
	  * @param rayon
	  * @param bitMap
	  * @return if enough space or not
	  */
	 public boolean checkSpaceBases(int oX, int oY, int rayon, int bitMap[][]){
		 int x, y;
	    	for (x=oX-(rayon); x<=oX+(rayon); x++){
	    		for (y=oY-(rayon); y<=oY+(rayon); y++) {
		    		if (bitMap[y][x]!=-1) return false;
	    		}
	    	} 
	    return true;
	 }

	public int[][] getBitMap() {
		return bitMap;
	}

	public void setBitMap(int[][] bitMap) {
		this.bitMap = bitMap;
	}

	public ArrayList<Point> getCenterBases() {
		return centerBases;
	}

	public void setCenterBases(ArrayList<Point> centerBases) {
		this.centerBases = centerBases;
	}

	//test arthur
	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}
	
	
	
	 
	 /**
		 * @param args
		 */
		/*public static void main(String[] args) {
			
			// Test Ramdon Map
				Map myMap = new Map();
			// Test Map with file
				// Map myMap = new Map("map/mapTest_3.gif");
			
				myMap.printMap();
			
		}*/
	 
	 
		

}
