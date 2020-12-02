
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
    ySpeed = screen.circleSpeed * 2;
    shouldDraw = true;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void drawBullet(Graphics g) {
    g.setColor(color);
    g.fillOval(xPos - size / 2, yPos - size / 2, size, size);
  }

  public void moveUp() {
    xPos += xSpeed;
    yPos -= ySpeed;
  }

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

  public boolean checkCollision(Enemy enemy) {
    if (!enemy.shouldDraw) {
      return false;
    }
    double distance = Point2D.distance(xPos,yPos,enemy.xPos,enemy.yPos);
    if((distance >= size) && (distance <= (size + 10))) {
      return true;
    }
    else {
      return false;
    }
  }

}
