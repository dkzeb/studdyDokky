package dk.teamawesome.studdydokky;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChooseInterests extends AppCompatActivity {


    ListView interestList;
    ArrayList<InterestModel> interesser;

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
                //Intent nextIntent = new Intent(getApplicationContext(), ChooseInterests.class);
                //startActivity(nextIntent);

                boolean nonChecked = false;
                for(int i = 0; i < interestList.getAdapter().getCount(); i++){
                    InterestModel model = (InterestModel) interestList.getAdapter().getItem(i);
                    nonChecked = model.getChecked();
                    System.out.println("Interesse: "+model.getName()+ "Checked: "+model.getChecked());
                }
                if(!nonChecked){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Ingen interesser")
                            .setMessage("Du har ikke valgt nogle interesser,hvilket vil betyde at du får vist alle aktiviteter på DOKK1. Er du sikker på du vil fortsætte?")
                            .setPositiveButton("Gå videre", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent nextIntent = new Intent(getApplicationContext(), MainViewFragment.class);
                                    startActivity(nextIntent);
                                    finish();
                                }

                            })
                            .setNegativeButton("Nej", null)
                            .show();
                } else {
                    Intent nextIntent = new Intent(getApplicationContext(), MainViewFragment.class);
                    startActivity(nextIntent);
                    finish();
                }



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_interests);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Vælg interesser");
        interestList = (ListView) findViewById(R.id.interest_list_view);

        interesser = new ArrayList<InterestModel>();
        for(int x = 0; x < 15; x++){
            interesser.add(new InterestModel("Interesse "+x));
        }
        final InterestAdapter iAdapter = new InterestAdapter(this, interesser);
        interestList.setAdapter(iAdapter);

    }
}
