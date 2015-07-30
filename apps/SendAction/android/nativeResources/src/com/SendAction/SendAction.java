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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.apache.cordova.CordovaActivity;
import org.json.JSONException;
import org.json.JSONObject;

import com.worklight.androidgap.api.WL;
import com.worklight.androidgap.api.WLInitWebFrameworkResult;
import com.worklight.androidgap.api.WLInitWebFrameworkListener;

public class SendAction extends CordovaActivity implements WLInitWebFrameworkListener {
	private URL serverURL;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		WL.createInstance(this);
		WL.getInstance().showSplashScreen(this);
		WL.getInstance().initializeWebFramework(getApplicationContext(), this);
	}

	/**
	 * The IBM MobileFirst Platform calls this method after its initialization is complete and web resources are ready to be used.
	 */
 	public void onInitWebFrameworkComplete(WLInitWebFrameworkResult result){
		if (result.getStatusCode() == WLInitWebFrameworkResult.SUCCESS) {
			super.loadUrl(WL.getInstance().getMainHtmlFilePath());
		} else {
			handleWebFrameworkInitFailure(result);
		}
		WL.getInstance().addActionReceiver(new ActionReceiver(this));
	}

	private void handleWebFrameworkInitFailure(WLInitWebFrameworkResult result){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setNegativeButton(R.string.close, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which){
				finish();
			}
		});

		alertDialogBuilder.setTitle(R.string.error);
		alertDialogBuilder.setMessage(result.getMessage());
		alertDialogBuilder.setCancelable(false).create().show();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	    if (requestCode == 1) {
	        if(resultCode == Activity.RESULT_OK){
	            String result = data.getStringExtra("result");
	            try {
					serverURL = new URL(result);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}	            
	            WL.getInstance().setServerUrl(serverURL);
	            
	            JSONObject returnData = new JSONObject();
	            try {
					returnData.put("serverURLChanged", 1234);
				} catch (JSONException e) {
					e.printStackTrace();
				}
	            WL.getInstance().sendActionToJS("refreshView", returnData);
	        }
	        if (resultCode == Activity.RESULT_CANCELED) {
	            // Do Nothing
	        }
	    }
	}
}



