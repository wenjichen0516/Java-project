/**
 * This is a model class to hold ranking item information
 * @author C0735536, C0729309, C0737700
 *
 */
public class RankingItem {
	private String name;
	private int points;

	public RankingItem (String name, int points) {
		this.name = name;
		this.points = points;
	}
	
	/**
	 * The getName method get the name that rankingItem hold
	 * @return name the name of the rankingItem
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * The getPoints method get the points that rankingItem hold
	 * @return points the points of the rankingItem
	 */
	public int getPoints() {
		return points;
	}
}
