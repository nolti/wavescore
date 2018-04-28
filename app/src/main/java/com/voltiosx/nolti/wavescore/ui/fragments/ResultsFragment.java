package com.voltiosx.nolti.wavescore.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voltiosx.nolti.wavescore.R;
import com.voltiosx.nolti.wavescore.models.InstanceItem;
import com.voltiosx.nolti.wavescore.models.ListItem;
import com.voltiosx.nolti.wavescore.models.ResultItem;
import com.voltiosx.nolti.wavescore.ui.adapters.ResultsAdapter;
import com.voltiosx.nolti.wavescore.ui.fragments.dummy.DummyContent;
import com.voltiosx.nolti.wavescore.ui.fragments.dummy.DummyContent.DummyItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResultsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    @NonNull
    private List<ListItem> items = new ArrayList<>();

    public ResultsFragment() {
    }

    /*// TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ResultsFragment newInstance(int columnCount) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recibe argumentos
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_results, container, false);
        Context contexto = vista.getContext();

        // inicializo colores lycras
        int colorTextLight = getResources().getColor(R.color.colorTextLight);
        int lycra1 = getResources().getColor(R.color.colorLycra1);
        ArrayList<Integer> tablecolor1 = new ArrayList<>();
        tablecolor1.add(colorTextLight);
        tablecolor1.add(lycra1);

        // CARGA DE DATOS
        items.add(new InstanceItem("Final"));
        for (int i=0; i<3;i++){
            items.add(new ResultItem(0, i, "Nombre Apellido" +i,"Ciudad Natal"+i,"Rank #"+i,"14."+i, tablecolor1, initwavestaken()));
        }
        items.add(new InstanceItem("Semifinal #1"));
        for (int i=0; i<2;i++){
            items.add(new ResultItem(0, i, "Nombre Apellido" +i,"Ciudad Natal"+i,"Rank #"+i,"14."+i, tablecolor1, initwavestaken()));
        }
        items.add(new InstanceItem("Semifinal #2"));
        for (int i=0; i<2;i++){
            items.add(new ResultItem(0, i, "Nombre Apellido" +i,"Ciudad Natal"+i,"Rank #"+i,"14."+i, tablecolor1, initwavestaken()));
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}