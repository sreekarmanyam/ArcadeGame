import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Monster2 represents a special type of Monster that can shoot bullets which
 * can kill the Hero. Monster2 is created the same way as Monster by calling
 * Monster2(int x, int y) where x and y are the positions where it will appear.
 * The initial position of Monster2 is determined by the data from the level
 * text file.
 *
 */
public class Monster2 extends Character {
	double jumpMax = Math.random() * 200 + 50;
	int jump;
	ArrayList<Projectile> bullets;
	public boolean facingRight;
	private ShowImage show = new ShowImage("blue dragon.png");
	private ShowImage showReverse = new ShowImage("blue dragon reversed.png");

	public Monster2(int x, int y) {
		super(x, y);
		bullets = new ArrayList<Projectile>();
		dx = 3;
		jump = 0;

	}

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
		updateMyFace();
		for (Projectile p : bullets) {
			p.updatePosition();
		}
	}

	private void jump() {
		if (jump >= jumpMax) {
			dy = -10;
			jump = 0;
			jumpMax = Math.random() * 200 + 50;
			shoot();
		} else {
			jump++;
		}
	}

	private void shoot() {
		Projectile bullet = new Projectile(x, y, dx, updateMyFace());
		bullets.add(bullet);
	}

	public boolean updateMyFace() {
		if (dx != 0) {
			if (dx > 0) {
				this.facingRight = true;
				return true;
			} else {
				this.facingRight = false;
				return false;
			}
		}
		return facingRight;
	}

	public void draw(Graphics g) {
		if (this.facingRight) {
			g.drawImage(show.image, (int) this.x, (int) this.y, this.WIDTH, this.HEIGHT, this.show);
		} else {
			g.drawImage(showReverse.image, (int) this.x, (int) this.y, this.WIDTH, this.HEIGHT, this.show);
		}
		for (Projectile p : bullets) {
			p.draw(g);
		}
	}

}
