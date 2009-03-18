package Model;


import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class TileMap {
	private Image[][] Map;
	private String[][] charMap;
    public ArrayList <Sprite> SpriteList;
    public ArrayList <Sprite> PlayerList;
    public ArrayList <Sprite> [][] Grid;
    public int width;
    public int height;
    
    public TileMap(int width, int height, String file) throws IOException {
        Map = new Image[width][height];
        SpriteList = new ArrayList <Sprite>();
        PlayerList = new ArrayList <Sprite>();
        //Grid = new ArrayList [width][height];
        charMap = new String[width][height];
        loadMap(file);
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    public Image getTile(int x, int y) {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()){
            return null;
        }else{
        	return Map[x][y];
        }
    }

    public void setTile(int x, int y, Image tile) {
        Map[x][y] = tile;
    }

    public void addToGrid(int x, int y, Sprite sprite) {
    	Grid[x][y].add(sprite);
    }
    
    public void removeFromGrid(int x, int y, Sprite sprite) {
        Grid[x][y].remove(sprite);
    }
    
    public void addSprite(Sprite sprite) {
        SpriteList.add(sprite);
    }

    public void removeSprite(Sprite sprite) {
        SpriteList.remove(sprite);
    }
    
    
    public void addPlayer(Sprite sprite) {
        PlayerList.add(sprite);
    }

    public void removePlayer(Sprite sprite) {
        PlayerList.remove(sprite);
    }
    
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
                if (ch == 'A') {
                	this.setTile(x, y, toolkit.getImage("innerwall_TB.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'B') {
                	this.setTile(x, y, toolkit.getImage("cornerTL.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'C') {
                	this.setTile(x, y, toolkit.getImage("cornerTR.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'D') {
                	this.setTile(x, y, toolkit.getImage("cornerBL.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'E') {
                	this.setTile(x, y, toolkit.getImage("cornerBR.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'F') {
                	this.setTile(x, y, toolkit.getImage("wall_T.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'G') {
                	this.setTile(x, y, toolkit.getImage("wall_B.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'H') {
                	this.setTile(x, y, toolkit.getImage("wall_R.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'I') {
                	this.setTile(x, y, toolkit.getImage("innerwall_TL.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'J') {
                	this.setTile(x, y, toolkit.getImage("innerwall_LR.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'K') {
                	this.setTile(x, y, toolkit.getImage("innerwall_TR.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'L') {
                	this.setTile(x, y, toolkit.getImage("wall_L.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'M') {
                	this.setTile(x, y, toolkit.getImage("innerwall_BR.jpg") );
                	this.setCharTile(x, y, "x");
                }
                if (ch == 'N') {
                	this.setTile(x, y, toolkit.getImage("innerwall_BL.jpg") );
                	this.setCharTile(x, y, "x");
                }
                
                if (ch == '*') {
                	this.setTile(x, y, toolkit.getImage("white.jpg") );
                	this.setCharTile(x, y, "*");
                }
                if (ch == 'Z') {
                	System.out.print("Z");
                	this.setTile(x, y, toolkit.getImage("black.jpg") );
                	this.setCharTile(x, y, "Z");
                }
                           
                if (ch == '1') {
                	this.setTile(x, y, toolkit.getImage("black.jpg") );
                	this.setCharTile(x, y, "1");
                }
                if (ch == '2') {
                	this.setTile(x, y, toolkit.getImage("black.jpg") );
                	this.setCharTile(x, y, "2");
                }
                if (ch == ' ') {
                    this.setTile(x, y, toolkit.getImage("white.jpg") );
                    this.setCharTile(x, y, " ");
                }
            }
        }
    }

	public String getCharTile(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()){
            return "x";
        }else{
        	return charMap[x][y];
        }
	}
	
	public void setPlayerOnePosition(){
		for ( int x = 0; x<=getWidth(); x++)
			for (int y = 0; y<=getHeight(); y++){
				if (getCharTile(x,y) == "*"){
					System.out.println(x + "  " + y);
					((Player) PlayerList.get(0)).setPlayerStartPosition(x*25,y*25);
					return;}
								
			}
	}
	
    


	public void setCharTile(int x, int y, String string) {
		charMap[x][y] = string;
	}
}
