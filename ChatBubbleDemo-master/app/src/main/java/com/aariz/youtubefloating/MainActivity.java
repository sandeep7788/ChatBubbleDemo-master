package com.aariz.youtubefloating;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    /*  Permission request code to draw over other apps  */
    private static final int RC_DRAW = 1222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBannerAd(R.id.banner_ad);
        createFloatingWidget(null);
    }

    /*  start floating widget service  */
    public void createFloatingWidget(View view) {
        if (!isPermissionGiven()) {
            Intent intent = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, RC_DRAW);
            }
        } else {
            startFloatingWidgetService();
        }

    }

    private boolean isPermissionGiven() {
        return !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(MainActivity.this));
    }

    public void setUpBannerAd(@IdRes int bannerAdID) {
        AdView categoryAd = findViewById(bannerAdID);
        AdRequest adRequest = new AdRequest.Builder().build();
        categoryAd.loadAd(adRequest);
    }

    /*  Start Floating widget service and finish current activity */
    private void startFloatingWidgetService() {
        startService(new Intent(MainActivity.this, FloatingWidgetService.class));
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_DRAW) {
            if (isPermissionGiven())
                startFloatingWidgetService();
            else
                Toast.makeText(this,
                        getResources().getString(R.string.draw_other_app_permission_denied),
                        Toast.LENGTH_SHORT).show();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
