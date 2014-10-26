package com.udaan.astrotrek;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.bindings.admob.GADAdSize;
import org.robovm.bindings.admob.GADBannerView;
import org.robovm.bindings.admob.GADRequest;

public class IOSLauncher extends IOSApplication.Delegate implements IActivityRequestHandler{
    private static final String AD_UNIT_ID = "ca-app-pub-8996795250788622/6788677098";

    private UIViewController rootViewController;
    private GADBannerView bannerView;

    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = false;
        config.orientationPortrait = true;
        return new IOSApplication(new AstroTrekGame(this), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    @Override
    public boolean showShop() {
        return false;
    }

    /**
     * Creates a new admob ad
     */
    private void createAd() {
        final CGSize screenSize = UIScreen.getMainScreen().getBounds().size();
        double screenWidth = screenSize.width();
        double screenHeight = screenSize.height();

        rootViewController = UIApplication.getSharedApplication().getKeyWindow().getRootViewController();
        bannerView = new GADBannerView(GADAdSize.banner());
        bannerView.setAdUnitID(AD_UNIT_ID);

        final CGSize adSize = bannerView.getBounds().size();
        double adWidth = adSize.width();
        double adHeight = adSize.height();
        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) (bannerWidth / adWidth * adHeight);

        rootViewController.getView().addSubview(bannerView);
        bannerView.setFrame(new CGRect((screenWidth / 2) - adWidth / 2, screenHeight - bannerHeight, bannerWidth, bannerHeight));
        bannerView.setRootViewController(rootViewController);

        GADRequest request = GADRequest.create();

        bannerView.loadRequest(request);

        this.showAds(true);
    }

    @Override
    public boolean didFinishLaunching (UIApplication application, UIApplicationLaunchOptions launchOptions) {
        boolean didFinish = super.didFinishLaunching(application, launchOptions);

        this.createAd();

        return didFinish;
    }

    @Override
    public void showAds(boolean show) {
        if (bannerView != null) {
            bannerView.setHidden(!show);
        }
    }
}