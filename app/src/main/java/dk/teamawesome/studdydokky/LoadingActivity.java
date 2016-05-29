package dk.teamawesome.studdydokky;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by zeb on 27-05-16.
 */
public class LoadingActivity extends AppCompatActivity {

    private static SharedPreferences prefs;
    private static Context mContext;
    public static Bitmap bmp;

    private static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        activity = this;

        setContentView(R.layout.loading_layout);
        Log.d(StuddyDokkyMap.TAG, "Loading application...");
        prefs = getApplicationContext().getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE);
        mContext = getApplicationContext();
        bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.placeholder);
        ActivityHandler ah = new ActivityHandler(mContext);
        try {
            ah.main();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void saveActivityToSharedPreferences() throws IOException {
        Gson gson = new Gson();
        String spArray = gson.toJson(ActivityHandler.activityDataAL);
        SharedPreferences.Editor editor = prefs.edit();
        Log.d(StuddyDokkyMap.TAG, "SpARR: " + spArray);
        editor.putString("KEY", spArray);
        editor.commit();
    }

    public static void startApp(){
        Intent mainIntent = new Intent(mContext, HelloActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mainIntent);
        activity.finish();
    }
}
