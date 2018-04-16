package com.voltiosx.nolti.wavescore;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import com.voltiosx.nolti.wavescore.io.AsyncResult;
import com.voltiosx.nolti.wavescore.io.DownloadGsonSpreadsheetGoogle;
import com.voltiosx.nolti.wavescore.io.ScorePickerComunicator;
import com.voltiosx.nolti.wavescore.models.ColorWaveScore;
import com.voltiosx.nolti.wavescore.models.Rider;
import com.voltiosx.nolti.wavescore.models.Wave;
import com.voltiosx.nolti.wavescore.ui.adapters.RidersAdapter;
import com.voltiosx.nolti.wavescore.ui.fragments.AmateursFragment;
import com.voltiosx.nolti.wavescore.ui.fragments.ContainerFragment;
import com.voltiosx.nolti.wavescore.ui.fragments.DKProFragment;
import com.voltiosx.nolti.wavescore.ui.fragments.DamasFragment;
import com.voltiosx.nolti.wavescore.ui.fragments.HeatFragment;
import com.voltiosx.nolti.wavescore.ui.fragments.InscriptosFragment;
import com.voltiosx.nolti.wavescore.ui.fragments.M12Fragment;
import com.voltiosx.nolti.wavescore.ui.fragments.M14Fragment;
import com.voltiosx.nolti.wavescore.ui.fragments.M16Fragment;
import com.voltiosx.nolti.wavescore.ui.fragments.M18Fragment;
import com.voltiosx.nolti.wavescore.ui.fragments.MastersFragment;
import com.voltiosx.nolti.wavescore.ui.fragments.ScorePickerFragment;
import com.voltiosx.nolti.wavescore.ui.fragments.ViewPagerInscriptosFragment;
import com.voltiosx.nolti.wavescore.ui.fragments.OpenProFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

// 2 >> implemento la interfaz definida en la clase del MainActivity OnFragmentInteractionListener (si no funciona implementarla en el Fragment A)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ViewPagerInscriptosFragment.OnFragmentInteractionListener, ContainerFragment.OnFragmentInteractionListener, InscriptosFragment.OnFragmentInteractionListener,
        OpenProFragment.OnFragmentInteractionListener, DKProFragment.OnFragmentInteractionListener, DamasFragment.OnFragmentInteractionListener,
        M18Fragment.OnFragmentInteractionListener, M16Fragment.OnFragmentInteractionListener, M14Fragment.OnFragmentInteractionListener, M12Fragment.OnFragmentInteractionListener,
        AmateursFragment.OnFragmentInteractionListener, MastersFragment.OnFragmentInteractionListener,
        ScorePickerFragment.OnFragmentInteractionListener, ScorePickerComunicator {

    /* VARIABLES GLOBALES */
    String categoryName;
    String fileInscriptos = "https://spreadsheets.google.com/tq?key=1OmpSsnC9Rn_wxdSs0_JpK86V1d8SfGWIvAqaskn7BXA";
    String totalInscriptos;
    ListView listview;
    Button btnDownload; //Reemplazarlo por el del menu Navigator
    /* FRAGMENTS */
    Fragment inscriptosViewPager = new ViewPagerInscriptosFragment();
    Fragment heatFragment = new HeatFragment();
    Fragment scorePickerFragment = new ScorePickerFragment();
    android.support.v4.app.FragmentManager fragmentmanager = getSupportFragmentManager();
    /* VARIABLES GLOBALES SCORING */
    int idrider;
    int p;
    Double score;

    public ArrayList<Rider> inscriptos = new ArrayList<>();

    // Creo las listas para las categorias
    ArrayList<Rider> masters = new ArrayList<>();
    ArrayList<Rider> openpros = new ArrayList<>();
    ArrayList<Rider> dkpros = new ArrayList<>();
    ArrayList<Rider> damas = new ArrayList<>();
    ArrayList<Rider> amateurs = new ArrayList<>();
    ArrayList<Rider> menores18 = new ArrayList<>();
    ArrayList<Rider> menores16 = new ArrayList<>();
    ArrayList<Rider> menores14 = new ArrayList<>();
    ArrayList<Rider> menores12 = new ArrayList<>();
    ArrayList<Rider> sincategorias = new ArrayList<>();

    /* ARRAYLISTS INICIALIZADORES */
    private ArrayList<Integer> colors = new ArrayList<>();
    private ArrayList<Double> wavestaken = new ArrayList<>();
    private ArrayList<Double> sortwavestaken = new ArrayList<>();
    private ArrayList<Double> heatscores = new ArrayList<>();
    private String heatstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Determinar si hay conexion a internet */
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        boolean isData = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        Log.d("INTERNET ", String.valueOf(isConnected));
        Log.d("WIFI ", String.valueOf(isWiFi));
        Log.d("DATOS MOBILES ", String.valueOf(isData));

        if (isConnected){
            Log.d("SE ACCEDIO: ", "Ejecutar acciones conectado.");

            new DownloadGsonSpreadsheetGoogle(new AsyncResult() {
                @Override
                public void onResult(JSONObject object) {
                    procesarJson(object);
                }
            }).execute(fileInscriptos);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });*/

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        } else {
            Log.d("NO SE ACCEDIO: ", "Ejecutar acciones desconectado.");
        }

    } // END onCreate

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_inscriptos) {
            Log.d("ENVIO PARCELABLE: ", String.valueOf(inscriptos));
            /* TRANSPORTO LOS DATOS DE LOS INSCRIPTOS */
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("inscriptos", inscriptos);
            bundle.putParcelableArrayList("openpros", openpros);
            bundle.putParcelableArrayList("dkpros", dkpros);
            bundle.putParcelableArrayList("damas", damas);
            bundle.putParcelableArrayList("amateurs", amateurs);
            bundle.putParcelableArrayList("menores18", menores18);
            bundle.putParcelableArrayList("menores16", menores16);
            bundle.putParcelableArrayList("menores14", menores14);
            bundle.putParcelableArrayList("menores12", menores12);
            bundle.putParcelableArrayList("masters", masters);
            bundle.putParcelableArrayList("sincategorias", sincategorias);
            inscriptosViewPager.setArguments(bundle);
            fragmentmanager.beginTransaction().replace(R.id.maincontainer, inscriptosViewPager).addToBackStack(null).commit();
        } else if (id == R.id.nav_fixtures) {
            fragmentmanager.beginTransaction().replace(R.id.maincontainer, heatFragment).addToBackStack(null).commit();
        } else if (id == R.id.nav_puntuaciones) {
            fragmentmanager.beginTransaction().replace(R.id.maincontainer, scorePickerFragment).addToBackStack(null).commit();
        } else if (id == R.id.nav_resultados) {
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* METODO PROCESAR JSON: Ponerlo en una clase  */
    private void procesarJson(JSONObject object) {
        try {
            JSONArray rows = object.getJSONArray("rows");
            //Almaceno todos los incriptos en un JSONARRAy para luego categorizarlos
            /* Recorro las filas r*/
            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");
                /* Datos a usar del Spreedsheet */
                int id = columns.getJSONObject(0).getInt("v");
                int posicion = columns.getJSONObject(0).getInt("v");
                String nombre = columns.getJSONObject(5).getString("v");
                String localidad = columns.getJSONObject(9).getString("v");
                String categoria = columns.getJSONObject(13).getString("v");
                heatstatus = getString(R.string.status0);
                Rider inscripto = new Rider(id, posicion, nombre, localidad, categoria, colors, wavestaken, sortwavestaken, heatscores, heatstatus); //corregir
                inscriptos.add(inscripto);
            }
            // Creo el adaptador y referencio su maqueta
            RidersAdapter adaptadorInscriptos = new RidersAdapter(this, R.layout.view_list_item_rider, inscriptos);
            listview = findViewById(R.id.inscriptos_chekin);
            listview.setAdapter(adaptadorInscriptos); // Coloco el adaptador en el listview
            adaptadorInscriptos.notifyDataSetChanged(); // Refresco el adaptador
            //Categorizo en sublistas
            categorizar(rows);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    } // END procesarJson

    /* METODO CATEGORIZAR Ponerlo en una clase */
    /* Este metodo sirve para sublisttar a los incriptos en la categoria a la que corresponden */
    public void categorizar(JSONArray insc){
        // Total de inscriptos
        int t=insc.length();
        String totalInscriptos = "Total de inscriptos: "+String.valueOf(t);
        //tvNumInsc.setText(totalInscriptos);
        try {
            // f es el contador de filas
            for (int f = 0; f < t ; ++f){
                JSONObject fila = insc.getJSONObject(f);
                JSONArray columnas = fila.getJSONArray("c");
                // Variable case para seleccionar en cada caso ;)
                String categoria = columnas.getJSONObject(13).getString("v");
                // tv.setText(categoria);
                // Log.d("Tag","Categorias que pasaron por aca: "+ categoria);
                // JSONArray masters, openpros, dkpros, damas, amateurs, menores16, menores14, menores12;
                int id = columnas.getJSONObject(0).getInt("v");
                int p = columnas.getJSONObject(0).getInt("v");
                String n = columnas.getJSONObject(5).getString("v");
                String l = columnas.getJSONObject(9).getString("v");
                String c = columnas.getJSONObject(13).getString("v");
                ArrayList<Double> w = wavestaken;
                ArrayList<Double> sw = sortwavestaken;
                ArrayList<Integer> colores = colors;
                ArrayList<Double> hs = heatscores;
                String s = heatstatus;
                switch(categoria) {
                    case "OPEN PRO":
                        Rider openpro = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        openpros.add(openpro);
                        break;
                    case "MASTERS":
                        Rider master = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        masters.add(master);
                        break;
                    case "OPEN PRO, MASTERS":
                        Rider openMaster = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        masters.add(openMaster);
                        break;
                    case "DK PRO":
                        Rider dkpro = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        dkpros.add(dkpro);
                        break;
                    case "DAMAS":
                        Rider dama = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        damas.add(dama);
                        break;
                    case "AMATEURS":
                        Rider amateur = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        amateurs.add(amateur);
                        break;
                    case "MENORES DE 18":
                        Rider menor18 = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        menores18.add(menor18);
                        break;
                    case "MENORES DE 16":
                        Rider menor16 = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        menores16.add(menor16);
                        break;
                    case "MENORES DE 14":
                        Rider menor14 = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        menores14.add(menor14);
                        break;
                    case "MENORES DE 12":
                        Rider menor12 = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        menores12.add(menor12);
                        break;
                    default:
                        Rider sincategoria = new Rider(id, p, n, l, c, colores, w, sw, hs, s);
                        sincategorias.add(sincategoria);
                } // end switch
            } // end for
        } catch (JSONException e){
            // Filtro todas las exepciones de los errores, podria mejorar esto :(
            e.printStackTrace();
        }
    } // END ctegorizar

    // siempre sobreescribir el metodo de la interfaz en la MainActivity
    // este no sirve (solo sirve onScoreSelected)
    /*@Override
    public void onRiderSelected(int idrider, int p){
        // Guardo el id globalmente
        this.idrider = idrider;
        this.p = p;
        ScorePickerFragment scorePickerFragment = new ScorePickerFragment();
        *//* ACA PREPARAR EL BUNDLE DE SCORE PICKER *//*
        Bundle bundleScorepicker = new Bundle();
        // Envio el id al Scorepicker tambien
        bundleScorepicker.putInt("idrider", idrider);
        bundleScorepicker.putInt("index", p);
        scorePickerFragment.setArguments(bundleScorepicker);
        *//* fin del empaquetado *//*
        fragmentmanager.beginTransaction().replace(R.id.maincontainer, scorePickerFragment).addToBackStack(null).commit();
        Log.d("RIDERSELECTED CALLBACK ", String.valueOf(idrider));
    }*/

    // siempre sobreescribir el metodo de la interfaz en la MainActivity
    @Override
    //public void onScoreSelected(double score, int index){
    public void onScoreSelected(double score){
        Log.d("SCORE Main Activity "+idrider, String.valueOf(score));
        // Log.d("INDEX RIDER main ", String.valueOf(index));
        /* BUNDLE DEL HEAT */
        Bundle bundleHeat = new Bundle();
        // Envio el idrider y score a actualizar
        // bundleHeat.putInt("idrider", idrider);
        // bundleHeat.putInt("index", index);
        bundleHeat.putDouble("scorerider", score);
        heatFragment.setArguments(bundleHeat);
        fragmentmanager.beginTransaction().replace(R.id.maincontainer, heatFragment).addToBackStack(null).commit();
        //getFragmentManager().popBackStack();
    }

    /*if (getArguments() != null) {
            idrider = getArguments().getInt("idrider");
            scorerider = getArguments().getInt("scorerider");
        }*/

    // metodo que se ejecuta a partir de la interfaz igualada en el fragmento comunicador A
    @Override
    public void onFragmentInteraction(int idrider) {

    }

}