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
    public int width;
    public int height;
    
    public TileMap(int width, int height, String file) throws IOException {
        Map = new Image[width][height];
        SpriteList = new ArrayList <Sprite>();
        PlayerList = new ArrayList <Sprite>();
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
        BufferedReader reader = new BufferedReader( new FileReader("EngSecondFloor.txt"));
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
                	this.setTile(x, y, toolkit.getImage("black.jpg") );
                	this.setCharTile(x, y, "x");
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
    


	public void setCharTile(int x, int y, String string) {
		charMap[x][y] = string;
	}
}
