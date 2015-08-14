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

namespace SendAction
{
    public partial class MainPage : PhoneApplicationPage
    {
        WLActionReceiver myReceiver;

        public MainPage()
        {
            InitializeComponent();
            WL.createInstance(); //Create the instance of the ActionSender API's
            myReceiver = new ActionReceiver(this);
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

        public void navigateServerURL(String data)
        {
            try
            {
                Dispatcher.BeginInvoke(() =>
                {
                    this.CordovaView.Margin = new Thickness(0, 400, 0, 0);
                    this.my_Stack.Visibility = System.Windows.Visibility.Visible;
                    Uri serveruri = WL.getInstance().getServerUrl();
                    Text.Text = serveruri.ToString();
                });
            }
            catch (Exception e)
            {
                System.Diagnostics.Debug.WriteLine(e.StackTrace);
            }
        }

        private void SaveURL_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                String newServerURL = Text.Text;
                Uri newUri = new Uri(newServerURL);
                WL.getInstance().setServerUrl(newUri);

                Dispatcher.BeginInvoke(() =>
                {
                    this.CordovaView.Margin = new Thickness(0, 0, 0, 0);
                    this.my_Stack.Visibility = System.Windows.Visibility.Collapsed;
                });
            }
            catch (Exception e1)
            {
                System.Diagnostics.Debug.WriteLine(e1.StackTrace);
            }
            
            JObject returnData = new JObject();
            returnData.Add("serverURLChanged", 1234);
            WL.getInstance().sendActionToJS("refreshView", returnData);
            //NavigationService.GoBack();
        }
   }
}
