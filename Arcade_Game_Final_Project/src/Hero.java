
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Hero represents as special type of Character that is controlled by the
 * player. Hero is constructed in GameComponent. The constructor takes no
 * parameters.
 *
 */
public class Hero extends Character {
	ArrayList<Bubble> bubbles;
	private ShowImage show = new ShowImage("buffalo.jpg");
	private ShowImage showReverse = new ShowImage("buffaloReversed.jpg");
	public int lives;
	public int score;
	private int spawnX;
	private int spawnY;

	public Hero(int x, int y) {
		super(x, y);
		dx = 0;
		facingRight = true;
		bubbles = new ArrayList<>();
		lives = 5;
		this.x = x;
		this.y = y;
		score = 0;
	}

	@Override
	public void updatePosition() {
		super.updatePosition();
		updateFace();
		for (Projectile p : bubbles) {
			p.updatePosition();
		}
	}

	public void draw(Graphics g) {
		if (this.facingRight) {
			g.drawImage(show.image, (int) this.x, (int) this.y, this.WIDTH, this.HEIGHT, this.show);
		} else {
			g.drawImage(showReverse.image, (int) this.x, (int) this.y, this.WIDTH, this.HEIGHT, this.show);
		}

		for (Projectile p : bubbles) {
			p.draw(g);
		}
	}

	public void shoot() {
		Bubble bullet = new Bubble(x, y, dx, facingRight);
		bubbles.add(bullet);
	}

	public void die(int level) throws RuntimeException {
		if (level > 0) {
			if (lives > 1) {
				this.lives -= 1;
				this.resetPosition();
			} else {
				throw new RuntimeException("Dead Hero");
			}
		}
	}

	public int updateScore(int score) {
		score += 200;
		return score;
	}

	public void changeSpawn(int newHeroX, int newHeroY) {
		spawnX = newHeroX;
		spawnY = newHeroY;
	}

	public void resetPosition() {
		this.x = spawnX;
		this.y = spawnY;
	}

}
