import java.awt.Graphics;

/**
 * * A fruit spawns a bit above the monster when it is trapped inside a bubble
 * and then popped. When eaten, it adds 200 points to the users score. Although
 * it may seem like no fruit has spawned, it usually is due to the fact that the
 * fruit spawned and then was eaten instantly by the hero. It is created
 * whenever the hero collides with the BubbleWithMonsterObject
 * 
 * 
 * Fruit is instantiated when the hero pops a bubble with a monster. Fruit takes
 * two int parameters, for the x and y positions of the monster when the bubble
 * was popped.
 * 
 * 
 **/

public class Fruit extends Character {

	public final int HEIGHT = 25;
	public final int WIDTH = 25;
	public final double g = .25;
	private ShowImage show = new ShowImage("Apple.png");

	public Fruit(int monsterX, int monsterY) {
		super(monsterX, monsterY);
		dx = 0;// fruit won't move left or right
		dy = -5;// fruit is thrown up after the bubble is popped so that it isn't instantly
				// collected by the hero
	}

	@Override
	public void sitOnPlatform(int blockSize) {
		if (!isFalling) {
			dy = 0;
			y = y - y % blockSize - HEIGHT + blockSize;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(show.image, (int) this.x, (int) this.y, this.WIDTH, this.HEIGHT, this.show);
	}

	public boolean checkHeroFruitCollision(Hero hero) {
		double hx = hero.x;
		double hy = hero.y;
		double hWidth = hero.WIDTH;
		double hHeight = hero.HEIGHT;
		if ((hx + hWidth >= x && hx <= x) && (hy + hHeight >= y && hy <= y)) { // sees if the hero touches the fruit
			hero.score += 200;
			return true;
		}
		return false;
	}

}