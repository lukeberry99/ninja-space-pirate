import javax.swing.ImageIcon;

public class Enemy extends Sprite implements Commons {
    public static final int HEIGHT = 30;
    public static final int WIDTH = 10;

    private int SPEED = 1;

    public boolean moving = true;
    public boolean outside = false;

    public Enemy(int x, int y) {
        ImageIcon iie = new ImageIcon(this.getClass().getResource("enemy.png"));

        setImage(iie.getImage());
        setX(x);
        setY(y);
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void move() {
        if(moving) {
            outside();
            int currX = getX();
            setX(currX-=SPEED);
        }
    }

    public void outside() {
        int x = getX();
        if(x < 0){
            this.x = -100;
            y = -100;
            moving = false;
            setDying(true);
            outside = true;
        }
    }

    public void die() {
        setDying(true);
        moving = false;
        x = -100;
        y = -100;
        this.setVisible(false);
    }

    public void ifHit(Bullet theBullet) {

        int bullX = theBullet.getX();
        int bullY = theBullet.getY();

        if(bullY > this.y && bullY <= (this.y+this.HEIGHT)) {
            if(bullX > this.x && bullX <= (this.x+this.WIDTH)) {
                this.die();
                theBullet.destroy();
            }
        }
    }
}