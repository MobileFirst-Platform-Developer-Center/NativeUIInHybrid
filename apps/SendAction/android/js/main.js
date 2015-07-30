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
function wlEnvInit(){
    wlCommonInit();
    // Environment initialization code goes here
    var busyIndicator = new WL.BusyIndicator(null, {text : 'Loading...'});
    $('#SearchAddressDiv').hide();
    $('#ChangeSettingsDiv').show();
    getServerURL(); //get the current server URL - for initial usage.
    WL.Client.connect({
		onSuccess: onConnectionSuccess,
		onFailure: onConnectionFailure
	})

	// SendAction to Native
    $('#changeServerURL').on('click', function(){
    	busyIndicator.show();
    	WL.App.sendActionToNative("displayNativeScreen", { requestedNativeScreen: 'ServerURL'});    	
	});

    // ActionReceiver (from Native)
	WL.App.addActionReceiver ("BackFromNative", function actionReceiver(received){
		if(received.action == "refreshView"){
			WL.Client.reloadApp();
		}
	});
}

//---------- Options for WL.Client.Connect ------------//
function onConnectionSuccess(){
	$('#ConnectionStatusDiv').html("Successfuly connected to server");
}
function onConnectionFailure(){
	$('#ConnectionStatusDiv').html("Could not connect to server");
}

//----------- getServerURL ------------//
function getServerURL(){
	WL.App.getServerUrl(getServerURLSuccess, getServerURLFailure)
}
function getServerURLSuccess(response) {
	$('#currentServerURLDiv').html('Current Server URL is:\n'+ response);
}
function getServerURLFailure(response){
	$('#currentServerURLDiv').html('Error: '+ response);
}
