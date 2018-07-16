package org.extractor.main;

public class GeoMethods {

	
	public static double[] pixelToCoors(int x, int y, double xScale,
			double yScale, double lonStart, double latStart) {
		
			double[] coors = new double[2];
			coors[0] = lonStart + x * xScale;
			coors[1] = latStart - y * yScale;			
			return coors;		
	}
	public static double distanceGPS(double lon1, double lon2, double lat1, double lat2) {
		double distance = 0;
		double r = 6371e3; // radius of Earth in 
		double phi1 = Math.toRadians(lat1);
		double phi2 = Math.toRadians(lat2);
		double deltaPhi = Math.toRadians(lat2 - lat1);
		double deltaLambda = Math.toRadians(lon2 - lon1);
		
		double a = Math.pow(Math.sin(deltaPhi/2),2) + 
				Math.cos(phi1/2) * Math.cos(phi2/2) * 
				Math.pow(Math.sin(deltaLambda/2), 2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 -a ));
		
		distance = r * c;
		
        
		return distance;
		
		
	}
	
	
	
}
