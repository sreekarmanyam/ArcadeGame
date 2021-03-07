
import java.awt.Graphics;

/**
 * Monster represents a special type of Character that moves on it own. Monsters
 * are created in GameComponent and stored in an ArrayList. The constructor
 * takes no parameters.
 *
 */
public class Monster extends Character {
	double jumpMax = Math.random() * 200 + 100;
	public int jump;
	private ShowImage show = new ShowImage("green dragon.gif");
	private ShowImage showReverse = new ShowImage("green dragon reversed.gif");

	public Monster(int x, int y) {
		super(x, y);
		dx = -2;
		jump = 0;
	}

	@Override
	public void updatePosition() {
		if (x + WIDTH >= 990 || x <= 0) {
			dx = -dx;

		}
		if (x + WIDTH > 990) {
			x = 990 - WIDTH;
		}
		if (x < 1) {
			x = 1;
		}
		jump();
		super.updatePosition();
		updateFace();
	}

	private void jump() {
		if (jump >= jumpMax) {
			dy = -10;
			jump = 0;
			jumpMax = Math.random() * 200 + 100;

		} else {
			jump++;
		}
	}

	public void draw(Graphics g) {
		if (this.facingRight) {
			g.drawImage(show.image, (int) this.x, (int) this.y, this.WIDTH, this.HEIGHT, this.show);
		} else {
			g.drawImage(showReverse.image, (int) this.x, (int) this.y, this.WIDTH, this.HEIGHT, this.show);
		}
	}
}
