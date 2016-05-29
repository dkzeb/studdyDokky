package dk.teamawesome.studdydokky;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Medes on 26/05/2016.
 */
public class ActivitiesAdapter extends BaseAdapter {
    ArrayList<ActivityData> arrayList;
    Context context;


    public ActivitiesAdapter(Context context, ArrayList<ActivityData> al){
        arrayList = al;
        this.context = context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ActivityData actD = arrayList.get(position);

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.grid_layout_item, parent, false);
        TextView tv = (TextView) convertView.findViewById(R.id.myImageViewText);
        TextView dv = (TextView) convertView.findViewById(R.id.myImageViewDate);
        ImageView iv = (ImageView) convertView.findViewById(R.id.myImageView);
        if(tv != null) {
            tv.setText(actD.getTitle());
            iv.setImageResource(R.drawable.placeholder);
            Long time = new Long((actD.getStartTime().toString()));
            dv.setText(readableTimeStamp(time));
            Picasso.with(context).load(actD.getImageUrl()).into(iv);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        return convertView;

    }

    @Override
    public int getCount() {
        return arrayList.size();
   }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static String readableTimeStamp(long l){
        Date time = new java.util.Date((long)l*1000);
        if (DateUtils.isToday(l)) {
            DateFormat df = new SimpleDateFormat("HH:mm");
            String todayStr = df.format(time);
            return todayStr;
        }
        else {
            DateFormat df = new SimpleDateFormat("HH:mm - dd.MM.yy");
            String elseStr = df.format(time);
            return elseStr;
        }
    }

}
