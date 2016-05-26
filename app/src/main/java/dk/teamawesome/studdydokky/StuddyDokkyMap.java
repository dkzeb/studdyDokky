package dk.teamawesome.studdydokky;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBar;;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class StuddyDokkyMap extends AppCompatActivity {

    public static final String TAG = "Studdy Dokky";

    private SharedPreferences mSharedPrefs;
    public static final String PREFS_NAME = "StudieDOKK1_prefs";

    private SlidingMenu menu;

    private IALocationManager mIALocationManager;

    private IALocationListener mIALocationListener = new IALocationListener() {

        // Called when the location has changed.
        @Override
        public void onLocationChanged(IALocation location) {

            Log.d("Location Change: ", "Latitude: " + location.getLatitude());
            Log.d("Location Change: ", "Longitude: " + location.getLongitude());

            Toast.makeText(getApplicationContext(), "Location Updated", Toast.LENGTH_SHORT);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }
    };

    Handler mHandler = new Handler();

    Runnable mHandlerTask = new Runnable()
    {
        @Override
        public void run() {
            mIALocationManager.requestLocationUpdates(IALocationRequest.create(), mIALocationListener);
            Toast.makeText(getApplicationContext(), "Updating Location", Toast.LENGTH_LONG);
            mHandler.postDelayed(mHandlerTask, 10000);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_sliding_menu, menu);
        return true;
    }

    public void toggleSlideMenu(MenuItem item) {
        if (menu != null) {
            menu.toggle();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studdy_dokky_map);


        String[] neededPermissions = {
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        mIALocationManager = IALocationManager.create(this);
        mHandlerTask.run();

        // shared prefs
        mSharedPrefs = getSharedPreferences(PREFS_NAME, 0);

        // bg services
        Intent mNotificationSystemServiceIntent = new Intent(getApplicationContext(), NotificationSystemService.class);
        getApplicationContext().startService(mNotificationSystemServiceIntent);

        /*
        Intent mIndoorAtlasLocationServiceIntent = new Intent(getApplicationContext(), IndoorAtlasLocationService.class);
        getApplicationContext().startService(mIndoorAtlasLocationServiceIntent);
           */



        // logo
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setTitle("studieDOKK1");
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setLogo(R.drawable.studiedokk1_logo_transparent_white);
        }

        // Menu - slider fra siden :D whey
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu);

        // menu knapper
        Button clearDataBtn = (Button) findViewById(R.id.clear_data_button);
        if (clearDataBtn != null) {
            clearDataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(StuddyDokkyMap.this)
                            .setTitle("Ryd al data")
                            .setMessage("Er du sikker på at du vil rydde al data?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    // hent en shared prefs editor!
                                    mSharedPrefs.edit().clear(); // ryd hele lortet!
                                    // tell em we did!
                                    Toast.makeText(StuddyDokkyMap.this, "Al data ryddet - lukker app'en", Toast.LENGTH_LONG).show();
                                    // exit app!
                                    finish();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // ingenting, ud igen :)
                                    Toast.makeText(StuddyDokkyMap.this, "Undlader at rydde data!", Toast.LENGTH_LONG).show();
                                    menu.toggle();

                                }
                            })
                            .setIcon(R.drawable.icon_delete)
                            .show();
                }
            });
        }


    }
}


