package com.voltiosx.wavescore;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.voltiosx.wavescore.io.IOnBackPressed;
import com.voltiosx.wavescore.ui.fragments.TournamentsFragment;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.voltiosx.wavescore.io.AsyncResult;
import com.voltiosx.wavescore.io.DownloadGsonSpreadsheetGoogle;
import com.voltiosx.wavescore.io.ScorePickerComunicator;
import com.voltiosx.wavescore.models.Rider;
import com.voltiosx.wavescore.ui.fragments.FixtureFragment;
import com.voltiosx.wavescore.ui.fragments.HeatFragment;
import com.voltiosx.wavescore.ui.fragments.ResultsFragment;
import com.voltiosx.wavescore.ui.fragments.ViewPagerInscriptosFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/* Wavescore-v1 API scripts google forms MZOdUP63plq0oLa4nQCxWgZz32uPXe2yX */
// 2 >> implemento la interfaz definida en la clase del MainActivity OnFragmentInteractionListener (si no funciona implementarla en el Fragment A)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ScorePickerComunicator{
    
    Context context = this;
    private static final String TAG = "MainActivity";
    ProgressBar progress;

    /* VARIABLES GLOBALES */
    private boolean isConnected, isWiFi, isData;
    private int idMenu;
    private String menuselect;

    String categoryName;
    String fileInscriptos = "https://spreadsheets.google.com/tq?key=1OmpSsnC9Rn_wxdSs0_JpK86V1d8SfGWIvAqaskn7BXA";
    //String fileInscriptos = "https://spreadsheets.google.com/tq?key=1EruePXicwIDMokjPB35shwUAkuosnCHbyvd9oY_YgX8";
    String totalInscriptos;
    ListView listview;
    Button btnDownload; //Reemplazarlo por el del menu Navigator

    /* MANAGER FRAGMENT */
    FragmentManager fragmentmanager = getSupportFragmentManager();

    /* FRAGMENTS */
    Fragment tournamentsFragment = new TournamentsFragment();
    Fragment inscriptosViewPager = new ViewPagerInscriptosFragment();
    Fragment heatFragment = new HeatFragment();
    Fragment resultsFragment = new ResultsFragment();
    Fragment fixtureFragment = new FixtureFragment();

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

    /* INTENT CREATE TOURNAMENT */
    Intent intentDriveQuickStart;
    private Intent intentLogin;
    private void startLogoutActivity(){
        intentLogin = new Intent(this, com.voltiosx.wavescore.ui.login.LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }

    /*** Checking for all possible internet providers ***/
    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network","NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        //Toast.makeText(mContext,mContext.getString(R.string.please_connect_to_internet),Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuselect = getString(R.string.torneos); // menu por defecto
        Bundle b = getIntent().getExtras();
        if (b != null) { idMenu = b.getInt("idMenu"); menuselect = b.getString("menuselect"); fragmentmanager.beginTransaction().replace(R.id.maincontainer, tournamentsFragment).addToBackStack(null).commit(); }

        /* Determinar si hay conexion a internet */
        /*ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        boolean isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;*/

        if (isConnectingToInternet()){
            Log.d("CONECTADO ", "Conectado ejecutar WaveScore");
            new DownloadGsonSpreadsheetGoogle(new AsyncResult() {
                @Override
                public void onResult(JSONObject object) {
                    procesarJson(object);
                    Log.d("procesarJson", "JSON procesado.");
                    fragmentmanager.beginTransaction().replace(R.id.maincontainer, tournamentsFragment).addToBackStack(null).commit();
                }
            }).execute(fileInscriptos);

            Toolbar toolbar = findViewById(R.id.toolbar);
            // setSupportActionBar(toolbar);

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

    /*@Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }*/

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

    /* MENU DE NAVEGACION */
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        idMenu = item.getItemId();
        menuselect = null;
        if (idMenu == R.id.nav_torneos){
            menuselect = getString(R.string.torneos);
            fragmentmanager.beginTransaction().replace(R.id.maincontainer, tournamentsFragment).addToBackStack(null).commit();
        } else if (idMenu == R.id.nav_inscriptos) {
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
            /*Bundle bundleOpen = new Bundle();
            bundleOpen.putParcelableArrayList("openpros", openpros);
            openproFragment.setArguments(bundleOpen);*/
            menuselect = getString(R.string.inscriptos);
            fragmentmanager.beginTransaction().replace(R.id.maincontainer, inscriptosViewPager).addToBackStack(null).commit();
        } else if (idMenu == R.id.nav_fixtures) {
            menuselect = getString(R.string.fixtures);
            sendtofixture(openpros);
        } else if (idMenu == R.id.nav_puntuaciones) {
            menuselect = getString(R.string.puntuaciones);
            fragmentmanager.beginTransaction().replace(R.id.maincontainer, heatFragment).addToBackStack(null).commit();
        } else if (idMenu == R.id.nav_resultados) {
            menuselect = getString(R.string.resultados);
            fragmentmanager.beginTransaction().replace(R.id.maincontainer, resultsFragment).addToBackStack(null).commit();
        } else if (idMenu == R.id.nav_rankings) {
            menuselect = getString(R.string.rankings);
            /*intentDriveQuickStart = new Intent(this, DriveQuickstart.class);
            startActivity(intentDriveQuickStart);*/
        } else if (idMenu == R.id.nav_logout) {
            menuselect = getString(R.string.log_out);
            //FirebaseAuth.getInstance().signOut();
            Toast.makeText(context, R.string.login_out, Toast.LENGTH_LONG).show();
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startLogoutActivity();
                            } else {
                                Log.e(TAG, "onComplete: ", task.getException());
                            }
                        }
                    });
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        // Cambio de nombre
        getSupportActionBar().setTitle(menuselect);
        return true;
    }

    /* METODO PROCESAR JSON: Ponerlo en una clase  */
    private void procesarJson(JSONObject object) {
        try {
            Log.d("TRY", "ACCEDIO");
            JSONArray rows = object.getJSONArray("rows");
            //Almaceno todos los incriptos en un JSONARRAy para luego categorizarlos
            /* Recorro las filas r*/
            for (int r = 0; r<rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                /* Datos a usar del Spreedsheet */
                // int id = columns.getJSONObject(0).getInt("v");
                // int posicion = columns.getJSONObject(0).getInt("v");
                String nombre = columns.getJSONObject(4).getString("v");
                String localidad = columns.getJSONObject(8).getString("v");
                if (localidad==""||localidad==null){
                    localidad = getString(R.string.no_localidad);
                }
                String categoria = columns.getJSONObject(12).getString("v");
                heatstatus = getString(R.string.status0);
                Rider inscripto = new Rider(r, r, nombre, localidad, categoria, colors, wavestaken, sortwavestaken, heatscores, heatstatus);
                inscriptos.add(inscripto);
            }

            // Listado de inscriptos
            /*RidersAdapter adaptadorInscriptos = new RidersAdapter(this, R.layout.view_list_item_rider, inscriptos);
            listview = findViewById(R.id.inscriptos_chekin);
            Log.d("INSCRIPTOS INICIO", String.valueOf(inscriptos));
            listview.setAdapter(adaptadorInscriptos);
            adaptadorInscriptos.notifyDataSetChanged();*/

            // Categorizo en sublistas
            categorizar(rows);
            //sendtofixture(openpros); //

            /*Gson gson = new Gson();
            String fixtureJson = gson.toJson(openpros);
            categoryName = openpros.get(0).getCategoria();
            Intent fixIntent = new Intent(MainActivity.this, FixtureActivity.class);
            fixIntent.putExtra("categoria",categoryName);
            fixIntent.putExtra("fixGson", fixtureJson);
            startActivity(fixIntent);*/

            // Abro countdawnFragment
            /*Fragment countdawnFragment = new CountDawnFragment();
            fragmentmanager.beginTransaction().replace(R.id.maincontainer, countdawnFragment).addToBackStack(null).commit();*/

            // abre heatFragment
            //fragmentmanager.beginTransaction().replace(R.id.maincontainer, heatFragment).addToBackStack(null).commit();

        } catch (JSONException e) {
            Log.d("CATCH", "ACCEDIO");
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
                String categoria = columnas.getJSONObject(12).getString("v");
                // tv.setText(categoria);
                // Log.d("Tag","Categorias que pasaron por aca: "+ categoria);
                // JSONArray masters, openpros, dkpros, damas, amateurs, menores16, menores14, menores12;
                int id = f; //columnas.getJSONObject(0).getInt("v");
                int p = f; //columnas.getJSONObject(0).getInt("v");
                String n = columnas.getJSONObject(4).getString("v");
                String l = columnas.getJSONObject(8).getString("v");
                String c = columnas.getJSONObject(12).getString("v");
                ArrayList<Double> w = wavestaken;
                ArrayList<Double> sw = sortwavestaken;
                ArrayList<Integer> colores = colors;
                ArrayList<Double> hs = heatscores;

                // Inicia Status
                String s = heatstatus;
                // Inicia Colores
                /*int textLight = ContextCompat.getColor(context, R.color.colorTextLight);
                int textDark = ContextCompat.getColor(context, R.color.colorTextDark);*/

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
                    case "AMATEUR":
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

    /* metodo BUNDLE envia los resultados a ResultFragment */
    private void sendtofixture(ArrayList<Rider> categorytofixture){
        Bundle fixturebundle = new Bundle();
        fixturebundle.putParcelableArrayList("fixturelist", categorytofixture);
        fixtureFragment.setArguments(fixturebundle);
        fragmentmanager.beginTransaction().replace(R.id.maincontainer, fixtureFragment).addToBackStack(null).commit();
    }

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

    /*@Override
    public void onBackPressed() {
        fragmentmanager.beginTransaction().replace(R.id.maincontainer, tournamentsFragment).addToBackStack(null).commit();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
            Toast.makeText(context, "GO BACK!", Toast.LENGTH_SHORT).show();
        } else {
            getSupportFragmentManager().popBackStack();
            Toast.makeText(context, "GO BACK else!", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.maincontainer);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) { // coordinator_tournament
            super.onBackPressed();
            fragmentmanager.beginTransaction().replace(R.id.maincontainer, tournamentsFragment).addToBackStack(null).commit();
        }
    }

}