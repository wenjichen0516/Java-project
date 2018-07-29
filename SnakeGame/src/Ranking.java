import java.util.ArrayList;
import java.util.Collections;
/**
 * This is a model class to hold ranking information
 * @author C0735536, C0729309, C0737700
 *
 */
public class Ranking {
	private Level level;
	private ArrayList<RankingItem> rankingItems = new ArrayList<RankingItem>();

	/** 
	 * Constructor
	 * @param level level of the ranking
	 */
	public Ranking(Level level) {
		this.level = level;
	}

	/**
	 * The getLevel method return level of the ranking
	 * @return level level of the game
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * The getRankingItems method return the rankingItems
	 * @return rankingItems The ArrayList of RankingItem
	 */
	public ArrayList<RankingItem> getRankingItems() {
		return rankingItems;
	}

	/**
	 * The addRankingItem method add a RankingItem and after that is sort to highest points
	 * @param rankingItem
	 */
	public void addRankingItem(RankingItem rankingItem) {
		this.rankingItems.add(rankingItem);
		if (this.rankingItems.size() <= 1) return;
		this.sortByBestRanking();
	}
	
	/**
	 * The sortByBestRanking method sort the rankingItems
	 */
	private void sortByBestRanking() {
		Collections.sort(this.rankingItems, (r1, r2) -> this.compareRankingItem(r1, r2));
	}
	
	/**
	 * The compareRankingItem method compare two RankingItem and return -1 if the first one is higher than second, 
	 * return 1 if is contrary. Or return 0 when is equal
	 * @param r1 RankingItem to compare
	 * @param r2 RankingItem to compare
	 * @return 
	 */
	private int compareRankingItem (RankingItem r1, RankingItem r2) {
		if (r1.getPoints() > r2.getPoints()) {
			return -1;
		}
		
		else if (r1.getPoints() < r2.getPoints()) {
			return 1;
		}
		
		return 0;
	}
}
