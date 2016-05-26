package dk.teamawesome.studdydokky;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChooseActivities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_activities);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Vælg interesser");

    }
}
