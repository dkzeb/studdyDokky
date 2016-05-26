package dk.teamawesome.studdydokky;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Medes on 25/05/2016.
 */
public class ActivityHandler {
    public static JSONObject allActivitiesObject = new JSONObject();
    public static JSONArray allActivitiesArray = new JSONArray();
    public static JSONArray allDokk1Activities = new JSONArray();
    public static JSONArray timeSortedActivities = new JSONArray();

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
                    stripDokk1Activities();
                    stripOldActivities();
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

    public static void stripDokk1Activities() throws JSONException {
        allActivitiesArray = (JSONArray) allActivitiesObject.get("events");
        int a = 0;
        for (int i = 0; i < allActivitiesArray.length(); i++) {
            JSONObject item = allActivitiesArray.getJSONObject(i);
            if(item.getString("location_name").equals("Dokk1")){
                allDokk1Activities.put(a, item);
                a++;
            }
        }
    }

    public static void stripOldActivities() throws JSONException{
        long unixTime = System.currentTimeMillis() / 1000L;
        int a = 0;
        for (int i = 0; i < allActivitiesArray.length(); i++) {
            JSONObject item = allActivitiesArray.getJSONObject(i);
            JSONArray arr = new JSONArray(item.getString("datelist"));
            JSONObject obj = new JSONObject(String.valueOf(arr.getJSONObject(0)));
            Long time = new Long(obj.get("end").toString());
            if(time > unixTime){
                timeSortedActivities.put(a, item);
                a++;
            }
        }
    }

}

