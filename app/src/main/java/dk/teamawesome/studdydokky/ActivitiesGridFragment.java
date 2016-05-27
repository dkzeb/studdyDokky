package dk.teamawesome.studdydokky;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class ActivitiesGridFragment extends Fragment {
    //private OnFragmentInteractionListener mListener;
    public SharedPreferences settings;
    public ArrayList<ActivityData> activityData;
    public ActivitiesGridFragment() {
    }


    public static ActivitiesGridFragment newInstance() {
        return new ActivitiesGridFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getActivity().getSharedPreferences(getString(R.string.prefs_name), 0);
        String str = settings.getString("KEY", null);
        Type type = new TypeToken<ArrayList<ActivityData>>(){}.getType();
        Gson gson = new Gson();
        activityData = gson.fromJson(str, type);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_grid_layout, container, false);
    }


}
