package org.extractor.main;


import java.io.File;
import java.io.IOException;



import mil.nga.tiff.*;

/**
 * Hello world!
 *https://www.eea.europa.eu/data-and-maps/figures/common-european-chorological-grid-reference-system-cgrs
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	openTiffViaNga();
    	/*
    	File file = new File("world/cgrs_grid.eps.75dpi.tif");
    	TIFFImage tiffImage = TiffReader.readTiff(file);        
        FileDirectory fileDirectory = tiffImage.getFileDirectories().get(0);
        Rasters rasters = fileDirectory.readRasters();  
        System.out.println(rasters.getPixelSample(0, 500, 500));
        */
    }
    
    
    
    
    public static void openTiffViaNga() throws IOException {
    	
    	File file = new File("world/wc2.0_10m_tavg_01.tif");       
        TIFFImage tiffImage = TiffReader.readTiff(file);        
        FileDirectory fileDirectory = tiffImage.getFileDirectories().get(0);        
        Rasters rasters = fileDirectory.readRasters();        
        float coors[][] = new float[rasters.getWidth()][rasters.getHeight()];        
        
        for(int i = 0; i < rasters.getWidth(); i++) {
        	for(int j = 0; j < rasters.getHeight(); j++) {      
        		
        		coors[i][j] = rasters.getPixel(i, j)[0].floatValue();     		
        		
        	}        	
        } 
        
        
        double xScale = 0;
        double yScale = 0;
        double lonStart = 0;
        double latStart = 0;        
        
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
        
        double[] loc1Coors = GeoMethods.pixelToCoors(1049, 238, xScale, yScale, lonStart, latStart);
        double[] loc2Coors = GeoMethods.pixelToCoors(1050, 238, xScale, yScale, lonStart, latStart);
        Location loc1 = new Location(loc1Coors[0],loc1Coors[1]);
        Location loc2 = new Location(loc2Coors[0],loc2Coors[1]);
        System.out.println(loc1);
        System.out.println(loc2);
        System.out.println(loc1.distanceTo(loc2));
        
        
        
        
        
    } 
}
