import processing.core.PApplet;

public class Menu {
    protected Button button1P, button2P, buttonL1, buttonL2, buttonL3, buttonNewGame, buttonQuit;
    protected Message messageScore, messageTurnMouse, messageTurnWalls, messageWinMouse, messageWinWalls, messageBadMove;

    public Menu(PApplet p) {
        // Screen: SCREEN_PLAYERS
        button1P = new Button(p, " 1 Player", 200, 320, 100, 30);
        button2P = new Button(p, "2 Players", 400, 320, 100, 30);

        // Screen: SCREEN_LEVELS
        buttonL1 = new Button(p, "   Easy", 150, 320, 100, 30);
        buttonL2 = new Button(p, " Medium", 300, 320, 100, 30);
        buttonL3 = new Button(p, "   Hard", 450, 320, 100, 30);

        // Screen: SCREEN_GAME
        buttonNewGame    = new Button(p, " Play again", 450, 7, 120, 30);
        buttonQuit       = new Button(p, "     Quit  ", 580, 7, 120, 30);
        messageScore     = new Message(p, "Score: ", 10, 30);
        messageTurnMouse = new Message(p, "Mouse's turn", 10, 30);
        messageTurnWalls = new Message(p, "Walls' turn", 10, 30);
        messageBadMove   = new Message(p, "Can't play there. Try again.", 160, 30);
        messageWinMouse  = new Message(p, "The mouse gets the cheese!", 160, 30);
        messageWinWalls  = new Message(p, "You caught the mouse!", 160, 30);
    }
}