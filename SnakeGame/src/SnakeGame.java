import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * The class extend from JPanel and hold the all panels, in this class
 * we choose the order of panel to be shown.
 * 
 * @author C0735536, C0729309, C0737700
 */
public class SnakeGame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<Ranking> allRanking = new ArrayList<Ranking>();

	/**
	 * Constructor
	 */
	public SnakeGame() {
		initGame();
	}
	
	/**
	 * The init method initialize the size of window and fixed, the title of panel, and start the game
	 */
	private void initGame () {
		this.initiateAllRanking();
		this.setTitle("Snake Game");
		this.setSize(500, 380);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setMaximumSize(new Dimension(500, 380));
		this.setMinimumSize(new Dimension(500, 380));
		startGame();
	}

	/**
	 * The startGame method initialize the startPanel and choose the playGame as the nextPanel
	 */
	private void startGame() {
		Consumer<String> fn = parameter -> playGame(parameter);
		StartPanel startPanel = new StartPanel(fn);
		changePanel(startPanel);
	}

	/**
	 * The playGame method initialize the gamePanel and choose the finishGame as the nextPanel
	 */
	private void playGame(String level) {
		BiConsumer<String, Integer> fn = (levelName, points) -> finishGame(levelName, points);
		GamePanel gamePanel = new GamePanel(fn, level);
		changePanel(gamePanel);
	}

	/**
	 * The finishGame method initialize the gameOverPanel and choose the startGame as the nextPanel
	 */
	private void finishGame(String levelName, int points) {
		Consumer<String> fn = parameter -> startGame();
		Ranking ranking = getLevelRanking(levelName);
		GameOverPanel gameOverPanel = new GameOverPanel(fn, points, ranking);
		changePanel(gameOverPanel);
	}

	/**
	 * The changePanel method change for the panel that receive as parameter
	 * @param panel the panel that should shown
	 */
	private void changePanel(JPanel panel) {
		setContentPane(panel);
		panel.requestFocus();
		setVisible(true);
	}
	
	/**
	 * The initiateAllRanking method initialize all level ranking
	 */
	private void initiateAllRanking () {
		List<Level> levelList = Arrays.asList(Level.values());
		for (int i = 0; i < levelList.size(); i++) {
			String levelName = levelList.get(i).toString();
			Level l = Level.valueOf(levelName);
			Ranking r = new Ranking(l);
			this.allRanking.add(r);
		}
	}
	/**
	 * The getLevelRanking method get the level by levelName
	 * @param levelName the name of level
	 */
	private Ranking getLevelRanking(String levelName) {
		Level level = Level.valueOf(levelName);
		for (Ranking r : this.allRanking) {
		    if (r.getLevel().equals(level)) {
		        return r;
		    }
		}
		
		return null;
	}
}