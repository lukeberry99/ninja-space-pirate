import java.awt.Image;

public class Sprite implements Commons {
    private boolean visible;
    private Image image;
    protected int x;
    protected int y;
    protected boolean dying;

    public Sprite() {
        visible = true;
    }

    public void die() {
        visible = false;
    }

    // Accessors
    public boolean isVisible() {
        return this.visible;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public Image getImage() {
        return this.image;
    }
    public boolean isDying() {
        return this.dying;
    }

    // Mutators
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public void setDying(boolean dying) {
        this.dying = dying;
    }
}