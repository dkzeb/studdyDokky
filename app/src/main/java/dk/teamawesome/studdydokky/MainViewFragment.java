package dk.teamawesome.studdydokky;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainViewFragment extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private int currentTab = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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
