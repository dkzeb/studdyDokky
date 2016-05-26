package dk.teamawesome.studdydokky;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;


/**
 * Created by zeb on 25-05-16.
 */
public class IndoorAtlasLocationService extends IntentService {

    private IALocationManager mIALocationManager;
    private String TAG = "IndoorAtlas Service: ";
    private Handler mHandler = new Handler();
    private final int UPDATE_TIME = 15000; // i milis


    private IALocationListener mIALocationListener = new IALocationListener() {

        // Called when the location has changed.
        @Override
        public void onLocationChanged(IALocation location) {

            Log.d(TAG, "Latitude: " + location.getLatitude());
            Log.d(TAG, "Longitude: " + location.getLongitude());
            showToast("Lat: "+ location.getLatitude() + "Lng: "+location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.d(TAG, "String: "+s);
        }


    };

    public IndoorAtlasLocationService(){
        super("IndoorAltasLocationService");

    }

    @Override
    public void onHandleIntent(Intent intent){

        showToast("Start!");
        mHandler.postDelayed(UpdateRunnable, 0);
    }

    final Runnable UpdateRunnable = new Runnable(){
        public void run(){

            Log.d(TAG, "Update Location");
            showToast("Update!");
            mIALocationManager.requestLocationUpdates(IALocationRequest.create(), mIALocationListener);
            // run every 15th second :D rekursivt!
            mHandler.postDelayed( UpdateRunnable, UPDATE_TIME);
        }
    };

    public void showToast(String message) {
        final String msg = message;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

}
