import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * The class extend from JPanel and hold the StartPanel, in this class show
 * option for user choose the level of the game.
 * 
 * @author C0735536, C0729309, C0737700
 */
public class StartPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ButtonGroup group = new ButtonGroup();
	private Consumer<String> nextPanelCall;
	private final static String TITLE_TEXT = "SNAKE GAME";
	private final static String LEVEL_OPTION_LABEL = "SELECT YOUR LEVEL";
	private final static String START_BUTTON_TEXT = "START GAME";

	/**
	 * Constructor
	 * 
	 * @param nextPanelCall
	 *            The function that call nextPanel when click in start button
	 */
	public StartPanel(Consumer<String> nextPanelCall) {
		this.nextPanelCall = nextPanelCall;
		init();
	}
	
	/**
	 * The init method call other method to set content on the panel
	 */
	private void init() {
		setLayout();
		setTitle();
		setLevelOptions();
		createAndAddStartButton();
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
		JLabel title = new JLabel(TITLE_TEXT);
		title.setFont(new Font("Dialog", Font.BOLD, 20));
		title.setForeground(Color.GREEN);
		title.setBounds(180, 40, 200, 20);
		add(title);
	}

	/**
	 * The setLevelOptions method will loop from level and call createAndAddLevelButton for each level
	 */
	private void setLevelOptions() {
		JLabel optionGroupLabel = new JLabel(LEVEL_OPTION_LABEL);
		optionGroupLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		optionGroupLabel.setForeground(new Color(255, 255, 255));
		optionGroupLabel.setBackground(Color.WHITE);
		optionGroupLabel.setBounds(175, 120, 190, 15);
		add(optionGroupLabel);

		List<Level> levelList = Arrays.asList(Level.values());
		for (int i = 0; i < levelList.size(); i++) {
			String buttonName = levelList.get(i).toString();
			boolean isPressed = buttonName == "EASY" ? true : false;
			createAndAddLevelButton(buttonName, isPressed, i + 1);
		}
	}

	/**
	 * The createAndAddLevelButton method create level option
	 */
	private void createAndAddLevelButton(String buttonName, boolean isPressed, int position) {
		int baseYPosition = 140;
		int yPosition = baseYPosition + (25 * position);
		JRadioButton button = new JRadioButton(buttonName, isPressed);
		button.setForeground(new Color(255, 255, 255));
		button.setFont(new Font("Dialog", Font.BOLD, 14));
		button.setBackground(Color.DARK_GRAY);
		button.setBounds(205, yPosition, 106, 15);
		button.setActionCommand(buttonName);
		group.add(button);
		add(button);
	}

	/**
	 * The createAndAddStartButton method create the start button and place in the panel
	 */
	private void createAndAddStartButton() {
		JButton startButton = new JButton(START_BUTTON_TEXT);
		startButton.setBackground(Color.WHITE);
		startButton.addActionListener(this);
		startButton.setBounds(193, 305, 120, 25);
		add(startButton);
	}

	/**
	 * The actionPerformed will wait for start button action and call the nextPanel function with chosen level
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String level = group.getSelection().getActionCommand();
		this.nextPanelCall.accept(level);
	}
}
