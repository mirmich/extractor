package org.extractor.main;


import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

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
    
    
    public static void openTiffViaGdal() {
    	
    	
    	Dataset dataset = gdal.Open("world/wc2.0_10m_tavg_01.tif");
    	Band band = dataset.GetRasterBand(1);
    }
    
    public static void openTiffViaNga() throws IOException {
    	
    	File file = new File("world/wc2.0_10m_tavg_01.tif");
    	//File file = new File("world/rendermap.tiff");
        
        TIFFImage tiffImage = TiffReader.readTiff(file);
        
        FileDirectory fileDirectory = tiffImage.getFileDirectories().get(0);        
        Rasters rasters = fileDirectory.readRasters();         
        ByteBuffer byteBuffer = rasters.getSampleValues()[0];        
        Set<Float> values = new HashSet<>();
        float coors[][] = new float[rasters.getWidth()][rasters.getHeight()];
        System.out.println(rasters.getPixel(2159, 1079)[0]);
        
        for(int i = 0; i < rasters.getWidth(); i++) {
        	for(int j = 0; j < rasters.getHeight(); j++) {      
        		
        		coors[i][j] = rasters.getPixel(i, j)[0].floatValue();     		
        		
        	}
        	
        } 
        System.out.println(rasters.getInterleaveIndex(1049, 223));
        
        
        //http://duff.ess.washington.edu/data/raster/drg/docs/geotiff.txt
        //tiepoint je na -180 long a 90 lat
        /*
        byteBuffer.rewind(); 
        while (byteBuffer.hasRemaining())
        {
             values.add(byteBuffer.getFloat());
        }
        
        System.out.println(Collections.max(values));
        //System.out.println(Collections.min(values));
        values.remove(Collections.min(values));
        System.out.println(Collections.min(values));
        System.out.println(fileDirectory.numEntries());
        
        
        System.out.println(fileDirectory.getEntries().size());
        */
        
        float xScale;
        float yScale;
        Set<FileDirectoryEntry> fileDirectories = fileDirectory.getEntries();
        for(FileDirectoryEntry fileE: fileDirectories) {
        	
        	if(fileE.getFieldTag().toString().equals("ModelPixelScale")) {
        		
        		/*
        		yScale = ((float[]) fileE.getValues())[1];
        		System.out.println(xScale);
        		System.out.println(yScale);
        		*/
        		
        	}        	
        	
        } 
        
        
        
    } 
}
