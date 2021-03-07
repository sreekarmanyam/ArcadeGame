import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Bubble is a special type of Projectile that is fired by the Hero. It is used
 * to capture Monsters which then allows for those Monsters to be killed.
 * Bubbles are stored in an ArrayList in GameComponent. Bubble is created by
 * calling Bubble(double shooterX, double shooterY, double shooterXVelocity,
 * boolean shooterFacingRight) where the parameters are data values associated
 * with the Characters that is doing the shooting.
 */
public class Bubble extends Projectile {

	double yVelocity;
	double xDistanceTraveled;
	final double xToStartMovingUp = 250;
	final double maxX = 400;
	final double g = -.5;

	public Bubble(double shooterX, double shooterY, double shooterXVelocity, boolean shooterFacingRight) {
		super(shooterX, shooterY, shooterXVelocity, shooterFacingRight);
		xDistanceTraveled = 0;
		yVelocity = 0;
		diameter = 40;
		color = Color.blue;
	}

	public Bubble(double shooterX, double shooterY, double shooterXVelocity, double DistanceTraveled2) {
		super(shooterX, shooterY, shooterXVelocity);
		xDistanceTraveled = DistanceTraveled2;
		diameter = 40;
	}

	@Override
	public void updatePosition() {
		if (xDistanceTraveled <= maxX && xPosition > 0 - diameter / 2 && xPosition < 990 - diameter / 2) {
			super.updatePosition();
		}
		if (xDistanceTraveled > xToStartMovingUp) {
			yPosition += yVelocity;
			yVelocity += g;
		}
		xDistanceTraveled += Math.abs(xVelocity);
	}

	public boolean collidesWithMonsters(Character m) {
		return ((this.xPosition + this.diameter >= m.x && this.xPosition <= m.x)
				&& (this.yPosition >= m.y && m.y + m.HEIGHT > this.yPosition) && (this.yVelocity == 0));
	}
}
