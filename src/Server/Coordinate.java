package Server;

/**
 * @author Janaka
 */
public class Coordinate {
	private float latitude;
	private float longitude;

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public Coordinate(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
