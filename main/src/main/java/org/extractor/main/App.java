package org.extractor.main;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import mil.nga.tiff.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    	//File file = new File("world/wc2.0_10m_tavg_01.tif");
    	File file = new File("world/rendermap.tiff");
        
        TIFFImage tiffImage = TiffReader.readTiff(file);
        
        FileDirectory fileDirectory = tiffImage.getFileDirectories().get(0);        
        Rasters rasters = fileDirectory.readRasters();         
        ByteBuffer byteBuffer = rasters.getSampleValues()[0];        
        Set<Float> values = new HashSet<>();
        
        
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
        Set<FileDirectoryEntry> fileDirectories = fileDirectory.getEntries();
        
        for(FileDirectoryEntry fileE: fileDirectories) {
        	
        	System.out.println(fileE.getFieldTag().toString());
        	System.out.println(fileE.getValues().toString());
        }
        
        
        
        
       
        
        
        
        
        
       
                        
        
        
        
        
    }
}
