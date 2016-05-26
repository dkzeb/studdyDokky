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

/**
 * Created by Medes on 26/05/2016.
 */
public class CustomAdapter extends BaseAdapter {
    JSONArray jsonArray = new JSONArray();

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        JSONObject temp = new JSONObject();
        try {
            temp = jsonArray.getJSONObject(position);
            return temp;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public long getItemId(int position) {
        try {
            return ((JSONObject) getItem(position)).getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Long.parseLong(null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    public View getView(int position, View convertView, ViewGroup parent, Context context) {
        //SKAL ÆNDRES TIL AT VÆRE LAYOUT FOR ACTIVITY FRAGMENT
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_adapter_griditem, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.imageTextView1);
        TextView time = (TextView) convertView.findViewById(R.id.imageTextView2);

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
        return convertView;
    }
}
