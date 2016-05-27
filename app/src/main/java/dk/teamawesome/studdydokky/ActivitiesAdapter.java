package dk.teamawesome.studdydokky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Medes on 26/05/2016.
 */
public class ActivitiesAdapter extends BaseAdapter {
    ArrayList<ActivityData> arrayList;

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public ActivityData getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0l;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    public View getView(int position, View convertView, ViewGroup parent, Context context) {
        //SKAL ÆNDRES TIL AT VÆRE LAYOUT FOR ACTIVITY FRAGMENT
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_grid_layout, parent, false);
        }
        /*
        //TextView title = (TextView) convertView.findViewById(R.id.imageTextView1);
        //TextView time = (TextView) convertView.findViewById(R.id.imageTextView2);

        JSONObject tempObj = null;
        try {
            tempObj = jsonArray.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            title.setText(tempObj.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            title.setText(tempObj.getString("time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
        return convertView;
    }
}
