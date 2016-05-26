package dk.teamawesome.studdydokky;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HelloActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        prefs = getApplicationContext().getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE);
        boolean firstRun = prefs.getBoolean("firstRun", true); // vi går ud fra at det her er first run
        if(!firstRun){
            Intent mainIntent = new Intent(getApplicationContext(), StuddyDokkyMap.class);
            startActivity(mainIntent);
            finish();
        } else {
            setContentView(R.layout.activity_hello);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();

            ImageButton nextBtn = (ImageButton) findViewById(R.id.welcome_next_btn);
            if(nextBtn != null){
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent activityIntent = new Intent(getApplicationContext(), ChooseActivities.class);
                        startActivity(activityIntent);
                    }
                });
            }
        }
    }
}
