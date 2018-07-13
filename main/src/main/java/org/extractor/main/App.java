package org.extractor.main;


import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Scanner;

import org.gdal.gdal.gdal;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import mil.nga.tiff.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	openTiffViaNga();
    	  
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
        
        System.out.println(GeoMethods.pixelToCoors(1049, 238, xScale, yScale, lonStart, latStart)[0]);
        System.out.println(GeoMethods.pixelToCoors(1049, 238, xScale, yScale, lonStart, latStart)[1]);
        System.out.println(GeoMethods.pixelToCoors(1050, 238, xScale, yScale, lonStart, latStart)[0]);
        System.out.println(GeoMethods.pixelToCoors(1050, 238, xScale, yScale, lonStart, latStart)[1]);
        
        
    } 
}
