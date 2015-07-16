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
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.IO.IsolatedStorage;
using System.Windows;
using Microsoft.Phone.Controls;
using System.Reflection;
using System.Collections;
using Microsoft.Phone.Shell;
using Newtonsoft.Json.Linq;
using IBM.Worklight;

namespace SendActionTest2
{
    public partial class MainPage : PhoneApplicationPage
    {
        WLActionReceiver myReceiver;

        public MainPage()
        {
            InitializeComponent();
            WL.createInstance(); //Create the instance of the ActionSender API's
            myReceiver = new action();
            Loaded += PhoneAppPage_Loaded;
            WL.getInstance().addActionReceiver(myReceiver);
        }

		void CordovaView_Loaded(object sender, RoutedEventArgs e)
		{
			
		}
		
		private void PhoneAppPage_Loaded(object sender, RoutedEventArgs e)
        {
            UserAgentHelper.GetUserAgent(LayoutRoot, userAgent =>
            {
                if (!PhoneApplicationService.Current.State.ContainsKey("userAgent"))
                	PhoneApplicationService.Current.State.Add("userAgent", userAgent); //Store it temporarly so that can be used later (in XHRSender)
            });
        }

        //*******************************************************************
        // action : WLActionReceiver
        // ******************************************************************
        public class action : WLActionReceiver
        {
            public void onActionReceived(string action, JObject data)
            {
                if (action == "displayAddress")
                {
                    Deployment.Current.Dispatcher.BeginInvoke(() =>
                    {
                        MessageBox.Show("Adress received from JS is: \n"+ data["address"]);
                        this.sendData(this, null);
                    });
                }
            }

            protected void sendData(object sender, RoutedEventArgs e)
            {
                String myAction = "displayError";
                JObject data = new JObject();               
                data.Add("errorReason", "Data sent from native to JS!");               
                WL.getInstance().sendActionToJS(myAction, data);
            }
        }
   }
}