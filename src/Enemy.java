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
  public void drawEnemy(Graphics2D g) {
    if(shouldDraw) {
      Color alien = new Color(15, 253, 1);
      g.setColor(alien);
      g.drawArc(xPos,yPos,size/3,size/4,0,180);
      g.fillArc(xPos,yPos,size/3,size/4,0,180);

      Color base = new Color(170, 170, 170);
      g.setColor(base);
      g.fillOval(xPos-size/3, yPos+size/8, size, size/4);

      Color stroke = new Color(0,0,0, 196);
      g.setColor(stroke);
      g.setStroke(new BasicStroke(2));
      g.drawLine(xPos-size/3,yPos+size/4, xPos+size-size/3,yPos+size/4);
    }
  }

  /**
   * Moves the enemy side to side until the end of the screen is reached then it moves down
   * one space and goes back to moving side to side 
   */
  public void move() {
    xPos += xSpeed;
  }

  /**
   * Moves the enemies in a space invaders like manner
   * @param screenSize is used to map out the border of the screen
   */
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
      if (!shouldDraw) {
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
