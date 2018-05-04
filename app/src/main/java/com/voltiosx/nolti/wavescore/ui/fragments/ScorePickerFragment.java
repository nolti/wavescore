package com.voltiosx.nolti.wavescore.ui.fragments;

import android.app.Activity;
import android.app.FragmentContainer;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shawnlin.numberpicker.NumberPicker;
//import com.voltiosx.nolti.wavescore.R;
import com.voltiosx.nolti.wavescore.R;
import com.voltiosx.nolti.wavescore.clases.CircleButton;
import com.voltiosx.nolti.wavescore.io.ScorePickerComunicator;
import com.voltiosx.nolti.wavescore.models.Wave;
import com.voltiosx.nolti.wavescore.ui.adapters.WavesAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
//import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ScorePickerFragment extends Fragment {
    private Context context;
    private static String TAG = "NumberPicker";
    //private String[] data = {"1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5", "9", "9.5", "10"};
    private String[] data = {"10", "9.5", "9", "8.5", "8", "7.5", "7", "6.5", "6", "5.5", "5", "4.5", "4", "3.5", "3", "2.5", "2", "1.5", "1"};
    public View vista;
    // public GridView gridWaves;
    private FrameLayout frameScorepicker;
    private int bakgroundScorepicker;
    private int colorScorepicker;
    private Double score;
    private TextView tvScore;
    private CircleButton btnScore;
    private CircleButton btnClose;
    // private int idrider;
    // private int index;
    // private Double score, bestscore1, bestscore2, totalscore = 0.0;
    private ScorePickerComunicator scorepickercomunicator;
    /* DATOS DEL RIDER */
    private String namerider;

    public ScorePickerFragment() {
        // Required empty public constructor
    }
    /* 1C >> Metodo para comunicarse enviar el SCORE de SCOREPICKER a la MainActivity */
    public void sendscore(Double s){
        // escuchador de la interfaz ScorePickerComunicator
        if (scorepickercomunicator!=null){
            scorepickercomunicator.onScoreSelected(s);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // recibo el iD del rider a calificar
        if (getArguments()!=null) {
            //bundlescorepicker.putInt("bakgroundScorepicker", COLORrider);
            bakgroundScorepicker = getArguments().getInt("bakgroundScorepicker");
            colorScorepicker = getArguments().getInt("colorScorepicker");
            // idrider = getArguments().getInt("idrider");
            // index = getArguments().getInt("index");
            // Log.d("ID SCOREPICKER ", String.valueOf(idrider));
            // Log.d("INDEX SCOREPICKER ", String.valueOf(index));
            //openpros = getArguments().getParcelableArrayList(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        vista = inflater.inflate(R.layout.score_picker, container, false);
        frameScorepicker = vista.findViewById(R.id.bg_scorepicker);
        // gridWaves = vista.findViewById(R.id.gridwaves);
        tvScore = vista.findViewById(R.id.tv_score);
        btnScore = vista.findViewById(R.id.btn_score);
        btnClose = vista.findViewById(R.id.btn_close);
        NumberPicker numberPicker = vista.findViewById(R.id.number_picker);

        // Set colors
        btnClose.setColor(colorScorepicker);
        btnScore.setColor(colorScorepicker);
        tvScore.setTextColor(colorScorepicker);
        /*btnClose.setColor(getResources().getColor(R.color.darkgrey));
        btnScore.setColor(getResources().getColor(R.color.darkgrey));*/
        btnClose.setColorFilter(getResources().getColor(R.color.monokai_magentuosa));
        btnScore.setColorFilter(getResources().getColor(R.color.monokai_green));
        frameScorepicker.setBackgroundColor(bakgroundScorepicker);

        // Set divider color
        numberPicker.setDividerColor(colorScorepicker);
        //numberPicker.setDividerColorResource(R.color.secondaryLightColor);

        // Set formatter
        numberPicker.setFormatter(getString(R.string.number_picker_formatter));
        numberPicker.setFormatter(R.string.number_picker_formatter);

        // Set selected text color
        numberPicker.setSelectedTextColor(colorScorepicker);
        //numberPicker.setSelectedTextColorResource(android.R.color.white);

        // Set selected text size
        //numberPicker.setSelectedTextSize(getResources().getDimension(R.dimen.score_selected_text_size));
        //numberPicker.setSelectedTextSize(R.dimen.score_selected_text_size);

        // Set text color
        numberPicker.setTextColor(colorScorepicker);
        //numberPicker.setTextColor(ContextCompat.getColor(context, R.color.monokai_magentuosa));
        //numberPicker.setTextColorResource(R.color.colorAccent);

        // Set text size
        //numberPicker.setTextSize(getResources().getDimension(R.dimen.score_text_size));
        //numberPicker.setTextSize(R.dimen.score_text_size);

        // Set typeface
        /*numberPicker.setTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setTypeface(getString(R.string.roboto_light), Typeface.NORMAL);
        numberPicker.setTypeface(getString(R.string.roboto_light));
        numberPicker.setTypeface(R.string.roboto_light, Typeface.NORMAL);
        numberPicker.setTypeface(R.string.roboto_light);*/

        // Using string values
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(11);
        score = Double.valueOf(data[numberPicker.getValue()-1]);
        tvScore.setText(String.valueOf(score));

        // Set fading edge enabled
        numberPicker.setFadingEdgeEnabled(true);

        // Set scroller enabled
        numberPicker.setScrollerEnabled(true);

        // Set wrap selector wheel
        numberPicker.setWrapSelectorWheel(true);

        // OnClickListener
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click en el valor actual");
            }
        });

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int p = Integer.valueOf(newVal)-1;
                score = Double.valueOf(data[p]);
                if (score!=null) {
                    textscoring(score);
                }
            }
        });

        /* BOTON CALIFICAR (SCORING) */
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // scoring(score);
                // enviar a HeatFragment el puntaje
                sendscore(score);
                // sendscore(score, index);
                // volver a HeatFragment
                // getFragmentManager().popBackStack();
            }
        });

        /* BOTON BACK */
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendscore(0.0);
                // getFragmentManager().popBackStack();
                Log.d("BOTON PRESIONADO: ", "BACK");
            }
        });

        return vista;
    }

    public void textscoring(Double score) {
        // Seteo el textview del puntaje
        tvScore.setText(String.valueOf(score));
        //Log.d("WAVE SCORE: ", String.valueOf(score));
    }

    /* El Fragmento captura la implementación de la interfaz durante el método de ciclo de vida en el onAttach()
    e iguala su contexto y llama a los métodos de la Interfaz para comunicarse con la Actividad. */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Igualo el contexto del scorepickercomunicator con la MainActivity
        if (context instanceof ScorePickerComunicator){
            scorepickercomunicator = (ScorePickerComunicator) context;
        } else {
            throw new RuntimeException(context.toString()+ "La comunicacion no ha podido llevarse a cabo");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        scorepickercomunicator = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int idrider);
    }

    /*public void printgridWaves(ArrayList<Wave> waveslist){
        Adapter adapterwaves = new WavesAdapter(getActivity(), R.layout.view_list_item_wave, waveslist);
        gridWaves.setAdapter((ListAdapter) adapterwaves);
        Log.d("OLAS DESORDENADAS: ", printWaves(wavestaken));
    }*/

    /*public void sortWaves(){
        *//* ORDENO LAS OLAS *//*
        Collections.sort(wavestakensort, new Comparator<Wave>() {
            @Override
            public int compare(Wave o1, Wave o2) {
                return new Double(o1.getScore()).compareTo(new Double(o2.getScore()));
            }
        });
    }*/

    // Método para recorrer e imprimir objetos Wave
    /*static String printWaves(ArrayList<Wave> waveslist) {
        Iterator<Wave> iterador = waveslist.iterator();
        String stringwave = "";
        while (iterador.hasNext()) {
            Wave w=iterador.next();
            stringwave += "WAVE#"+String.valueOf((w.getPosition()+1))+"= "+String.valueOf(w.getScore())+" / ";
        }
        return stringwave;
    }*/

    // Procedimiento mejores puntasjes y sumatoria total
    /*public void tryScore(){
        *//* // Primary best score // Second best score // Total score *//*
        int i=wavestakensort.size()-1; Log.d("INDICE TRYSCORE >>", String.valueOf(i));
        if (i==0) {
            // Primary best score
            Log.d("INDICE BEST SCORE >>", String.valueOf(i));
            bestscore1 = wavestakensort.get(i).getScore(); Log.d("BEST SCORE >>", String.valueOf(bestscore1));
            bestscore2 = 0.0;
        } else if (i>0){
            // Primary & Secondary best score
            Log.d("INDICE TOTAL SCORE >>", String.valueOf(i));
            bestscore1 = wavestakensort.get(i).getScore(); Log.d("BEST SCORE1 >>", String.valueOf(bestscore1));
            bestscore2 = wavestakensort.get(i-1).getScore(); Log.d("BEST SCORE2 >>", String.valueOf(bestscore2));
        }
        totalscore = bestscore1+bestscore2;
    }*/

    // Método devuelve el mejor puntaje
    /*static String bestWave1(ArrayList<Wave> waveslist) {
        String bestwave="";
        int t = waveslist.size()-1;
        Log.d("TOTAL AFUERA ",String.valueOf(t));
        if (t>=0){
            bestwave=String.valueOf(waveslist.get(t).getScore());
        }
        return bestwave;
    }*/
    // Método devuelve el segundo mejor puntaje
    /*static String bestWave2(ArrayList<Wave> waveslist) {
        String secbestwave="";
        int t = waveslist.size()-2;
        if (t>=1){
            secbestwave=String.valueOf(waveslist.get(t).getScore());
        }
        return secbestwave;
    }*/

    /*public void closefragment(){
        getActivity().getFragmentManager().popBackStack();
    }*/

    /*public static int maxwaves = 10;
    public ArrayList<Wave> wavestaken = new ArrayList<>();
    public ArrayList<Wave> wavestakensort = new ArrayList<>();
    Wave wave = new Wave(0,0.0); // Ola desordenada
    public void scoring(final Double score) {
        int totalwavestaken = wavestaken.size();
        int numwaves = totalwavestaken+1;
        if (totalwavestaken<maxwaves) {
            wave = new Wave(totalwavestaken, score);
            // Asignar a Olas desordenadas
            wavestaken.add(wave);
            Log.d("INDICE OLA >>", String.valueOf(totalwavestaken));
            Log.d("NUM OLA >>", String.valueOf(numwaves));
            printgridWaves(wavestaken);

            *//* -------------- envio de datos ---------------  *//*

            *//* ------------- fin envio de datos ------------  *//*

            // Asignar a Olas ordenadas
            wavestakensort.add(wave);
            sortWaves(); Log.d("OLAS ORDENADAS: ", printWaves(wavestakensort));
            tryScore();

            // Mostrar resultados
            TextView tvBestWave1,tvBestWave2,tvTotaScore;
            tvBestWave1 = vista.findViewById(R.id.tv_best_score1);
            tvBestWave2 = vista.findViewById(R.id.tv_best_score2);
            tvTotaScore = vista.findViewById(R.id.id_total_score);
            tvBestWave1.setText(String.valueOf(bestscore1));
            tvBestWave2.setText(String.valueOf(bestscore2));
            tvTotaScore.setText(String.valueOf(totalscore));
            *//*tvBestWave1.setText(bestWave1(wavestakensort));
            tvBestWave2.setText(bestWave2(wavestakensort));*//*

            if (totalwavestaken>=(maxwaves-1)){
                new MaterialDialog.Builder(context).content(R.string.maximum_waves).positiveText(R.string.agree).show();
            }
        } else {
            new MaterialDialog.Builder(context).content(R.string.no_more_waves).positiveText(R.string.agree).show();
        }
    }*/

}