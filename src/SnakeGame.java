import javax.swing.JFrame;

public class SnakeGame {
    public static void main(String[] args) {
        JFrame game = new JFrame();
        game.setBounds(0, 0, 700, 600);
        Gameplay mechanics = new Gameplay();
        game.setTitle("Snek");
        game.setResizable(false);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.add(mechanics);
        game.setVisible(true);
        mechanics.initializeGame();
    }
}
