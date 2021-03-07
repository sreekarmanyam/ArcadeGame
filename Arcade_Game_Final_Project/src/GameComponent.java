import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * GameComponent handles most of the basic functionality of the game. It creates
 * and stores the Monsters, Hero, and LevelLoader. GameComponent is constructed
 * in GameViewer and takes no parameters.
 *
 */
@SuppressWarnings("serial")
public class GameComponent extends JComponent {
	private final int UPDATE_INTERVAL_MS = 15;
	Scanner s;
	public LevelLoader level;
	public Hero hero;
	public ArrayList<Character> monsters;
	public ArrayList<Character> monstersToRemove;
	public ArrayList<Monster2> monsters2;
	public ArrayList<Projectile> bulletsToRemove;
	public ArrayList<Bubble> bubbles;
	public ArrayList<Bubble> bubblesToRemove;
	public ArrayList<Fruit> fruits;
	public ArrayList<Fruit> fruitsToRemove;
	public ArrayList<BubbleWithMonster> bubblesWithMonsters;
	public ArrayList<BubbleWithMonster> bubblesWithMonstersToRemove;
	public ArrayList<ExtraLife> extraLives;
	public ArrayList<ExtraLife> extraLivesToRemove;
	public Timer advanceStateTimer;
	public final int block = 100;
	public int lives;
	public boolean endFlag = false;

	public final int ghostStartX = 10;
	public final int ghostStartY = 10;

	JPanel livesAndScore;

	public GameComponent() {

		level = new LevelLoader();
		hero = new Hero(level.newHeroX, level.newHeroY);

		newHeroAndMonsters();

		advanceStateTimer = new Timer(UPDATE_INTERVAL_MS, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				hero.updatePosition();
			}
		});
		advanceStateTimer.start();
	}

	private void newHeroAndMonsters() {
		fruits = new ArrayList<>();
		bubbles = new ArrayList<Bubble>();
		bulletsToRemove = new ArrayList<Projectile>();
		monsters2 = new ArrayList<Monster2>();
		fruits = new ArrayList<Fruit>();
		bubblesWithMonsters = new ArrayList<>();
		monsters = new ArrayList<Character>();
		bubblesWithMonstersToRemove = new ArrayList<>();
		monsters2 = new ArrayList<Monster2>();
		extraLives = new ArrayList<ExtraLife>();
		extraLivesToRemove = new ArrayList<ExtraLife>();
		newRemoveLists();
		monsters = level.monsters;
		monsters2 = level.monsters2;
		extraLives = level.extraLives;

		hero.changeSpawn(level.newHeroX, level.newHeroY);
		hero.resetPosition();
	}

	private void newRemoveLists() {
		bubblesToRemove = new ArrayList<>();
		bubblesWithMonstersToRemove = new ArrayList<>();
		fruitsToRemove = new ArrayList<>();
		monstersToRemove = new ArrayList<>();
		extraLivesToRemove = new ArrayList<>();
	}

	@Override
	protected void paintComponent(Graphics g) {
		handleUpdatePosition(hero);
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		level.draw(g2);
		moveHero();
		hero.draw(g2);

		g2.drawString("SCORE: " + hero.score + " Lives: " + hero.lives, 450, 20);

		for (Character m : monsters) {
			m.draw(g2);
			moveMonster(m);
		}
		// shooting the hero
		for (Monster2 m2 : monsters2) {
			for (Projectile b : m2.bullets)
				if (b.checkHeroProjectileCollision(hero, level)) {
					bulletsToRemove.add(b);
					tryToKillHero();
				}
		}
		// handles bubbles
		for (Bubble p : bubbles) {
			p.draw(g2);
			moveBubble(p);
		}
		// handles monster bubbles
		for (BubbleWithMonster p : bubblesWithMonsters) {
			p.updatePosition();
			p.draw(g2);
			if (p.escape) {
				bubblesWithMonstersToRemove.add(p);
				p.moveMonsterHere();
				monsters.add(p.trappedMonster);
			}
			if (p.checkHeroProjectileCollision(hero, level)) {
				fruits.add(new Fruit((int) p.xPosition, (int) p.yPosition - 100));
				bubblesWithMonstersToRemove.add(p);
			}
		}
		// handles fruit
		for (Fruit f : fruits) {
			moveFruit(f);
			f.draw(g2);
			if (f.checkHeroFruitCollision(hero)) {
				fruitsToRemove.add(f);
			}

		}
		// handles extra Life PowerUps
		for (ExtraLife l : extraLives) {
			l.draw(g2);
			if (l.checkHeroExtraLifeCollision(hero)) {
				extraLivesToRemove.add(l);
			}
		}
		checkEndGame();
		killStuff();
	}

	private void checkEndGame() {
		if (monsters.isEmpty() && bubblesWithMonsters.isEmpty()) {
			if (level.levelAt == level.levelNames.size() - 1) {
				System.exit(0);
				// endFlag = true;
				System.out.println("You Won!");
			}
			level.updateLevel(true);
			newHeroAndMonsters();
		}
	}

	private void killStuff() {
		for (Character c : monstersToRemove) {
			monsters.remove(c);
		}
		for (Fruit f : fruitsToRemove) {
			fruits.remove(f);
		}
		for (BubbleWithMonster b : bubblesWithMonstersToRemove) {
			bubblesWithMonsters.remove(b);
		}
		for (Bubble b : bubblesToRemove) {
			bubbles.remove(b);
		}
		for (Monster2 m : monsters2) {
			for (Projectile p : bulletsToRemove)
				m.bullets.remove(p);
		}
		for (ExtraLife l : extraLivesToRemove) {
			extraLives.remove(l);
		}
		newRemoveLists();
	}

	private void moveFruit(Fruit f) {
		if (level.collideDown(f)) {
			handleUpdateVerticalVelocity(f, 0);
		}
		handleUpdatePosition(f);
	}

	private void moveBubble(Bubble p) {
		p.updatePosition();
		if (p.yPosition < 0) {
			bubblesToRemove.add(p);
		}
	}

	private void tryToKillHero() {
		try {
			hero.die(level.levelAt);
		} catch (RuntimeException e) {
			level = new LevelLoader();// restart game
			hero.lives = 5;
			newHeroAndMonsters();
			hero.lives = 5;
			hero.score = 0;
		}
	}

	private void moveMonster(Character m) {
		if (m.collideUp() || level.collideDown(m)) {
			handleUpdateVerticalVelocity(m, 0);
			if (m.checkMonsterHeroCollision(hero, level)) {
				tryToKillHero();
			}
		}
		handleUpdatePosition(m);
		if (!bubbles.isEmpty()) {
			for (Bubble b : bubbles) {
				boolean hitBox = b.collidesWithMonsters(m);
				if (hitBox) {
					bubblesToRemove.add(b);
					monstersToRemove.add(m);
					bubblesWithMonsters
							.add(new BubbleWithMonster(b.xPosition, b.yPosition, b.xVelocity, b.xDistanceTraveled, m));
				}
			}
		}
	}

	private void moveHero() {
		if (hero.collideUp() || level.collideDown(hero)) {
			handleUpdateVerticalVelocity(0);
		}
		if (level.collideLeft(hero) || level.collideRight(hero)) {
			handleUpdateHorizontalVelocity(0);
		}
	}

	public void nextLevel() {
		level.updateLevel(true);
		newHeroAndMonsters();
	}

	public void previousLevel() {
		level.updateLevel(false);
		newHeroAndMonsters();
	}

	public void handleUpdatePosition(Character man) {
		if (!level.collideDown(man)) {
			handleFall(man);
		}
		man.updatePosition();
	}

	private void handleFall(Character man) {
		man.fall();
	}

	public void handleUpdateVerticalVelocity(Character man, double vy) {
		man.updateVerticalVelocity(vy);
	}

	public void handleUpdateHorizontalVelocity(Character man, int i) {
		man.updateHorizontalVelocity(i);
	}

	public void handleUpdateHorizontalVelocity(int i) {
		handleUpdateHorizontalVelocity(hero, i);
	}

	public void handleUpdateVerticalVelocity(double i) {
		handleUpdateVerticalVelocity(hero, i);

	}

	public void heroShoot() {
		if (bubbles.size() >= 10) {
			return;
		}
		Bubble bullet = new Bubble(hero.x, hero.y, hero.dx, hero.facingRight);
		bubbles.add(bullet);
	}
}