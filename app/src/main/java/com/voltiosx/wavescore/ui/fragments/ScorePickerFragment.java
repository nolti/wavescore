package com.voltiosx.wavescore.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.shawnlin.numberpicker.NumberPicker;
import com.voltiosx.wavescore.R;
import com.voltiosx.wavescore.clases.CircleButton;
import com.voltiosx.wavescore.io.ScorePickerComunicator;

public class ScorePickerFragment extends Fragment {
    private Context context;
    private static String TAG = "NumberPicker";
    //private String[] data = {"1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5", "9", "9.5", "10"};
    private String[] data = {"10", "9.5", "9", "8.5", "8", "7.5", "7", "6.5", "6", "5.5", "5", "4.5", "4", "3.5", "3", "2.5", "2", "1.5", "1"};
    public View vista;
    private FrameLayout frameScorepicker;
    private int bakgroundScorepicker;
    private int colorScorepicker;
    private Double score;
    private TextView tvScore;
    private CircleButton btnScore;
    private CircleButton btnClose;
    private ScorePickerComunicator scorepickercomunicator;


    public ScorePickerFragment() {
        // Required empty public constructor
    }

    /* 1C >> Metodo para comunicarse enviar el SCORE de SCOREPICKER a la MainActivity */
    public void sendscore(Double s) {
        // escuchador de la interfaz ScorePickerComunicator
        if (scorepickercomunicator != null) {
            scorepickercomunicator.onScoreSelected(s);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bakgroundScorepicker = getArguments().getInt("bakgroundScorepicker");
            colorScorepicker = getArguments().getInt("colorScorepicker");
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
        /*btnClose.setColor(ContextCompat.getColor(context, R.color.darkgrey));
        btnScore.setColor(ContextCompat.getColor(context, R.color.darkgrey));*/
        btnClose.setColorFilter(ContextCompat.getColor(context, R.color.monokai_magentuosa));
        btnScore.setColorFilter(ContextCompat.getColor(context, R.color.monokai_green));
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
        score = Double.valueOf(data[numberPicker.getValue() - 1]);
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
                int p = Integer.valueOf(newVal) - 1;
                score = Double.valueOf(data[p]);
                if (score != null) {
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
        if (context instanceof ScorePickerComunicator) {
            scorepickercomunicator = (ScorePickerComunicator) context;
        } else {
            throw new RuntimeException(context.toString() + "La comunicacion no ha podido llevarse a cabo");
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
}