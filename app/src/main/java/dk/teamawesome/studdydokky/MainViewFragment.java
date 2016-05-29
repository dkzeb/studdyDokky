package dk.teamawesome.studdydokky;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainViewFragment extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private int currentTab = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    String DEBUG_TAG = "Touch: ";

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = event.getAction();
        switch(action & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_POINTER_DOWN:
                // multitouch!! - touch down
                int count = event.getPointerCount(); // Number of 'fingers' in this time
                Log.d(DEBUG_TAG,"COunt was: "+count);
                if(count >= 3){
                    NotificationSystemService nSS = new NotificationSystemService();
                    // name, place, time}
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MINUTE, 15);
                    String time = df.format(cal.getTime());

                    NotificationManager mNotifyMgr = (NotificationManager)
                            this.getSystemService(Context.NOTIFICATION_SERVICE);;

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.studiedokk1_icon)
                                    .setContentTitle("Der er aktiviteter i dit område")
                                    .setContentText("Kl. " + time + " - " + "Børne-teater" + " - " + "Scenetrappen")
                                    .setAutoCancel(true)
                                    .setPriority(NotificationCompat.PRIORITY_MAX);


                    mNotifyMgr.notify(0, mBuilder.build());
                }
                break;
        }

        //int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(DEBUG_TAG,"Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_clear_data:
                SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_name), 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                finish(); // luk app'en - vi starter forfra

            case R.id.action_luk:
                finish(); // luk app'en!

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_fragment);

        // logo
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setTitle("studieDOKK1");
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setLogo(R.drawable.studiedokk1_logo_transparent_white);
        }

        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                View currentView = mTabHost.getCurrentView();

                if (mTabHost.getCurrentTab() > currentTab) {
                    currentView.setAnimation(inFromRightAnimation());
                } else {
                    currentView.setAnimation(outToRightAnimation());
                }

                currentTab = mTabHost.getCurrentTab();
            }
        });
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Aktiviteter", null),
                ActivitiesGridFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Kort", null),
                MapFragment.class, null);

        TabWidget tw = mTabHost.getTabWidget();
        System.out.println("TW cnt: "+tw.getTabCount());
        for(int i = 0; i < tw.getTabCount(); i++){
            TextView tv = (TextView) tw.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
        }

        mTabHost.setCurrentTab(1);

    }

    public Animation inFromRightAnimation()
    {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(240);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    public Animation outToRightAnimation()
    {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(240);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

}
