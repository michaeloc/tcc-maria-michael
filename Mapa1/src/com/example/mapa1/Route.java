package com.example.mapa1;



import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;

public class Route {
	private final List<GeoPoint> points;  
	private final List <LatLng> points1;
	  private String polyline;  
	  
	  public Route() {  
	    points = new ArrayList<GeoPoint>();  
	    points1 = new ArrayList<LatLng>();
	  }  
	  
	  public void addPoints(final List<GeoPoint> points) {  
	    this.points.addAll(points);  
	  }  
	  public void addPoints1(final List<LatLng> points){
		  this.points1.addAll(points);
	  }
	  public List<LatLng> getPoints1(){
		  return points1;
	  }
	  public List<GeoPoint> getPoints() {  
	    return points;  
	  }  
	  
	  public void setPolyline(String polyline) {  
	    this.polyline = polyline;  
	  }  
	  
	  public String getPolyline() {  
	    return polyline;  
	  }  

}
