package org.extractor.main;


import javafx.scene.shape.Polygon;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

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
    	HashMap<String,Double[]> mammalsMap = GeoMethods.createCGRSHashMap();
    	GeoTiff avgTempJan = new GeoTiff("world/wc2.0_10m_tavg_01.tif");
    	//[[[-22.03984,64.47181],[-22.05719,64.92039],[-21,64.92414],[-21,64.47548],[-22.03984,64.47181]]]
    	//System.out.println(GeoMethods.distanceGPS(-21.0, -21.0, 64.92414, 64.47548));
    	
    	Polygon polygon = new Polygon();
    	polygon.getPoints().addAll(mammalsMap.get("29UPR4"));
    	
    	Location pxLocation = avgTempJan.getPxLocation(1041, 240);
    	Location pxLocation2 = avgTempJan.getPxLocation(1042, 240);
    	
    	System.out.println("Coordinates of area 29UPR4 " + Arrays.toString(mammalsMap.get("29UPR4")));
    	System.out.println("Latitude " + pxLocation.getLatitude());
    	System.out.println("Longitude " + pxLocation.getLongitude());    	
    	System.out.println("Point mentioned above inside polygon: " + polygon.contains(pxLocation.getLongitude(), pxLocation.getLatitude()));
    	System.out.println(GeoMethods.distanceGPS(pxLocation.getLongitude(), pxLocation2.getLongitude(), pxLocation.getLatitude(), pxLocation2.getLatitude()));
    	Location middleLocation = GeoMethods.middleCoorOfPolygon(mammalsMap.get("29UPR4"));
    	System.out.println("Middle coors: " + middleLocation);    	
    	System.out.println("Pixel coors of GPS Coors "  + Arrays.toString(avgTempJan.coorsToPixel(pxLocation2)));
    	System.out.println("Pixel coors of Middle GPS Coors "  + Arrays.toString(avgTempJan.coorsToPixel(middleLocation)));
    	System.out.println(avgTempJan.getPxValue(1041, 240));
    	System.out.println(avgTempJan.getPxValue(1042, 240));
    	
    	
    }
    
    
    
    
    
    
    
}
