package org.extractor.main;




import java.awt.geom.Area;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import mil.nga.tiff.FileDirectory;
import mil.nga.tiff.FileDirectoryEntry;
import mil.nga.tiff.Rasters;
import mil.nga.tiff.TIFFImage;
import mil.nga.tiff.TiffReader;


public class App 
{
    @SuppressWarnings("restriction")
	public static void main( String[] args ) throws IOException
    {
    	// 1041,240 - px pidi ostrova u GB 29UPR4 jeho ID
    	 
    	// HashMap stores data from european mammals, cgrs cells with coors
    	HashMap<String,double[]> mammalsMap = GeoMethods.createCGRSHashMap();
    	GeoTiff avgTempJan = new GeoTiff("world/wc2.0_10m_tavg_01.tif");
    	 	
    	
    	
    	double[] biggerPoly = mammalsMap.get("29UPR4");
    	//double[] smallerPoly = avgTempJan.getPixelBoundaries(1042, 240); 	    	
    	System.out.println("Coordinates of area 29UPR4 " + Arrays.toString(biggerPoly));
    	//System.out.println("Boundaries of pixel " + Arrays.toString(smallerPoly)); 
    	
    	Location middleLocation = GeoMethods.middleCoorOfPolygon(biggerPoly);
    	System.out.println("Middle coors: " + middleLocation);  
    	int[] middlePixel = avgTempJan.coorsToPixel(middleLocation);
    	System.out.println(Arrays.toString(middlePixel));
    	System.out.println(avgTempJan.getAvgFromNeighborhood(new int[] {1041,240}, biggerPoly));
    	
    	
    	//System.out.println(Arrays.toString(PolygonIntersect.getIntersect(biggerPoly, smallerPoly)));
    	//System.out.println(GeoMethods.getArea((PolygonIntersect.getIntersect(biggerPoly, smallerPoly))));
    	
    	//PolygonIntersect.getIntersect(refPoly, incPoly);
    	/*
    	 *
    	 * Location pxLocation = avgTempJan.getPxLocation(1041, 240);
    	Location pxLocation2 = avgTempJan.getPxLocation(1042, 240);
    	
    	
    	System.out.println("Latitude " + pxLocation.getLatitude());
    	System.out.println("Longitude " + pxLocation.getLongitude());    	
    	//System.out.println("Point mentioned above inside polygon: " + polygon.contains(pxLocation.getLongitude(), pxLocation.getLatitude()));
    	System.out.println(GeoMethods.distanceGPS(pxLocation.getLongitude(), pxLocation2.getLongitude(), pxLocation.getLatitude(), pxLocation2.getLatitude()));
    	
    	Location middleLocation = GeoMethods.middleCoorOfPolygon(mammalsMap.get("29UPR4"));
    	System.out.println("Middle coors: " + middleLocation);    	
    	System.out.println("Pixel coors of GPS Coors "  + Arrays.toString(avgTempJan.coorsToPixel(pxLocation2)));
    	System.out.println("Pixel coors of Middle GPS Coors "  + Arrays.toString(avgTempJan.coorsToPixel(middleLocation)));    	
    	
    	
    	
    	
    	
    	System.out.println(GeoMethods.getArea(mammalsMap.get("29UPR4")));
    	 */
    	/*
    	int lenFirst = 4;    	
    	double[][] puvodni = new double[lenFirst][lenFirst];
    	
    	int lengthExt = 8;
		double[][] pole = new double[lengthExt][lengthExt];
		
		for(int i = 0; i < lenFirst;i++) {
			for(int j = 0; j <lenFirst;j++) {				
				puvodni[i][j] = ThreadLocalRandom.current().nextInt(3, 9+ 1);				
			}
		}
		
		System.out.println(Arrays.toString(puvodni[0]));
		System.out.println(Arrays.toString(puvodni[1]));
		System.out.println(Arrays.toString(puvodni[2]));
		System.out.println(Arrays.toString(puvodni[3]));
		int x = 0;
		int y = 0;
		
		for(int i = 0; i < lengthExt;i++) {
			for(int j = 0; j < lengthExt;j++) {
				
				if(i > 1 && i < lengthExt - 2){
					x = i - 2;
				}else {
					x = Math.abs(lenFirst - Math.abs(i - 2));
				}
				
				if(j > 1 && (j < lengthExt - 2)) {
					y = j - 2;
				}
				else {
					y = Math.abs(lenFirst - Math.abs(j - 2));
				}
				
				pole[i][j] = puvodni[x][y];
			}
			
		}
		
		System.out.println(Arrays.toString(pole[0]));
		System.out.println(Arrays.toString(pole[1]));
		System.out.println(Arrays.toString(pole[2]));
		System.out.println(Arrays.toString(pole[3]));
		System.out.println(Arrays.toString(pole[4]));
		System.out.println(Arrays.toString(pole[5]));
		System.out.println(Arrays.toString(pole[6]));
		System.out.println(Arrays.toString(pole[7]));
		
		*/
    	
    	
    	
    
    	
    	
    }
    
    
    
    
    
    
    
}
