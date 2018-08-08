package org.extractor.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolygonIntersect {

	
	public static double[] getIntersect(double[] reference, double[] incident) {
		
		double[][] linesReference = getLines(reference);
		double[][] linesIncident = getLines(incident);
		
		
		
		return new double[3];
	}
	
	public static double[][] getLines(double[] polygon) {
		int length = polygon.length/2 - 1;
		
		double[][] lines = new double[length][length];
		int k = 0;		
		
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) {
				lines[i][j] = polygon[k + j];			
			}
			k += 2;			
		}
		return lines;
	}
	
	public static boolean isFront(double[] line, double[] point) {	
		
		double result = ((line[3] - line[1]) * point[0] - (line[2] - line[0]) * point[1] + line[2] * line[1] - line[3]*line[0])/
				Math.sqrt(Math.pow((line[3] - line[1]), 2) + Math.pow((line[2] - line[0]), 2));
		
		return result >= 0;
	}
	
	public static double[] intersection(double[] a, double[] b, double[] p, double[] q) {
        double A1 = b[1] - a[1];
        double B1 = a[0] - b[0];
        double C1 = A1 * a[0] + B1 * a[1];
 
        double A2 = q[1] - p[1];
        double B2 = p[0] - q[0];
        double C2 = A2 * p[0] + B2 * p[1];
 
        double det = A1 * B2 - A2 * B1;
        double x = (B2 * C1 - B1 * C2) / det;
        double y = (A1 * C2 - A2 * C1) / det;
 
        return new double[]{x, y};
    }
	
}
