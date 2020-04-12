package com.voltiosx.wavescore.ui.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.voltiosx.wavescore.MainActivity;
import com.voltiosx.wavescore.R;
import com.voltiosx.wavescore.models.Category;
import com.voltiosx.wavescore.models.Tournament;
import com.voltiosx.wavescore.ui.adapters.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FormTournament extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static String RESP = "RESPONSE";
    private Context xcontext;
    private OnFragmentInteractionListener mListener;
    private ProgressBar spinner;

    // DATA FORM TOURNAMENT
    private String txtJSON;
    private JSONObject json;
    private String form;
    private String ss;
    private Date timestamp = new Date();
    private String owner;
    private int points = 1000;
    private int rating = 5;
    private Double pricemoney = 100.000;
    //private String status = this.getResources().getString(R.string.status_tournament);
    private String status = "";
    private TextView nameTournament, cityTournament, locationTournament, descriptionTournament, categoryTournament, dateTournament, timeTournament;
    private List<Category> categorys;
    private RecyclerView recyclerCategorys;
    private CategoryAdapter adapter;
    private int heightLineRecycler;
    //private ArrayList<Category> fullCategorys;

    // USER DATA
    FirebaseUser user;
    private String uid, email;

    // DATA STRING FORM TOURNAMENT
    private Date dateIni;
    private String nameTournamentInput, cityTournamentInput, locationTournamentInput, descriptionTournamentInput, dateTournamentInput, timeTournamentInput;
    private String PERSONAL_DATA, TOURNAMENT_DATA;

    // TOURNAMENT OBJECT
    Tournament tournament;

    // FIREBASE
    private DatabaseReference dbTournamentsRef;
    // FIRESTORE
    private static final String FS = "FIRESTORE";
    FirebaseFirestore dbTournamentsFS;

    public FormTournament() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /*mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater layout, ViewGroup container, Bundle savedInstanceState) {
        xcontext = getContext();
        View v = layout.inflate(R.layout.fragment_form_tournament, container, false);

        // Progress
        spinner = v.findViewById(R.id.progressBar);
        spinner.setIndeterminate(true);
        spinner.setVisibility(ProgressBar.GONE);

        PERSONAL_DATA = getResources().getString(R.string.personal_data);
        TOURNAMENT_DATA = getResources().getString(R.string.tournament_data);

        // DATA FORM TOURNAMENT findViewById
        Button btnCategory, btnTournament;
        ImageView imgDate, imgTime;
        imgDate = v.findViewById(R.id.imageDate);
        imgTime = v.findViewById(R.id.imageTime);
        btnCategory = v.findViewById(R.id.btn_category);
        btnTournament = v.findViewById(R.id.btn_tournament);
        nameTournament = v.findViewById(R.id.name_tournament);
        cityTournament = v.findViewById(R.id.city_tournament);
        locationTournament = v.findViewById(R.id.location_tournament);
        descriptionTournament = v.findViewById(R.id.description_tournament);
        recyclerCategorys= v.findViewById(R.id.recycler_categorys_tournament);
        categoryTournament = v.findViewById(R.id.category_tournament);
        dateTournament = v.findViewById(R.id.date_tournament);
        timeTournament = v.findViewById(R.id.time_tournament);

        // Scrolleable de categorias
        categorys = new ArrayList<>();
        // Adaptador y administrador del Recycler
        adapter = new CategoryAdapter((ArrayList<Category>) categorys);
        adapter.notifyDataSetChanged(); // Refresca las categorias agregadas
        LinearLayoutManager layoutManager = new LinearLayoutManager(xcontext);
        // Seteos del Recycler
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerCategorys.setLayoutManager(layoutManager);
        recyclerCategorys.setAdapter(adapter);
        // recyclerCategorys.setItemAnimator(new DefaultItemAnimator());

        // BOTON DATE PICKER DIALOG con Lambda
        imgDate.setOnClickListener((View vDate)->showDatePickerDialog());
        // BOTON TIME PICKER DIALOG con Lambda
        imgTime.setOnClickListener((View vTime)->showTimePickerDialog());
        // BOTON CREATE CATEGORY
        btnCategory.setOnClickListener((View vCreate)->addCategory());
        // BOTON CREATE TOURNAMENT
        btnTournament.setOnClickListener((View vCreate)->createTournament());

        return v;
    }

    public class GenerateUUID {
        public final void main(String... args){
            //generate random UUIDs
            UUID idOne = UUID.randomUUID();
            log("UUID One: " + idOne);
        }
        private void log(Object object){
            System.out.println( String.valueOf(object) );
        }
    }

    private void addCategory(){
        int position = categorys.size();
        String CATEGORY_MAX = getResources().getString(R.string.category_limit_max);
        String categoryTournamentInput = categoryTournament.getText().toString();
        ViewGroup.LayoutParams params=recyclerCategorys.getLayoutParams();
        Category currentCategory = new Category(position, categoryTournamentInput);

        if (position==0) { // si no se creo ninguna categoria todavia
            if (categoryTournamentInput.isEmpty() || categoryTournamentInput.trim().equals("")) {
                categoryTournament.setError("Ingrese almenos una categoria.");
            } else if (categoryTournamentInput.length()<3) {
                categoryTournament.setError("El campo es muy corto.");
            } else {
                //adapter.addItem(position, currentCategory);
                categorys.add(position, currentCategory);
                categoryTournament.setText("");
            }
        } else if (position==1) { // si hay una sola categoria creada
            // tomo la altura de un elemento para luego multiplicar la altura
            heightLineRecycler = recyclerCategorys.getLayoutParams().height;
            if ( (categoryTournamentInput.length()>=1) && (categoryTournamentInput.length()<3) ) {
                categoryTournament.setError("El campo es muy corto.");
            } else if (categoryTournamentInput.length()>=3){
                //adapter.addItem(position, currentCategory);
                categorys.add(position, currentCategory);
                categoryTournament.setText("");
            } else {
                categoryTournament.setError("El campo esta vacio.");
            }
        } else if (position<10) { // si hay mas de 1 categoria y menos de 10
            if ( (categoryTournamentInput.length()>=1) && (categoryTournamentInput.length()<3) ) {
                categoryTournament.setError("El campo es muy corto.");
            } else if (categoryTournamentInput.length()>=3){
                //adapter.addItem(position, currentCategory);
                categorys.add(position, currentCategory);
                categoryTournament.setText("");
                // actualizacion de altura
                params.height = heightLineRecycler * position;
                recyclerCategorys.setLayoutParams(params);
            } else {
                categoryTournament.setError("El campo esta vacio.");
            }
        } else {
            Toast.makeText(xcontext, CATEGORY_MAX, Toast.LENGTH_LONG).show();
            categoryTournament.setText("");
            categoryTournament.setError(null);
        }
    }

    // Retorna el perfil del usuario Firebase con el id de quien creo el torneo
    private void getAuthProfile(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            uid = user.getUid();
        }
    }

    private void createTournament(){
        boolean formOk=true;
        String mError;

        // Creador del Torneo
        getAuthProfile();
        owner = uid;

        // Strings
        nameTournamentInput = nameTournament.getText().toString();
        cityTournamentInput = cityTournament.getText().toString();
        locationTournamentInput = locationTournament.getText().toString();
        descriptionTournamentInput = descriptionTournament.getText().toString();
        dateTournamentInput = dateTournament.getText().toString();
        timeTournamentInput = timeTournament.getText().toString();

        // Validación owner
        if (owner.isEmpty() || owner.trim().equals("")) {
            formOk=false;
            Toast.makeText(getContext(), "Usuario no registrado", Toast.LENGTH_LONG).show();
        } else {
            Log.d("OWNER FIREBASE: ", owner);
        }

        // Validación nameTournament
        mError = validateTextField(nameTournamentInput);
        if (!mError.equals("")){
            nameTournament.setError(mError);
            formOk=false;
        }

        // Validación cityTournament
        mError = validateTextField(cityTournamentInput);
        if (!mError.equals("")){
            cityTournament.setError(mError);
            formOk=false;
        }

        // Validación locationTournament
        mError = validateTextField(locationTournamentInput);
        if (!mError.equals("")){
            locationTournament.setError(mError);
            formOk=false;
        }

        // Validación descriptionTournament
        mError = validateTextField(descriptionTournamentInput);
        if (!mError.equals("")){
            descriptionTournament.setError(mError);
            formOk=false;
        }

        // Validación categoryTournament
        if (categorys.size()<1){
            categoryTournament.setError("Ingrese almenos una categoria");
        }

        // Validación dateTournament
        String stringDateIni = getResources().getString(R.string.date);
        if (dateTournamentInput.equals(stringDateIni) || dateTournamentInput.isEmpty()) {
            dateTournament.setError(getResources().getString(R.string.click_to_icon)+" "+stringDateIni);
            Log.d("ERROR", "ERORR EN LA FECHA!");
            formOk=false;
        } else {
            dateTournament.setError(null);
        }

        // Validación timeTournament
        String timeIni = getResources().getString(R.string.time);
        if (timeTournamentInput.equals(timeIni) || timeTournamentInput.isEmpty()) {
            timeTournament.setError(getResources().getString(R.string.click_to_icon)+" "+timeIni);
            formOk=false;
        } else {
            timeTournament.setError(null);
        }

        // Si el formulario esta completamente validado...
        if (formOk){
            // Creo el Formulario en Google Drive
            createTournamentForm();
        }

    }

    private String validateTextField(String textfield){
        if (textfield.isEmpty() || textfield.trim().equals("")) {
            return "El campo esta vacio.";
        } else if (textfield.length()<3){
            return "El campo es muy corto.";
        } else {
            return "";
        }
    }

    private void showTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        final boolean is24Hours = DateFormat.is24HourFormat(getContext());
        final TimePickerFragment timePicker = TimePickerFragment.newInstance(hourOfDay, minute, is24Hours);

        timePicker.setListener(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeTournament.setError(null);
                String time = hourOfDay+":"+minute;
                timeTournament.setText(time);
                timeTournament.setTextColor(ContextCompat.getColor(xcontext, R.color.colorTextLight));
            }
        });
        timePicker.showNow(getChildFragmentManager(), null);
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void createTournamentForm() {
        //progressBar.setVisibility(View.GONE);
        //progressBar.setVisibility(ProgressBar.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        // final ProgressBar loading = ProgressBar.show(xcontext,"Enviando datos a Google Forms","Procesando "+nameTournamentInput+" espere un momento...", true);
        // ProgressBar progressBar = new ProgressBar(xcontext, null, android.R.attr.progressBarStyleLarge);
        //ConstraintLayout.LayoutParams params = new ConstraintLayout().LayoutParams(100, 100);
        //layout.addView(progressBar, params);

        final String urlWebApp = "https://script.google.com/macros/s/AKfycbytSQBamt9ZHmLNFqBo44FS2A0SFPnAsTaAfW17rYLxddRUhLLh/exec";
        // Instancia la RequestQueue (cola de solicitud)
        RequestQueue requestQueue = Volley.newRequestQueue(xcontext);

        // Realizo la solicitud POST con respuesta de texto
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlWebApp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseJSON) {
                        Log.d(RESP, responseJSON);
                        txtJSON = responseJSON;
                        try {
                            json = new JSONObject(txtJSON);
                            Log.d(RESP, json.toString());
                            JSONArray jsonArray = json.getJSONArray("datos");
                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                form = obj.getString("form");
                                ss = obj.getString("ss");
                            }
                        } catch (Throwable t) {
                            Log.e(RESP, "No pudo parsearse el JSON: \"" + json + "\"");
                        }
                        addTournamentFirestore();
                        goTournamentsFragment(R.id.nav_torneos, getString(R.string.torneos));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(RESP, error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                // Map<KEY, VALUE> params = new HashMap<>();
                Map<String, String> params = new HashMap<>();

                // Parametros POST
                params.put("action", "createTournamentForm");
                params.put("ownerEmail", email);
                params.put("nameTournament", nameTournamentInput);
                params.put("cityTournament", cityTournamentInput);
                params.put("locationTournament", locationTournamentInput);
                params.put("dateTournament", dateTournamentInput);
                params.put("timeTournament", timeTournamentInput);
                params.put("personalData", PERSONAL_DATA.toUpperCase());
                params.put("tournamentData", TOURNAMENT_DATA.toUpperCase());

                // Parametros POST dinamicos
                //int count = fullCategorys.size();
                int count = categorys.size();
                for(int i=0; i<count; i++){
                    params.put("catTournament"+i, categorys.get(i).getName());
                    Log.d("catTournament"+i, categorys.get(i).getName());
                }
                return params;
            }
        }; // end stringRequest

        // Seting solicitud volley
        int socketTimeOut = 50000; // 50 seconds
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        // envia la Solicitud volley
        requestQueue.add(stringRequest);
        spinner.setVisibility(ProgressBar.VISIBLE);

        //spinner = (spinner)findViewById(R.Id.progressbar);
        //progressBar.setVisibility(View.);
        //loading.show();
    }

    private void goTournamentsFragment(int idMenu, String menuselect) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        Bundle b = new Bundle();
        b.putInt("idMenu", idMenu);
        b.putString("menuselect", menuselect);
        intent.putExtras(b);
        startActivity(intent);
        spinner.setVisibility(View.GONE);
    }

    // Crea un torneo y lo escribe en la db de Firestore
    private void addTournamentFirestore(){
        // Instancio la db
        dbTournamentsFS = FirebaseFirestore.getInstance();
        // Creo un nuevo objeto de torneo
        tournament = new Tournament(timestamp, owner, points,rating,nameTournamentInput,cityTournamentInput,locationTournamentInput,descriptionTournamentInput,pricemoney,dateIni,timeTournamentInput,status,categorys,form, ss);

        // Agrego un nuevo documento con ID autogenerado
        dbTournamentsFS.collection("tournaments")
                .add(tournament)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(FS, "Torneo id: "+documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FS, "Error al insertar el torneo", e);
                    }
                });
        // DocumentReference newTournamentRef = dbTournamentsFS.collection("tournaments").document();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateTournament.setError(null);
        String date = dayOfMonth+"/"+month+"/"+year;
        dateTournament.setText(date);
        dateTournament.setTextColor(ContextCompat.getColor(xcontext, R.color.colorTextLight));
        dateIni = new Date(dayOfMonth, month, year);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}