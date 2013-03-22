package com.example.mapa1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityAddress extends Activity {
	public static Intent mapa;
	EditText origemEdit;
	EditText destinoEdit;
	Button send;
	public static final String LATLNG = "address";
	public static double [] endereco = new double [4];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_layout);
		origemEdit = (EditText)findViewById(R.id.origemEditText1);
		destinoEdit = (EditText)findViewById(R.id.destinoEditText2);
		
				
	}
	public void showMap(View view)
	{
		GeocodingTask processo = new GeocodingTask(this);
		processo.execute();
		mapa = new Intent (ActivityAddress.this, MainActivity.class);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_address, menu);
		return true;
	}
	
	class GeocodingTask extends AsyncTask<String,Void,Void>
	{
		Context mContext;
		
		
		public GeocodingTask(Context context){
			super();
			mContext = context;
		}
		

		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			List<Address> address1,address2 = null;
			try {
				
				address1 = geocoder.getFromLocationName(origemEdit.getText().toString(),10);
				address2 = geocoder.getFromLocationName(destinoEdit.getText().toString(),10);
				//endereco = new ArrayList<Double>();
				endereco[0] = address1.get(0).getLatitude();
				endereco[1] = address1.get(0).getLongitude();
				endereco[2] = address2.get(0).getLatitude();
				endereco[3] = address2.get(0).getLongitude();
				
				
				//endereco.addAll(address2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{ 
			super.onPostExecute(result); 
			mapa.putExtra("key", endereco);
			mContext.startActivity(mapa);
			/*mapa.putExtra("send1", endereco.get(0));
			mapa.putExtra("send2", endereco.get(1));
			mapa.putExtra("send3", endereco.get(2));
			mapa.putExtra("send4", endereco.get(3));
			mContext.startActivity(mapa);*/
			
		}
		
	}

}
