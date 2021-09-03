import java.awt.Rectangle;

public class BodyLink {
    int x, y;
    Rectangle boundingBox;

    public BodyLink(int x, int y) {
        this.x = x;
        this.y = y;
        boundingBox = new Rectangle(this.x, this.y, 50, 50);
    }
    public void move() {
        this.boundingBox.x = this.x;
        this.boundingBox.y = this.y;
    }
}
