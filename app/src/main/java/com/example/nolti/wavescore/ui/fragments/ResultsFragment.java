package com.example.nolti.wavescore.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nolti.wavescore.R;
import com.example.nolti.wavescore.models.InstanceItem;
import com.example.nolti.wavescore.models.ListItem;
import com.example.nolti.wavescore.models.ResultItem;
import com.example.nolti.wavescore.models.Rider;
import com.example.nolti.wavescore.models.RiderHeat;
import com.example.nolti.wavescore.ui.adapters.ResultsAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private FirebaseDatabase WaveScoreDB;
    private DatabaseReference WaveScoreDBRef;

    public ResultsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recibe argumentos
        if (getArguments() != null) {
            resultsheat = getArguments().getParcelableArrayList(KEY);
            Log.d("RESULTADOS DEL HEAT", String.valueOf(resultsheat));

            // Set instance of FIREBASE DATABASE REFERENCE
            //WaveScoreDBRef = FirebaseDatabase.getInstance().getReference("RidersHeats");
            WaveScoreDB = FirebaseDatabase.getInstance();
            WaveScoreDBRef = FirebaseDatabase.getInstance().getReference();
            WaveScoreDBRef.setValue("Hola");
            writeRiderHeat();
            Log.d("FIREBASE CONECT KEY", String.valueOf(WaveScoreDBRef.getKey()));


            /*
            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("message");

            myRef.setValue("Hello, World!");
            */

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

    // Metodo para registrar los RIDERS HEATS
    public void writeRiderHeat() { //public void writeRiderHeat(String userId, String name, String email, etc) {
        //String key = dbRef.push().getKey();

        // Preparo un objeto con los datos del RiderHeat
        ArrayList wavescores = new ArrayList<Double>();
        wavescores.add(3.5);
        wavescores.add(9);
        wavescores.add(2);
        wavescores.add(5);
        wavescores.add(7.5);
        RiderHeat riderHeat = new RiderHeat(1, 5, "7", "Manuel Santamaria", "Mar del Plata", "OPEN PRO", "Semifinal #1", "Gano por 2.5", "Azul", wavescores, 16.5);
        RiderHeat riderHeat2 = new RiderHeat(2, 5, "7", "Celeste Marinesco", "Mar del Plata", "DAMAS PRO", "FINAL", "Gano por 1.5", "Azul", wavescores, 1.5);
        WaveScoreDBRef.setValue(riderHeat);
        WaveScoreDBRef.setValue(riderHeat2);
        //WaveScoreDB.child("RidersHeats").setValue(riderHeat);
        /*User user = new User(name, email);
        mDatabase.child("users").child(userId).setValue(user);*/
    }

    // Metodo redondeo Double
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