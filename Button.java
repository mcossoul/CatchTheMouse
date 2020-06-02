import processing.core.PApplet;

public class Button {
    private PApplet p;
    private String text;
    private float x, y, width, height;
    private boolean debug = false;

    public Button(PApplet p, String text, float x, float y, float width, float height) {
        this.p = p;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        p.textSize(20);
    }

    public void draw() {
        p.fill(150);
        p.rect(x, y , width, height);
        p.fill(0);
        p.text(text, x+5, y+23 );
    }

    public boolean isPressed(float mouseX, float mouseY) {
        if ( mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY <= y+height ) {
            if (debug) System.out.println("[DEBUG] Button pressed: " + text);
            return true;
        }
        return false;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
