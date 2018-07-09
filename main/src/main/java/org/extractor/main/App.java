package org.extractor.main;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mil.nga.tiff.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    	File file = new File("world/wc2.0_10m_tavg_01.tif");
        
        TIFFImage tiffImage = TiffReader.readTiff(file);
        
        
        
        FileDirectory fileDirectory = tiffImage.getFileDirectories().get(0);
        System.out.println(fileDirectory.getImageWidth());
        Rasters rasters = fileDirectory.readRasters();
        
        System.out.println(fileDirectory.getEntries().size());
        System.out.println(rasters.getPixel(0, 0)[0]);
       
                        
        
        
        
        
    }
}
