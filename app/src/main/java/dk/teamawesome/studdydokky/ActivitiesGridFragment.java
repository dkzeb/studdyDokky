package dk.teamawesome.studdydokky;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class ActivitiesGridFragment extends Fragment {
    //private OnFragmentInteractionListener mListener;
    private SharedPreferences prefs;
    public ArrayList<ActivityData> activityData;
    public ActivitiesGridFragment() {
    }


    public static ActivitiesGridFragment newInstance() {
        return new ActivitiesGridFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE);
        String str = prefs.getString("KEY", null);
        Log.d("QQQQQQQQQQQQQQQQ",str);
        Type type = new TypeToken<ArrayList<ActivityData>>(){}.getType();
        Gson gson = new Gson();
        activityData = gson.fromJson(str, type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_grid_layout, container, false);
    }


}
