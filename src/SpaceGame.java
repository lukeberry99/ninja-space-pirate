import javax.swing.JFrame;

public class SpaceGame extends JFrame implements Commons{
    public SpaceGame() {
        add(new Board());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setTitle(GAME_TITLE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SpaceGame();
    }
}