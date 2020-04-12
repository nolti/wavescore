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

public class M14Fragment extends Fragment {
    private static final String KEY = "menores14";
    private ArrayList<Rider> menores14;

    public M14Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menores14 = getArguments().getParcelableArrayList(KEY);
            Log.d("RECIBIDOS M14", String.valueOf(menores14));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_menores14, container, false);
        ListView lv = v.findViewById(R.id.list_menores14);
        RidersAdapter adaptador = new RidersAdapter(this.getActivity(), R.layout.view_list_item_rider, menores14);
        lv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        return v;
    }
}