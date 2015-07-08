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



