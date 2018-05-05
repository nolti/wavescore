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
import com.voltiosx.nolti.wavescore.models.InstanceItem;
import com.voltiosx.nolti.wavescore.models.ListItem;
import com.voltiosx.nolti.wavescore.models.ResultItem;
import com.voltiosx.nolti.wavescore.models.Rider;
import com.voltiosx.nolti.wavescore.ui.adapters.ResultsAdapter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResultsFragment extends Fragment {

    private static final String KEY = "results";
    private ArrayList<Rider> resultsheat = new ArrayList<>();
    @NonNull
    private List<ListItem> items = new ArrayList<>();

    public ResultsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recibe argumentos
        if (getArguments() != null) {
            resultsheat = getArguments().getParcelableArrayList(KEY);
            Log.d("RESULTADOS DEL HEAT", String.valueOf(resultsheat));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_results, container, false);
        Context contexto = vista.getContext();

        // FORMATEO LA INSTANCIA
        items.add(new InstanceItem("Semifinal #1"));
        // FORMATEO LOS RESULTADOS
        int totadvancing = resultsheat.size();
        //int totadvancing = 2;
        for (int i=0; i<totadvancing ;i++){
            Rider rider = resultsheat.get(i);
            int id = rider.getId();
            int position = rider.getPosition();
            String name = rider.getName();
            String hometown = rider.getHometown();
            int ranking = 7;//int ranking = rider.getRanking();
            Double score = rider.getHeatscores().get(0);
            ArrayList<Integer> tablecolor = rider.getColors();
            ArrayList<Double> wavestaken = rider.getWavestaken();
            items.add(new ResultItem(id, position, name, hometown, ranking, score, tablecolor, wavestaken));
        }
        // Set the adapter to RECYCLER
        RecyclerView recycler = vista.findViewById(R.id.recycler_results_heat);
        recycler.setLayoutManager(new LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(new ResultsAdapter(items));
        return vista;
    }

    // metodo redondeo Double
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // Metodo inicializa los puntajes al azar
    private ArrayList<Double> initwavestaken(){
        ArrayList<Double> waves = new ArrayList<>();
        Random r = new Random();
        Double random, min, max;
        min=1.0;
        max=10.0;
        for (int i=0; i<10; i++){
            random = min + (max - min) * r.nextDouble();
            random = round(random,1);
            waves.add(i, random);
        }
        return waves;
    }
}