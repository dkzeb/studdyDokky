package dk.teamawesome.studdydokky;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class ActivitiesGridFragment extends Fragment {
    //private OnFragmentInteractionListener mListener;
    private SharedPreferences prefs;
    private ActivitiesAdapter aaAdapter;
    private GridView gridView;
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
        String str = prefs.getString("KEY", null  );
        Type type = new TypeToken<ArrayList<ActivityData>>(){}.getType();
        Gson gson = new Gson();
        activityData = gson.fromJson(str, type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_grid_layout, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView2);
        aaAdapter = new ActivitiesAdapter(getContext(),activityData);
        gridView.setAdapter(aaAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), activityData.get(position).toString(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


}
