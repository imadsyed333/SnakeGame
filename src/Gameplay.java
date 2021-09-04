import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    boolean play;
    BodyLink head;
    ArrayList<BodyLink> body;
    int score;
    Font scoreFont = new Font("calibri", Font.BOLD, 70);
    Timer timer = new Timer(200, this);
    Image backgroundImage, snakeHeadImage, snakeBodyImage, foodImage;
    int dirX, dirY;
    Random random = new Random();
    int randomX, randomY;
    BodyLink nugget;
    String snakeHeadURL;

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            snakeMovement();
            collisionDetection();
            repaint();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        backgroundImage = Toolkit.getDefaultToolkit().getImage("./src/swirly background.jpg");
        snakeHeadImage = Toolkit.getDefaultToolkit().getImage(snakeHeadURL);
        snakeBodyImage = Toolkit.getDefaultToolkit().getImage("./src/snake_body.png");
        foodImage = Toolkit.getDefaultToolkit().getImage("./src/chicken.png");

        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        if (play) {
            g2.drawImage(snakeHeadImage, head.x, head.y, 50, 50, this);
            for (BodyLink chunk: body) {
                g2.drawImage(snakeBodyImage, chunk.x, chunk.y, 50, 50, this);
            }

            g2.drawImage(foodImage, nugget.x, nugget.y, 50, 50, this);

            g2.setColor(Color.white);
            g2.setFont(scoreFont);
            g2.drawString(Integer.toString(score), 0, 0);
        }
        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            snakeHeadURL = "./src/snake_up.png";
            dirX = 0;
            dirY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            snakeHeadURL = "./src/snake_down.png";
            dirX = 0;
            dirY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            snakeHeadURL = "./src/snake_left.png";
            dirX = -1;
            dirY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            snakeHeadURL = "./src/snake_right.png";
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
            currLink.move();
            i--;
        }
        currLink = body.get(i);
        currLink.x = head.x;
        currLink.y = head.y;
        currLink.move();

        head.x += 50 * dirX;
        head.y += 50 * dirY;
        head.move();
    }

    public void generateFood() {
        int smallWidth = getWidth() / 50;
        int smallHeight = getHeight() / 50;
        nugget.x = random.nextInt(smallWidth - 1) * 50;
        nugget.y = random.nextInt(smallHeight - 1) * 50;
        nugget.move();
    }

    public void collisionDetection() {
        if (head.boundingBox.intersects(nugget.boundingBox)) {
            generateFood();
            nugget.move();
            score++;
            BodyLink end = body.get(body.size() - 1);
            body.add(new BodyLink(end.x, end.y));
            snakeMovement();
        }
        for (BodyLink chunk: body) {
            if (head.boundingBox.intersects(chunk.boundingBox)) {
                play = false;
                initializeGame();
            }
        }
    }

    public void initializeGame() {
        nugget = new BodyLink(0, 0);
        generateFood();
        head = new BodyLink((int) getWidth() / 2, (int) getHeight() / 2);
        body = new ArrayList<>();
        body.add(new BodyLink(head.x + 50, head.y));
        body.add(new BodyLink(head.x + 100, head.y));
        dirX = -1;
        dirY = 0;
        play = true;
        score = 0;
        snakeHeadURL = "./src/snake_left.png";
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
