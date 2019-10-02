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

public class InscriptosFragment extends Fragment {

    private static final String KEY = "inscriptos";
    private ArrayList<Rider> inscriptos;

    public InscriptosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            inscriptos = getArguments().getParcelableArrayList(KEY);
            Log.d("RECIBIDOS inscriptos", String.valueOf(inscriptos));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_inscriptos, container, false);
        ListView lv = v.findViewById(R.id.list_inscriptos);
        RidersAdapter adaptador = new RidersAdapter(this.getActivity(), R.layout.view_list_item_rider, inscriptos);
        lv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        return v;
    }

}