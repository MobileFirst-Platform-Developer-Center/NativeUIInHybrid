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

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.worklight.androidgap.api.WL;
import com.worklight.androidgap.api.WLActionReceiver;

public class ActionReceiver implements WLActionReceiver{
	private Activity parentActivity;
	private String requestedScreen;
	
	public ActionReceiver(Activity pActivity){
		parentActivity = pActivity;		
	}
	
	public void onActionReceived(String action, JSONObject data){
		// Display Settings Screen
		if(action.equals("displayNativeScreen")){
			try {
				requestedScreen = data.getString("requestedNativeScreen");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if(requestedScreen.equals("ServerURL")){
				Intent intent = new Intent(parentActivity, serverurlActivity.class);
				parentActivity.startActivityForResult(intent, 1);
			}
		}
	 }
}