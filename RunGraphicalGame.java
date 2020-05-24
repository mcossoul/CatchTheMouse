import processing.core.*;

public class RunGraphicalGame extends PApplet {
	int num_players = 1;
	GameBoard game;
	Display display;
	private boolean mouse_turn = false;

	public void settings() {
		size(715, 635);
	}

	public void setup() {
		game = new GameBoard(13, 13);

		display = new Display(this, 30, 35, 550, 550);
		// TODO replace grid squares w/ hexagons
		display.setImage(1, "assets/mouse.png");
		display.setImage(2, "assets/cheese.png");
		display.setImage(4, "assets/wall.png"); // wall
		display.initializeWithGame(game);
	}

	@Override
	public void draw() {
		background(200);
		if ( game.isGameOver() ) {
			display.drawInsideGrid(game.getGrid()); // display the game
			System.out.println("You lost! Try again.");
			super.stop();
		} else {
			display.drawInsideGrid(game.getGrid()); // display the game
		}
	}

	public void mouseReleased() {
		Location loc = display.gridLocationAt(mouseX, mouseY);
		int row = loc.getRow();
		int col = loc.getCol();

		if (num_players == 2 && mouse_turn) {
			game.moveMouse(row, col);
			mouse_turn = !mouse_turn;
		}
		else {
			game.addWall(row, col);
			if (num_players ==1) { game.moveMouse(); }
			mouse_turn = !mouse_turn;
		}
	}

	// main method to launch this Processing sketch from computer
	public static void main(String[] args) {
		PApplet.main(new String[] { "RunGraphicalGame" });
	}
}