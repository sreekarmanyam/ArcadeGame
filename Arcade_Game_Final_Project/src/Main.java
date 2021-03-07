/**
 * Main starts the game by making a GameViewer object which handles making the
 * window and its related elements.
 * 
 * @author Aditya Burle, Jeff La Dine, and Sreekar Manyam
 *
 */
public class Main {

	public static void main(String[] args) {
		GameViewer viewer = new GameViewer();
		viewer.makeWindow();
	}
}