package org.extractor.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeoMethods {

	
	
	public static double distanceGPS(double lon1, double lon2, double lat1, double lat2) {
		double distance = 0;
		double r = 6371e3; // radius of Earth in 
		double phi1 = Math.toRadians(lat1);
		double phi2 = Math.toRadians(lat2);
		double deltaPhi = Math.toRadians(lat2 - lat1);
		double deltaLambda = Math.toRadians(lon2 - lon1);
		
		double a = Math.pow(Math.sin(deltaPhi/2),2) + 
				Math.cos(phi1/2) * Math.cos(phi2/2) * 
				Math.pow(Math.sin(deltaLambda/2), 2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 -a ));
		
		distance = r * c;
		
        
		return distance;
		
		
	}
	
	public static JSONObject getCoorsJSON() throws JSONException, IOException {
		
		String url = "https://www.european-mammals.org/osm/EMMA2grid.php";
		File file = new File("mammals/CGRSJSON.txt");
		JSONObject cgrsJson;
		
		if(file.exists()) {			
			cgrsJson = readJSONFromFile(file.getPath());
			
		}else {			
			cgrsJson = readJSONFromUrl(url);
			writeJSONToFile(cgrsJson,file.getPath());				
		}
		return cgrsJson;
		
	}
	
	public static JSONObject readJSONFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = rd.lines().collect(Collectors.joining()); 
          //System.out.println(jsonText);
          JSONObject json = new JSONObject(jsonText);
          
          
          return json;
        } finally {
          is.close();
        }
      }
	
	public static void writeJSONToFile(JSONObject obj, String path) {
		
		try (FileWriter file = new FileWriter(path)) {
			file.write(obj.toString());			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JSONObject readJSONFromFile(String url) throws FileNotFoundException {
		
		BufferedReader rd = new BufferedReader(new FileReader(url));
		String jsonText = rd.lines().collect(Collectors.joining());          
        JSONObject json = new JSONObject(jsonText);
        return json;
	}
	
	public static HashMap<String, Double[]> createCGRSHashMap() throws JSONException, IOException{
		
		JSONArray jsonArray = new JSONArray(getCoorsJSON().get("features").toString());
		HashMap<String,Double[]> cgrsCoordinates = new HashMap<>();
		
		for(int i = 0; i < jsonArray.length(); i++) {
			
			JSONObject cgrsCellJson = ((JSONObject) jsonArray.get(i));
	    	JSONObject geometry = ((JSONObject) cgrsCellJson.get("geometry"));
	    	JSONObject properties = (JSONObject) cgrsCellJson.get("properties");   	
	    	
	    	String[] coorsList = geometry.get("coordinates").toString()
	    			.replace("[", "").replace("]", "").split(",");
	    	
	    	Double[] doubleCoors = new Double[coorsList.length];
	    	
	    	for(int j =0; j < coorsList.length; j++) {
	    		doubleCoors[j] = Double.parseDouble(coorsList[j]);    		
	    	}
	    	
	    	cgrsCoordinates.put(properties.getString("CGRSName"), doubleCoors);   	
			
		}
		
    	return cgrsCoordinates;
		
	}
	
	public static Location middleCoorOfPolygon(Double[] polygon) {
		
		Double height = polygon[1] + polygon[3];
		Double width = polygon[0] + polygon[4];
		
		return new Location(width/2,height/2);		
	}
	public static double getArea(Double[] points) {
		double area = 0;
		
		for(int i = 0; i < points.length-2; i += 2) {
			area += (points[i] * points[i + 3]) - (points[i + 1] * points[i + 2]); 
			
		}
		
		
		return -(area/2);
	}
	
	
	
}
