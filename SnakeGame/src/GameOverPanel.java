import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

/**
 * The class extend from JPanel and hold the GameOverPanel, in this class show
 * top 5 information from Ranking, and also it has input to put the player name.
 * 
 * @author C0735536, C0729309, C0737700
 *
 */
public class GameOverPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Consumer<String> nextPanelCall;
	private int playerPoint = 0;
	private Ranking ranking;
	private JFormattedTextField nameInput;
	private static final String TITLE = "GAME OVER";
	private static final String INITIAL_LABEL = "PLEASE TYPE YOUR INITIAL";
	private static final String CONGRATULATION_MESSAGE = "CONGRATULATION YOU ARE RANK";
	private static final String UNFORTUNATELY_MESSAGE = "YOU ARE NOT TOP 5, YOUR RANK IS";
	private static final int RANK_NUMBER = 5;
	private static final String[] COLUMN_NAMES = { "RANK", "SCORE", "NAME" };

	/**
	 * Constructor
	 * 
	 * @param nextPanel
	 *            The function that call nextPanel when click in restart button
	 * @param playerPoint
	 *            The point that player made in the game
	 * @param ranking
	 *            The ranking
	 */
	public GameOverPanel(Consumer<String> nextPanel, int playerPoint, Ranking ranking) {
		this.nextPanelCall = nextPanel;
		this.playerPoint = playerPoint;
		this.ranking = ranking;
		init();
	}

	/**
	 * The init method call other method to set content on the panel
	 */
	private void init() {
		setLayout();
		setTitle();
		createAndAddNameField();
		setRanking();
		createAndAddRestartButton();
	}

	/**
	 * The setLayout method set background color and removes the default panel
	 * layout
	 */
	private void setLayout() {
		setBackground(Color.DARK_GRAY);
		setLayout(null);
	}

	/**
	 * The setLayout method add Title on panel
	 */
	private void setTitle() {
		JLabel labelGameOver = new JLabel(TITLE);
		labelGameOver.setForeground(Color.RED);
		labelGameOver.setFont(new Font("DialogInput", Font.BOLD, 18));
		labelGameOver.setBounds(193, 49, 106, 15);
		add(labelGameOver);
	}

	/**
	 * The createAndAddNameField method create input field to player name
	 */
	private void createAndAddNameField() {
		JLabel initialLabel = new JLabel(INITIAL_LABEL);
		initialLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		initialLabel.setForeground(Color.YELLOW);
		initialLabel.setBounds(95, 97, 228, 15);
		add(initialLabel);

		MaskFormatter formatter = new MaskFormatter();

		try {
			formatter = new MaskFormatter("***");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.nameInput = new JFormattedTextField(formatter);
		this.nameInput.setFont(new Font("Dialog", Font.BOLD, 18));
		this.nameInput.setBounds(330, 88, 51, 32);
		add(this.nameInput);
	}

	/**
	 * The method setRanking call methods related to ranking information
	 */
	private void setRanking() {
		int playerRank = findPlayerRank();
		setRankTitle(playerRank);
		setRankHeader();
		setRankingItems(playerRank);
	}

	/**
	 * The method findPlayerRank will find the rank position of player
	 * 
	 * @return rank the position of player in Ranking
	 */
	private int findPlayerRank() {
		if (this.ranking.getRankingItems().size() == 0)
			return 1;

		int rank = -1;

		for (int i = 0; i < this.ranking.getRankingItems().size(); i++) {
			RankingItem rankingItem = this.ranking.getRankingItems().get(i);
			if (this.playerPoint > rankingItem.getPoints()) {
				rank = i + 1;
				break;
			}
		}

		if (rank == -1) {
			rank = this.ranking.getRankingItems().size() + 1;
		}

		return rank;
	}

	/**
	 * The setRankTitle method set the title of ranking table
	 * 
	 * @param playerRank
	 *            the position of player in rank
	 */
	private void setRankTitle(int playerRank) {
		String message;

		if (playerRank <= RANK_NUMBER) {
			message = CONGRATULATION_MESSAGE;
		} else {
			message = UNFORTUNATELY_MESSAGE;
		}

		JLabel rankingItemTitle = new JLabel(message + " " + playerRank);
		rankingItemTitle.setForeground(Color.WHITE);
		rankingItemTitle.setBounds(130, 133, 280, 15);
		add(rankingItemTitle);
	}

	/**
	 * The setRankHeader method set the name of columns of table
	 */
	private void setRankHeader() {
		JLabel labelRank = new JLabel(COLUMN_NAMES[0]);
		labelRank.setHorizontalAlignment(SwingConstants.CENTER);
		labelRank.setForeground(Color.ORANGE);
		labelRank.setBounds(97, 176, 66, 15);
		add(labelRank);

		JLabel labelScore = new JLabel(COLUMN_NAMES[1]);
		labelScore.setHorizontalAlignment(SwingConstants.CENTER);
		labelScore.setForeground(Color.ORANGE);
		labelScore.setBounds(193, 176, 106, 15);
		add(labelScore);

		JLabel labellName = new JLabel(COLUMN_NAMES[2]);
		labellName.setHorizontalAlignment(SwingConstants.CENTER);
		labellName.setForeground(Color.ORANGE);
		labellName.setBounds(338, 176, 66, 15);
		add(labellName);
	}

	/**
	 * The setRankHeader method created own rankingItem with the current player
	 * points and pass to another method to create the ranking Table
	 */
	private void setRankingItems(int playerRank) {
		ArrayList<RankingItem> rankingItems = new ArrayList<RankingItem>();

		int i = 0;
		int rank = 1;
		boolean enterToRanking = false;
		while (rankingItems.size() < RANK_NUMBER && (i < this.ranking.getRankingItems().size() || !enterToRanking)) {
			if (rank == playerRank) {
				rank++;
				enterToRanking = true;
				rankingItems.add(new RankingItem("IT'S YOU :)", playerPoint));
			} else {
				rankingItems.add(this.ranking.getRankingItems().get(i));
				i++;
				rank++;
			}
		}

		for (int j = 0; j < rankingItems.size(); j++) {
			setRankingItem(rankingItems.get(j), j);
		}
	}

	/**
	 * The setRankingItem method create the item of ranking table
	 * @param rankingItem rankingItem to get the name and point
	 * @param position the position of rank
	 */
	private void setRankingItem(RankingItem rankingItem, int position) {
		String rank = (position + 1) + "";

		int baseYPosition = 200;
		int yPosition = baseYPosition + (20 * position);
		JLabel rankLabel = new JLabel(rank);
		rankLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rankLabel.setForeground(Color.WHITE);
		rankLabel.setBounds(118, yPosition, 32, 15);
		add(rankLabel);

		String score = String.format("%06d", rankingItem.getPoints());
		JLabel scoreLabel = new JLabel(score);
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setBounds(215, yPosition, 66, 15);
		add(scoreLabel);

		JLabel nameLabel = new JLabel(rankingItem.getName());
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setBounds(350, yPosition, 85, 15);
		add(nameLabel);
	}

	/**
	 * The createAndAddRestartButton method create the restart button and place in the panel
	 */
	private void createAndAddRestartButton() {
		JButton restartButton = new JButton("RESTART");
		restartButton.setBackground(Color.WHITE);
		restartButton.setBounds(196, 305, 114, 25);
		restartButton.addActionListener(this);
		add(restartButton);
	}

	/**
	 * The actionPerformed will wait for restart button action and insert the new RankingItem for ranking and call the nextPanel function
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		RankingItem rankingItem = new RankingItem(this.nameInput.getText(), this.playerPoint);
		this.ranking.addRankingItem(rankingItem);
		this.nextPanelCall.accept("");
	}
}
