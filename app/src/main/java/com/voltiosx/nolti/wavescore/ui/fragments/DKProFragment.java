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

public class DKProFragment extends Fragment {

    private static final String KEY = "dkpros";
    private ArrayList<Rider> dkpros;

    public DKProFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dkpros = getArguments().getParcelableArrayList(KEY);
            Log.d("RECIBIDOS DK Pro Tab", String.valueOf(dkpros));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_dk_pro, container, false);
        ListView lv = v.findViewById(R.id.list_dk_pro);
        RidersAdapter adaptador = new RidersAdapter(this.getActivity(), R.layout.view_list_item_rider, dkpros);
        lv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        return v;
    }
}