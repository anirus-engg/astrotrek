package com.udaan.astrotrek.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.udaan.astrotrek.Settings;
import com.udaan.astrotrek.android.util.IabHelper;
import com.udaan.astrotrek.android.util.IabResult;
import com.udaan.astrotrek.android.util.Inventory;
import com.udaan.astrotrek.android.util.Purchase;

import java.util.ArrayList;
import java.util.List;

/**
 * This class shows the main screen for the shop
 *
 */
public class ShopActivity extends Activity{
    private final static String SKU_5STARS = "astro_5stars";
    private final static String SKU_12STARS = "astro_12stars";
    private final static String TAG = "ShopActivity";

    private ImageView stars5, stars12, cancel;
    private IabHelper mHelper;
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener;
    private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;
    private List<String> skuList;
    private String stars5Price, stars12Price;

    private String key1 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm7fXh5RNuyOmwZi6M8qUqe2J2WWEkrXAFye";
    private String key2 = "qzcZavpabtVlJTmUPBgKdHaMmog9VWuSYRuhb9k4GrL3zMFC+n4aefRlY/0nIVXffiTy9eUEfQHxibC";
    private String key3 = "zphru6HK3gvx2rEtXGLwk133DYpLUVLRWZhHhVCpzFAfoRfeJuRy6xVsrzGNVs6OJpUoJwrmrPVDM04";
    private String key4 = "2kA58UEDMkOPvcZymTpL1tn6xU29Hl/wklWn7lANBHlbM/7dCe6p1ui8vjkVbr5hNGoy/K9eE1Qujar";
    private String key5 = "AINWWQ98szRFBK6PXPKpO7gIIhNjAQycTXOpDd0K5yYXsUVk8fKwWnnF5wBpXgFOUasgyQIDAQAB";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shop);

        cancel = (ImageView) findViewById(R.id.cancel);
        stars5 = (ImageView) findViewById(R.id.stars_5);
        stars12 = (ImageView) findViewById(R.id.stars_12);

        skuList = new ArrayList<String>();
        skuList.add(SKU_5STARS);
        skuList.add(SKU_12STARS);

        mHelper = new IabHelper(this, key1 + key2 + key3 + key4 + key5);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    //there was a problem.
                    Log.e(TAG, "Problem setting up In-app Billing: " + result);
                }
                Log.d(TAG, "IAB is fully setup. Querying inventory");
                mHelper.queryInventoryAsync(true, skuList, mGotInventoryListener);
            }
        });

        mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory)
            {
                if (result.isFailure()) {
                    // handle error
                    Toast.makeText(getApplicationContext(), "Error getting price", Toast.LENGTH_LONG).show();
                    return;
                }

                if (inventory.getSkuDetails(SKU_5STARS) == null) Log.d(TAG, "Inventory getSkuDetails null");
                stars5Price = inventory.getSkuDetails(SKU_5STARS).getPrice();
                stars12Price = inventory.getSkuDetails(SKU_12STARS).getPrice();
            }
        };

        mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
            @Override
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                if (result.isFailure()) {
                    Log.d(TAG, "Error purchasing: " + result);
                    //Toast.makeText(getApplicationContext(), "Error purchasing: " + result, Toast.LENGTH_LONG).show();
                    return;
                }

                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
        };

        mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
            @Override
            public void onConsumeFinished(Purchase purchase, IabResult result) {
                if (result.isSuccess()) {
                    if (purchase.getSku().equals(SKU_5STARS)) {
                        Settings.setGoldStars(Settings.getGoldStars() + 5);
                        Toast.makeText(getApplicationContext(), "Successfully purchased 5 stars", Toast.LENGTH_LONG).show();
                    }
                    else if (purchase.getSku().equals(SKU_12STARS)) {
                        Settings.setGoldStars(Settings.getGoldStars() + 12);
                        Toast.makeText(getApplicationContext(), "Successfully purchased 12 stars", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    showAlertOK("There was an error processing your purchase. Please send an email to developer and mention order: " + purchase.getOrderId());
                }
            }
        };

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        stars5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*stars5.setImageResource(R.raw.btn_stars_5_clicked);

                new CountDownTimer(1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        stars5.setImageResource(R.raw.btn_stars_5);
                    }
                }.start();*/

                if(mHelper != null) mHelper.flagEndAsync();

                Log.d(TAG, "Purchase price is " + stars5Price);
                mHelper.launchPurchaseFlow(ShopActivity.this, SKU_5STARS, 5, mPurchaseFinishedListener, "stars5purchase");
            }
        });

        stars12.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*stars12.setImageResource(R.raw.btn_stars_12_clicked);

                new CountDownTimer(1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        stars12.setImageResource(R.raw.btn_stars_12);
                    }
                }.start();*/

                if(mHelper != null) mHelper.flagEndAsync();

                Log.d(TAG, "Purchase price is " + stars12Price);
                mHelper.launchPurchaseFlow(ShopActivity.this, SKU_12STARS, 12, mPurchaseFinishedListener, "stars12purchase");
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    private void showAlertOK(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }
}