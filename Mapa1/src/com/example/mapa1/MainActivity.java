package com.example.mapa1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import android.os.AsyncTask;
import com.google.android.maps.*; 
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	public double [] endereco;
	@Override 
	protected void onCreate(Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		SupportMapFragment fragment =  
			     (SupportMapFragment)getSupportFragmentManager()  
			       .findFragmentById(R.id.map);
		
		GoogleMap map = fragment.getMap();
		
		Bundle extras = getIntent().getExtras();
		endereco = new double [4];
		endereco = extras.getDoubleArray("key");
		
		//long a = extras.getLong("send1");
		//long b = extras.getLong("send2");
		//long c = extras.getLong("send3");
		//long d = extras.getLong("send4");
		
		LatLng latLng = new LatLng(endereco[0],endereco[1]);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12.0f)); //zoom no mapa
		 /*map.addMarker(new MarkerOptions()  
		      .position(latLng)  
		      .icon(BitmapDescriptorFactory.fromResource(  
		        R.drawable.ic_launcher))  
		      .title("Av. Paulista")  
		      .snippet("São Paulo"));   
		    
		    configuraPosicao(map, latLng);*/
		 /*new RotaAsyncTask(this, map).execute(  
			      // Latitude, Logintude de Origem  
			      latLng.latitude, latLng.longitude,      
			      // Latitude, Longitude de Destino  
			      -23.604262,-46.676513);*/    
		new RotaAsyncTask(this, map).execute(  
			      // Latitude, Logintude de Origem  
				latLng.latitude,latLng.longitude,      
			      // Latitude, Longitude de Destino  
			      endereco[2],endereco[3]);
	}
	public class RotaAsyncTask extends AsyncTask<Double, Void, Void>{  
		  
		  private ProgressDialog dialog = new ProgressDialog(MainActivity.this);  
		  private GoogleMap mapView;  
		  private Context context;  
		  private Route rota;  
		  
		  public RotaAsyncTask(Context ctx, GoogleMap mapa) {  
		    mapView = mapa;  
		    context = ctx;  
		  }  
		   
		  @Override  
		  protected void onPreExecute() {  
		    super.onPreExecute();  
		   // dialog = ProgressDialog.show( "Aguarde",   
		    //  "Calculando rota");
		    dialog.setMessage("Loading...");
		    dialog.show();
		  }  
		   
		  @Override  
		  protected Void doInBackground(Double... params) {  
		  
			  LatLng aux = new LatLng(params[0], params[1]);
			  
			  GeoPoint saida = new GeoPoint((int)(aux.latitude*1E6),(int)(aux.longitude*1E6));
			  
			  LatLng aux1 = new LatLng(params[2],params[3]);
			  
			  GeoPoint destino = new GeoPoint((int)(aux1.latitude*1E6),(int)(aux1.longitude*1E6));
			  
			  rota = directions(saida, destino);  
		    return null;  
		  }  
		   
		  @Override  
		  protected void onPostExecute(Void result) {  
		    super.onPostExecute(result);  
		    PolylineOptions options = new PolylineOptions();  
		      options.width(5);  
		      options.color(Color.RED);   
		      options.visible(true);  
		    
	    for (LatLng latlng : rota.getPoints1()) 
		    {
		     // options.add(new LatLng((float)(latlng.getLatitudeE6()/1E6),(float)(latlng.getLongitudeE6()/1E6)));
	    		options.add(latlng);
		    }  
		   
		   //options.add(new LatLng(-23.561660,-46.655941)); 
		  // options.add(new LatLng( -23.556459,-46.661750));  
		    mapView.addPolyline(options);  
		    
		    dialog.dismiss();  
		  }  
		   
		  private Route directions(  
		    final GeoPoint start, final GeoPoint dest) {  
		    
		    // Formatando a URL com a latitude e longitude    
		    // de origem e destino.    
		    String urlRota = String.format(Locale.US,  
		      "http://maps.googleapis.com/maps/api/"+  
		      "directions/json?origin=%f,%f&"+  
		      "destination=%f,%f&" +  
		      "sensor=true&mode=driving",     
		      start.getLatitudeE6()/1E6,   
		      start.getLongitudeE6()/1E6,   
		      dest.getLatitudeE6()/1E6,   
		      dest.getLongitudeE6()/1E6);   
		    
		    GoogleParser parser;  
		    parser = new GoogleParser(urlRota);  
		    return parser.parse();  
		  }  
		}  

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
