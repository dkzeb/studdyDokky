package dk.teamawesome.studdydokky;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HelloActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.next_arrow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_next:
                // gå til næste side
                Intent nextIntent = new Intent(getApplicationContext(), ChooseInterests.class);
                startActivity(nextIntent);
                
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // alt herunder er init!




        // alt herover er init!

        prefs = getApplicationContext().getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE);
        boolean firstRun = prefs.getBoolean("firstRun", true); // vi går ud fra at det her er first run
        if(!firstRun){
            Intent mainIntent = new Intent(getApplicationContext(), MainViewFragment.class);
            startActivity(mainIntent);
            finish();
        } else {
            setContentView(R.layout.activity_hello);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("");
            actionBar.setElevation(0);

            // start animation
            ImageView animated_hello_image = (ImageView) findViewById(R.id.hello_wave_imageview);
            animated_hello_image.setImageDrawable(getDrawable(R.drawable.boy_wave_animated));
            AnimationDrawable helloAnimated = (AnimationDrawable) animated_hello_image.getDrawable();
            helloAnimated.start();
        }
    }
}
