package dk.teamawesome.studdydokky;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Medes on 25/05/2016.
 */
public class ActivityHandler {
    public static JSONObject allActivitiesObject = new JSONObject();
    public static JSONArray allActivitiesArray = new JSONArray();
    public static JSONArray allDokk1Activities = new JSONArray();
    public static JSONArray timeSortedActivities = new JSONArray();
    public static ArrayList<JSONObject> holdActivityData = new ArrayList<>();
    public static ArrayList<String> comparisonArray = new ArrayList<>();
    public static ArrayList<String> interestAL = new ArrayList<>();
    public static ArrayList<ActivityData> activityDataAL = new ArrayList<>();
    private static Context context;

    public ActivityHandler(Context current){
        context = current;
    }

    public static void main() throws JSONException {
        getJSONArrayFromUrl();
    }

    public static void getJSONArrayFromUrl() {
        class anonymousJSONParser extends AsyncTask<String, Void, JSONObject> {
            JSONParser jsp = new JSONParser();
            JSONObject tempJObj = new JSONObject();


            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(JSONObject s) {
                super.onPostExecute(s);
                allActivitiesObject = tempJObj;

                try {
                    getInterests();
                    stripDokk1Activities();
                    stripOldActivities();
                    makeActivityList();
                    LoadingActivity.saveActivityToSharedPreferences();
                    LoadingActivity.startApp();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            protected JSONObject doInBackground(String... params) {
                tempJObj = jsp.getJSONFromUrl("http://events.makeable.dk/api/getEvents");
                return tempJObj;
            }
        }

        AsyncTask<String, Void, JSONObject> task = new anonymousJSONParser();
        task.execute();
    }

    public static void getInterests() throws JSONException {
        allActivitiesArray = (JSONArray) allActivitiesObject.get("events");
        for (int i = 0; i < allActivitiesArray.length(); i++) {
            JSONObject item = allActivitiesArray.getJSONObject(i);
            String str = item.getString("tags");
            List<String> tagsList = Arrays.asList(str.split(","));
            for (int q = 0; q < tagsList.size(); ) {
                String holder = tagsList.get(q);
                String lowercase = holder.toLowerCase();
                String noSpaces = lowercase.replaceAll("\\s", "");
                if (comparisonArray.contains(noSpaces)) {
                    q++;
                } else {
                    comparisonArray.add(noSpaces);
                    interestAL.add(holder);
                    q++;
                }
            }
        }
    }

    public static void stripDokk1Activities() throws JSONException {
        allActivitiesArray = (JSONArray) allActivitiesObject.get("events");
        int a = 0;
        for (int i = 0; i < allActivitiesArray.length(); i++) {
            JSONObject item = allActivitiesArray.getJSONObject(i);
            if (item.getString("location_name").equals("Dokk1")) {
                allDokk1Activities.put(a, item);
                a++;
            }
        }
    }

    public static void stripOldActivities() throws JSONException {
        long unixTime = System.currentTimeMillis();
        Date tom = new java.util.Date(unixTime);
        DateFormat df = new SimpleDateFormat("HH:mm - dd/MM/yy");
        //Log.d("Which date do we check with?", df.format(tom));
        Log.d("unixtime current", String.valueOf(unixTime));
        int a = 0;
        for (int i = 0; i < allActivitiesArray.length(); i++) {
            JSONObject item = allActivitiesArray.getJSONObject(i);
            JSONArray arr = new JSONArray(item.getString("datelist"));
            JSONObject obj = new JSONObject(String.valueOf(arr.getJSONObject(0)));
            Long time = new Long(obj.get("end").toString());
            Long timeM = time * 1000L;
            if (timeM > unixTime) {
                holdActivityData.add(item);
                a++;
            }
        }
        Log.d("CRUUELINTENTIONS BEFORE", holdActivityData.toString());
        Collections.sort(holdActivityData, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                try {
                    JSONArray jar1 = new JSONArray(lhs.getString("datelist"));
                    JSONArray jar2 = new JSONArray(rhs.getString("datelist"));
                    JSONObject jobj1 = new JSONObject(String.valueOf(jar1.getJSONObject(0)));
                    JSONObject jobj2 = new JSONObject(String.valueOf(jar2.getJSONObject(0)));
                    Long long1 = new Long(jobj1.get("start").toString());
                    Long long2 = new Long(jobj2.get("start").toString());
                    if (long1 > long2){
                        return 1;
                    }
                    if (long2 > long1){
                        return -1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        Log.d("CRUUELINTENTIONS AFTER", holdActivityData.toString());
        for (int q = 0; q < holdActivityData.size(); q++){
            timeSortedActivities.put(q, holdActivityData.get(q));
        }

    }

    public static ArrayList<ActivityData> makeActivityList() throws JSONException, IOException {
        String title;
        String startTime;
        String endTime;
        String imageUrl;
        String description;
        String category;
        String id;
        String tagHolder;
        String[] tags;
        for(int i = 0; i < timeSortedActivities.length();i++){
            JSONObject item = timeSortedActivities.getJSONObject(i);
            JSONArray timeArr = item.getJSONArray("datelist");
            JSONObject timeObj = timeArr.getJSONObject(0);
            title = item.getString("title");
            startTime = timeObj.getString("start");
            endTime = timeObj.getString("end");
            imageUrl = item.getString("picture_name");
            description = item.getString("description");
            category = item.getString("category");
            id = item.getString("eventid");
            tagHolder = item.getString("tags");
            List<String> tagsList = Arrays.asList(tagHolder.split(","));
            tags = new String[tagsList.size()];
            for(int q = 0; q < tagsList.size(); q++){
                tags[q] = tagsList.get(q);
            }
            ActivityData activityData = new ActivityData(title, startTime, endTime, imageUrl, description, category, id ,tags);
            if(activityDataAL.contains(activityData)){
                i++;
            }
            else{
                activityDataAL.add(activityData);
            }
        }
        Log.d("All activities", String.valueOf(activityDataAL.size()));
        return activityDataAL;
    }



}

