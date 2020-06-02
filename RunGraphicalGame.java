import processing.core.*;

public class RunGraphicalGame extends PApplet {
	protected GameBoard game;
	protected Display display;
	protected Menu menu;

	private final int SCREEN_PLAYERS = 1;
	private final int SCREEN_LEVELS  = 2;
	private final int SCREEN_GAME    = 3;

	private int num_players;
	private int screen = SCREEN_PLAYERS;
	private boolean mouse_turn;
	private boolean playAgain;
	private int score;

	public void settings() {
		size(715, 670);
	}

	public void setup() {
		game = new GameBoard(13, 13);
		game.setDebug(false);
		menu = new Menu(this);
		display = new Display(this, 30, 65, 550, 550);
		display.setDebug(false);
		display.setImage(Const.PIECE_TITLE, "assets/catch_the_mouse.png");
		display.setImage(Const.PIECE_MOUSE, "assets/mouse.png");
		display.setImage(Const.PIECE_CHEESE, "assets/cheese.png");
		display.initializeWithGame(game);

		mouse_turn = false;
		score = 20000;
	}

	@Override
	public void draw() {
		background(200);
		menu.buttonQuit.draw();

		if ( screen == SCREEN_PLAYERS ) {
			image( display.getImage(Const.PIECE_TITLE), 175, 0, 350, 350);
			menu.button1P.draw();
			menu.button2P.draw();
		}
		else if ( screen == SCREEN_LEVELS ) {
			image( display.getImage(Const.PIECE_TITLE), 175, 0, 350, 350);
			menu.buttonL1.draw();
			menu.buttonL2.draw();
			menu.buttonL3.draw();
		}
		else { // SCREEN_GAME
			if ( game.isGameOver() == Const.WIN_NONE ) { // game is not over
				if (num_players == 1) {
					menu.messageScore.setText("Score: " + score);
					menu.messageScore.draw();
				}
				else { // 2 players
					if (mouse_turn) menu.messageTurnMouse.draw();
					else 			menu.messageTurnWalls.draw();
				}

				if (playAgain) menu.messageBadMove.draw();
			}
			else { // Game over
				menu.buttonNewGame.draw();
				if (num_players == 1) {
					if (game.isGameOver() == Const.WIN_MOUSE) {
						menu.messageWinMouse.draw();
						score = 0;
						menu.messageScore.setText("Score: " + score);
					} else { // Walls win
						menu.messageWinWalls.draw();
					}
					menu.messageScore.draw();
				}
				else { // 2 players
					if (game.isGameOver() == Const.WIN_MOUSE) menu.messageWinMouse.draw();
					else menu.messageWinWalls.draw();
				}
			}

			display.drawInsideGrid( game.getGrid() ); // display the game
		}
	}

	public void mouseReleased() {
		if ( menu.buttonQuit.isPressed( mouseX, mouseY ) ) {
			System.exit(0);
		}

		if ( screen == SCREEN_PLAYERS ) {
			if ( menu.button1P.isPressed(mouseX, mouseY) ) {
				num_players = 1;
				screen = SCREEN_LEVELS;
			}
			if ( menu.button2P.isPressed(mouseX, mouseY) ) {
				num_players = 2;
				screen = SCREEN_LEVELS;
			}
		}
		else if ( screen == SCREEN_LEVELS ) {
			if ( menu.buttonL1.isPressed(mouseX, mouseY) ) {
				game.reset(12);
				screen = SCREEN_GAME;
			}
			if ( menu.buttonL2.isPressed(mouseX, mouseY) ) {
				game.reset(8);
				screen = SCREEN_GAME;
			}
			if ( menu.buttonL3.isPressed(mouseX, mouseY) ) {
				game.reset(4);
				screen = SCREEN_GAME;
			}
		}
		else { // SCREEN_GAME
			playAgain = false;

			if ( game.isGameOver() == Const.WIN_NONE ) {
				Location loc = display.gridLocationAt(mouseX, mouseY);
				int row = loc.getRow();
				int col = loc.getCol();

				if (num_players == 1) {
					if (!game.addWall(row, col)) {
						playAgain = true;
					} else {
						game.mouse.move();
						score -= 500;
					}
				} else {  // 2 players
					if (mouse_turn) {
						if (!game.mouse.move(row, col)) {
							playAgain = true;
						} else {
							mouse_turn = !mouse_turn;
						}
					} else { // Walls' turn
						if (!game.addWall(row, col)) {
							playAgain = true;
						} else {
							mouse_turn = !mouse_turn;
						}
					}
				}
			}

			if ( game.isGameOver() != Const.WIN_NONE ) {  // game over
				if ( menu.buttonNewGame.isPressed( mouseX, mouseY ) ) {
					setup();
					screen = SCREEN_PLAYERS;
				}
			}
		}
	}

	// main method to launch this Processing sketch from computer
	public static void main(String[] args) {
		PApplet.main(new String[] { "RunGraphicalGame" });
	}
}