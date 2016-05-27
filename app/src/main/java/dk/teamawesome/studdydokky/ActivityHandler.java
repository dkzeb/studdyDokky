package dk.teamawesome.studdydokky;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Medes on 25/05/2016.
 */
public class ActivityHandler {
    public static JSONObject allActivitiesObject = new JSONObject();
    public static JSONArray allActivitiesArray = new JSONArray();
    public static JSONArray allDokk1Activities = new JSONArray();
    public static JSONArray timeSortedActivities = new JSONArray();
    public static ArrayList<String> comparisonArray = new ArrayList<>();
    public static ArrayList<String> interestAL = new ArrayList<>();
    public static ArrayList<ActivityData> activityDataAL = new ArrayList<>();



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
                } catch (JSONException e) {
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
        //SAVE interestAL to sharedPreferences, udskyd til AppFix
        //Log.d("Size of array", String.valueOf(interestAL.size()));
        //Log.d("Whole array", interestAL.toString());
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
        long unixTime = System.currentTimeMillis() / 1000L;
        int a = 0;
        for (int i = 0; i < allActivitiesArray.length(); i++) {
            JSONObject item = allActivitiesArray.getJSONObject(i);
            JSONArray arr = new JSONArray(item.getString("datelist"));
            JSONObject obj = new JSONObject(String.valueOf(arr.getJSONObject(0)));
            Long time = new Long(obj.get("end").toString());
            if (time > unixTime) {
                timeSortedActivities.put(a, item);
                a++;
            }
        }
    }

    public static ArrayList<ActivityData> makeActivityList() throws JSONException {
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

