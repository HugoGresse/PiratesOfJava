package fr.imac.javawars.engine;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Base base = new Base(0, null, null, null, 0, 0, 0);
		base.computeDistanceMap(4, 4);
		base.displayDistanceMap();
	}

}
