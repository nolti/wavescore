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

public class OpenProFragment extends Fragment {
    private static final String KEY = "openpros";
    private ArrayList<Rider> openpros;

    public OpenProFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            openpros = getArguments().getParcelableArrayList(KEY);
            Log.d("RECIBIDOS Open Pro Tab", String.valueOf(openpros));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.page_open_pro, container, false);
        ListView lv = v.findViewById(R.id.list_openpros);
        RidersAdapter adaptador = new RidersAdapter(this.getActivity(), R.layout.view_list_item_rider, openpros);
        Log.d("RECIBIDOS Open Pro Tab", String.valueOf(openpros));
        lv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        return v;
    }

}