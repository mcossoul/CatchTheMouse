import processing.core.*;

public class RunGraphicalGame extends PApplet {
	GameBoard game;
	Display display;

	public void settings() {
		size(640, 550);
	}

	public void setup() {
		// Create a game object
		game = new GameBoard(5, 5);

		// Create the display
		// parameters: (10,10) is upper left of display
		// (300, 300) is the width and height
		display = new Display(this, 10, 10, 400, 400);

		display.setColor(1, color(255, 0, 0)); // SET COLORS FOR PLAYER 1 & 2
		display.setColor(2, color(0, 255, 0));

		// You can use images instead if you'd like.
		// d.setImage(1, "c:/data/ball.jpg");
		// d.setImage(2, "c:/data/cone.jpg");

		display.initializeWithGame(game);
	}

	@Override
	public void draw() {
		background(200);

		display.drawGrid(game.getGrid()); // display the game

	}

	public void mouseClicked() {
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