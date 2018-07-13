package org.extractor.main;

public class GeoMethods {

	
	public static double[] pixelToCoors(int x, int y, double xScale,
			double yScale, double lonStart, double latStart) {
		
			double[] coors = new double[2];
			coors[0] = lonStart + x * xScale;
			coors[1] = latStart - y * yScale;			
			return coors;		
	}
}
