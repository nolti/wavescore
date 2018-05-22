package com.example.nolti.wavescore.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nolti.wavescore.R;
import com.example.nolti.wavescore.models.Rider;
import com.example.nolti.wavescore.ui.adapters.RidersAdapter;

import java.util.ArrayList;

public class AmateursFragment extends Fragment {
    private static final String KEY = "amateurs";
    private ArrayList<Rider> amateurs;

    public AmateursFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            amateurs = getArguments().getParcelableArrayList(KEY);
            Log.d("RECIBIDOS Amateur Tab", String.valueOf(amateurs));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.page_amateurs, container, false);
        ListView lv = v.findViewById(R.id.list_amateurs);
        RidersAdapter adaptador = new RidersAdapter(this.getActivity(), R.layout.view_list_item_rider, amateurs);
        lv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        return v;
    }
}