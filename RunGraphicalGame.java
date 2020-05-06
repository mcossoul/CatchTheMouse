import processing.core.*;

public class RunGraphicalGame extends PApplet {
	GameBoard game;
	Display display;

	public void settings() {
		size(640, 550);
	}

	public void setup() {
		// Create a game object
		game = new GameBoard(11, 11);

		// Create the display
		// parameters: (10,10) is upper left of display
		// (400, 400) is the width and height
		display = new Display(this, 55, 10, 400, 400);

		// You can use images instead if you'd like.
		 display.setImage(1, "assets/mouse.png");
		 display.setImage(2, "assets/wall.png");

		display.initializeWithGame(game);
	}

	@Override
	public void draw() {
		background(200);
		display.drawGrid(game.getGrid()); // display the game
	}

	public void mouseReleased() {
		Location loc = display.gridLocationAt(mouseX, mouseY);
		int row = loc.getRow();
		int col = loc.getCol();

		game.move(row, col);
	}

	// main method to launch this Processing sketch from computer
	public static void main(String[] args) {
		PApplet.main(new String[] { "RunGraphicalGame" });
	}
}