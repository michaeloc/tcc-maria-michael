package com.example.mapa1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;

public class GoogleParser
{
	 protected URL feedUrl;  
	 List<GeoPoint> ponto;
	  public GoogleParser(String feedUrl) {  
	    try {  
	      this.feedUrl = new URL(feedUrl);  
	    } catch (MalformedURLException e) {  
	    }  
	  }  
	   
	  public Route parse() {  
	    // Cria uma rota vazia  
	    final Route route = new Route();  
	    try {  
	      // Obt�m a String do JSON  
	      final String result =   
	        convertStreamToString(  
	          feedUrl.openConnection()  
	            .getInputStream());  
	  
	      // Transforma a string em JSON  
	      JSONObject json = new JSONObject(result);  
	      // Pega a primeira rota retornada  
	      JSONObject jsonRoute =   
	        json.getJSONArray("routes")  
	          .getJSONObject(0);  
	      JSONObject leg = jsonRoute  
	        .getJSONArray("legs").getJSONObject(0);  
	  
	      // Obt�m os passos do caminho  
	      JSONArray steps = leg.getJSONArray("steps");  
	  
	      final int numSteps = steps.length();  
	      /* 
	       * Itera atrav�s dos passos, decodificando  
	       * a polyline e adicionando � rota. 
	       */  
	      JSONObject step;  
	      for (int i = 0; i < numSteps; i++) {  
	        // Obt�m o passo corrente  
	        step = steps.getJSONObject(i);  
	        // Decodifica a polyline e adiciona � rota  
	      /*  List<LatLng> aux = decodePolyLine(step.getJSONObject("polyline").getString("points"));
	        for (LatLng iterator : aux){
	        	GeoPoint aux1 = new GeoPoint((int)(iterator.latitude*1E6),(int)(iterator.longitude*1E6));
	        	ponto = new ArrayList<GeoPoint>();
	        	ponto.add(aux1);
	        	}*/
	        //route.addPoints(ponto);
	       route.addPoints1(decodePolyLine((step.getJSONObject("polyline").getString("points"))));
	      }  
	    } catch (Exception e) {  
	    }  
	    return route;  
	  }  
	  
	  private String convertStreamToString(  
	    final InputStream input) {  
	  
	    final BufferedReader reader =   
	      new BufferedReader(  
	        new InputStreamReader(input));  
	    final StringBuilder sBuf = new StringBuilder();  
	  
	    String line = null;  
	    try {  
	      while ((line = reader.readLine()) != null) {  
	        sBuf.append(line);  
	      }  
	    } catch (IOException e) {  
	    } finally {  
	      try {  
	        input.close();  
	      } catch (IOException e) {  
	      }  
	    }  
	    return sBuf.toString();  
	  }  
	  
	  private List<LatLng> decodePolyLine(final String poly) {  
	    int len = poly.length();  
	    int index = 0;  
	    List<LatLng> decoded =   
	      new ArrayList<LatLng>();  
	  
	    int lat = 0;  
	    int lng = 0;  
	  
	    while (index < len) {  
	      int b;  
	      int shift = 0;  
	      int result = 0;  
	      do {  
	        b = poly.charAt(index++) - 63;  
	        result |= (b & 0x1f) << shift;  
	        shift += 5;  
	      } while (b >= 0x20);  
	      int dlat =   
	        ((result & 1) != 0 ?   
	          ~(result >> 1) :   
	          (result >> 1));  
	      lat += dlat;  
	  
	      shift = 0;  
	      result = 0;  
	      do {  
	        b = poly.charAt(index++) - 63;  
	        result |= (b & 0x1f) << shift;  
	        shift += 5;  
	      } while (b >= 0x20);  
	  
	      int dlng = ((result & 1) != 0 ?   
	        ~(result >> 1) :   
	        (result >> 1));  
	      lng += dlng;  
	  
	     /* decoded.add(  
	        new LatLng(  
	          (float)(lat / 1E5),  
	          (float)(lng / 1E5)));*/  
	      LatLng aux = new LatLng((float)(lat / 1E5),(float)(lng / 1E5));
	      
	      //int b =  (int) (aux.latitude*1E6);
	      decoded.add(aux);
	    }  
	    
	    
	    
	    return decoded;  
	  }  

}
