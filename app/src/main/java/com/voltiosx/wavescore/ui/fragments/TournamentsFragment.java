package com.voltiosx.wavescore.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.voltiosx.wavescore.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.voltiosx.wavescore.CreateTournamentActivity;
import com.voltiosx.wavescore.io.FirestoreRecyclerClickInterface;
import com.voltiosx.wavescore.io.IOnBackPressed;
import com.voltiosx.wavescore.models.Tournament;
import com.voltiosx.wavescore.ui.adapters.TournamentAdapter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

/* TUTORIAL transicion de elementos https://android.jlelse.eu/easy-android-shared-element-transition-ac36952a4a4 */
public class TournamentsFragment extends Fragment implements IOnBackPressed, FirestoreRecyclerClickInterface {
    private DecimalFormat currency = new DecimalFormat("$###,###.##");
    private static final int LIMIT = 15; // Limite de torneos mostrados
    private Context mcontext = getContext();
    private RecyclerView recycler;
    private WebView webView;
    private CollectionReference tournamentsRef;
    private TournamentAdapter adapter;
    // private String status = getResources().getString(R.string.status_tournament);

    public TournamentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        /* Firestore Querys */
        String collectionPath = "tournaments";
        // Firestore
        FirebaseFirestore dbTournamentsFS;
        // Instancio la db
        dbTournamentsFS = FirebaseFirestore.getInstance();
        // Referencio la coleccion "tournaments"
        tournamentsRef = dbTournamentsFS.collection(collectionPath);

        Query query = FirebaseFirestore.getInstance()
                .collection(collectionPath)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(LIMIT);

        // Configure Firestore recycler adapter options:
        //  * query is the Query object defined above.
        //  * Tournament.class instructs the adapter to convert each DocumentSnapshot to a Tournament object
        FirestoreRecyclerOptions<Tournament> options = new FirestoreRecyclerOptions.Builder<Tournament>()
                .setQuery(query, Tournament.class)
                .build();
        adapter = new TournamentAdapter(options, this);
    }

    @Override
    public View onCreateView(LayoutInflater layout, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = layout.inflate(R.layout.fragment_tournaments, container, false);

        // RecyclerView
        recycler = v.findViewById(R.id.recycler_tournaments);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(mcontext));
        recycler.setAdapter(adapter);

        // Arrastre para eliminar torneo
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recycler);

        // WebView
        webView = v.findViewById(R.id.webview_form);
        webView.setWebViewClient(new WebViewClient());

        // BOTON CREATE TOURNAMENT
        FloatingActionButton createTournament = v.findViewById(R.id.create_tournament);
        createTournament.setOnClickListener((View vCreate)->createTournamentActivity());
        return v;
    }

    private void createTournamentActivity() {
        Intent intentCreateTournament = new Intent(getContext(), CreateTournamentActivity.class);
        startActivity(intentCreateTournament);
    }

    private void openTournamentForm(String formUrl){
        // JavaScript enabled in Chrome
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Log.d("URL formView", formUrl);
        //webView = new WebView(mcontext);
        //setContentView(R.id.recycler_tournaments);
        recycler.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(formUrl);
    }

    @Override // WebView Control
    public boolean onBackPressed() {
        // ACA EL CODIGO ENTRA!
        Log.d("ATRAS", "onBackPressed funciona");
        //Toast.makeText(mcontext, "GO BACK!", Toast.LENGTH_SHORT).show();
        if (webView.canGoBack()) { // Si el navegador ingresa a otras URLs
            //Toast.makeText(mcontext, "GO BACK!", Toast.LENGTH_LONG).show();
            webView.goBack();
            return true;
        } else {
            // Toast.makeText(mcontext, "NO GO BACK!", Toast.LENGTH_LONG).show();
            webView.setVisibility(View.INVISIBLE);
            recycler.setVisibility(View.VISIBLE);
            return false;
        }
    }

    // onItemClick interface Firestore
    @Override
    public void onItemClick(int position) {
        String formUrl = adapter.getSnapshots().getSnapshot(position).getString("formUrl");
        openTournamentForm(formUrl);
        // Toast.makeText(getContext(), formUrl, Toast.LENGTH_SHORT).show();
    }
    // onLongItemClick interface Firestore
    @Override
    public void onLongItemClick(int position) {
        String p = String.valueOf(adapter.getSnapshots().getSnapshot(position));
        Toast.makeText(getContext(), "onLongItemClick: "+p, Toast.LENGTH_SHORT).show();
    }
}

// Inicializo torneos ficticios
/*
        tournaments.add(new Tournament(points,rating,  "MS Pipeline Invitational", "Oahu Hawaii", "Pipeline", "aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa",pricemoney, "", "", "En vivo",new ArrayList<>()));
        tournaments.add(new Tournament(points,rating,  "Itacoatiara Pro", "Niteroi, Brasil", "Itacoatiara", "aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa",pricemoney, "", "", "Por iniciar",new ArrayList<>()));
        tournaments.add(new Tournament(points,rating,  "Bellavista Bodyboard Pro", "Iquique, Chile", "Punta 2", "aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa",pricemoney, "", "", "Proximamente",new ArrayList<>()));
        tournaments.add(new Tournament(points,rating,  "Antofagasta Bodyboard Festival", "Antofagasta, Chile", "La Cupula", "aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa",pricemoney, "", "", "Finalizado",new ArrayList<>()));
        tournaments.add(new Tournament(points,rating,  "Arica Cultura Bodyboard", "Arica, Chile", "El Gringo", "aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa",pricemoney, "", "", "En vivo",new ArrayList<>()));
        tournaments.add(new Tournament(points,rating,  "Kiama Bodyboard King Pro", "NSW, Australia", "Kiama", "aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa",pricemoney, "", "", "En vivo",new ArrayList<>()));
        tournaments.add(new Tournament(points,rating,  "Sintra Portugal Pro", "Sintra, Portugal", "Praia Grande", "aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa",pricemoney, "", "", "En vivo",new ArrayList<>()));
        tournaments.add(new Tournament(points,rating,  "Fronton King Pro", "Galdar, Gran Canaria", "Fronton", "aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa aaaaaaa",pricemoney, "", "", "En vivo",new ArrayList<>()));*
*/