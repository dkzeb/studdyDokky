package dk.teamawesome.studdydokky;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by zeb on 27-05-16.
 */
public class InterestAdapter extends ArrayAdapter {

    ArrayList<InterestModel> models;
    Context context;

    public InterestAdapter(Context context, ArrayList<InterestModel> resource){
        super(context, R.layout.interest_row, resource);
        this.context = context;
        this.models = resource;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        final int innerPos = pos;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.interest_row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.interest_row_text);
        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.interest_row_checkbox);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                models.get(innerPos).setChecked(cb.isChecked());
                System.out.println("U ClickMe Bro!");
            }
        });
        name.setText(models.get(pos).getName());
        cb.setChecked(models.get(pos).getChecked());

        return convertView;
    }




}
