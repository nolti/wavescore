package com.voltiosx.nolti.wavescore.ui.fragments;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.afollestad.materialdialogs.MaterialDialog;
import com.voltiosx.nolti.wavescore.R;
import com.voltiosx.nolti.wavescore.io.ScorePickerComunicator;
import com.voltiosx.nolti.wavescore.models.ColorWaveScore;
import com.voltiosx.nolti.wavescore.models.Rider;
import com.voltiosx.nolti.wavescore.models.Wave;
import com.voltiosx.nolti.wavescore.ui.adapters.HeatAdapter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class HeatFragment extends Fragment {

    private Context contexto;
    public ArrayList<Rider> ridersheat = new ArrayList<>();
    public ArrayList<Double> scoredwaves = new ArrayList<>();
    public String statusridersheat;

    // public ArrayList<ArrayList<Double>> matrixscores = new ArrayList<>();
    // public ArrayList<Double> heatscores = new ArrayList<>();
    // public ArrayList<Double> wavestakensort = new ArrayList<>();

    // 1A >> defino el Listener de la interfaz
    private ScorePickerComunicator scorepickercomunicator;

    // Declaro las Vistas Globales
    private View vista;
    private RecyclerView recycler;
    public HeatAdapter heatAdapter;

    /* VARIABLES HEAT SCORING GLOBALES*/
    public int IDrider;
    public int INDEXrider;
    public static int maxwaves = 10;
    private Double scorerider, totalscore, tiebreakescore;

    // Required empty public constructor
    public HeatFragment() {

    }
    /* 1C >> Metodo para comunicarse con el SCOREPICKER desde la MainActivity*/
    public void scorepicker(int idrider, int p){
        // ScorePickerComunicator
        if (scorepickercomunicator!=null){
            // scorepickercomunicator.onRiderSelected(idrider, p);
        }
    }

    // NO LE PASO NADA solo necesito saber el valor de scorepicker al regresar
    public void scorepicker(){
        ScorePickerFragment scorePickerFragment = new ScorePickerFragment();
        getFragmentManager().beginTransaction().replace(R.id.maincontainer, scorePickerFragment).addToBackStack(null).commit();
        Log.d("SCOREPICKER ", "open");
    }

    // metodo redondeo Double
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // Metodo inicializa los puntajes al azar
    private ArrayList<Wave> initwavestake(){
        ArrayList<Wave> waves = new ArrayList<>();
        Random r = new Random();
        Double random, min, max;
        min=1.0;
        max=10.0;
        for (int i=0; i<10; i++){
            random = min + (max - min) * r.nextDouble();
            random = round(random,1);
            Wave wave = new Wave(i, random);
            waves.add(i, wave);
            Log.d("WAVE #"+i, String.valueOf(random));
        }
        return waves;
    }

    // metodo inicializa los puntajes a cero
    private ArrayList<Double> initscores(int t){
        ArrayList<Double> scores = new ArrayList<>();
        for (int i=0; i<t; i++){ scores.add(0.0); } return scores;
    }

    /*private ArrayList<Wave> initwavestaken(){
        ArrayList<Wave> waves = new ArrayList<>();
        for (int i=0; i<10; i++){
            waves.add(i, new Wave(i, 0.0));
        }
        return waves;
    }*/

    private ArrayList<Wave> initsortwavestaken(){
        ArrayList<Wave> sortwaves = new ArrayList<>();
        for (int i=0; i<10; i++){
            sortwaves.add(i, new Wave(i, 0.0));
        }
        return sortwaves;
    }

    // metodo inicializa los puntajes a cero
    private ArrayList<Double> initheatscores(){
        ArrayList<Double> heatscores = new ArrayList<>();
        for (int i=0; i<10; i++){
            heatscores.add(0.0);
        }
        return heatscores;
    }

    /*private ArrayList<Double> initheatscores(){
        ArrayList<Double> heatscores = new ArrayList<>();
        for (int i=0; i<13; i++){
            heatscores.add(0.0);
        }
        return heatscores;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contexto = getActivity();

        // inicializo colores lycras
        int colorTextLight, colorTextDark;
        colorTextLight = getResources().getColor(R.color.colorTextLight);
        colorTextDark = getResources().getColor(R.color.colorTextDark);
        int lycra1, lycra2, lycra3, lycra4, lycra5, lycra6, lycra7, lycra8;
        lycra1 = getResources().getColor(R.color.colorLycra1);
        lycra2 = getResources().getColor(R.color.colorLycra2);
        lycra3 = getResources().getColor(R.color.colorLycra3);
        lycra4 = getResources().getColor(R.color.colorLycra4);
        lycra5 = getResources().getColor(R.color.colorLycra5);
        lycra6 = getResources().getColor(R.color.colorLycra6);
        lycra7 = getResources().getColor(R.color.colorLycra7);
        lycra8 = getResources().getColor(R.color.colorLycra8);
        ArrayList<Integer> cardcolors1 = new ArrayList<>();
        ArrayList<Integer> cardcolors2 = new ArrayList<>();
        ArrayList<Integer> cardcolors3 = new ArrayList<>();
        ArrayList<Integer> cardcolors4 = new ArrayList<>();
        ArrayList<Integer> cardcolors5 = new ArrayList<>();
        ArrayList<Integer> cardcolors6 = new ArrayList<>();
        ArrayList<Integer> cardcolors7 = new ArrayList<>();
        ArrayList<Integer> cardcolors8 = new ArrayList<>();
        cardcolors1.add(colorTextLight);
        cardcolors1.add(lycra1);
        cardcolors2.add(colorTextLight);
        cardcolors2.add(lycra2);
        cardcolors3.add(colorTextDark);
        cardcolors3.add(lycra3);
        cardcolors4.add(colorTextLight);
        cardcolors4.add(lycra4);
        cardcolors5.add(colorTextLight);
        cardcolors5.add(lycra5);
        cardcolors6.add(colorTextLight);
        cardcolors6.add(lycra6);
        cardcolors7.add(colorTextLight);
        cardcolors7.add(lycra7);
        cardcolors8.add(colorTextDark);
        cardcolors8.add(lycra8);

        Log.d("onCreate", "RIDERSHEAT");

        /*statusridersheat.add(0, getString(R.string.status0) );
        statusridersheat.add(1, getString(R.string.status1));
        statusridersheat.add(2, getString(R.string.status2));
        statusridersheat.add(3, getString(R.string.status3));
        statusridersheat.add(4, getString(R.string.status4));
        statusridersheat.add(5, getString(R.string.status5));
        statusridersheat.add(6, getString(R.string.status6));
        statusridersheat.add(7, getString(R.string.status7));*/

        // status inicial
        statusridersheat = getString(R.string.status0);;

        // Inicializo valores ficticios
        ridersheat.add(new Rider(88,0,"Gustavo Alba", "Mar del Plata", "OPEN PRO", cardcolors2, initscores(10), initscores(10), initscores(4), statusridersheat));
        ridersheat.add(new Rider(11,1,"Manuel Santamaria", "Mar del Plata", "OPEN PRO", cardcolors1, initscores(10), initscores(10), initscores(4), statusridersheat));
        ridersheat.add(new Rider(22,2,"Emiliano Tabare", "Mar del Plata", "OPEN PRO", cardcolors3, initscores(10), initscores(10), initscores(4), statusridersheat));
        ridersheat.add(new Rider(33,3,"Eric Barberón ", "Necochea", "OPEN PRO", cardcolors4, initscores(10), initscores(10), initscores(4), statusridersheat));
        ridersheat.add(new Rider(44,4, "Patricio Galay ", "Mar del Plata", "OPEN PRO", cardcolors5, initscores(10), initscores(10), initscores(4), statusridersheat));
        ridersheat.add(new Rider(55,5, "Hugo Rolón", "Mar del Plata", "OPEN PRO", cardcolors6, initscores(10), initscores(10), initscores(4), statusridersheat));
        ridersheat.add(new Rider(66,6, "Ariel Di Giusto", "Santa Teresita", "OPEN PRO", cardcolors7, initscores(10), initscores(10), initscores(4), statusridersheat));
        ridersheat.add(new Rider(77,7, "Diego Gonzalez", "Mar del Plata", "OPEN PRO", cardcolors8, initscores(10), initscores(10), initscores(4), statusridersheat));
    }

    public ArrayList<Double> getScoredwaves(Rider r){
        int totalscored = r.getWavestaken().size();
        Log.d("TOTAL getScoredwaves >>", String.valueOf(totalscored));
        Double s;
        scoredwaves = initscores(10);
        Log.d("TOTAL getScoredwaves >>", "initscores: "+String.valueOf(scoredwaves.size()));
        for (int i=0; i<totalscored; i++){
            s = r.getWavestaken().get(i);
            if ( s!=0.0 ) {
                scoredwaves.set(i, r.getWavestaken().get(i));
                Log.d("scoredwaves list >>> ", String.valueOf(scoredwaves));
            }
        }
        return scoredwaves;
    }

    // 1er metodo llamado -> mover a clase Score
    // modificar aca para que puntee por el verdadero id
    // public void scoring(int idrider, int indexrider, Double scorerider) {
    public void scoring(Double s) {
        if ( s!=0.0 ) {
            Rider riderNow = ridersheat.get(INDEXrider);
            int t = riderNow.getWavestaken().size();
            Log.d("RIDER ACTUAL >>> ", riderNow.getNombre());
            Log.d("ID RIDER >>> ",String.valueOf(IDrider));
            Log.d("INDEX RIDER >>> ",String.valueOf(INDEXrider));
            Log.d("TOTAL WAVESTAKEN >>> ",String.valueOf(t)); // wavestaken devuelve 10 OK
            int i = 0;
            while (i < t) {
                Double initScore = riderNow.getWavestaken().get(i);
                Log.d("PUNTAJE inicial", String.valueOf(initScore));
                if (initScore == 0.0) {
                    Log.d("PUNTAJE a adicionar", String.valueOf(s));
                    // cambia el 0.0 por el score de la ola
                    riderNow.getWavestaken().set(i,s);
                    Log.d("PUNTAJE adicionado get", String.valueOf(riderNow.getWavestaken().get(i)));
                    Log.d("PUNTAJE adicionado get2", String.valueOf(riderNow.getWavestaken().get(i+1)));
                    // olas punteadas
                    scoredwaves.clear();
                    scoredwaves = getScoredwaves(riderNow); // devuelve: ArrayList<Double> scoredwaves "completado"
                    Log.d("SCOREDWAVES ",riderNow.getNombre()+" Olas punteadas "+String.valueOf(scoredwaves));
                    int totalscoredwaves = getScoredwaves(riderNow).size();
                    Log.d("SCOREDWAVES ",riderNow.getNombre()+" size Olas punteadas "+String.valueOf(totalscoredwaves));

                    // Promedio envio --> la fila riderNow y olas punteadas
                    averageScore(riderNow, scoredwaves); //OK
                    heatChangePosition(); // OK
                    heatSetPositions(); // OK
                    scoreNeededToWin(); // CORRIGIENDO
                    Log.d("VALOR i antes de break", String.valueOf(i));
                    Log.d("PUNTEO NUM DE OLA", String.valueOf(i+1));
                    break;
                }
                i++;
                Log.d("VALOR i luego de break", String.valueOf(i));
            }
        } // end if
    }
    // 2do metodo llamado -> Procedimiento mejores puntajes y sumatoria total
    public void averageScore(Rider averageRider, ArrayList<Double> averagedwaves){
        // ordenación descendente
        Comparator<Double> reverse = Collections.reverseOrder();
        Collections.sort(averagedwaves, reverse);
        Log.d("WAVES 9 ---> 1 ", String.valueOf(averagedwaves));
        int t = averagedwaves.size();
        Log.d("WAVES totalaveraged", String.valueOf(t));
        for (int i=0; i<t; i++){
            // obtengo el score de todas las olas tomadas
            Double averagedwavescore = averagedwaves.get(i);
            // seteo el score de todas las olas tomadas en heatscores del rider
            averageRider.getSortwavestaken().set(i, averagedwavescore);
            Log.d("OLAS sortwavestaken","#"+i+" "+averageRider.getNombre()+" Lista: "+String.valueOf(averageRider.getSortwavestaken()));
            if (i==0) {
                totalscore = averagedwaves.get(i);
                //tiebreakescore =averagedwaves.get(i);
            } else if (i==1) {
                totalscore = averagedwaves.get(i-1)+averagedwaves.get(i);
            }

            /* else if (i>1){
            }*/
            /*if (averagedwavescore!=0.0) {
                iTie++;
                Log.d("iTie", "averagedwavescore = "+averagedwavescore);
                Log.d("iTie", "contador = "+iTie);
            }
            Log.d("PASADAS", "i = "+i);
            tiebreakescore += averagedwaves.get(i);
            Log.d("TIEBREAKSCORE var", averageRider.getNombre()+" "+tiebreakescore);
            Log.d("TIEBREAKSCORE get", averageRider.getNombre()+" "+averageRider.getHeatscores().get(12));
            averageRider.getHeatscores().set(12, tiebreakescore);*/
        }
        //averageRider.getHeatscores().set(10, totalscore);
        averageRider.getHeatscores().set(0, totalscore);
    }

    // 4to metodo llamado -> Procedimiento para reordenar los puestos
    public void heatChangePosition() {
        Comparator<Rider> reverse = Collections.reverseOrder();
        Collections.sort(ridersheat, reverse);
    }

    // 5to metodo llamado -> Procedimiento para re-setear los puestos
    public void heatSetPositions() {
        // Update position data values
        for (int i=0; i<ridersheat.size(); i++){
            Rider rNow = ridersheat.get(i);
            int oldposition = rNow.getPosition();
            String namerider = rNow.getNombre();
            // Log.d("Posicion anterior rider", namerider+": "+String.valueOf(oldposition));
            rNow.setPosition(i);
            // Log.d("Posicion nueva rider", namerider+": "+String.valueOf(i));
        }
        // Log.d("POSITION", "NEWS OK");
    }

    // 6to metodo score needed to win
    public void scoreNeededToWin(){
        Double winScore = null;
        Double winBy;
        Double loserScore;
        Double needtieScore;
        Double needwinScore;
        Double needwinScoreOneWave;
        Double needwinScoreCombo;
        Double loserbestWave1;
        Double loserbestWave2;
        // recorre todos los riders del Heat para actualizar su Status
        for (int i=0; i<ridersheat.size(); i++){
            Rider rider = ridersheat.get(i);
            // Guarda el Status de cada rider
            String status;
            // Ingresa el ganador
            if (i==0) {
                Log.d("IN","GANADOR");
                // heatscore[0]== totalscore
                winScore = rider.getHeatscores().get(0);
                Log.d("WINSCORE", String.valueOf(rider.getHeatscores().get(0)));
                /* REVISAR DESDE ACA */
                if (winScore!=0.0) {
                    // heatscore[1] == needtowin
                    rider.getHeatscores().set(1, 0.0);
                    // Ganando al segundo por...
                    winBy = winScore - ridersheat.get(i+1).getHeatscores().get(0);
                    // Set status
                    if (winBy!=0.0){
                        rider.setHeatstatus(getString(R.string.status1)+" por "+winBy); // Ganando por
                    } else {
                        rider.setHeatstatus(getString(R.string.status1)); // Ganando
                    }
                    Log.d("STATUS", rider.getNombre() +" "+ rider.getHeatstatus());
                } else {
                    Log.d("IN","GANADOR SIN PUNTUAR");
                    // Aún no fue puntuado
                    rider.setHeatstatus(getString(R.string.status0)); // Aún no fue puntuado
                    Log.d("STATUS", rider.getNombre() +" "+ rider.getHeatstatus());
                }
            }
            /*if (i==1) { // Ingresa el segundo
            }*/
            // Ingresan los perdedores
            if (i>0) {
                Log.d("IN","PERDEDORES");
                // mejor ola 1
                loserbestWave1 = rider.getSortwavestaken().get(0);
                // mejor ola 2
                loserbestWave2 = rider.getSortwavestaken().get(1);

                if ( (loserbestWave1!=0.0)&&(loserbestWave2==0.0) ) { // "A" >> ingresa si tiene una sola Ola puntuada
                    loserScore = loserbestWave1;
                    Log.d("IN","UNA SOLA OLA PUNTUADA");
                    Log.d("WINSCORE",String.valueOf(winScore));
                    Log.d("OLAS","OLA1 "+loserbestWave1+" ~ OLA2 "+loserbestWave2);
                    Log.d("PROMEDIO",String.valueOf(loserScore));
                    // puntaje necesario para EMPATAR al rider anterior
                    needtieScore = (winScore-loserbestWave1);
                    Log.d("loserbestWave1",String.valueOf(loserbestWave1));
                    // puntaje necesario para GANAR al rider anterior
                    needwinScore = needtieScore+0.5;
                    // puntaje necesario para GANAR en COMBO al rider anterior
                    needwinScoreCombo = winScore;
                    // EMPATE
                    if (needtieScore==0.0) {
                        Log.d("EMPATE una ola", "needtieScore==0.0");
                    }
                    /* GANAR */
                    if (needwinScore>10.0){
                        // Puntaje necesario en combo
                        rider.getHeatscores().set(1, needwinScoreCombo);
                        // Necesitando para ganar una combinación de
                        status = getString(R.string.status4);
                    } else {
                        if (needwinScore==0.5){
                            needwinScore=1.0;
                        }
                        // Set value status for first wave
                        rider.getHeatscores().set(1, needwinScore);
                        status = getString(R.string.status3); // Necesitando para ganar otra ola de
                    }
                    // Set status
                    rider.setHeatstatus(status+" "+rider.getHeatscores().get(1));
                    // Log status
                    Log.d("STATUS", rider.getNombre() +" "+ rider.getHeatstatus());
                } else if (loserbestWave2!=0.0) { // // "B" >> ingresa si tiene dos o mas Olas puntuadas
                    Log.d("IN","DOS O MAS OLAS PUNTUADAS");
                    Log.d("OLAS","OLA1 "+loserbestWave1+" ~ OLA2 "+loserbestWave2);
                    // puntaje necesario para EMPATAR al rider anterior
                    needtieScore = (winScore-(loserbestWave1+loserbestWave2));
                    // puntaje necesario para GANAR al rider anterior
                    needwinScore = needtieScore+0.5;
                    // puntaje necesario para GANAR al rider anterior (necsitando solo una ola)
                    needwinScoreOneWave = (winScore-loserbestWave1)+0.5;
                    // puntaje necesario para GANAR en COMBO
                    needwinScoreCombo = winScore;
                    Log.d("loserbestWave1", String.valueOf(loserbestWave1));
                    Log.d("loserbestWave2", String.valueOf(loserbestWave2));
                    Log.d("needtieScore", String.valueOf(needtieScore));
                    if (needtieScore==0.0) {
                        Log.d("EMPATE dos olas", "needtieScore==0.0");
                        Log.d("GANADOR empate", ridersheat.get(0).getNombre()+" OLAS "+ridersheat.get(0).getHeatscores());
                        Log.d("LOSER empate", ridersheat.get(i).getNombre()+" OLAS "+ridersheat.get(i).getHeatscores());
                        Rider winerTie = ridersheat.get(0);
                        Rider loserTie = ridersheat.get(i);
                        int postie = postieBreaker(winerTie.getSortwavestaken(), loserTie.getSortwavestaken());
                        // Desempate posible a partir de la 3era ola wave[1]>1 ( wave[2] == 3ra ola )
                        if (postie>1) {
                            Log.d("EJECUTADO", "changePosTieBreaker()");
                            changePosTieBreaker(winerTie, loserTie, postie);
                            Log.d("NUM OLA DESEMPATE", "#"+(postie+1));
                        }
                    }
                    if (needwinScore>10.0){
                        // Puntaje necesario en combo
                        rider.getHeatscores().set(1, needwinScoreCombo);
                        status = getString(R.string.status4); // Necesitando para ganar una combinación de
                    } else {
                        if (needwinScoreOneWave==0.5){
                            needwinScoreOneWave=1.0;
                        }
                        // Set value for second wave
                        rider.getHeatscores().set(1, needwinScoreOneWave);
                        status = getString(R.string.status3); // Necesitando para ganar otra ola de
                    }
                    // Set status // heatscore[1] == needtowin
                    rider.setHeatstatus(status+" "+rider.getHeatscores().get(1));
                    // Log status
                    Log.d("STATUS", rider.getNombre() +" "+ rider.getHeatstatus());
                } else { // ingresa si no tiene olas puntuadas aun
                    Log.d("IN","NO TIENE OLAS PUNTUADAS");
                    Log.d("IN","OLA1 "+loserbestWave1+" ~ OLA2 "+loserbestWave2);
                    needtieScore = winScore; // usar para el empate
                    needwinScore = needtieScore+0.5;
                    if (needwinScore>10.0){
                        // Set value for combo
                        rider.getHeatscores().set(1, needwinScore);
                        status = getString(R.string.status4); // Necesitando para ganar una combinación de
                    } else {
                        // Set value for first wave
                        rider.getHeatscores().set(1, needwinScore);
                        status = getString(R.string.status2); // Necesitando para ganar una ola de
                    }
                    // Set status
                    rider.setHeatstatus(status+" "+rider.getHeatscores().get(1));
                    // Log status
                    Log.d("STATUS", rider.getNombre() +" "+ rider.getHeatstatus());
                }

            }
        }
    }

    public int postieBreaker(ArrayList<Double> waves1, ArrayList<Double> waves2) {
        int postie = -1;
        boolean tie = true;
        for (int i=0; (i<10) && tie; i++) {
            Double ola1 = waves1.get(i);
            Double ola2 = waves2.get(i);
            int retval = Double.compare(ola1,ola2);

            Log.d("WAVE R1 empate","#"+i+" "+ola1);
            Log.d("WAVE R2 empate","#"+i+" "+ola2);
            Log.d("R1 waves","#"+i+" "+waves1);
            Log.d("R2 waves","#"+i+" "+waves2);
            postie++;

            if(retval > 0) {
                Log.d("COMPARE >","WAVE R1 es mayor que WAVE R2");
            } else if(retval < 0) {
                Log.d("COMPARE <","WAVE R1 es menor que WAVE R2");
            } else {
                Log.d("COMPARE ==","WAVE R1 es igual que WAVE R2");
            }

            if (retval!=0){
                tie = false;
                Log.d("tie","= "+tie);
                Log.d("ola1 != ola2","SON DISTINTOS");
            } else {
                Log.d("tie","= "+tie);
                Log.d("ola1 == ola2","SON IGUALES");
            }

        }
        // devuelve la posicion donde ocurre el desempate, para que luego se comparen los scores y gane la ola de mayor puntaje y asi cambiar la posicion
        return postie;
    }

    public void changePosTieBreaker(Rider winerTie, Rider loserTie, int postie) {
        // obtener los puntajes donde se produce el desempate
        Double waveWiner = winerTie.getSortwavestaken().get(postie); // error ya corregido aqui
        Double waveLoser = loserTie.getSortwavestaken().get(postie);
        Log.d("PUNTAJES DESEMPATE","waveWiner = "+waveWiner+" - "+"waveLoser = "+waveLoser);
        // obtener las posiciones de los riders previo al desempate
        int posWiner = winerTie.getPosition();
        int posLoser = loserTie.getPosition();
        int posAuxiliary = -1;
        Log.d("NAMES PRE-DESEMPATE","nameWiner = "+winerTie.getNombre()+" - "+"nameLoser = "+loserTie.getNombre());
        Log.d("POSICION PRE-DESEMPATE","posWiner = "+posWiner+" - "+"posLoser = "+posLoser+" - "+"posAuxiliary = "+posAuxiliary);
        Log.d("IDs PRE-DESEMPATE","idWiner = "+winerTie.getId()+" - "+"idLoser = "+loserTie.getId());
        // comparar los puntajes
        int retval = Double.compare(waveWiner,waveLoser);
        if(retval > 0) {
            Log.d("REPOSICIONAR?","NO --> waveWiner es mayor > que waveLoser");
        } else if(retval < 0) {
            Log.d("REPOSICIONAR?","SI --> waveWiner es menor < que waveLoser");
            // Guardo la posicion del Loser
            posAuxiliary = posLoser;
            Log.d("POSICION auxiliar","guarda a posLoser= "+posAuxiliary);
            // Loser pasa a ser ganador entonces le seteo la posicion del Winer
            loserTie.setPosition(posWiner);
            // Winer pasa a ser perdedor entonces le seteo la posicion del Perdedor guardada (posAuxiliary)
            winerTie.setPosition(posAuxiliary);
            // Refresco el adaptador
            heatAdapter.notifyDataSetChanged();
            Log.d("DESEMPATADO","OK !!! \"se cambiaron las posiciones\"");
            Log.d("NAMES DESEMPATADOS","nameWiner = "+winerTie.getNombre()+" - "+"nameLoser = "+loserTie.getNombre());
            Log.d("POSICION DESEMPATADOS","posWiner = "+winerTie.getPosition()+" - "+"posLoser = "+loserTie.getPosition());
            Log.d("IDs DESEMPATADOS","idWiner = "+winerTie.getId()+" - "+"idLoser = "+loserTie.getId());
        } else {
            Log.d("REPOSICIONAR?","NO --> waveWiner es igual == que waveLoser");
        }
    }

    // 7mo metodo score needed to advance
    public void scoreNeededToAdvance(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_heat, container, false);
        recycler = vista.findViewById(R.id.recycler_cards_heat);
        recycler.setLayoutManager(new LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL, false));
        //recycler.setLayoutManager(new GridLayoutManager(contexto, 2));
        heatAdapter = new HeatAdapter(ridersheat);
        recycler.setAdapter(heatAdapter);
        heatAdapter.notifyDataSetChanged(); // Refresco el adaptador

        /* BOTON CARD RIDER */
        heatAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // heatAdapter.notifyDataSetChanged();

                // Actualizo Globales a puntear
                IDrider = ridersheat.get(recycler.getChildAdapterPosition(v)).getId();
                INDEXrider = ridersheat.get(recycler.getChildAdapterPosition(v)).getPosition();
                //int id = ridersheat.get(recycler.getChildAdapterPosition(v)).getId();
                //int p = ridersheat.get(recycler.getChildAdapterPosition(v)).getPosition();
                String n = ridersheat.get(recycler.getChildAdapterPosition(v)).getNombre();
                String l = ridersheat.get(recycler.getChildAdapterPosition(v)).getHometown();
                String s=" - ";
                Log.d("1er INDEX RIDER",n+s+String.valueOf(INDEXrider));
                String mensaje="RIDER: ";
                mensaje += "iD: "+IDrider+s+INDEXrider+s;
                mensaje += "Nombre: "+n+s;
                mensaje += "Localidad: "+l;

                // new MaterialDialog.Builder(contexto).content(mensaje).positiveText(R.string.agree).show();
                // Metodo scorepicker y le paso el id del rider a puntear;
                scorepicker();
                // scorepicker(id, p);
            }
        });
        return vista;
    }

    // Al restaurarse se puntea --> scoring();
   /* @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (getArguments() != null) {
            // recibo a quien puntear y su puntaje
            *//*idrider = getArguments().getInt("idrider");
            indexrider = getArguments().getInt("index");*//*
            // recibo el score
            scorerider = getArguments().getDouble("scorerider");
            // metodo puntearlo
            scoring(scorerider);
            // scoring(idrider, indexrider, scorerider);
        }
    }*/

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (getArguments() != null) {
            /*idrider = getArguments().getInt("idrider");
            indexrider = getArguments().getInt("index");*/
            // recibo el score
            scorerider = getArguments().getDouble("scorerider");
            // metodo puntearlo
            scoring(scorerider);
            // scoring(idrider, indexrider, scorerider);
        }
    }

    /*@Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        scorepickercomunicator = null;
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
}