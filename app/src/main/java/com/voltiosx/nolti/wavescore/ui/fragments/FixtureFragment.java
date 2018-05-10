package com.voltiosx.nolti.wavescore.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.voltiosx.nolti.wavescore.R;
import com.voltiosx.nolti.wavescore.clases.Fixture;
import com.voltiosx.nolti.wavescore.models.FixtureItem;
import com.voltiosx.nolti.wavescore.models.HeaderFixtureItem;
import com.voltiosx.nolti.wavescore.models.Rider;
import com.voltiosx.nolti.wavescore.models.RiderFixtureItem;
import com.voltiosx.nolti.wavescore.ui.adapters.FixtureAdapter;

import java.util.ArrayList;
import java.util.List;

public class FixtureFragment extends Fragment {

    private static final String KEY = "fixturelist";
    private int totalridersfixture;
    private int totalheats;
    private ArrayList<Rider> fixturelist = new ArrayList<>();
    @NonNull
    private List<FixtureItem> fixtureitems = new ArrayList<>();
    private Fixture fixture = new Fixture();
    private int heatgroup;

    public FixtureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recibe argumentos
        if (getArguments() != null) {
            fixturelist = getArguments().getParcelableArrayList(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fixture, container, false);
        Context context = v.getContext();
        totalridersfixture = fixturelist.size();
        heatgroup = fixture.heatGroupQuery(totalridersfixture);
        totalheats = (int) Math.ceil(totalridersfixture/heatgroup);

        // FORMATEO LA INSTANCIA
        int r=0;
        for (int h=0; h<totalheats; h++) {
            int numheat=h+1;
            HeaderFixtureItem headerfixture = new HeaderFixtureItem(numheat, "HEAT #"+numheat);
            fixtureitems.add(headerfixture);
            /* ENCABEZADO ASIGNADO */

            for (int i=0; i<heatgroup; i++){ //for necesario para agrupar
                if (r<totalridersfixture) {
                    String namerider = fixturelist.get(r).getName();

                    // inicializo colores lycras
                    int colortextcontrast = getResources().getColor(R.color.colorTextLight);
                    int tshirtcolor = getResources().getColor(R.color.inactive);
                    ArrayList<Integer> colores = new ArrayList<>();
                    colores.add(colortextcontrast);
                    colores.add(tshirtcolor);
                    fixturelist.get(r).setColors(colores);
                    colortextcontrast = fixturelist.get(r).getColors().get(0);
                    tshirtcolor = fixturelist.get(r).getColors().get(1);
                    RiderFixtureItem riderfixture = new RiderFixtureItem(numheat, namerider, colortextcontrast, tshirtcolor);
                    fixtureitems.add(riderfixture);
                    r++;
                }
                /* RIDER ASIGNADO */
            } //for al siguiente rider a asignar al heat

        } // for al siguiente encabezado
        // Set the adapter to RECYCLER
        RecyclerView recyclerfixture = v.findViewById(R.id.recycler_fixture);
        recyclerfixture.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerfixture.setAdapter(new FixtureAdapter(fixtureitems));


        /*RecyclerView recycler = vista.findViewById(R.id.recycler_results_heat);
        recycler.setLayoutManager(new LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(new ResultsAdapter(items));*/

        return v;
    }

}
