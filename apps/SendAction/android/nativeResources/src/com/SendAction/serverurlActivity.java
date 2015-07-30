package com.SendAction;
/**
* Copyright 2015 IBM Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
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