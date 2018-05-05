package com.voltiosx.nolti.wavescore.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.voltiosx.nolti.wavescore.R;
import com.voltiosx.nolti.wavescore.models.Rider;
import com.voltiosx.nolti.wavescore.ui.adapters.RidersAdapter;
import java.util.ArrayList;

public class M16Fragment extends Fragment {

    private static final String KEY = "menores16";
    private ArrayList<Rider> menores16;

    public M16Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menores16 = getArguments().getParcelableArrayList(KEY);
            Log.d("RECIBIDOS M16 Tab", String.valueOf(menores16));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_menores16, container, false);
        ListView lv = v.findViewById(R.id.list_menores16);
        RidersAdapter adaptador = new RidersAdapter(this.getActivity(), R.layout.view_list_item_rider, menores16);
        lv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        return v;
    }
}