import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Bubble extends Projectile {

	double yVelocity;
	double xDistanceTraveled;
	final double xToStartMovingUp = 250;
	final double maxX = 250;
	final double g = -.5;

	public Bubble(double shooterX, double shooterY, double shooterXVelocity, boolean shooterFacingRight) {
		super(shooterX, shooterY, shooterXVelocity, shooterFacingRight);
		xDistanceTraveled = 0;
		yVelocity = 0;
		diameter = 40;
		color = Color.blue;
	}

	@Override
	public void updatePosition() {
		if (xDistanceTraveled <= maxX) {
			super.updatePosition();
		}
		if (xDistanceTraveled > xToStartMovingUp) {
			yPosition += yVelocity;
			yVelocity += g;
		}
		xDistanceTraveled += Math.abs(xVelocity);
	}

	public boolean collidesWithMonsters(Character m) {

		if ((this.xPosition + this.diameter >= m.x && this.xPosition <= m.x)
				&& (this.yPosition >= m.y && m.y + m.HEIGHT > this.yPosition) && (this.yVelocity == 0)) {
			return true;
		} else {
			return false;
		}
	}
}
