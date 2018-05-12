package com.voltiosx.nolti.wavescore.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private int restoriders;

    public FixtureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fixturelist = getArguments().getParcelableArrayList(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fixture, container, false);
        Context context = v.getContext();
        totalridersfixture = fixturelist.size();
        heatgroup = heatGroupQuery(totalridersfixture);
        restoriders = totalridersfixture%heatgroup;
        Log.d("RIDERS",restoriders+" quedan afuera");
        totalheats = (int) Math.ceil(totalridersfixture/heatgroup);
        if (restoriders!=0) { totalheats++; }
        Log.d("Math.ceil",totalridersfixture+"/"+heatgroup+" = "+totalheats+" heat/s");

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

        return v;
    }

    // Metodo que devuelve el numero con el que se debe agrupar los Heats del Fixture
    public int heatGroupQuery(int n) {
        if (n>1){
            //entro el 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17 infinito
            if (n%2==0) {
                //es par
                if (n!=2) {
                    //entro el 4,6,8,10,12,14,16,50,54,62
                    if (n%4==0) {
                        //entro el 4,8,12,16
                        return 4;
                    } else if (n%3==0) {
                        //entro el 6,10,14,54,62
                        return 3;
                    } else if (n%5==0) { //entro el 10,14
                        return 5;
                    } else {
                        if ((n%4)>0) {
                            switch (n) {
                                case 14: return 5;
                                default: return 4;
                            }
                        }
                        return 4;
                    }
                } else {
                    //entro el 2
                    return 2; //final de 2
                }
            } else {
                //es impar
                //entro el 3,5,7,9,11,13,15,17,49,53,61,63
                if (n%3==0) {
                    //entro el 3,9,15
                    if (n!=3) {
                        //entro el 9,15
                        if ((n%3)>2) { //si lo que quedan afuera son mas que dos riders...
                            //entro el 15
                            return 4;
                        } else {
                            //entro el 9
                            return 3;
                        }
                    } else {
                        //entro el 3
                        return 3;
                    }
                } else if (n%5==0) {
                    //entro el 5,65
                    return 5;
                } else {
                    //entro el 11,13,49,53
                    switch (n) {
                        case 41: return 3;
                        case 49: return 5;
                        case 53: return 5;
                        default: return 4;
                    }
                }
            }
        } else {
            //return "Solo hay un inscripto, por lo tanto no podras competir";
            return 1;
        }
    }

}
