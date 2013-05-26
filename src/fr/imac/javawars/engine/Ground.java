package fr.imac.javawars.engine;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.player.Player;
//import java.awt.Point;
//import java.awt.Point;

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

public class Ground {

	
	private int bitMap[][];
	
	
	private ArrayList<Point> centerBases = new ArrayList<Point>();
	

    private static final int WHITE=255;
    private static final int BLACK=0;
    private static final int WIN_HEIGHT=500;
    private static final int WIN_WIDTH=700;
    private static final int RADIUS=25;
	private final double coefSpeedRegen = 0.04;
	//test arthur
	//nbPlayers calculated from the number of bases for players at the beginning.
	private int numberOfPlayers;

	/** Constructor to generate Random Map
	 * 
	 */
    public Ground(){
    	// Map Random
    	bitMap = new int[WIN_HEIGHT][WIN_WIDTH];
    	numberOfPlayers = 4;
    	
    	initGroundPath();
    	generateBasesPlayer();
    	generateBasesNeutral(2);//(10);
    	generateWall();
    	//extendPathBases();
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
			generateGroundByXML(file);
		} 
		
		// IF it is a text file (xml)
		else {
			System.err.println("Fichier image de la map invalide, utiliser .gif, .png, .xml ou rien pour une génération aléatoire" );
		}
		
		
	}
	
	/** Print the ground
	 * 
	 */
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
	
	public int getGroundPosition(int x, int y){
		return bitMap[x][y];
	}

	/** Generate the Ground by a XML file
	 * Struct of the file :
	 * width height
	 * bitMap ....
	 * 
	 * @param file
	 */
	public void generateGroundByXML(String file){

		String chaine ="";
		
		File f = new File (file);
		
		// Open file xml
		try
		{
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		    
		    // Read line per line and save in "Chaine"
			try
			{
				String line = br.readLine();
				 
		        while (line != null)
		        {
		        	chaine += line;
		            line = br.readLine();

		        }
		 
		        br.close();
		        fr.close();
			}
			catch (IOException exception)
			{
			    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
			}
			
		}
		catch (FileNotFoundException exception)
		{
		    System.out.println ("Le fichier n'a pas été trouvé");
		}
				 
		
		// Splite "chaine" with space to have a table of elements ( [-1][-2]...)
		String decoup[]=chaine.split(" ");		
		
		int i=0;
		int j=0;
		
		// The 2 first number of the file are width and height, so began count at 2
		int l=2;
		int width = Integer.parseInt(decoup[0]);
		int height = Integer.parseInt(decoup[1]);
		
		// Initialized bitmap[][]
		bitMap = new int[height][width];
		
		CopyOnWriteArrayList<Base> listBases = new CopyOnWriteArrayList<Base>();
		
		while(i<height){
			// row to row
			j=0;
		    	while(j<width){
		    		// column to column
		    		
			    		int coord = Integer.parseInt(decoup[l]);
			    		bitMap[i][j]= coord;
			    		
			    		// If its neutral bases
			    		if (coord==0){
			    			Random rnd = new Random();
			    	 		int rayon = rnd.nextInt(RADIUS-10)+10;

			    	 		Base base = createBases(new Point(j,i), rayon);

	    					listBases.add(base);
	    					
			    		} 
			    		// If its player bases
			    		else if (coord>=1){
			    			centerBases.add(new Point(j,i));
			    		}
			    	j++;	
		    		l++;
		    	}    	
		    i++;
		}
		
		// Loop arround Player List to associate a base to a player
		Iterator<Map.Entry<Integer, Player>> itTemp = JavaWars.getEngine().getPlayers().entrySet().iterator();
		while (itTemp.hasNext()) {
			Map.Entry<Integer, Player> entry = itTemp.next();
			
			Point p = centerBases.get(entry.getKey()-1);
			Base b = createBases(p,RADIUS, entry.getValue());
			
			listBases.add(b);

		}
		
		JavaWars.getEngine().setBases(listBases);

	}
	
	/** Generate a bitMap by a File
	 * 
	 * @param file
	 */
	public void generateGroundByImg(String file){
		
		LinkedList<Color> playerColor = new LinkedList<Color>();
		
		// Read the image
		BufferedImage mapImg;
		try {
			mapImg = ImageIO.read(new File(file));
			bitMap = new int[mapImg.getHeight()][mapImg.getWidth()];
		}
		catch (IOException e) {
			mapImg = null;
			System.err.println("Fichier image de la map invalide" );
		}
		int i=0;
		int j=0;
		
		
		CopyOnWriteArrayList<Base> listBases = new CopyOnWriteArrayList<Base>();
		
		//Create the map table
		while(i<mapImg.getHeight()){
			// row to row
			j=0;
		    	while(j<mapImg.getWidth()){
		    		// column to column
		    		Color c = new Color(mapImg.getRGB(j,i));
		    		Point p = new Point(j, i);
		    		bitMap[i][j]=fillBitMapByImg(c, playerColor, listBases, p);
		    		
		    		j++;	   
		    	}
		    	
		    i++;
		}
		
		Iterator<Map.Entry<Integer, Player>> itTemp = JavaWars.getEngine().getPlayers().entrySet().iterator();
		while (itTemp.hasNext()) {
			Map.Entry<Integer, Player> entry = itTemp.next();
			
			Point p = centerBases.get(entry.getKey()-1);
			Base b = new Base(p, entry.getValue(), coefSpeedRegen*RADIUS, RADIUS);
			
			listBases.add(b);

		}
		
		JavaWars.getEngine().setBases(listBases);
		saveAsXML(bitMap, "mapCool");
	}
	
	/** Save bitMap in XML file
	 * 
	 * @param File
	 */
	public static void saveAsXML(int[][] bm, String File){
	    String nameFile = File+".xml";
	    try{
	      PrintWriter out  = new PrintWriter(new FileWriter(nameFile));
	      int i, j;
	      for (i = 0; i < bm.length; i++) {
	    	  j = 0;
	    	  for (j = 0; j< bm[0].length; j++)
	    		  out.print(bm[i][j] + " " );
	    	  out.println("");
	      }
	      out.close();
	    }
	    catch(Exception e){
	      e.printStackTrace();
	    }
	  }
	
	
	public boolean checkSpaceTower(int x, int y){

		for (int i = x-13; i<= x+13; ++i){
			for (int j = y-13; j<= y+13; ++j){
				if (bitMap[j][i]!=-2)
					return false;
			}
		}
		return true;
	}
	
	/** Convert Color in "Wall", "Path", "Player's Bases" or "Neutral Bases"
	 * 
	 * @param c
	 * 			Color in the image
	 * @param playerColor
	 * @param listBases
	 * @param p
	 * @return
	 * 
	 */
	 private int fillBitMapByImg(Color c,  LinkedList<Color> playerColor, CopyOnWriteArrayList<Base> listBases, Point p){

 		int r = c.getRed();
 		int g = c.getGreen();
 		int b = c.getBlue();
 		
 		Random rnd = new Random();
 		int rayon = rnd.nextInt(RADIUS-10)+10;
 		
		 if(r==g && g==b){
			 if (r == BLACK) // WALL
				 return -2;
			 else if (r == WHITE ) // PATH
				 return -1;
			 else { // NEUTRAL BASES
				 Base base = new Base(rnd.nextInt(100)+1, p, coefSpeedRegen*rayon, rayon);
				 listBases.add(base);
				 return 0;
			 }
		 }
		 else {
			 int i = 1;
			 // LinkedList Empty, add first value
			 if (playerColor.isEmpty()){
				 playerColor.add(c);
				 centerBases.add(p);
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
				 // Add center of Bases to create Base with player
				 centerBases.add(p);
				 
				 return i;
			 }
			 
		 }
	 }
	 
	 /** Init all the map at -1 --> all path
	  * 
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
	  */
	 public void generateBasesPlayer(){	
		CopyOnWriteArrayList<Base> bases = new CopyOnWriteArrayList<Base>();
		
    	Random rnd = new Random();
    	int oX, oY;
    	
    	// loop on all players to create base in 4 corner of the windows
		Iterator<Map.Entry<Integer, Player>> itTemp = JavaWars.getEngine().getPlayers().entrySet().iterator();
		while (itTemp.hasNext()) {
			Map.Entry<Integer, Player> entry = itTemp.next();
			do {
				switch(entry.getKey()){
					case 1:
						oX = rnd.nextInt(WIN_WIDTH-(2*(RADIUS+1)+WIN_WIDTH/2))+(RADIUS+1);
					 	oY = rnd.nextInt(WIN_HEIGHT-(2*(RADIUS+1)+WIN_HEIGHT/2))+(RADIUS+1);	
					break;
					case 2:
						oX = rnd.nextInt(WIN_WIDTH-(2*(RADIUS+1)+WIN_WIDTH/2))+((RADIUS+1)+WIN_WIDTH/2);
					 	oY = rnd.nextInt(WIN_HEIGHT-(2*(RADIUS+1)+WIN_HEIGHT/2))+(RADIUS+1);
					break;
					case 3:
						oX = rnd.nextInt(WIN_WIDTH-(2*(RADIUS+1)+WIN_WIDTH/2))+(RADIUS+1);
					 	oY = rnd.nextInt(WIN_HEIGHT-(2*RADIUS+WIN_HEIGHT/2))+(RADIUS+WIN_HEIGHT/2);	
					break;
					case 4:
						oX = rnd.nextInt(WIN_WIDTH-(2*(RADIUS+1)+WIN_WIDTH/2))+((RADIUS+1)+WIN_WIDTH/2);
					 	oY = rnd.nextInt(WIN_HEIGHT-(2*RADIUS+WIN_HEIGHT/2))+(RADIUS+WIN_HEIGHT/2);
					break;
					default:
						oX = rnd.nextInt(WIN_WIDTH-2*RADIUS)+RADIUS;
					 	oY = rnd.nextInt(WIN_HEIGHT-2*RADIUS)+RADIUS;	
					break;
				}		 
				
			} while(bitMap[oY][oX]!= -1 || !checkSpaceBases(oX, oY, RADIUS, bitMap) );
			//end Do WHILE
			
			
			generateCircleInPixel(RADIUS, oX, oY, bitMap, entry.getKey());
			
			Point p = new Point(oX, oY);
			Base b = createBases(p,RADIUS, entry.getValue());
			bases.add(b);
			centerBases.add(p);
				
		} // end WHILE
	    	
		JavaWars.getEngine().setBases(bases);

	 }
	 
	 /** Generate neutral Bases
	  * 
	  * @param nbBases
	  */
	 
	 public void generateBasesNeutral(int nbBases){	    	
	    	Random rnd = new Random();
	    	int oX, oY, rayon;
	    	CopyOnWriteArrayList<Base> bases = JavaWars.getEngine().getBases();
	    	
		 for (int i=1; i<= nbBases; ++i){
			 rayon = rnd.nextInt(RADIUS-10)+10;
			 do {
				
				oX = rnd.nextInt(WIN_WIDTH-2*rayon)+rayon;
			 	oY = rnd.nextInt(WIN_HEIGHT-2*rayon)+rayon;		 
			 } while(bitMap[oY][oX]!=-1 || !checkSpaceBases(oX, oY, rayon, bitMap) );
			 
			 generateCircleInPixel(rayon, oX, oY, bitMap, 0);
			 Point p = new Point(oX, oY);
			 Base b = createBases(p,rayon);
			 bases.add(b);
			 centerBases.add(p); 	
		 }
		 JavaWars.getEngine().setBases(bases);
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
	    		
	    		disposition = rnd.nextInt(6);
	    		rayon = rnd.nextInt(RADIUS)+10;
	    		
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
		    		
	    			generateCircleInPixel(rayon,WIN_WIDTH-1, j, bitMap, -2);
	   		 	}
		    	j++;
	    	}
	    	
	    	// Create wall in the windows
	    	for (int i = 0; i<20; ++i) {
	    		rayon = rnd.nextInt(RADIUS)+20;
	    		
	    		oX = rnd.nextInt(WIN_HEIGHT-2*RADIUS)+RADIUS;
		 		oY = rnd.nextInt(WIN_WIDTH-2*RADIUS)+RADIUS;	
		 		
		 		for (int l=0; l<5; ++l) {
		 			rndX = rnd.nextInt(rayon);
		 			rndY = rnd.nextInt(rayon);
		 			generateCircleInPixel(rayon, oX+rndX, oY+rndY, bitMap, -2);
		 		}
	    	}

	 }
	 

	 /** Create an extend circle around Bases
	  * 
	  */
	 public void extendPathBases(){
		 Random rnd = new Random();
		
		 int rndX = 0;
		 int rndY = 0;
		 for (int i =0; i<centerBases.size(); ++i){
			 generateCircleInPixel(RADIUS+20, (int)centerBases.get(i).getX(), (int)centerBases.get(i).getY(), bitMap, -1);
			 for (int l=0; l<5; ++l) {
				
				 int rayon = rnd.nextInt(RADIUS);
		 			rndX = 20;
		 			rndY = 20;
		 			generateCircleInPixel(rayon, (int)centerBases.get(i).getX()+rndX, (int)centerBases.get(i).getY()+rndY, bitMap, -1);
		 		}
		 }
	 }
	 
	 
	 public void onePixelPerBase(){
		 for (int i =0; i<centerBases.size(); ++i){
			 
		 }
	 }
	 
	 public Base createBases(Point p, int radius, Player pl){
		 Base b = new Base(p, pl, coefSpeedRegen*radius, radius);
		 return b;
	 }
	 public Base createBases(Point p, int radius){
		 Random rnd = new Random();
		 Base b = new Base(rnd.nextInt(100)+1,p,coefSpeedRegen*radius, radius);
		 return b;
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
		    		if( ( (x-oX)*(x-oX) + (y-oY)*(y-oY) ) <= rayon*rayon && x>=0 && y>=0 && x<WIN_WIDTH && y<WIN_HEIGHT && bitMap[y][x]<0){
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

	public static int getWinHeight() {
		return WIN_HEIGHT;
	}

	public static int getWinWidth() {
		return WIN_WIDTH;
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
