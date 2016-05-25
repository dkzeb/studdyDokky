package dk.teamawesome.studdydokky;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.json.JSONArray;

public class ActivityView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        JSONArray jArray = (JSONArray) getIntent().getSerializableExtra("activityArray");
        //Toast.makeText(getApplicationContext(),jArray.length(),Toast.LENGTH_LONG);
    }





}
