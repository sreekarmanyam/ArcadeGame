import java.awt.*;
import javax.swing.*;
/**
 * ShowImage helps to display the images for Characters.
 * It is created by calling ShowImage(String picture) where picture is the
 * name of an image file in the project folder.
 *
 */
public class ShowImage extends JPanel {
	
	private static final long serialVersionUID = -1516620913434308952L;
	Image image; 

	public ShowImage(String picture) {
		super();
		image = Toolkit.getDefaultToolkit().getImage(picture);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(image, 75, 50, 50, 75, this); 
	}
}