import java.rmi.activation.UnknownObjectException;
import java.util.LinkedHashMap;
import java.util.Map;

import processing.core.*;

public class Display {
    // Colors used for empty locations.
    private static int EMPTY_COLOR;

    // Color used for objects that have no defined color.
    private static int UNKNOWN_COLOR;

    private PApplet p; // the applet we want to display on

    private int x, y, w, h; // (x, y) of upper left corner of display
    // the width and height of the display

    private float dx, x_shift, dy; // calculate the width and height of each box
    // in the field display using the size of the field
    // and the width and height of the display

    private int rows, cols;

    // A map for storing colors for participants in the simulation
    private Map<Object, Integer> colors;
    private Map<Object, PImage> images;

    // (x, y) is the upper-left corner of the display in pixels
    // w and h are the width and height of the display in pixels
    public Display(PApplet p, int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.p = p;

        EMPTY_COLOR = p.color(0, 0, 0, 0);
        UNKNOWN_COLOR = p.color(200, 200, 200);

        colors = new LinkedHashMap<Object, Integer>();
        images = new LinkedHashMap<Object, PImage>();
    }

    /*
     * Don't draw 1 row/col around the grid (so mouse can move out of it)
     */
    public void drawInsideGrid(int[][] f) {
        int piece;
        int numcols = f[0].length;
        int numrows = f.length;

        for (int i = 0; i < numrows; i++) {
            for (int j = 0; j < numcols; j++) {
                piece = f[i][j];
                int pieceColor = getColor(piece);
                PImage pieceImage = getImage(piece);

                float shift = (i%2 == 1) ? x_shift : 0;

                if (pieceImage != null) {
                    p.image(pieceImage, x + shift + j * dx, y + i * dy, dx, dy);
                } else if ( i > 0 && i < numrows-1 && j > 0 && j < numcols-1 ){
                    p.fill(getColor(piece));
                    p.rect(x + shift + j * dx, y + i * dy, dx, dy);
                }
            }
        }
    }

    /**
     * Define a color to be used for a given value in the grid.
     *
     * @param pieceType The type of piece in the grid.
     * @param color     The color to be used for the given type of piece.
     */
    public void setColor(Object pieceType, Integer color) {
        colors.put(pieceType, color);
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

    /**
     * @return The color to be used for a given class of animal.
     */
    private Integer getColor(Object pieceType) {
        Integer col = colors.get(pieceType);
        if (col == null) { // no color defined for this class
            return UNKNOWN_COLOR;
        } else {
            return col;
        }
    }

    private PImage getImage(Object pieceType) {
        PImage img = images.get(pieceType);
        return img;
    }

    // return the y pixel value of the upper-left corner of location l
    private float yCoordOf(Location l) {
        return y + l.getRow() * dy;
    }

    // return the x pixel value of the upper-left corner of location l
    private float xCoordOf(Location l) {
        float shift = ( l.getRow()%2 == 1 ) ? x_shift : 0;
        return x + shift + l.getCol() * dx;
    }

    // Return location at coordinates x, y on the screen
    public Location gridLocationAt(float mousex, float mousey) {
        int row = (int) Math.floor( (mousey - y) / dy );

        float shift = (row%2 == 1) ? x_shift : 0;
        int col = (int) Math.floor( (mousex - (x + shift)) / dx );

        Location l = new Location(row , col);
        return l;
    }

    public void setNumCols(int numCols) {
        rows = numCols;
        dx = w / rows;
    }

    public void setNumRows(int numRows) {
        cols = numRows;
        dy = h / cols;
    }

    public void initializeWithGame(GameBoard game) {
        int[][] grid = game.getGrid();
        if (grid == null) {
            System.out
                    .println("Your 2d int array grid is null!  Create it by saying grid = new int[___][___] inside your constructor!");
        }
        setNumCols(grid[0].length);
        setNumRows(grid.length);
        this.x_shift = dx/2;
        System.out.println("Setting disply: # rows is " + grid.length + ", # cols is " + grid[0].length);
    }
}