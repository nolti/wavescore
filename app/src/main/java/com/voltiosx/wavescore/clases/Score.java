package com.voltiosx.wavescore.clases;

import android.util.Log;

import com.voltiosx.wavescore.models.Wave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Score {

    private int idrider;
    private Double score;
    private Double bestscore1;
    private Double bestscore2;
    private Double totalscore;

    public int getIdrider() {
        return idrider;
    }
    public void setIdrider(int idrider) {
        this.idrider = idrider;
    }

    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }

    public static int maxwaves = 10;
    public ArrayList<Wave> wavestaken = new ArrayList<>();
    public ArrayList<Wave> wavestakensort = new ArrayList<>();

    public Score(int idrider, Double score){
        this.idrider = idrider;
        this.score = score;
    }

    Wave wave = new Wave(0,0.0); // Ola desordenada
    public void scoring(final Double score) {
        int totalwavestaken = wavestaken.size();
        int numwaves = totalwavestaken+1;
        if (totalwavestaken<maxwaves) {
            wave = new Wave(totalwavestaken, score);
            // Asignar a Olas desordenadas
            wavestaken.add(wave);
            // 1er metodo llamado
            printgridWaves(wavestaken);

            /* -------------- envio de datos ---------------  */
            /* ------------- fin envio de datos ------------  */

            // Asignar a Olas ordenadas
            wavestakensort.add(wave);
            // 2do metodo llamado
            sortWaves(); Log.d("OLAS ORDENADAS: ", printWaves(wavestakensort)); //metodo log printWaves
            // 3er metodo llamado
            tryScore();

            // Mostrar resultados
            /*TextView tvBestWave1,tvBestWave2,tvTotaScore;
            tvBestWave1 = vista.findViewById(R.id.tv_best_score1);
            tvBestWave2 = vista.findViewById(R.id.tv_best_score2);
            tvTotaScore = vista.findViewById(R.id.id_total_score);
            tvBestWave1.setText(String.valueOf(bestscore1));
            tvBestWave2.setText(String.valueOf(bestscore2));
            tvTotaScore.setText(String.valueOf(totalscore));*/

            if (totalwavestaken>=(maxwaves-1)){
                //new MaterialDialog.Builder(context).content(R.string.maximum_waves).positiveText(R.string.agree).show();
            }
        } else {
            //new MaterialDialog.Builder(context).content(R.string.no_more_waves).positiveText(R.string.agree).show();
        }
    }
    // 1er metodo llamado
    public void printgridWaves(ArrayList<Wave> waveslist){
        //Adapter adapterwaves = new WavesAdapter(getActivity(), R.layout.view_list_item_wave, waveslist);
        //gridWaves.setAdapter((ListAdapter) adapterwaves);
        Log.d("OLAS DESORDENADAS: ", printWaves(wavestaken));
    }

    // 2do metodo llamado
    public void sortWaves(){
        /* ORDENO LAS OLAS */
        Collections.sort(wavestakensort, new Comparator<Wave>() {
            @Override
            public int compare(Wave o1, Wave o2) {
                return new Double(o1.getScore()).compareTo(new Double(o2.getScore()));
            }
        });
    }

    // Procedimiento mejores puntajes y sumatoria total
    public void tryScore(){
        /* // Primary best score // Second best score // Total score */
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
    }

    // Método para recorrer e imprimir objetos Wave
    static String printWaves(ArrayList<Wave> waveslist) {
        Iterator<Wave> iterador = waveslist.iterator();
        String stringwave = "";
        while (iterador.hasNext()) {
            Wave w=iterador.next();
            stringwave += "WAVE#"+String.valueOf((w.getPosition()+1))+"= "+String.valueOf(w.getScore())+" / ";
        }
        return stringwave;
    }

}
