import javax.swing.ImageIcon;

import java.awt.event.KeyEvent;

public class Player extends Sprite implements Commons{
    private int speed = 4;

    public static final int HEIGHT  = 30;
    public static final int WIDTH   = 10;

    private Boolean up = false;
    private Boolean down = false;

    private int HEALTH  = 100;

    // Constructor
    public Player(int x, int y) {
        setX(x);
        setY(y);

        ImageIcon ii = new ImageIcon(this.getClass().getResource("char.png"));
        setImage(ii.getImage());
    }

    public int getHealth() {
        return HEALTH;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void setSpeed(int inSpeed) {
        speed = inSpeed;
    }

    public void removeHealth(int deduct) {
        HEALTH -= deduct;
    }

    public void addHealth(int add) {
        HEALTH += add;
    }

    public void move() {
        if(up) {
            checkCollision();
            int y = getY();
            setY(y-=speed);
        } else if(down) {
            checkCollision();
            int y = getY();
            setY(y+=speed);
        }
    }

    public void checkCollision() {
        if(getX() <= 0) {
            setX(0);
        }
        if(getX() >= (BOARD_WIDTH - WIDTH)) {
            setX(BOARD_WIDTH - WIDTH);
        }
        if(getY() <= 0) {
            setY(1);
        }
        if(getY() >= (BOARD_HEIGHT - HEIGHT - 30)){
            setY(BOARD_HEIGHT - HEIGHT - 30);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP) {
            up = true;
            down = false;
        } else if(key == KeyEvent.VK_DOWN) {
            down = true;
            up = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP) {
            up = false;
            down = false;
        } else if(key == KeyEvent.VK_DOWN) {
            down = false;
            up = false;
        }
    }
}