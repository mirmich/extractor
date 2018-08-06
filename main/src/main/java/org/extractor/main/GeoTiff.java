package org.extractor.main;

import java.io.File;
import java.io.IOException;

import mil.nga.tiff.FileDirectory;
import mil.nga.tiff.FileDirectoryEntry;
import mil.nga.tiff.Rasters;
import mil.nga.tiff.TIFFImage;
import mil.nga.tiff.TiffReader;

public class GeoTiff {
	
	private double xScale = 0;
    private double yScale = 0;
    private double lonStart = 0;
    private double latStart = 0;
    private float[][] values;
	
	public GeoTiff(String path) throws IOException {
		
		File file = new File(path);       
        TIFFImage tiffImage = TiffReader.readTiff(file);        
        FileDirectory fileDirectory = tiffImage.getFileDirectories().get(0);        
        Rasters rasters = fileDirectory.readRasters();        
        values = new float[rasters.getWidth()][rasters.getHeight()];        
        
        for(int i = 0; i < rasters.getWidth(); i++) {
        	for(int j = 0; j < rasters.getHeight(); j++) {      
        		
        		values[i][j] = rasters.getPixel(i, j)[0].floatValue();     		
        		
        	}        	
        } 
        
        
                
        
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
	
	public float getPxValue(int x, int y) {		
		return values[x][y];
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
	


}
