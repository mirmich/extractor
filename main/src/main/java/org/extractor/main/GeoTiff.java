package org.extractor.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mil.nga.tiff.FileDirectory;
import mil.nga.tiff.FileDirectoryEntry;
import mil.nga.tiff.Rasters;
import mil.nga.tiff.TIFFImage;
import mil.nga.tiff.TiffReader;
import java.util.stream.*;

public class GeoTiff {
	
	private double xScale = 0;
    private double yScale = 0;
    private double lonStart = 0;
    private double latStart = 0;
    private double[][] values;
    
	
	public GeoTiff(String path) throws IOException {
		
		File file = new File(path);       
        TIFFImage tiffImage = TiffReader.readTiff(file);        
        FileDirectory fileDirectory = tiffImage.getFileDirectories().get(0);        
        Rasters rasters = fileDirectory.readRasters();  
        int width = rasters.getWidth();
        int height = rasters.getHeight();
        int widthExt = width + 4;
        int heightExt = height + 4;
        values = new double[widthExt][heightExt];  
        int x = 0;
        int y = 0;
        
        for(int i = 0; i < widthExt; i++) {
        	for(int j = 0; j < heightExt; j++) {      
        		
        		//values[i+2][j+2] = rasters.getPixel(i, j)[0].floatValue();  
        		
        		if(i > 1 && i < widthExt - 2){
					x = i - 2;
				}else {
					x = Math.abs(width - Math.abs(i - 2));
				}
				
				if(j > 1 && (j < heightExt - 2)) {
					y = j - 2;
				}
				else {
					y = Math.abs(height - Math.abs(j - 2));
				}
				
				values[i][j] = rasters.getPixel(x, y)[0].doubleValue();
        		
        	}        	
        }  
        
        // awful piece of code to extract scale and start params
        for(FileDirectoryEntry fileE: fileDirectory.getEntries()) {
        	
        	if(fileE.getFieldTag().toString().equals("ModelPixelScale")) { 	        		
        		
        		// get values of x y Scale
        		String[] scales = fileE.getValues().toString().replace("[","").replace("]", "").split(",");        		        	
        		xScale = Double.valueOf(scales[0].trim());
        		yScale = Double.valueOf(scales[1].trim());       		
        		
        	}else if(fileE.getFieldTag().toString().equals("ModelTiepoint")) {
        		// get Longitude, Latitude start positions
        		String[] tiePointCoors = fileE.getValues().toString().replace("[","").replace("]", "").split(",");        		
        		lonStart = Double.valueOf(tiePointCoors[3].trim());
        		latStart = Double.valueOf(tiePointCoors[4].trim());
        		
        	}       	
        }
        
	}
	
	public Location getPxLocation(int x, int y) {
		double[] loc1Coors = pixelToCoors(x, y);
        return new Location(loc1Coors[0],loc1Coors[1]);        
	}
	
	public double getPxValue(int x, int y) {
		return values[x+2][y+2];
	}
	
	public  double[] pixelToCoors(int x, int y) {
		
			double[] coors = new double[2];
			coors[0] = lonStart + x * xScale;
			coors[1] = latStart - y * yScale;			
			return coors;		
	}
	
	public int[] coorsToPixel(Location loc) {
		int pixelCoors[] = new int[2];
		pixelCoors[0] = (int) ((loc.getLongitude() - lonStart) / xScale);
		pixelCoors[1] = (int) ((loc.getLatitude() - latStart) / yScale) * -1;
		
		return pixelCoors;
		
		
	}
	
	public double getAvgFromNeighborhood(int[] point, double[] refPoly) 	{
		
		double[] coverages = new double[5*5];
		int k = 0;
		
		for (int i = point[0] - 2; i < point[0] + 2; i++) {
			for (int j = point[1] - 2; j < point[1] + 2; j++) {
				
				coverages[k] = GeoMethods.getArea(PolygonIntersect.getIntersect(refPoly, getPixelBoundaries(i,j)));
			}
			
		}
		//System.out.println(Arrays.toString(coverages));
		System.out.println(GeoMethods.getArea(refPoly));
		System.out.println(DoubleStream.of(coverages).sum());
		System.out.println(DoubleStream.of(coverages).sum()/(GeoMethods.getArea(refPoly)/100));
		return 0;
		
		
	
	}
	
	// returns ale GPS coordinates bound pixel
	public double[] getPixelBoundaries(int x, int y) {
		
		double[] boundaries = new double[10];	
		int k = 0;
		double tempX = 0;
		double tempY = 0;
		
		for(int i = 0; i <= 1; i++) {
			for(int j = 0; j <= 1; j++) {
				Location corner = getPxLocation(x + i, y + j);
				boundaries[k] = corner.getLongitude();
				boundaries[k + 1] = corner.getLatitude();
				k = k + 2;				
			}			
		}
		// make polygon points in counter-clock order
		
		tempX = boundaries[0];
		boundaries[0] = boundaries[2];
		boundaries[2] = tempX;
		
		tempY = boundaries[1];
		boundaries[1] = boundaries[3];
		boundaries[3] = tempY;
		boundaries[8] = boundaries[0];
		boundaries[9] = boundaries[1];
		
		return boundaries;		
	}
	
	
	


}
