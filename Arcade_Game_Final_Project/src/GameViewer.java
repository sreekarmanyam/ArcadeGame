import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

/**
 * GameViewer helps display all the components of the game on the screen.
 * GameViewer is created in Main by calling the empty constructor.
 *
 */
public class GameViewer {

	public JFrame f;
	public JFrame fWon;
	public JPanel panel;
	public JLabel scoreLabel;
	public JLabel livesLabel;
	public JLabel wonLabel;
	public JPanel gameInfo;
	public boolean flag = false;
	GameComponent component = new GameComponent();
	public final int WIDTH = 1000;
	public final int HEIGHT = 1000;

	@SuppressWarnings("static-access")
	public void makeWindow() {
		f = new JFrame();
		f.setSize(new Dimension(WIDTH, HEIGHT));
		f.setTitle("Bubble Bobble 2.0 'The Upgrade'");

		f.addKeyListener(new ControlListener(component));
		f.add(component);
		f.setVisible(true);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);

//		if(component.endFlag) {
//			f.setVisible(false);
//			f.dispose();
//			makeGameWonWindow();
//		}
	}

	// NOT YET IMPLEMENTED!
	@SuppressWarnings("static-access")
	public void makeGameWonWindow() {
		fWon = new JFrame();
		fWon.setSize(new Dimension(WIDTH, HEIGHT));
		fWon.setTitle("YOU WON!!!");
		gameInfo = new JPanel();
		gameInfo.setSize(100, 100);
		wonLabel = new JLabel("YOU WON!!!");
		wonLabel.setSize(100, 100);
		gameInfo.add(wonLabel, BorderLayout.CENTER);
		fWon.add(gameInfo, BorderLayout.CENTER);
		fWon.setVisible(true);
		fWon.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
	}

}
