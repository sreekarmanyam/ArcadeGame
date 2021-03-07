import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This is where we handle the control inputs from a keyboard. The controls work
 * like so: When a key is TYPED, ... Up Arrow: Jump Down Arrow: Move down Left
 * Arrow: Move left Right Arrow: Move right Space: Hero shoots a bubble U:
 * Switches to the next level D: Switches to the previous level The movement
 * arrows work by setting the characters horizontal or vertical velocity to a
 * certain value (a negative number moves it to the left/up, a positive number
 * moves it to the right/down) When a key is released, it makes the user stop
 * moving to the left of right. It is done using Java's KeyListener Interface
 */
public class ControlListener implements KeyListener {

	GameComponent component;

	public ControlListener(GameComponent c) {

		component = c;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (!component.hero.collideUp() && component.level.collideDown(component.hero))
				component.handleUpdateVerticalVelocity(-8);
		} else {
			component.handleUpdateVerticalVelocity(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (!component.level.collideDown(component.hero))
				component.handleUpdateVerticalVelocity(5);
			else
				component.handleUpdateVerticalVelocity(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (!component.level.collideLeft(component.hero)) {
				component.handleUpdateHorizontalVelocity(-5);
			} else {
				component.handleUpdateHorizontalVelocity(0);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (!component.level.collideRight(component.hero)) {
				component.handleUpdateHorizontalVelocity(5);
			} else {
				component.handleUpdateHorizontalVelocity(0);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			component.handleUpdateHorizontalVelocity(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'u') {
			component.nextLevel();
		}
		if (e.getKeyChar() == 'd') {
			component.previousLevel();
		}
		if (e.getKeyChar() == ' ') {
			component.heroShoot();
		}
	}

}
