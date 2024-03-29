package Model;


import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;



/**
 * Class that loads all game maps, while being able to add and remove sprites to a particular
 * map.
 */
public class TileMap {
	private Image[][] Map;
	private String[][] charMap;
	
	
	
    public ArrayList <Sprite> SpriteList;
    public ArrayList <Sprite> PlayerList;
    public ArrayList <Sprite> [][] Grid;
    public ArrayList <Item> ItemList;
    public int width;
    public int height;
    Random generator  = new Random();
    
   
    public TileMap(int width, int height, String file) throws IOException {
        Map = new Image[width][height];
        SpriteList = new ArrayList <Sprite>();
        PlayerList = new ArrayList <Sprite>();
        ItemList = new ArrayList <Item>();
        charMap = new String[width][height];
        loadMap(file);
        this.width = width;
        this.height = height;
        
        
    }
    /**
	 * get map Width
	 * @return width
	 */
    public int getWidth() {
        return width;
    }
    
    /**
	 * get map Height
	 * @return health
	 */
    public int getHeight() {
        return height;
    }

    /**
	 * get tile Image
	 * @return width
	 */
    public Image getTile(int x, int y) {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()){
            return null;
        }else{
        	return Map[x][y];
        }
    }
    /**
	 * sets the Image for a Tile
	 */
    public void setTile(int x, int y, Image tile) {
        Map[x][y] = tile;
    }

    /**
	 * Adds sprite to grid at position X, Y
	 */
    public void addToGrid(int x, int y, Sprite sprite) {
    	Grid[x][y].add(sprite);
    }
    
    /**
	 * Removes sprite at grid position X, Y
	 */
    public void removeFromGrid(int x, int y, Sprite sprite) {
        Grid[x][y].remove(sprite);
    }
    
    /**
	 * Adds target sprite to SpriteList
	 */
    public void addSprite(Sprite sprite) {
        SpriteList.add(sprite);
    }

    /**
	 * Removes target sprite from SpriteList
	 */
    public void removeSprite(Sprite sprite) {
        SpriteList.remove(sprite);
    }
    
    /**
	 * Adds target player to PlayerList
	 */
    public void addPlayer(Sprite sprite) {
        PlayerList.add(sprite);
    }

    /**
	 * Removes target player from PlayerList
	 */
    public void removePlayer(Sprite sprite) {
        PlayerList.remove(sprite);
    }
    
    /**
	 * read and load map from text file
	 */
    @SuppressWarnings("unchecked")
	private void loadMap(String filename) throws IOException {

        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        // read every line in the text file into the list
        BufferedReader reader = new BufferedReader( new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            // add every line except for comments
            if (!line.startsWith("#")) {
                lines.add(line);
                width = Math.max(width, line.length());
            }
        }

        // parse the lines to create a TileEngine
        height = lines.size();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        
        for (int y=0; y<height; y++) {
            String line = (String)lines.get(y);
            for (int x=0; x<line.length(); x++) {
                char ch = line.charAt(x);
                if (ch == 'Z') {
                	this.setTile(x, y, toolkit.getImage("black.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'A') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/LR_Wall.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'B') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/T_North.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'C') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/T_Right.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'D') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/T_South.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'E') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/RS_Corner.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'F') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/NS_Wall.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'G') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/NS_Wall.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'H') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/LR_Wall.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'I') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/LN_Corner.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'J') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/NS_Wall.jpg") );
                	this.setCharTile(x, y, "x");
                	
                }
                if (ch == 'K') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/NR_Corner.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'L') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/LR_Wall.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'M') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/RS_Corner.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'N') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/LS_Corner.jpg") );
                	this.setCharTile(x, y, "x");
                }
                
                if (ch == 'P') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/R_End.jpg") );
                	this.setCharTile(x, y, "x");                	
                }
                
                if (ch == 'Q') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/N_End.jpg") );
                	this.setCharTile(x, y, "x");                	
                }
                
                if (ch == 'R') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/L_End.jpg") );
                	this.setCharTile(x, y, "x");                	
                }
                
                if (ch == 'T') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/S_End.jpg") );
                	this.setCharTile(x, y, "x");                	
                }
                
                if (ch == 'S') {
                	this.setTile(x, y, toolkit.getImage("stairs.png") );
                	this.setCharTile(x, y, " ");
                }
                
                if (ch == 'U') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/T_Left.jpg") );
                	this.setCharTile(x, y, "x");
                }
                
                if (ch == 'V') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/Cross.jpg") );
                	this.setCharTile(x, y, "x");
                }
                
                if (ch == 'W') {
                	
                	int type = Math.abs(generator.nextInt(3));
                	if (type == 1){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NR_Angle_Wall_Left1.jpg") );}
                	else if (type == 2){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NR_Angle_Wall_Left2.jpg") );}
                	else if (type == 3){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NR_Angle_Wall_Left3.jpg") );}
                	else {
                		this.setTile(x, y, toolkit.getImage("BuildImg/NR_Angle_Wall_Left1.jpg") );}

                    this.setCharTile(x, y, "x");                   
                  
                    }
                
                if (ch == 'X') {
                	
                	int type = Math.abs(generator.nextInt(3));
                	if (type == 1){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NR_Angle_Wall1.jpg") );}
                	else if (type == 2){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NR_Angle_Wall2.jpg") );}
                	else if (type == 3){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NR_Angle_Wall3.jpg") );}
                	else {
                		this.setTile(x, y, toolkit.getImage("BuildImg/NR_Angle_Wall1.jpg") );}

                    this.setCharTile(x, y, "x");                   
                  
                    }
                
                if (ch == 'Y') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/NR_Angle_Wall_End.jpg") );
                	this.setCharTile(x, y, "x");
                }
                
                if (ch == 'a') {
                	this.setTile(x, y, toolkit.getImage("BuildImg/fill.jpg") );
                	this.setCharTile(x, y, "x");
                }
                
                if (ch == 'b') {
                	
                	int type = Math.abs(generator.nextInt(3));
                	if (type == 1){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NL_Angle_Wall_Left1.jpg") );}
                	else if (type == 2){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NL_Angle_Wall_Left2.jpg") );}
                	else if (type == 3){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NL_Angle_Wall_Left3.jpg") );}
                	else {
                		this.setTile(x, y, toolkit.getImage("BuildImg/NL_Angle_Wall_Left1.jpg") );}

                    this.setCharTile(x, y, "x");                   
                  
                    }

                if (ch == 'c') {
	
                	int type = Math.abs(generator.nextInt(3));
                	if (type == 1){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NL_Angle_Wall1.jpg") );}
                	else if (type == 2){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NL_Angle_Wall2.jpg") );}
                	else if (type == 3){
                		this.setTile(x, y, toolkit.getImage("BuildImg/NL_Angle_Wall3.jpg") );}
                	else {
                		this.setTile(x, y, toolkit.getImage("BuildImg/NL_Angle_Wall1.jpg") );}

                	this.setCharTile(x, y, "x");                   
  
                }
                
    
                 // create Tiles to contain Items
                if (ch == '!') {
                	
                	int type = Math.abs(generator.nextInt(3));
                	if (type == 0){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor1.jpg") );}
                	else if (type == 1){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor2.jpg") );}
                	else if (type == 2){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor3.jpg") );}
                	else {
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor4.jpg") );}

                	
                	int Itemtype = Math.abs(generator.nextInt(1));
                	if (Itemtype == 0){
                		Item item = new Item("ammo", 20,x*25, y*25);
                		ItemList.add(item);
                	}
                	if (type == 1){
                		Item item = new Item("health", 10,x*25, y*25);
                		ItemList.add(item);
                	}	
                	this.setCharTile(x, y, " ");
                }
                
                if (ch == '#') {
                	
                	int type3 = Math.abs(generator.nextInt(3));
                	if (type3 == 1){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor1.jpg") );}
                	else if (type3 == 2){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor2.jpg") );}
                	else if (type3 == 3){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor3.jpg") );}
                	else {
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor4.jpg") );}

                	
                	int Itemtype2 = Math.abs(generator.nextInt(3));
                	System.out.println(Itemtype2);
                	if (Itemtype2 == 0){
                		Item item = new Item("points10", 10,x*25, y*25);
                		ItemList.add(item);
                	}
                	else if (Itemtype2 == 1){
                		Item item = new Item("points10", 20,x*25, y*25);
                		ItemList.add(item);
                	}	
                	
                	else if (Itemtype2 == 2){
                		Item item = new Item("points20", 50,x*25, y*25);
                		ItemList.add(item);
                	}	
                	this.setCharTile(x, y, " ");
                }
                
                if (ch == '$') {
                	
                	int type4 = Math.abs(generator.nextInt(4));
                	if (type4 == 1){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor1.jpg") );}
                	else if (type4 == 2){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor2.jpg") );}
                	else if (type4 == 3){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor3.jpg") );}
                	else {
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor4.jpg") );}

                	
                
               		Item item = new Item("points50", 10,x*25, y*25);
               		ItemList.add(item);
                	
                	this.setCharTile(x, y, " ");
                }
                            
                if (ch == '1') {
                	this.setTile(x, y, toolkit.getImage("stairs.png") );
                	this.setCharTile(x, y, "1");
                }
                
                if (ch == '2') {
                	this.setTile(x, y, toolkit.getImage("stairs.png") );
                	this.setCharTile(x, y, "2");
                }
                if (ch == ' ') {
                	
                	int type = Math.abs(generator.nextInt(3));
                	if (type == 1){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor1.jpg") );}
                	else if (type == 2){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor2.jpg") );}
                	else if (type == 3){
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor3.jpg") );}
                	else {
                		this.setTile(x, y, toolkit.getImage("BuildImg/floor4.jpg") );}

                    this.setCharTile(x, y, " ");                   
                  
                    }
                }
        }
    }

    /**
	 * Gets the character for a Character Tile
	 * @return charMap[x][y]
	 */
	public String getCharTile(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()){
            return "x";
        }else{
        	return charMap[x][y];
        }
	}
	
	/**
	 * Sets the Player Position on the Map based on the tile map
	 */
	public void setPlayerOnePosition(){
		for ( int x = 0; x<=getWidth(); x++)
			for (int y = 0; y<=getHeight(); y++){
				if (getCharTile(x,y) == "*"){
					((Player) PlayerList.get(0)).setPlayerStartPosition(x*25,y*25);
					return;}
								
			}
	}
	
    

	/**
	 * Sets the character for a Character Tile
	 * 
	 */
	public void setCharTile(int x, int y, String string) {
		charMap[x][y] = string;
	}
}
