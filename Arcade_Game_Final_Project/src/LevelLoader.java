import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class loads levels from the text file and draws them the code is as
 * follows: 0 = blank 1 = platform 2 = green monster 3 = shooter 4 = hero 5 =
 * lifeUp
 * 
 * This also handles collisions between Character objects and the platforms.
 * 
 * LevelLoader is instantiated with no parameters, but will not work without
 * text files that follow the aforementioned cypher.
 * 
 */

public class LevelLoader {
	int[][] map;
	public final int block = 100;
	public final int height = 50;
	ArrayList<String> levelNames;
	int levelAt;
	public ArrayList<Character> monsters;
	public ArrayList<Monster2> monsters2;
	public ArrayList<ExtraLife> extraLives;
	public int newHeroX;
	public int newHeroY;

	private ShowImage tutorial = new ShowImage("tutorial.jpg");
	private ShowImage buffalo = new ShowImage("buffalo.jpg");

	public LevelLoader() {
		map = new int[10][10];
		levelNames = new ArrayList<String>();
		levelNames.add("level1.txt");
		levelNames.add("level2.txt");
		levelNames.add("level3.txt");
		monsters = new ArrayList<Character>();
		monsters2 = new ArrayList<Monster2>();
		extraLives = new ArrayList<ExtraLife>();
		levelAt = 0;
		newHeroX = 0;
		newHeroY = 0;
		read();

	}

	public void read() {
		FileReader file;
		try {
			file = new FileReader(levelNames.get(levelAt));

		} catch (FileNotFoundException e) {
			throw new RuntimeException("bad file name");
		}
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);

		for (int i = 0; i < map.length; i++) {
			String line = scan.nextLine();
			for (int j = 0; j < map[i].length; j++) {
				map[j][i] = line.charAt(j) - '0';
			}
		}
		updateCharacters();
	}

	public void updateLevel(boolean nextLevel) {
		if (levelAt > 0 && !nextLevel) {
			levelAt--;
			monsters.clear();
			monsters2.clear();
			extraLives.clear();
			read();
		}
		if (levelAt < levelNames.size() - 1 && nextLevel) {
			levelAt++;
			monsters.clear();
			monsters2.clear();
			extraLives.clear();
			read();
		}
	}

	public void updateCharacters() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 2) {
					Monster monster = new Monster(i * block, j * block);
					monsters.add(monster);
				} else if (map[i][j] == 3) {
					Monster2 monster2 = new Monster2(i * block, j * block);
					monsters.add(monster2);
					monsters2.add(monster2);
				} else if (map[i][j] == 4) {
					newHeroX = i * block;
					newHeroY = j * block;
				} else if (map[i][j] == 5) {
					ExtraLife extraLife = new ExtraLife(i * block, j * block);
					extraLives.add(extraLife);
				}
			}
		}
	}

	public void draw(Graphics g2) {

		if (levelAt == 0) {
			g2.drawImage(tutorial.image, 0, 0, 1000, 1000, this.tutorial);
		} else {
			g2.drawImage(buffalo.image, 0, 0, 1000, 1000, this.buffalo);
		}
		g2.setColor(Color.BLACK);
		int[][] levelMap = map;
		for (int i = 0; i < levelMap.length; i++) {
			for (int j = 0; j < levelMap[i].length; j++) {
				if (levelMap[i][j] == 1) {
					g2.fillRect((i * block), j * block, block, height);
				}
			}
		}
	}

	public boolean collideDown(Character man) {

		boolean isCollided = isOutsideFrameUpOrDown(man);
		double hx = man.x;
		double hy = man.y + man.HEIGHT;
		int I = (int) hx / block;
		int J = (int) hy / block;
		int[][] levelMap = map;

		if ((hy % block <= block/2) && levelMap[I][J] == 1) {
			isCollided = true;
		}

		hx += man.WIDTH;
		I = (int) hx / block;

		if ((hy % block <= 50) && levelMap[I][J] == 1) {
			isCollided = true;
		}

		if (isCollided) {
			man.sitOnPlatform(block);
			man.isFalling = false;
		}
		return isCollided;
	}

	public boolean isOutsideFrameUpOrDown(Character man) {
		return (man.y < 0 || man.y + man.HEIGHT >= 990);
	}

	public boolean collideRight(Character man) {
		return collideLeftOrRight(man, true);
	}

	public boolean collideLeft(Character man) {
		return collideLeftOrRight(man, false);
	}

	private boolean collideLeftOrRight(Character man, boolean checkingRight) {
		boolean isCollided = false;
		double hx = man.x;
		double hy = man.y;
		int J = (int) hy / block;
		int I = (int) hx / block;

		if ((hx <= 0 && !checkingRight) || (hx + man.WIDTH >= 990 && checkingRight)) {
			isCollided = true;
		}

		if (map[I][J] == 1) {
			isCollided = true;
		}
		return isCollided;
	}
}