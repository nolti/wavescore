package com.example.nolti.wavescore.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.nolti.wavescore.R;
import com.example.nolti.wavescore.models.Rider;
import com.example.nolti.wavescore.ui.adapters.RidersAdapter;
import java.util.ArrayList;

public class DamasFragment extends Fragment {

    private static final String KEY = "damas";
    private ArrayList<Rider> damas;

    public DamasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            damas = getArguments().getParcelableArrayList(KEY);
            Log.d("RECIBIDOS Damas Tab", String.valueOf(damas));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_damas, container, false);
        ListView lv = v.findViewById(R.id.list_damas);
        RidersAdapter adaptador = new RidersAdapter(this.getActivity(), R.layout.view_list_item_rider, damas);
        lv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        return v;
    }
}