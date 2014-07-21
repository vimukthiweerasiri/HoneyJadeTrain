package Server;

/**
 * @author Janaka
 */
public class Station {
	private int id;
	private Coordinate upside;
	private Coordinate downside;
	private boolean passed = false;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the upside
	 */
	public Coordinate getUpside() {
		return upside;
	}

	/**
	 * @return the downside
	 */
	public Coordinate getDownside() {
		return downside;
	}
	
	public Station(int id, Coordinate upside, Coordinate downside) {
		this.id = id;
		this.upside = upside;
		this.downside = downside;
	}

	/**
	 * @return the isPassed
	 */
	public boolean isPassed() {
		return passed;
	}
	
	public void setPassed() {
		this.passed = true;
	}
}
