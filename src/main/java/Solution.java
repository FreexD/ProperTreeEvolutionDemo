import java.util.ArrayList;
import java.util.List;

public class Solution implements Comparable<Solution>{

	private int dim;
	private List<Pair> collectionOfPairs;
	private double valueOfRastriginFunction;
	
	public Solution(int dim) {
		super();
		this.dim = dim;
		this.collectionOfPairs = new ArrayList<Pair>();
		for (int i = 0; i < dim; i++) {
			this.collectionOfPairs.add(new Pair());
		}
		this.setValueOfRastriginFunction();
	}
	
	public Solution(int dim, List<Pair> collectionOfPairs) {
		super();
		this.dim = dim;
		this.collectionOfPairs = collectionOfPairs;
		this.setValueOfRastriginFunction();
	}

	public int getDim() {
		return dim;
	}
	public void setDim(int dim) {
		this.dim = dim;
	}
	public List<Pair> getCollectionOfPairs() {
		return collectionOfPairs;
	}
	public void setCollectionOfPairs(List<Pair> collectionOfPairs) {
		this.collectionOfPairs = collectionOfPairs;
	}
	public double getValueOfRastriginFunction() {
		return this.valueOfRastriginFunction;
	}
	public void setValueOfRastriginFunction() {
		double sum = 0;
		double x;
		for (int i = 0; i < this.dim; i++) {
			x = this.collectionOfPairs.get(i).getX();
			sum += Math.pow(x, 2) - 10*Math.cos(2*Math.PI*x);
		}
		this.valueOfRastriginFunction = sum;
	}

	public int compareTo(Solution sol) {
		if(this.getValueOfRastriginFunction() - sol.getValueOfRastriginFunction() > 0)
			return 1;
		else if(this.getValueOfRastriginFunction() - sol.getValueOfRastriginFunction() < 0)
			return -1;
		return 0;
	}
	
}

