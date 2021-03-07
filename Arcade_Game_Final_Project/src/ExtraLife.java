import java.awt.Graphics;

/**
 * ExtraLives are created from a level's text file, and then put into an
 * ArrayList. When they collide with a hero, they increase the hero's lives by
 * one.
 * 
 *
 */
public class ExtraLife {
	public final int HEIGHT = 50;
	public final int WIDTH = 50;
	public int x;
	public int y;
	private ShowImage show = new ShowImage("BDubs.png");

	public ExtraLife(int i, int j) {
		x = i;
		y = j;
	}

	public void draw(Graphics g) {
		g.drawImage(show.image, (int) this.x, (int) this.y, this.WIDTH, this.HEIGHT, this.show);
	}

	public boolean checkHeroExtraLifeCollision(Hero hero) {
		double hx = hero.x;
		double hy = hero.y;
		double hWidth = hero.WIDTH;
		double hHeight = hero.HEIGHT;
		if ((hx + hWidth >= x && hx <= x + WIDTH) && (hy + hHeight >= y && hy <= y + HEIGHT)) { // sees if hero has
																								// intersected with the
																								// extralife
			hero.lives++;
			return true;
		}
		return false;
	}
}
