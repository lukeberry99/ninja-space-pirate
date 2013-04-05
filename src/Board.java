import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel implements ActionListener, Commons{
    private Player thePlayer;

    private Image background;

    private List<Enemy> enemiesList =  new ArrayList<Enemy>();
    private List<Bullet> bulletList = new ArrayList<Bullet>();

    private boolean gameStart = false;

    private Timer timer;

    public Board() {
        ImageIcon iib = new ImageIcon(this.getClass().getResource("bg.png"));
        background = iib.getImage();

        addKeyListener(new TAdapter());
        thePlayer = new Player(15,15);

        generateEnemies();
        setBackground(Color.BLACK);
        setFocusable(true);
        startGame();
    }

    public void startGame() {
        timer = new Timer(DELAY, this);
        timer.start();
        gameStart = true;
    }

    public void restartGame() {
        enemiesList.clear();
        bulletList.clear();
        thePlayer.setY(15);
        thePlayer.setSpeed(4);
        generateEnemies();
        gameStart = true;
    }

    public void debugGame() {
        gameStart = true;
        thePlayer.setSpeed(1);
        enemiesList.clear();
        bulletList.clear();
        enemiesList.add(new Enemy(BOARD_WIDTH /2, BOARD_HEIGHT /2));
        enemiesList.add(new Enemy(BOARD_WIDTH /2, BOARD_HEIGHT /2 + 60));
        for(Enemy theEnemy : enemiesList) {
            theEnemy.moving = false;
        }
    }

    public void generateEnemies(){
        int maxX = 1500;
        int minX = 800;

        int maxY = 545;
        int minY = 15;

        Random rnd = new Random();
        int amountOfEnemies = rnd.nextInt(100 - 30 + 1) + 30;
        for(int i = 0; i < amountOfEnemies; i++) {
            int x = rnd.nextInt(maxX - minX + 1) + minX;
            int y = rnd.nextInt(maxY - minY + 1) + minY;
            for(Enemy theEnemy : enemiesList) {
                if(theEnemy.getX() >= x && theEnemy.getX() <= x+10) {

                }
            }
            enemiesList.add(new Enemy(x+15, y));
        }
    }

    public void drawPlayer(Graphics g) {
        if(thePlayer.isVisible())
            g.drawImage(thePlayer.getImage(), thePlayer.getX(), thePlayer.getY(), this);
    }

    public void drawBullet(Graphics g) {
        for(Bullet theBullet : bulletList) {
            if(theBullet.isVisible())
                g.drawImage(theBullet.getImage(), theBullet.getX(), theBullet.getY(), this);
        }
    }

    public void drawEnemy(Graphics g) {
        for(Enemy currEnemy: enemiesList) {
            if(currEnemy.isVisible())
                g.drawImage(currEnemy.getImage(), currEnemy.getX(), currEnemy.getY(), this);
        }
    }

    public void drawWords(Graphics g) {
        int aliveEnemies = 0;
        int outsideEnemies = 0;
        int iScore = 0;

        for(Enemy theEnemy : enemiesList) {
            if(!theEnemy.isDying()) {
                aliveEnemies++;
            }
            if(theEnemy.isDying())
                iScore += 100;
            if(theEnemy.outside) {
                outsideEnemies++;
                iScore-=100;
            }
        }

        String alive = "" + aliveEnemies + " enemies left alive " + outsideEnemies + " enemies escaped.";
        String score = "" + (iScore);

        Font small = new Font("Helvetica", Font.BOLD, 14);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(alive, 5, 15);
        g.drawString(score, 5, BOARD_HEIGHT - 25);

    }

    public void drawEnd(Graphics g) {
        int iScore = 0;
        int missedBullets = 0;

        for(Enemy theEnemy : enemiesList) {
            if(theEnemy.isDying())
                iScore += 100;
            if(theEnemy.outside)
                iScore -= 100;
        }

        for(Bullet theBullet : bulletList) {
            if(!theBullet.hit)
                missedBullets++;
        }

        String msg = "GAME OVER";
        String finalScore = "" + (iScore - missedBullets) + " of a possible " + enemiesList.size()*100;
        String fired = "You missed " + missedBullets + " shots, losing " + missedBullets + " points.";
        String restart = "Press R to restart";

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg)) / 2, BOARD_HEIGHT/2 -30);
        g.drawString(finalScore, (BOARD_WIDTH - metr.stringWidth(finalScore))/2, BOARD_HEIGHT / 2 - 15);
        g.setColor(Color.RED);
        g.drawString(fired, (BOARD_WIDTH - metr.stringWidth(fired))/2, BOARD_HEIGHT / 2 );
        g.setColor(Color.WHITE);
        g.drawString(restart, (BOARD_WIDTH - metr.stringWidth(restart))/2, BOARD_HEIGHT / 2 + 15);
    }

    public void drawBG(Graphics g) {
        g.drawImage(background, 0, 0, this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawBG(g);
        if(gameStart) {
            drawBullet(g);
            drawPlayer(g);
            drawEnemy(g);
            drawWords(g);

            g.dispose();
        } else {
            drawEnd(g);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(gameStart) {
            for(Enemy currEnemy : enemiesList) {
                for(Bullet theBullet : bulletList) {
                    currEnemy.ifHit(theBullet);
                }
                currEnemy.move();
            }
            for(Bullet theBullet : bulletList) {
                theBullet.fire();
            }

            checkAlive();
            thePlayer.move();
        }
        repaint();
    }

    public void checkAlive() {
        int deadEnemies = 0;
        for(Enemy theEnemy : enemiesList) {
            if(theEnemy.isDying())
                deadEnemies++;
        }
        if(deadEnemies == enemiesList.size()) {
            gameStart = false;
        }
    }

    private class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(gameStart) {
                if(key == KeyEvent.VK_SPACE) {
                    Bullet currBullet = new Bullet(thePlayer.getX(), thePlayer.getY()+13);
                    currBullet.isFired = true;
                    currBullet.moving = true;
                    bulletList.add(currBullet);
                }
                thePlayer.keyPressed(e);
            }
            if(key == KeyEvent.VK_R) {
                restartGame();
            }
            if(key == KeyEvent.VK_Q) {
                debugGame();
            }
        }
        public void keyReleased(KeyEvent e) {
            if(gameStart) {
                thePlayer.keyReleased(e);
            }
        }
    }
}

