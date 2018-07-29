import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.function.BiConsumer;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The class extend from JPanel and hold the game panel, in this class we can
 * play the game
 * 
 * @author C0735536, C0729309, C0737700
 *
 */
public class GamePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private final int BOARD_WIDTH = 315;
	private final int BOARD_HEIGHT = 345;
	private final int SQUARE_SIZE = 15;
	private final int ALL_squares = BOARD_WIDTH * BOARD_HEIGHT / SQUARE_SIZE;
	private final int BOARD_ROW_COUNT = BOARD_HEIGHT / SQUARE_SIZE;
	private final int BOARD_COLUMN_COUNT = BOARD_WIDTH / SQUARE_SIZE;
	private final int POINTS_VALUE = 10;

	private final int snakeXPosition[] = new int[ALL_squares];
	private final int snakeYPosition[] = new int[ALL_squares];

	private int squares;
	private int points;
	private int levelSpeed;
	private int foodX;
	private int foodY;

	private boolean isGoingLeft = false;
	private boolean isGoingRight = true;
	private boolean isGoingUp = false;
	private boolean isGoingDown = false;
	private boolean isPlayingGame = true;
	private boolean gameStarted = false;

	private String level;
	private BiConsumer<String, Integer> nextPanel;
	private Timer timer;

	/**
	 * Constructor
	 * 
	 * @param nextPanel
	 *            the nextPanel to be called
	 * @param level
	 *            the level of game
	 */
	public GamePanel(BiConsumer<String, Integer> nextPanel, String level) {
		this.nextPanel = nextPanel;
		this.level = level;
		Level l = Level.valueOf(level);
		this.levelSpeed = l.getLevelSpeed();
		initBoard();
	}

	/**
	 * The initBoard method will add key listener and set the background color.
	 * And call initGame
	 */
	private void initBoard() {
		addKeyListener(new GameControl());
		setBackground(Color.DARK_GRAY);
		initGame();
	}

	/**
	 * The initGame method will set the initial position of snake, and the
	 * number of squares. And also start the timer class
	 */
	private void initGame() {
		squares = 3;

		for (int i = 0; i < squares; i++) {
			snakeXPosition[i] = 150 - i * SQUARE_SIZE;
			snakeYPosition[i] = 150;
		}

		setFoodLocation();

		// Timer will call actionPerformed by the time specified
		timer = new Timer(levelSpeed, this);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawSidePanel(g);
		if (gameStarted) {
			if (isPlayingGame) {
				drawGameBoard(g);
				drawFood(g);
				drawSnake(g);
			} else {
				gameOver();
			}
		} else {
			drawStartMessage(g);
		}
	}

	/**
	 * The drawGameBoard method draw the game board
	 * 
	 * @param g
	 *            Graphics object to draw message
	 */
	private void drawGameBoard(Graphics g) {
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, BOARD_WIDTH - 1, BOARD_HEIGHT - 1);
		for (int x = 0; x < BOARD_COLUMN_COUNT; x++) {
			for (int y = 0; y < BOARD_ROW_COUNT; y++) {
				g.drawLine(x * SQUARE_SIZE, 0, x * SQUARE_SIZE, BOARD_HEIGHT);
				g.drawLine(0, y * SQUARE_SIZE, BOARD_WIDTH, y * SQUARE_SIZE);
			}
		}
	}

	/**
	 * The drawFood method draw the food in the game board
	 * 
	 * @param g
	 *            Graphics object to draw message
	 */
	private void drawFood(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(foodX, foodY, SQUARE_SIZE - 2, SQUARE_SIZE - 2);
	}

	/**
	 * The drawSnake method draw the snake in the game board
	 * 
	 * @param g
	 *            Graphics object to draw message
	 */
	private void drawSnake(Graphics g) {
		for (int i = 0; i < squares; i++) {
			g.setColor(Color.GREEN);
			g.fillRect(snakeXPosition[i], snakeYPosition[i], SQUARE_SIZE, SQUARE_SIZE);
		}
	}

	/**
	 * The drawSidePanel method draw the side panel, points, level, the keys of
	 * the game
	 * 
	 * @param g
	 *            Graphics object to draw message
	 */
	private void drawSidePanel(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawString("Snake Game", BOARD_WIDTH + 50, 40);
		g.setColor(Color.WHITE);
		g.drawRect(BOARD_WIDTH + 40, 10, 100, 50);
		g.drawRect(BOARD_WIDTH + 40, 80, 100, 50);
		g.drawRect(BOARD_WIDTH + 40, 150, 100, 50);
		g.drawRect(BOARD_WIDTH + 40, 150, 100, 50);

		g.setColor(Color.ORANGE);
		g.drawString("LEVEL", BOARD_WIDTH + 70, 98);

		g.setColor(Color.WHITE);
		g.drawString(this.level, BOARD_WIDTH + 70, 115);

		g.setColor(Color.YELLOW);
		g.drawString("SCORE", BOARD_WIDTH + 68, 168);
		g.setColor(Color.WHITE);
		g.drawString(String.format("%06d", this.points), BOARD_WIDTH + 68, 185);

		g.setColor(Color.MAGENTA);
		g.drawString("KEYS", BOARD_WIDTH + 70, 230);
		g.setColor(Color.WHITE);
		g.drawString("Up: \u2191", BOARD_WIDTH + 70, 250);
		g.drawString("Down: \u2193", BOARD_WIDTH + 70, 270);
		g.drawString("Left: \u2190", BOARD_WIDTH + 70, 290);
		g.drawString("Right: \u2192", BOARD_WIDTH + 70, 310);
	}

	/**
	 * The drawStartMessage method draw start message
	 * 
	 * @param g
	 *            Graphics object to draw message
	 */
	private void drawStartMessage(Graphics g) {
		g.setFont(new Font("Dialog", Font.BOLD, 16));
		g.drawString("READY?", BOARD_WIDTH / 2 - 10, BOARD_HEIGHT / 2 - 10);

		g.setFont(new Font("Dialog", Font.BOLD, 10));
		g.drawString("PRESS SPACE TO BEGIN", BOARD_WIDTH / 2 - 40, BOARD_HEIGHT / 2 + 10);
	}

	/**
	 * The gameOver method call the nextPanel
	 */
	private void gameOver() {
		this.nextPanel.accept(this.level, this.points);
	}

	/**
	 * The checkFood method check if the snake got the food
	 */
	private void checkfood() {
		if ((snakeXPosition[0] == foodX) && (snakeYPosition[0] == foodY)) {
			squares++;
			this.points += POINTS_VALUE;
			setFoodLocation();
		}
	}

	/**
	 * The move method will move the snake in the game board
	 */
	private void move() {
		// In this loop the body of the snake changes position, starting from
		// the tail to the head
		for (int i = squares; i > 0; i--) {
			snakeXPosition[i] = snakeXPosition[(i - 1)];
			snakeYPosition[i] = snakeYPosition[(i - 1)];
		}

		// In this decision the positions of X/Y is decrease or increase by the
		// size of square, the decision is take from direction given
		if (isGoingLeft) {
			snakeXPosition[0] -= SQUARE_SIZE;

		} else if (isGoingRight) {
			snakeXPosition[0] += SQUARE_SIZE;

		} else if (isGoingUp) {
			snakeYPosition[0] -= SQUARE_SIZE;

		} else if (isGoingDown) {
			snakeYPosition[0] += SQUARE_SIZE;
		}
	}

	/**
	 * The checkCollistion method will check if some part of snake hit bumps
	 */
	private void checkCollision() {

		// Check if snake hit itself
		for (int i = squares; i > 0; i--) {
			if ((i > 4) && (snakeXPosition[0] == snakeXPosition[i]) && (snakeYPosition[0] == snakeYPosition[i])) {
				isPlayingGame = false;
			}
		}

		// Check if snake hit the top
		if (snakeYPosition[0] >= BOARD_HEIGHT) {
			isPlayingGame = false;
		}

		// Check if snake hit the bottom
		if (snakeYPosition[0] < 0) {
			isPlayingGame = false;
		}

		// Check if the snake hit the right side
		if (snakeXPosition[0] >= BOARD_WIDTH) {
			isPlayingGame = false;
		}

		// Check if the snake hit the left side
		if (snakeXPosition[0] < 0) {
			isPlayingGame = false;
		}

		// Check if is still in the game, if not stop the timer
		if (!isPlayingGame) {
			timer.stop();
		}
	}

	/**
	 * The setFoodLocation method set a food some location on the game board
	 */
	private void setFoodLocation() {
		Random random = new Random();
		foodX = random.nextInt(BOARD_COLUMN_COUNT) * SQUARE_SIZE;
		foodY = random.nextInt(BOARD_ROW_COUNT) * SQUARE_SIZE;
	}

	/**
	 * This actionPerformed method will be called from timer and check if is
	 * game over and repaint the game component
	 * 
	 * @param e
	 *            ActionEvent event that receive
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameStarted && isPlayingGame) {
			checkfood();
			checkCollision();
			move();
		}

		repaint();
	}

	/**
	 * The class extends from KeyAdapter to get the key pressed from player, to
	 * decide which direction should took
	 */
	private class GameControl extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if ((key == KeyEvent.VK_LEFT) && (!isGoingRight)) {
				isGoingLeft = true;
				isGoingUp = false;
				isGoingDown = false;

			} else if ((key == KeyEvent.VK_RIGHT) && (!isGoingLeft)) {
				isGoingRight = true;
				isGoingUp = false;
				isGoingDown = false;

			} else if ((key == KeyEvent.VK_UP) && (!isGoingDown)) {
				isGoingUp = true;
				isGoingRight = false;
				isGoingLeft = false;

			} else if ((key == KeyEvent.VK_DOWN) && (!isGoingUp)) {
				isGoingDown = true;
				isGoingRight = false;
				isGoingLeft = false;

			} else if (key == KeyEvent.VK_SPACE && !gameStarted) {
				gameStarted = true;
			}
		}
	}
}
