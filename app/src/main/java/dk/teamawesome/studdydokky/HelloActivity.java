package dk.teamawesome.studdydokky;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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


        }
    }
}
