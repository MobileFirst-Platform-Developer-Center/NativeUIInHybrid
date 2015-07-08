package com.SendAction;
/*
*
   COPYRIGHT LICENSE: This information contains sample code provided in source code form. You may copy, modify, and distribute
   these sample programs in any form without payment to IBM® for the purposes of developing, using, marketing or distributing
   application programs conforming to the application programming interface for the operating platform for which the sample code is written.
   Notwithstanding anything to the contrary, IBM PROVIDES THE SAMPLE SOURCE CODE ON AN "AS IS" BASIS AND IBM DISCLAIMS ALL WARRANTIES,
   EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, ANY IMPLIED WARRANTIES OR CONDITIONS OF MERCHANTABILITY, SATISFACTORY QUALITY,
   FITNESS FOR A PARTICULAR PURPOSE, TITLE, AND ANY WARRANTY OR CONDITION OF NON-INFRINGEMENT. IBM SHALL NOT BE LIABLE FOR ANY DIRECT,
   INDIRECT, INCIDENTAL, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR OPERATION OF THE SAMPLE SOURCE CODE.
   IBM HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS OR MODIFICATIONS TO THE SAMPLE SOURCE CODE.

*/
import java.net.MalformedURLException;
import java.net.URL;

import com.worklight.androidgap.api.WL;


//import com.SendAction.R;
import android.app.Activity;
import android.content.Intent;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
//import android.preference.EditTextPreference;
//import android.preference.PreferenceActivity;
//import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Toast;

public class serverurlActivity extends Activity {
	protected URL serverURL = null;
	private EditText urlEditText;
	private Button saveURL;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serverurl);
		
		serverURL = WL.getInstance().getServerUrl();		
		urlEditText = (EditText) findViewById(R.id.serverURLTextBox);		
		saveURL = (Button) findViewById(R.id.saveURL);
		urlEditText.setText(serverURL.toString());
		
		saveURL.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				String strServerURL = urlEditText.getText().toString();
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result",strServerURL);
				setResult(RESULT_OK,returnIntent);
				finish();
			}
		});
	}

}