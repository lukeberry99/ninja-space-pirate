import javax.swing.ImageIcon;

public class Bullet extends Sprite implements Commons {

    private static final int SPEED = 4;

    public boolean isFired = false;
    public boolean moving = false;
    public boolean hit = false;


    public Bullet(int x, int y) {
        ImageIcon iib = new ImageIcon(this.getClass().getResource("bullet.png"));

        setX(x);
        setY(y);
        setImage(iib.getImage());

        setVisible(false);
    }

    public void destroy() {
        this.setVisible(false);
        this.moving = false;
        this.setX(-100);
        this.setY(-100);
        this.hit = true;
    }

    public void fire() {
        if(isFired && moving) {
            setVisible(true);
            int x = getX();
            setX(x+=SPEED);
            checkIt();
        }
    }

    private void checkIt() {
        int x = getX();
        if(x > BOARD_WIDTH) {
            moving = false;
            setVisible(false);
            setX(-100);
            setY(-100);
        }
    }
}