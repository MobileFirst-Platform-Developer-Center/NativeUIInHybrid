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

#import "ViewController.h"
#import <MapKit/MapKit.h>
#import <IBMMobileFirstPlatformFoundationHybrid/IBMMobileFirstPlatformFoundationHybrid.h>

@interface ViewController ()<MKMapViewDelegate, WLActionReceiver>
@property (weak, nonatomic) IBOutlet MKMapView *map;
@property CLGeocoder* geocoder;
@end

@implementation ViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"This is a native header";
    self.geocoder = [[CLGeocoder alloc] init];
    [self.map setDelegate:self];
    [[WL sharedInstance] addActionReceiver:self]; //Register to receive javascript actions
    

}

- (void)mapView:(MKMapView *)mapView didUpdateUserLocation:(MKUserLocation *)userLocation{
    [self.map setCenterCoordinate:self.map.userLocation.location.coordinate animated:YES];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) onActionReceived:(NSString *)action withData:(NSDictionary *) data {
    if ([action isEqualToString:@"displayAddress"] && [data objectForKey:@"address"]){
        NSString* address = (NSString*) [data objectForKey:@"address"];
        NSLog(@"Received address %@", address);
        [self.geocoder geocodeAddressString:address
                     completionHandler:^(NSArray* placemarks, NSError* error){
                         if([placemarks count]){
                             CLPlacemark *topResult = [placemarks objectAtIndex:0];
                             float spanX = 0.00725;
                             float spanY = 0.00725;
                             MKCoordinateRegion region;
                             region.center.latitude = topResult.location.coordinate.latitude;
                             region.center.longitude = topResult.location.coordinate.longitude;
                             region.span = MKCoordinateSpanMake(spanX, spanY);
                             [self.map setRegion:region animated:YES];
                             
                             
                             MKPlacemark *placemark = [[MKPlacemark alloc]initWithPlacemark:topResult];
                             [self.map addAnnotation:placemark];
                         }
                         else{
                             NSLog(@"%@",error);
                             [[WL sharedInstance] sendActionToJS:@"displayError" withData:@{@"errorReason": [error localizedDescription]}];
                         }

                     }];
    }
    else{
        NSLog(@"In onActionReceived with action = %@", action);
    }
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
