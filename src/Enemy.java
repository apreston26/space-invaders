import java.awt.*;
import java.awt.geom.Point2D;

public class Enemy {

  int size;
  double xSpeed;
  int xPos;
  int yPos;
  Color enemyColor;
  boolean shouldDraw;
  Screen screen;


  /**
   * Constructor, initializes all class variables
   */
  Enemy(int xPosIn, int yPosIn, int sizeIn, Screen screenIn) {
    xPos = xPosIn;
    yPos = yPosIn;
    size = sizeIn;
    screen = screenIn;
    enemyColor = new Color(0,255,0);
    xSpeed = screen.circleSpeed / 2 ;
    shouldDraw = true;
  }

  /**
   * Creates the player sprite
   */
  public void drawEnemy(Graphics g) {
    if(shouldDraw) {
      g.setColor(enemyColor);
      g.fillOval(xPos - size / 2, yPos - size / 2, size, size);
    }
  }

  /**
   * Moves the enemy side to side until the end of the screen is reached then it moves down
   * one space and goes back to moving side to side 
   */

  public void move() {
    xPos += xSpeed;
  }

  public void wrapAround(int screenSize ) {
    if (xPos < 0) {
      xSpeed = xSpeed * -1.025;
      yPos += size;
    }
    if (xPos > screenSize) {
      xSpeed = xSpeed * -1.025;
      yPos += size;
    }
  }

  /**
   * Deals with the collision between projectiles from the player on the enemy
   * @return
   */
  public boolean onHit(Projectile projectile) {
      if (!projectile.shouldDraw) {
        return false;
      }
      double distance = Point2D.distance(xPos,yPos,projectile.xPos,projectile.yPos);
      if((distance >= size) && (distance <= (size + 10))) {
        return true;
      }
      else {
        return false;
      }
    }


  /**
   * Spawns a new projectile going toward the player that will do however much the damage is mapped to
   */
  public void attack(Graphics g) {
    Projectile projectile = new Projectile(0,0,0,screen);
    projectile.setColor(Color.red);
    projectile.drawBullet(g);
    projectile.moveDown();

  }

}
