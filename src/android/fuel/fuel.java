package android.fuel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import org.apache.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.util.EntityUtils;

import android.app.Activity;

import android.net.ConnectivityManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView; 
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


public class fuel extends Activity {
    /** Called when the activity is first created. */
	private ImageButton btn_lower, btn_upper, btn_lower2, btn_upper2;
	private Button btn_done, btn_cancel;
	private Spinner spn_type;
	
	public double iOilPrice;
	public int iTotalMetre;
	public int iItemPrice, iItemMetre;
	
	public int iFreqItemPrice;
	
	public String uriAPI = "http://211.157.41.203/test/0305.htm";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //init value
        iFreqItemPrice = 200;
        
        iOilPrice = 6.7;
        iItemPrice = iFreqItemPrice;
        String ret = sendGetRequest(uriAPI);
        if (ret != null)
        	iOilPrice = Double.parseDouble(ret);
        else
        	Toast.makeText(getApplicationContext(), getString(R.string.hint_msg_net), Toast.LENGTH_LONG).show();
        
        //init ui
        btn_lower = (ImageButton)findViewById(R.id.lower);
        btn_lower.setOnClickListener(btnLowerListen);
        btn_upper = (ImageButton)findViewById(R.id.upper);
        btn_upper.setOnClickListener(btnUpperListen);
        
        btn_lower2 = (ImageButton)findViewById(R.id.lower2);
        btn_lower2.setOnClickListener(btnLowerListen2);
        btn_upper2 = (ImageButton)findViewById(R.id.upper2);
        btn_upper2.setOnClickListener(btnUpperListen2);
        
        btn_done = (Button)findViewById(R.id.done);
        btn_done.setOnClickListener(btnDoneListen);
        btn_cancel = (Button)findViewById(R.id.cancel);
        btn_cancel.setOnClickListener(btnCancelListen);
        
        spn_type = (Spinner)findViewById(R.id.type);
        spn_type.setOnItemSelectedListener(spnSelectedListen); 
        
        EditText mEditPrice = (EditText)findViewById(R.id.price);
        mEditPrice.setText(String.valueOf(iOilPrice));
        
        EditText mEditMetre = (EditText)findViewById(R.id.metre);
        mEditMetre.requestFocus();
        mEditMetre.selectAll();
    }

    private OnClickListener btnDoneListen = new OnClickListener(){
   
		public void onClick(View arg0) {	       	
			Toast.makeText(getApplicationContext(), getString(R.string.hint_msg_net), Toast.LENGTH_LONG).show();
		}
 
    };

    private OnClickListener btnCancelListen = new OnClickListener(){
   
		public void onClick(View arg0) {	       	
			finish();
		}
 
    };
    
    private OnClickListener btnLowerListen = new OnClickListener(){
   
		public void onClick(View arg0) {
	       	EditText mEditPrice = (EditText)findViewById(R.id.price);
	       	iOilPrice -= 0.01;
	       	DecimalFormat df = new DecimalFormat(); 
	       	df.setMaximumFractionDigits(2);
	       	mEditPrice.setText(df.format(iOilPrice));
		}
 
    };

    private OnClickListener btnUpperListen = new OnClickListener(){
   
		public void onClick(View arg0) {	       	
			EditText mEditPrice = (EditText)findViewById(R.id.price);
			iOilPrice += 0.01;
	       	DecimalFormat df = new DecimalFormat(); 
	       	df.setMaximumFractionDigits(2);
	       	mEditPrice.setText(df.format(iOilPrice));
		}
 
    };

    private OnClickListener btnLowerListen2 = new OnClickListener(){
   
		public void onClick(View arg0) {
	       	EditText mEditPrice = (EditText)findViewById(R.id.itemprice);
	       	iItemPrice -= 5;
	       	if (iItemPrice < 0) iItemPrice = iFreqItemPrice;
	       	mEditPrice.setText(String.valueOf(iItemPrice));
		}
 
    };

    private OnClickListener btnUpperListen2 = new OnClickListener(){
   
		public void onClick(View arg0) {	       	
			EditText mEditPrice = (EditText)findViewById(R.id.itemprice);
			iItemPrice += 5;
			mEditPrice.setText(String.valueOf(iItemPrice));
		}
 
    };
    
    private OnItemSelectedListener spnSelectedListen = new OnItemSelectedListener(){  

    	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {  
	    	Spinner spinner = (Spinner) parent;  
	    	int pos = spinner.getSelectedItemPosition();
	    	String ret = sendGetRequest(uriAPI);
	        if (ret != null) {
	        	iOilPrice = Double.parseDouble(ret);
	        	EditText mEditPrice = (EditText)findViewById(R.id.price);
	        	if (mEditPrice != null)
	        		mEditPrice.setText(String.valueOf(iOilPrice));
	            
	        } else
	        	Toast.makeText(getApplicationContext(), getString(R.string.hint_msg_net), Toast.LENGTH_LONG).show();
	        
    	}  

    	public void onNothingSelected(AdapterView<?> parent) {  
    	}
    };
    
    public static String sendGetRequest(String endpoint) 
    {
    	String result = null;  
    	if (endpoint.startsWith("http://")) {  
	    	// Send a GET request to the servlet  
	    	try {
		    	String urlStr = endpoint; 
		    	URL url = new URL(urlStr);  
		    	URLConnection conn = url.openConnection();  
		    	// Get the response  
		    	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
		    	StringBuffer sb = new StringBuffer();  
		    	String line;  
		    	while ((line = rd.readLine()) != null) {  
		    		sb.append(line);  
		    	}  
		    	rd.close();  
		    	result = sb.toString();  
	    	} catch (Exception e) {  
	    		return null; 
	    	}
    	}  
    	return result;  
    } 
}