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

#import "NativeSplashScreen.h"
#import <IBMMobileFirstPlatformFoundationHybrid/IBMMobileFirstPlatformFoundationHybrid.h>
#import "Cordova/CDVViewController.h"
#import "PageViewController.h"


@interface MyAppDelegate()
//SAMPLE: Adding a reference to the Cordova view for global use
@property CDVViewController* cordovaViewController;
@end

@implementation MyAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
	BOOL result = [super application:application didFinishLaunchingWithOptions:launchOptions];
    
    //SAMPLE: This colors the bullets for the slides
    UIPageControl *pageControl = [UIPageControl appearance];
    pageControl.pageIndicatorTintColor = [UIColor lightGrayColor];
    pageControl.currentPageIndicatorTintColor = [UIColor blackColor];
    pageControl.backgroundColor = [UIColor whiteColor];
    
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    
    // Create page view controller
    PageViewController* pageViewController = [[UIStoryboard storyboardWithName:@"Storyboard" bundle: nil] instantiateViewControllerWithIdentifier:@"PageViewController"];
    [self.window setRootViewController:pageViewController];
    [self.window makeKeyAndVisible];
    
    //SAMPLE Initialize the web framework in the meantime
    [[WL sharedInstance] initializeWebFrameworkWithDelegate:self];
    
    return result;
}

-(void)onSplashScreenDone {
    //Gets called when the user is done with the slides. Set the cordova view as the main vew.
    NSLog(@"splash screen done");
    UIViewController* rootViewController = [[UIViewController alloc] init];
    
    [self.window setRootViewController:rootViewController];
    [self.window makeKeyAndVisible];
    
    // Adjust the Cordova view controller view frame to match its parent view bounds
    self.cordovaViewController.view.frame = rootViewController.view.bounds;
    
	// Display the Cordova view
    [rootViewController addChildViewController:self.cordovaViewController];
    [rootViewController.view addSubview:self.cordovaViewController.view];
}

// This method is called after the WL web framework initialization is complete and web resources are ready to be used.
-(void)wlInitWebFrameworkDidCompleteWithResult:(WLWebFrameworkInitResult *)result
{
    if ([result statusCode] == WLWebFrameworkInitResultSuccess) {
        [self wlInitDidCompleteSuccessfully];
    } else {
        [self wlInitDidFailWithResult:result];
    }
}

-(void)wlInitDidCompleteSuccessfully
{    
    // Create a Cordova View Controller
    self.cordovaViewController = [[CDVViewController alloc] init] ;
    self.cordovaViewController.startPage = [[WL sharedInstance] mainHtmlFilePath];
    
    //This will trigger initialization in the background, optional
    self.cordovaViewController.view.frame = self.cordovaViewController.view.frame; 
    
}
-(void)wlInitDidFailWithResult:(WLWebFrameworkInitResult *)result
{
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"ERROR"
                                                  message:[result message]
                                                  delegate:self
                                                  cancelButtonTitle:@"OK"
                                                  otherButtonTitles:nil];
    [alertView show];
}


- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
