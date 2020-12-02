import java.awt.*;

public class Player {
    int size;
    float xSpeed, ySpeed;
    int myHealth;
    int damage;
    int xPos, yPos;
    public boolean shouldDraw;
    int[] xPoints1;
    int[] yPoints1;
    int[] xPoints2;
    int[] yPoints2;
    Screen screen;
    Projectile projectile;


    /**
     * Constructor for our player
     */
    Player(int xPosIn, int yPosIn, int sizeIn, Screen screenIn) {
        size = sizeIn;
        xPos = xPosIn;
        yPos = yPosIn;
        screen = screenIn;
        myHealth = 100;
        damage = 5;
        xSpeed = 10;
        ySpeed = 10;
        shouldDraw = true;
        xPoints1 = new int[]{xPos, xPos, xPos - size / 4};
        yPoints1 = new int[]{yPos + size / 2, yPos + size, yPos + size};
        xPoints2 = new int[]{xPos + size / 2, xPos + size / 2 + size / 4, xPos + size / 2};
        yPoints2 = new int[]{yPos + size / 2, yPos + size, yPos + size};
        projectile = new Projectile(50, 50, 50, screen);
    }

    /**
     * Draws the player to the screen
     */
    public void drawPlayer(Graphics2D g) {
        Color base = new Color(128, 127, 127);
        g.setColor(base);
        g.fillOval(xPos, yPos, size / 2, size);

        Color window = new Color(54, 77, 125);
        g.setColor(window);
        g.fillOval(xPos + size / 8, yPos + size / 6, size / 4, size / 4);

        Color reflect = new Color(173, 192, 252, 60);
        g.setColor(reflect);
        g.fillOval(xPos + size / 7, yPos + size / 6, size / 6, size / 6);

        xPoints1 = new int[]{xPos, xPos, xPos - size / 4};
        yPoints1 = new int[]{yPos + size / 2, yPos + size, yPos + size};
        xPoints2 = new int[]{xPos + size / 2, xPos + size / 2 + size / 4, xPos + size / 2};
        yPoints2 = new int[]{yPos + size / 2, yPos + size, yPos + size};

        Color wings = new Color(199, 7, 7);
        g.setColor(wings);
        g.fillPolygon(xPoints1, yPoints1, 3);
        g.fillPolygon(xPoints2, yPoints2, 3);

        Color rectColor = new Color(234, 115, 24);
        g.setColor(rectColor);
        g.fillRect(xPos + size / 7, yPos + size / 2 + size / 8, size / 4, size / 6);

        Color antenna = new Color(83, 79, 79);
        g.setStroke(new BasicStroke(3));
        g.setColor(antenna);
        g.drawLine(xPos + size / 4, yPos - size / 5, xPos + size / 4, yPos);

        Color top = new Color(0, 174, 255, 255);
        g.setColor(top);
        g.fillOval(xPos + size / 5, yPos - size / 4, 10, 10);
    }

    public void movePlayer(int keyCode, int screenSize) {
        //right arrow key
        if (keyCode == 39 || keyCode == 68)
            xPos += xSpeed;
        //left arrow key
        if (keyCode == 37 || keyCode == 65)
            xPos -= xSpeed;
        //up arrow key
        if (keyCode == 38 || keyCode == 87) {
            screen.shoot();
        }
        //wrap around
        if (xPos < 0)
            xPos = screenSize;
        if (xPos > screenSize)
            xPos = 0;
        if (yPos < 0)
            yPos = screenSize;
        if (yPos > screenSize)
            yPos = 0;
    }

}
