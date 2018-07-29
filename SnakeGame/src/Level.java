/**
 * This is a ENUM class to hold level names
 * @author C0735536, C0729309, C0737700
 *
 */
public enum Level {
	EASY(90), NORMAL(60), HARD(40);

	private int levelSpeed;

	Level(int levelSpeed) {
		this.levelSpeed = levelSpeed;
	}

	/**
	 * The getLevelSpeed method return levelSpeed
	 * @return levelSpeed
	 */
	public int getLevelSpeed() {
		return levelSpeed;
	}
}