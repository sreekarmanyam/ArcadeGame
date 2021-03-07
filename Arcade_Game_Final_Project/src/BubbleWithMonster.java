import java.awt.Color;
/**
 * BubbleWithMonster is constructed when a Monster collides with a Bubble. 
 * It inherits from Bubble which itself inherits from Projectile.
 * BubbleMonster's constructor takes as its parameters the position and velocity
 * of the Character that collided with the Bubble.
 *
 */
public class BubbleWithMonster extends Bubble {

	int timePassed;
	final int timeToEscape = 400;
	boolean escape;
	Character trappedMonster; 

	public BubbleWithMonster(double shooterX, double shooterY, double shooterXVelocity, double xDistanceTraveled, Character c) {
		super(shooterX, shooterY, shooterXVelocity, xDistanceTraveled);
		escape = false;
		color = Color.GREEN;
		trappedMonster = c;
	}

	@Override
	public void updatePosition() {
		if (xDistanceTraveled <= maxX && xPosition > diameter && xPosition < 950 - diameter) {
			xPosition += xVelocity;
		}
		if (xDistanceTraveled > xToStartMovingUp && yPosition > (diameter / 2)) {
			yPosition += yVelocity;
			yVelocity += g;
		}

		xDistanceTraveled += Math.abs(xVelocity);
		timePassed++;
		if (timePassed > timeToEscape) {
			escape = true;
		}
	}

	public void moveMonsterHere() {
	trappedMonster.moveTo(this.xPosition, this.yPosition);
	}

}
