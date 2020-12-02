import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Screen extends Canvas implements KeyListener {

    char keyPressed;
    int screenSize;
    Color backgroundColor;
    String scoreText;
    int circleSize;
    int xPos, yPos;
    int keyCode;
    int circleSpeed;
    Projectile projectile;
    int delayTime = 20;
    int score;
    int coverBoxXPos;
    int coverBoxYPos;
    BufferStrategy bufferStrategy;
    Enemy enemy;
    Enemy[] enemies;
    int numberOfEnemies;
    int enemySize;
    Player player;
    int playerSize;

    /**
     * The main part of the program
     */
    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.setupScreen(screen);
        while (true) {
            for (int i = 0; i < screen.enemies.length; i++) {
                screen.enemies[i].move();
                screen.enemies[i].wrapAround(screen.screenSize);
            }
            screen.projectile.moveUp();
            screen.repaint();
            screen.delay(screen.delayTime);
            //FIXME: there is an annoying bug right now where there will always be 21 enemies
            if (screen.score == 21) {
                System.out.println("You Won!! Thanks for Playing!!");
                System.exit(0);
            }
        }

    }

    /**
     * Constructor, initializes all class variables
     */
    Screen() {
        screenSize = 600;
        backgroundColor = new Color(5, 19, 80);
        circleSize = 100;
        xPos = screenSize / 2;
        yPos = screenSize - 10;
        scoreText = "Score: ";
        circleSpeed = screenSize / 50;
        coverBoxXPos = 250;
        coverBoxYPos = 500;
        playerSize = 100;
        player = new Player(xPos - playerSize, yPos - playerSize, playerSize, this);
        // the numbers for projectile and enemy here are arbitrary
        // since their respective shouldDraw's are turned off initially
        projectile = new Projectile(50, 50, 50, this);
        enemy = new Enemy(50, 50, 50, this);
        projectile.shouldDraw = false;
        numberOfEnemies = screenSize / 10;
        enemies = new Enemy[numberOfEnemies];
        enemySize = 30;
        //draws the row of enemies
        for (int i = 0; i < enemies.length; i++) {
            int x = (i * enemySize);
            int y = 0;
            enemies[i] = new Enemy(x , y, enemySize, this);
        }
    }

    /**
     * Sets up the screen
     *
     * @param canvas the Canvas object that is the main
     *               part of the screen
     */
    void setupScreen(Screen canvas) {
        JFrame frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setSize(screenSize, screenSize);
        canvas.setBackground(backgroundColor);
        canvas.addKeyListener(canvas);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        canvas.requestFocusInWindow();
        canvas.createBufferStrategy(4);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * draws everything on the screen
     *
     * @param g the Graphics object to draw on
     */
    public void paint(Graphics g) {
        player.movePlayer(keyCode, screenSize);
        player.drawPlayer((Graphics2D) g);
        drawBackground(g);
        keyCode = '0';
        scoreCalc(g);
        for (int i = 0; i < enemies.length; i++) {
            enemies[i].drawEnemy((Graphics2D) g);
        }
        fontSetter(g);
    }

    /**
     * This method is called when repaint() is called.
     * Adding this to your program should fix flickering issues.
     * It requires a global variable called bufferStrategy
     * that is initialized somewhere else in your program.
     *
     * @param g the Graphics object to draw on
     */
    public void update(Graphics g) {
        bufferStrategy = getBufferStrategy();
        g = bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, screenSize, screenSize);
        paint(g);
        bufferStrategy.show();
    }

    /**
     * This method responds to the keyTyped event
     * by setting the keypressed variable to the key
     * that was typed. keyTyped responds to "regular"
     * character keys. It does not respond to special
     * keys like return, delete, shift, etc.
     *
     * @param e the key event that happened
     */
    public void keyTyped(KeyEvent e) {
        keyPressed = e.getKeyChar();
        repaint();
    }

    /**
     * This method responds to the keyPressed event
     * by printing out the keycode for the key
     * that was typed. keyPressed responds to all
     * keys, including special keys. For a list of
     * keycodes, see: http://www.foreui.com/articles/Key_Code_Table.htm
     *
     * @param e the key event that happened
     */
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();
        repaint();
    }

    /**
     * This method responds to the keyReleased event
     *
     * @param e the key event that happened
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     * This program makes it sleep or pause for a very short period to allow the player to view the code properly
     * @param delayTime the amount of time in milliseconds that the program is paused
     */
    void delay(int delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (Exception exc) {
        }
    }

    /**
     * shoot() is what always the player to shoot by creating a new projectile every time it is referenced
     */
    public void shoot() {
        projectile = new Projectile(player.xPos + 25, player.yPos - 25, 15, this);
        projectile.setColor(Color.red);
        projectile.moveUp();
    }

    /**
     * draws the background for the game
     * @param g Graphics object we draw with
     */
    public void drawBackground(Graphics g) {
        for (int i = 0; i < 30; i++) {
            g.setColor(Color.white);
            g.fillOval((int) (Math.random() * screenSize), (int) (Math.random() * screenSize), 3, 3);
        }
        for (int i = 0; i < 30; i++) {
            g.setColor(Color.white);
            g.fillOval((int) (Math.random() * screenSize), (int) (Math.random() * screenSize), 6, 6);
        }
    }

    /**
     * Calculates the score by checking the collision between the bullet and the enemy
     * @param g Graphics object we draw with
     */
    public void scoreCalc(Graphics g) {
        if (projectile.shouldDraw) {
            projectile.drawBullet(g);
        }
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].onHit(projectile)) {
                score++;
                enemies[i].shouldDraw = false;
                projectile.shouldDraw = false;
            }
        }
    }

    /**
     * Sets the font for the score in the lower right hand corner
     * @param g Graphics object we draw with
     */
    public void fontSetter(Graphics g) {
        Font font = new Font("Thonburi", Font.BOLD, 24);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(scoreText + score, 25, 575);
    }

}