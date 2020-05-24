import java.util.LinkedHashMap;
import java.util.Map;

import processing.core.*;

public class Display {
    private PApplet p; // the applet we want to display on

    private int x, y, w, h; // (x, y) of upper left corner of display, the width and height of the display
    private int dx, x_shift, dy; // calculate the width and height of each box
    private int rows, cols;

    // A map for storing colors for participants in the simulation
    private Map<Object, PImage> images;

    // (x, y) is the upper-left corner of the display in pixels
    // w and h are the width and height of the display in pixels
    public Display(PApplet p, int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.p = p;

        images = new LinkedHashMap<Object, PImage>();
    }

    /*
     * Don't draw 1 row/col around the grid (so mouse can move out of it)
     */
    public void drawInsideGrid(int[][] f) {
        int piece, hex_x;
        int img_dx = -dx/2; // origin shift (image top left, hexagon center)
        int img_dy = -dy/2;

        for (int r = 0; r < rows; r++) {
            hex_x = ( r%2 == 1 ) ? x + x_shift : x;
            for (int c = 0; c < cols; c++) {
                piece = f[r][c];
                if (piece == 1) { // mouse
                    PImage pieceImage = getImage(piece);
                    draw_hexagon( false, hex_x + c*dx, y + r*dy, dx ); // draw empty hexagon on top of it
                    p.image(pieceImage, hex_x + img_dx + c*dx, y + img_dy + r*dy, dx, dy);
                } else if (piece == 2) { // cheese
                    PImage pieceImage = getImage(piece);
                    p.image(pieceImage, hex_x + img_dx + c*dx, y + img_dy + r*dy, dx, dy);
                } else if (piece == 4) { // wall
                    draw_hexagon( true, hex_x + c*dx, y + r*dy, dx );
                } else if ( r > 0 && r < rows-1 && c > 0 && c < cols-1 ){ // empty space
                    draw_hexagon( false, hex_x + c * dx, y + r * dy, dx );
                }
            }
        }
    }

    void draw_hexagon(boolean fill, int x, int y, int diam) {
        diam += 8;
        int hex_dx = (int)( 0.433 * diam );
        int hex_dy = (int)( 0.25 * diam );

//        p.background(51);
        if (fill) {
            p.fill(196, 85, 26);
        } else {
            p.fill(100,255,100);
        }
        p.stroke(0);
        p.strokeWeight(1);
        p.beginShape();
        p.vertex(x         , y - 2*hex_dy );
        p.vertex(x + hex_dx, y - hex_dy );
        p.vertex(x + hex_dx, y + hex_dy );
        p.vertex(x         , y + 2*hex_dy );
        p.vertex(x - hex_dx, y + hex_dy );
        p.vertex(x - hex_dx, y - hex_dy );
        p.vertex(x         , y - 2*hex_dy );
        p.endShape();
    }

    /**
     * Define an Image to be used for a given value in the grid.
     *
     * @param pieceType The type of piece in the grid.
     * @param img       The image to be used for the given type of piece.
     */
    public void setImage(Object pieceType, PImage img) {
        images.put(pieceType, img);
    }

    /**
     * Define a color to be used for a given value in the grid.
     *
     * @param pieceType The type of piece in the grid.
     * @param filename  The file path to the image to be used for the given type of piece.
     */
    public void setImage(Object pieceType, String filename) {
        PImage img = p.loadImage(filename);
        setImage(pieceType, img);
    }

    private PImage getImage(Object pieceType) {
        PImage img = images.get(pieceType);
        return img;
    }

    // Return location at coordinates x, y on the screen
    public Location gridLocationAt(float mousex, float mousey) {
        int hex_x = 0;
        for (int r = 0; r < rows; r++) {
            hex_x = (r % 2 == 1) ? x + x_shift : x;
            for (int c = 0; c < cols; c++) {
                if (distance((int)(mousex), (int)(mousey), hex_x + c * dx, y + r * dy) < dx * 0.433) {
                    return new Location(r, c);
                }
            }
        }
        return new Location(-1 , -1);

//        int row = (int) Math.floor( (mousey - y) / dy );
//
//        float shift = (row%2 == 1) ? x_shift : 0;
//        int col = (int) Math.floor( (mousex - (x + shift)) / dx );
    }

    private double distance(int x0, int y0, int x1, int y1) {
        return Math.sqrt( ( Math.pow(x0-x1, 2) + Math.pow(y0-y1, 2) ));
    }

    public void setNumCols(int numCols) {
        cols = numCols;
        dx = 10 + w / cols;
        x_shift = (int)( 0.5 * dx );
    }

    public void setNumRows(int numRows) {
        rows = numRows;
        dy = 5  + h / rows;
    }

    public void initializeWithGame(GameBoard game) {
        int[][] grid = game.getGrid();
        if (grid == null) {
            System.out.println("Your 2d int array grid is null!  Create it by saying grid = new int[___][___] inside your constructor!");
        }
        setNumRows( grid.length );
        setNumCols( grid[0].length );
        System.out.println("Setting display: # rows is " + grid.length + ", # cols is " + grid[0].length);
    }
}