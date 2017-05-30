import java.util.Random;


public class Pair {

	private double x;
	private double s;
	
	public Pair() {
		super();
		
		this.x = randomInRange(-5.12, 5.12);
		this.s = randomInRange(0, 5.12);
	}
	
	public Pair(double x, double s) {
		super();
		this.x = x;
		this.s = s;
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getS() {
		return s;
	}
	public void setS(double s) {
		this.s = s;
	}
	
	public static double randomInRange(double min, double max) {
		
		Random random = new Random();
		double range = max - min;
		double scaled = random.nextDouble() * range;
		double shifted = scaled + min;
		return shifted; // == (rand.nextDouble() * (max-min)) + min;
	}
}
