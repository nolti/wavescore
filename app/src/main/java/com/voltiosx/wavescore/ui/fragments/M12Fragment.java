package com.voltiosx.wavescore.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.voltiosx.wavescore.R;
import com.voltiosx.wavescore.models.Rider;
import com.voltiosx.wavescore.ui.adapters.RidersAdapter;
import java.util.ArrayList;

public class M12Fragment extends Fragment {

    private static final String KEY = "menores12";
    private ArrayList<Rider> menores12;

    public M12Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menores12 = getArguments().getParcelableArrayList(KEY);
            Log.d("RECIBIDOS M12 Tab", String.valueOf(menores12));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_menores12, container, false);
        ListView lv = v.findViewById(R.id.list_menores12);
        RidersAdapter adaptador = new RidersAdapter(this.getActivity(), R.layout.view_list_item_rider, menores12);
        lv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        return v;
    }
}