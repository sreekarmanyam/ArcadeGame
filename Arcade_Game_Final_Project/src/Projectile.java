import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Projectile is an object that is fired from a Character like Hero or Monster.
 * When it is created it initially has the same position and velocity as the
 * Character that fired it. The bullets fired by monsters and the bubbles fired
 * by Hero inherit the properties of Projectile.
 *
 */
public class Projectile {
	double xPosition;
	double yPosition;
	double xVelocity;
	int diameter;
	Color color;
	Ellipse2D ellipse;

	public Projectile(double shooterX, double shooterY, double shooterXVelocity, boolean facingRight) {
		this.xPosition = shooterX;
		this.yPosition = shooterY + 40;
		if (facingRight) {
			xVelocity = shooterXVelocity + 10;
		} else {
			xVelocity = shooterXVelocity - 10;
		}
		diameter = 10;
		color = Color.MAGENTA;
	}

	public Projectile(double shooterX, double shooterY, double shooterXVelocity) {
		this.xPosition = shooterX;
		this.yPosition = shooterY;
		xVelocity = shooterXVelocity;
	}

	public void updatePosition() {
		xPosition += xVelocity;
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(color);
		ellipse = new Ellipse2D.Double(xPosition, yPosition, diameter, diameter);
		g2.fill(ellipse);
	}

	public boolean checkHeroProjectileCollision(Hero hero, LevelLoader level) {
		boolean hit = false;

		double hx = hero.x;
		double hy = hero.y;
		double hWidth = hero.WIDTH;
		double hHeight = hero.HEIGHT;

		if ((hx + hWidth > xPosition && hx < xPosition + diameter) // checks if hero intersects with a projectile fired
																	// from Monster2
				&& (hy + hHeight >= yPosition && hy <= yPosition + diameter)) {
			hit = true;
		}
		return hit;
	}

}
