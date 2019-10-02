package com.example.nolti.wavescore.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nolti.wavescore.R;
import com.example.nolti.wavescore.models.FixtureItem;
import com.example.nolti.wavescore.models.HeaderFixtureItem;
import com.example.nolti.wavescore.models.Rider;
import com.example.nolti.wavescore.models.RiderFixtureItem;
import com.example.nolti.wavescore.ui.adapters.FixtureAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class FixtureFragment extends Fragment {

    private static final String KEY = "fixturelist";
    private int totalridersfixture;
    private int totalheats;
    private ArrayList<Rider> fixturelist = new ArrayList<>();
    @NonNull
    private List<FixtureItem> fixtureitems = new ArrayList<>();
    // private Fixture fixture = new Fixture();
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
        // test(100);
    }

    public void test(int t) {
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<Double> wavestaken = new ArrayList<>();
        ArrayList<Double> sortwavestaken = new ArrayList<>();
        ArrayList<Double> heatscores = new ArrayList<>();
        for (int i=0; i<t; i++) {
            fixturelist.add(i, new Rider(i, i, "rider " + (i + 1), "", "", colors, wavestaken, sortwavestaken, heatscores, ""));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fixture, container, false);
        Context context = v.getContext();

        totalridersfixture = fixturelist.size();
        /* COMPRUEBO SI SE PUEDE COMPETIR */
        if (totalridersfixture>1) {
            /* COMPRUEBO SI TOTAL DE RIDERS ES PRIMO */
            if (isPrime(totalridersfixture)) {
                // es primo;
                Log.d("ES PRIMO", "num "+totalridersfixture);
                switch (totalridersfixture) {
                    case 11: heatgroup=4; break;
                    case 19: heatgroup=4; break;
                    default: heatgroup=5;
                }
                Log.d("AGRUPAR", "de a "+heatgroup);
            } else {
                // no es primo
                heatgroup = heatGroupQuery(totalridersfixture);
            }
            // para todos...
            totalheats = (int) Math.ceil(totalridersfixture/heatgroup);
            restoriders = totalridersfixture%heatgroup;
            if (restoriders!=0) { totalheats++; }
            heatFormat(totalridersfixture);
        } else {
            // NO SE PUEDE COMPETIR
        }

        /*for (int i=0; i<100; i++) {
            if (isPrime(i)) {
                Log.d("COMPETENCIA",":::::::::::::::::: para "+i+" RIDERS "+"::::::::::::::::::");
                // Log.d("", i + " RIDERS AGRUPAR DE A " + heatGroupQuery(i));
                heatgroup = heatGroupQuery(i);
                totalheats = (int) Math.ceil(i/heatgroup);
                restoriders = i%heatgroup;
                if (restoriders!=0) { totalheats++; }
                heatFormat(i);
            }//Log.d("",i+" ¿Es primo? "+isPrime(i));
        }*/

        /*restoriders = totalridersfixture%heatgroup;
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
            *//* ENCABEZADO ASIGNADO *//*

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
                *//* RIDER ASIGNADO *//*
            } //for al siguiente rider a asignar al heat

        } // for al siguiente encabezado
*/
        // Set the adapter to RECYCLER
        RecyclerView recyclerfixture = v.findViewById(R.id.recycler_fixture);
        recyclerfixture.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerfixture.setAdapter(new FixtureAdapter(fixtureitems));

        return v;
    }

    // Metodo que conforma los Heats
    public void heatFormat(int totalridersfixture) {
        int r = 0;
        int unassigned=totalridersfixture;
        int penultimateheat=(totalheats-2);
        int latestheat=(totalheats-1);
        for (int h = 0; h < totalheats; h++) {
            int numheat = h+1;
            if ( h==penultimateheat ){
                Log.d("PENULTIMO", "HEAT #"+numheat);
                Log.d("RIDERS", "NO ASIGNADOS "+unassigned);
                java.math.BigDecimal a = new java.math.BigDecimal(unassigned);
                java.math.BigDecimal b = new java.math.BigDecimal(2);
                BigDecimal group = a.divide(b, RoundingMode.CEILING);
                heatgroup = group.intValueExact();
                Log.d("TOTAL RIDERS", totalridersfixture+" / reagrupar de a "+group);
            } else if ( h==latestheat ){
                Log.d("ULTIMO", "HEAT #"+numheat);
            }
            String titleheat = "HEAT #"+numheat;
            Log.d("", titleheat);
            HeaderFixtureItem headerfixture = new HeaderFixtureItem(numheat, titleheat);
            fixtureitems.add(headerfixture);
            for (int i=0; i<heatgroup; i++) {
                if (r<totalridersfixture) {
                    //String namerider = "competidor";
                    String namerider = fixturelist.get(r).getName();
                    Log.d("", (i+1)+" "+namerider);
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
                    unassigned--;
                }
            } //for al siguiente rider a asignar al heat
        } // for al siguiente encabezado
    }

    // Metodo que determina si el total de riders "es primo o no"
    public boolean isPrime(int n) {
        //comprueba si n es múltiplo de 2
        if (n%2==0) return false;
        //si no, entonces solo revisa las probabilidades
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    // Metodo que devuelve el numero de agrupamiento del Fixture segun un "numero compuesto"
    public int heatGroupQuery(int n) {
        if (n%2==0) {
            //es par
            if (n!=2) {
                if ((n%4==0)||(n%4==2)) {
                    return 4;
                } else if (n%3==0) {
                    return 3;
                } else {
                    return 5;
                }
            } else {
                return 2; //final de 2
            }
        } else {
            //es impar
            if (n%3==0) {
                if (n!=3) {
                    if ((n%3)>2) { //si lo que quedan afuera son mas que dos riders...
                        return 4;
                    } else {
                        return 3;
                    }
                } else {
                    return 3;
                }
            } else {
                return 5;
            }
        }
    } // end heatGroupQuery
}
