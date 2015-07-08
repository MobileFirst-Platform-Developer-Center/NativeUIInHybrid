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