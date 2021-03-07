import java.awt.Graphics;

/**
 * Character represents objects like Monsters and Hero. Character is never
 * created directly, instead Character just holds properties shared by both
 * Monster and Hero like xy positions and size of the image on the screen.
 * 
 * subclasses include: hero monster monster2 fruit
 * 
 * all of these have the same basic behaviors, the fall, have the same gravity
 * (g), have vertical and horizontal positions and velocities, heights and
 * widths.
 * 
 * Characters are instantiated with two parameters, initial x and initial y
 * values for the point at the top left corner of the character
 *
 *
 */
public abstract class Character {

	public final int HEIGHT = 75;
	public final int WIDTH = 50;
	public double x;
	public double y;
	public double dx;
	public double dy;
	public boolean facingRight;
	public boolean isFalling = false;

	public double g = .25;
	private ShowImage show;
	private ShowImage showReverse;

	public Character(int x1, int y1) {
		this.x = x1;
		this.y = y1;
		dy = 0;
	}

	public void updatePosition() {
		x += dx;
		y += dy;
	}

	public void fall() {
		isFalling = true;
		dy += g;
	}

	// snaps back the character to the top of the platform if he ever falls through
	public void sitOnPlatform(int blockSize) {
		if (!isFalling) {
			dy = 0;
			y = y - y % blockSize - HEIGHT + blockSize;
		}
	}

	public void updateFace() {
		if (dx != 0) {
			if (dx > 0) {
				this.facingRight = true;
			} else {
				this.facingRight = false;
			}
		}
	}

	public void draw(Graphics g) {
		if (this.facingRight) {
			g.drawImage(show.image, (int) this.x, (int) this.y, this.WIDTH, this.HEIGHT, this.show);
		} else {
			g.drawImage(showReverse.image, (int) this.x, (int) this.y, this.WIDTH, this.HEIGHT, this.show);
		}
	}

	public void moveTo(double xPosition, double yPosition) {
		x = xPosition;
		y = yPosition;
	}

	public void updateHorizontalVelocity(double i) {
		dx = i;
	}

	public void updateVerticalVelocity(double i) {
		dy = i;
	}

	public boolean checkMonsterHeroCollision(Hero hero, LevelLoader level) {
		double hx = hero.x;
		double hy = hero.y;
		double hWidth = hero.WIDTH;
		double hHeight = hero.HEIGHT;
		if ((hx + hWidth > x && x > hx) && (hy + hHeight >= y && hy <= y)) {
			// You can't die on the first level
			if (level.levelAt != 0) {
				hero.score -= 200;
				return true;
			}
		}
		return false;
	}

	/*
	 * We considered putting this method in the levelLoader class along with other
	 * collision methods, but this method only checks if we hit the celing, which is
	 * always zero, and then modifies the fields of the character it is called on,
	 * rather than only returning a value.
	 */
	public boolean collideUp() {
		boolean isCollided = false;

		if (y <= 0) {
			dy = 0;
			y = 0;
			isCollided = true;
		}

		return isCollided;
	}

}
