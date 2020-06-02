import processing.core.PApplet;

public class Message {
    private PApplet p;

    private String text;
    private float x, y, width, height;

    public Message(PApplet p, String text, float x, float y) {
        this.p = p;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = text.length() * 5;
        this.height = 30;

        p.textSize(20);
        p.stroke(150);
    }

    public void draw() {
        p.fill(0);
        p.text(text, x, y );
    }

    public void setText(String text) {
        this.text = text;
    }
}
