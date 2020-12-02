
import java.awt.*;
import java.awt.geom.Point2D;

public class Projectile {
  public int xPos, yPos;
  public int size;
  public int xSpeed, ySpeed;
  Color color;
  public boolean shouldDraw;
  Screen screen;

  public Projectile(int xPosIn, int yPosIn, int sizeIn, Screen screenIn) {
    xPos = xPosIn;
    yPos = yPosIn;
    size = sizeIn;
    screen = screenIn;
    ySpeed = screen.circleSpeed  * 2;
    shouldDraw = true;
  }

  /**
   * setColor is used to change the color of the projectile to the player's liking
   * @param color a reference to the object Color
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * This creates a temporary object in the game that the player shoots at the enemy
   * @param g Graphics object we draw with
   */
  public void drawBullet(Graphics g) {
    g.setColor(color);
    g.fillOval(xPos - size / 2, yPos - size / 2, size, size);
  }

  /**
   * This function is used to move the bullet up towards to enemies
   */
  public void moveUp() {
    xPos += xSpeed;
    yPos -= ySpeed;
  }

  /**
   * This function (does not work at the moment) is used to move the bullet down towards the player
   */
  public void moveDown() {
    xPos += xSpeed;
    yPos += ySpeed ;
  }

  // this could be used to change the game into a breakout game if we wanted to
  public void bounce(int screenSize) {
    if (xPos < 0 || xPos > screenSize)
      xSpeed = xSpeed * -1;
    else if (yPos < 0 || yPos > screenSize)
      ySpeed = ySpeed * -1;
  }

}
