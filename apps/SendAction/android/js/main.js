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
function wlEnvInit(){
    wlCommonInit();
    // Environment initialization code goes here
    $('#SearchAddressDiv').hide();
    $('#ChangeSettingsDiv').show();
    getServerURL(); //get the current server URL - for initial usage.
    WL.Client.connect({
		onSuccess: onConnectionSuccess,
		onFailure: onConnectionFailure
	})

	// SendAction to Native
    $('#changeServerURL').on('click', function(){
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
