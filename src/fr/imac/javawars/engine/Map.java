package fr.imac.javawars.engine;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class Map : create map with : image (gif or png), xml file or random
 * 
 * * * * Image map
 * Wall : Black
 * Path : White
 * Neutral Basis : Grey
 * Players' Basis : 4 distincts colors (with R, G & B differents)
 * 
 * 
 * * * * Legends XML
 * Wall --> 1
 * Path --> 0
 * Neutral Basis --> 2
 * Players' Basis --> >= 3
 * 
 */

// TODO make a real map with swing

public class Map {

	
	private int bitMap[][];
	
	private ArrayList<Point> centerBasis = new ArrayList<Point>();
	
    public static final int WHITE=255;
    public static final int BLACK=0;
    public static final int WIN_HEIGHT=100;
    public static final int WIN_WITDH=150;
	public static final int RADIUS=5;

	/** Constructor to generate Random Map
	 * 
	 */
    public Map(){
    	// Map Random
    	bitMap = new int[WIN_HEIGHT][WIN_WITDH];
    	
    	initMapPath(bitMap);
    	geneBasisPlayer(4, bitMap);
    	geneBasisNeutral(10,bitMap);
    	
    	geneWall(bitMap);
    	
    	extendPathBasis(bitMap);
    	saveAsXML(bitMap, "map/randomMap_1");
    	
    	
    }
    
    /** Constructor to generate Map by file (Text or Image)
     * 
     * @param file
     */
	public Map(String file) {
		
		// Search extension of file
		String[] ext = file.split("\\.");
		
		// IF it is an image (gif or png)
		if (ext[1].toString().equals("gif") || ext[1].toString().equals("png") ){
			geneMapbyImg(file);
			saveAsXML(bitMap, ext[0].toString());
			// Generation Map with XML
		} 
		
		// IF it is a text file (xml)
		else if (ext[1].toString().equals("xml")){
			System.out.println("XML");
			geneMapbyXML(file);
		} 
		
		// IF it is a text file (xml)
		else {
			System.err.println("Fichier image de la map invalide, utiliser .gif, .png, .xml ou rien pour une génération aléatoire" );
		}
		
		
	}
	
	private void printMap(){
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
	
	private int getMapPosition(int x, int y){
		return bitMap[x][y];
	}

	
	public void geneMapbyXML(String file){
		
	}
	
	/** Generate a bitMap by a File
	 * 
	 * @param file
	 */
	public void geneMapbyImg(String file){
		
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
	public void saveAsXML(int bitMap[][], String File){
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
	
	/** Convert Color in "Wall", "Path", "Player's Basis" or "Neutral Basis"
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
				 return 1;
			 else if (r == WHITE )
				 return 0;
			 else 
				 return 2;
		 }
		 else {
			 int i = 3;
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
				 return i;
			 }
			 
		 }
	 }
	 
	 /** Init all the map at 0 --> all path
	  * 
	  * @param bitMap
	  */
	 public void initMapPath(int bitMap[][]){
		int i=0;
		int j=0;
			
			//Create the map table
			while(i<bitMap.length){
				// column to column
				j=0;
			    	while(j<bitMap[0].length){
			    		// row to row
			    		
			    		bitMap[i][j]=0;
			    		
			    		j++;	   
			    	}
			    i++;
			}
	 }
	 
	 
	 /** Generate Player's Basis
	  * 
	  * @param nbBasis
	  * @param bitMap
	  */
	 public void geneBasisPlayer(int nbBasis, int bitMap[][]){	    	
	    	Random rnd = new Random();
	    	int oX, oY;
		 for (int i=1; i<= nbBasis; ++i){
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
			 } while(bitMap[oY][oX]!=0 || !checkSpaceBasis(oX, oY, RADIUS, bitMap) );

			 geneCircleInPixel(RADIUS, oX, oY, bitMap, i+2);
			// TODO Create class basis 
			 Point p = new Point(oX, oY);
			 centerBasis.add(p);
		 }
	 }
	 
	 /** Generate neutral basis
	  * 
	  * @param nbBasis
	  * @param bitMap
	  */
	 
	 public void geneBasisNeutral(int nbBasis, int bitMap[][]){	    	
	    	Random rnd = new Random();
	    	int oX, oY, rayon;
	    	
		 for (int i=1; i<= nbBasis; ++i){
			 rayon = rnd.nextInt(RADIUS-1)+1;
			 do {
				
				oX = rnd.nextInt(WIN_WITDH-2*rayon)+rayon;
			 	oY = rnd.nextInt(WIN_HEIGHT-2*rayon)+rayon;		 
			 } while(bitMap[oY][oX]!=0 || !checkSpaceBasis(oX, oY, rayon, bitMap) );

			 geneCircleInPixel(rayon, oX, oY, bitMap, 2);
			 Point p = new Point(oX, oY);
			 centerBasis.add(p);
		 }
	 }
	 
	 /** Create wall in the bitMap
	  * 
	  * @param bitMap
	  */
	 
	 public void geneWall(int bitMap[][]){
		 Random rnd = new Random();
		 int disposition;
		 int rayon;
		 int j=0;
		 int oX, oY, rndX, rndY;
		 
		 // Create wall around the window
	    	while(j<bitMap[0].length){
	    		
	    		disposition = rnd.nextInt(10);
	    		rayon = rnd.nextInt(RADIUS)+5;
	    		
	    		if (disposition>4 && bitMap[0][j]==0){
	    			geneCircleInPixel(rayon, 0, j, bitMap, 1);
	   		 	}	
	    		if (disposition>4 && bitMap[WIN_HEIGHT-1][j]==0){
	    			geneCircleInPixel(rayon, WIN_HEIGHT-1, j, bitMap, 1);
	   		 	}
	    		if (disposition>4 && bitMap[0][j]==0){
	    			geneCircleInPixel(rayon, j, 0, bitMap, 1);
	   		 	}
	    		if (disposition>3 && bitMap[WIN_HEIGHT-1][j]==0){
	    			geneCircleInPixel(rayon, j, WIN_HEIGHT-1, bitMap, 1);
	   		 	}
	    		j++;	   
	    	}
	    	
	    	// Create wall in the windows
	    	for (int i = 0; i<5; ++i) {
	    		rayon = rnd.nextInt(RADIUS)+5;
	    		
	    		oX = rnd.nextInt(WIN_WITDH-2*RADIUS)+RADIUS;
		 		oY = rnd.nextInt(WIN_HEIGHT-2*RADIUS)+RADIUS;	
		 		
		 		for (int l =0; l<5; ++l) {
		 			rndX = rnd.nextInt(rayon);
		 			rndY = rnd.nextInt(rayon);
		 			geneCircleInPixel(rayon, oX+rndX, oY+rndY, bitMap, 1);
		 		}
	    	}

	 }
	 

	 /** Create an extend circle around Basis
	  * 
	  * @param bitMap
	  */
	 public void extendPathBasis(int bitMap[][]){
		 for (int i =0; i<centerBasis.size(); ++i){
			 geneCircleInPixel(RADIUS+7, (int)centerBasis.get(i).getX(), (int)centerBasis.get(i).getY(), bitMap, 0);
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
	 
	 public void geneCircleInPixel(int rayon, int oX, int oY, int bitMap[][], int value){	
	    	int x, y;
	    	for (x=oX-(rayon); x<=oX+(rayon); x++){
	    		for (y=oY-(rayon); y<=oY+(rayon); y++) {
		    		if( ( (x-oX)*(x-oX) + (y-oY)*(y-oY) ) <= rayon*rayon && x>=0 && y>=0 && x<WIN_WITDH && y<WIN_HEIGHT && bitMap[y][x]<2){
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
	 public boolean checkSpaceBasis(int oX, int oY, int rayon, int bitMap[][]){
		 int x, y;
	    	for (x=oX-(rayon); x<=oX+(rayon); x++){
	    		for (y=oY-(rayon); y<=oY+(rayon); y++) {
		    		if (bitMap[y][x]!=0) return false;
	    		}
	    	} 
	    return true;
	 }
	 
	 
	 /**
		 * @param args
		 */
		public static void main(String[] args) {
			
			// Test Ramdon Map
				Map myMap = new Map();
			// Test Map with file
				// Map myMap = new Map("map/mapTest_3.gif");
			
				myMap.printMap();
			
		}
		

}
