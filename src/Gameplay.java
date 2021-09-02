import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.*;
import javax.swing.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    boolean play;
    BodyLink head = new BodyLink((int) getWidth() / 2, (int) getHeight() / 2);
    ArrayList<BodyLink> body = new ArrayList<>();
    int score, foodX, foodY;
    Font scoreFont = new Font("calibri", Font.BOLD, 70);
    Timer timer = new Timer(200, this);
    Image backgroundImage, snakeHeadImage, snakeBodyImage;
    int dirX, dirY;

    public Gameplay() {
        body.add(new BodyLink(head.x + 50, head.y));
        body.add(new BodyLink(head.x + 100, head.y));
        dirX = 0;
        dirY = 0;
        play = true;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            snakeMovement();
            repaint();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        backgroundImage = Toolkit.getDefaultToolkit().getImage("./src/swirly background.jpg");
        snakeHeadImage = Toolkit.getDefaultToolkit().getImage("./src/snake_head.png");
        snakeBodyImage = Toolkit.getDefaultToolkit().getImage("./src/snake_body.png");

        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g2.drawImage(snakeHeadImage, head.x, head.y, 50, 50, this);
        for (BodyLink chunk: body) {
            g2.drawImage(snakeBodyImage, chunk.x, chunk.y, 50, 50, this);
        }
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            dirX = 0;
            dirY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            dirX = 0;
            dirY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            dirX = -1;
            dirY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            dirX = 1;
            dirY = 0;
        }
    }

    public void snakeMovement() {
        BodyLink currLink = new BodyLink(0, 0);
        int i = body.size() - 1;
        while (i > 0) {
            currLink = body.get(i);
            BodyLink nextLink = body.get(i - 1);
            currLink.x = nextLink.x;
            currLink.y = nextLink.y;
            i--;
        }
        currLink = body.get(i);
        currLink.x = head.x;
        currLink.y = head.y;

        head.x += 50 * dirX;
        head.y += 50 * dirY;
    }

    public void generateFood() {
        // Chicken nugget goes here
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
