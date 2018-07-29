import javax.swing.JFrame;

/**
 * Main class to initiate the game
 * @author C0735536, C0729309, C0737700
 *
 */
public class Main {
	public static void main(String[] args) {
		SnakeGame s = new SnakeGame();
		s.setVisible(true);
		s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}