package com.voltiosx.wavescore.ui.login;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.remote.FacebookSignInHandler;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.voltiosx.wavescore.MainActivity;
import com.voltiosx.wavescore.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.BreakIterator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private Context mcontext = this;

    static final int GOOGLE_SIGN = 123;
    static final String GTAG = "GoogleSignTag";
    static final String FTAG = "FIREBASE";

    ImageView isologo;
    CircleImageView prof_pic;
    SignInButton googleButton;
    LoginButton facebookLoginButton;
    Button signOutButton;
    ProgressBar progress;
    private TextView textStatus, nameStatus, emailStatus;

    // EMAIL Auth
    private String authEmail;
    private String authPassword;
    private LoginViewModel loginViewModel;

    // Firebase Auth
    FirebaseAuth mAuth;
    // DatabaseReference para almacenar los usuarios logueados
    DatabaseReference wsDatabase;

    /* GOOGLE LOGIN */
    GoogleSignInClient mGoogleSignInClient;

    /* FACEBOOK LOGIN */
    CallbackManager fbCallManager;

    // Intent para acceder a la App WaveScore
    private Intent intentWS;

    @Override // lifecycle 1
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isologo = findViewById(R.id.isologo);
        prof_pic = findViewById(R.id.prof_pic);
        textStatus = findViewById(R.id.status);

        // Variables de los Campos OK
        final EditText useremailEditText = findViewById(R.id.useremail);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        progress = findViewById(R.id.progress);

        // Opciones de Google SignIn button Options
        // El ID de cliente de tipo aplicación web es el ID de cliente de OAuth 2.0 de tu servidor backend
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) //.Builder()
                .requestIdToken(getString(R.string.default_web_client_id)) // Pasa el ID de cliente de tu servidor al método requestIdToken
                .requestEmail()
                .build();
        // Crea un GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory()).get(LoginViewModel.class);

        // Inicializa Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        wsDatabase = FirebaseDatabase.getInstance().getReference();

        // Validacion de los Campos
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                /*if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }*/
                if (loginFormState.getUseremailError() != null){
                    useremailEditText.setError(getString(loginFormState.getUseremailError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                progress.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                // Si el login es exitoso
                if (loginResult.getSuccess() != null) {
                    // Obtengo los datos de registro una vez que ya fueron validados
                    //authName = usernameEditText.getText().toString();
                    authEmail = useremailEditText.getText().toString();
                    authPassword = passwordEditText.getText().toString();
                    registerUserFirebase(authEmail, authPassword);
                    updateUiWithUser(loginResult.getSuccess());
                    textStatus.setText(R.string.login_succes);
                }
                setResult(Activity.RESULT_OK);
                //Complete and destroy login activity once successful
                finish();
            }
        });
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(
                        //usernameEditText.getText().toString(),
                        useremailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        useremailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(
                            useremailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        // Boton de Login WaveScore
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                // Llamo al metodo login
                loginViewModel.login(
                        //usernameEditText.getText().toString(),
                        useremailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        // Seteo de Google button
        googleButton = findViewById(R.id.google_button);
        googleButton.setSize(SignInButton.SIZE_WIDE);
        googleButton.setColorScheme(SignInButton.COLOR_AUTO);
        // Google login button
        googleButton.setOnClickListener(v -> SignInGoogle());

        // Seteo de SignOut Google button
        signOutButton = findViewById(R.id.signout);
        //signOutButton.setVisibility(View.INVISIBLE);
        signOutButton.setOnClickListener(v -> gLogOut());

        // Firebase: ¿emparejamiento del usuario?
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }

        // status
        nameStatus = findViewById(R.id.name_status);
        emailStatus = findViewById(R.id.email_status);

        /* FACEBOOK LOGIN BUTTON */
        fbCallManager = CallbackManager.Factory.create();
        facebookLoginButton = findViewById(R.id.facebook_login_btn);
        facebookLoginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        checkLoginStatus();

        facebookLoginButton.registerCallback(fbCallManager, new FacebookCallback<com.facebook.login.LoginResult>() {
            @Override
            public void onSuccess(com.facebook.login.LoginResult loginResult) {
                /*AccessToken acct = loginResult.getAccessToken();
                String fbUserId = acct.getUserId();
                textStatus.setText("FB User Id: "+fbUserId);
                Log.d("FACEBOOK", "FB User Id: "+fbUserId );
                String photo = "https://graph.facebook.com/"+fbUserId+"/picture?return_ssl_resources=1";
                Log.d("FACEBOOK", "FB foto url: "+photo);*/
                //String photo = String.valueOf(user.getPhotoUrl());
                //Glide.with(this).load(photo).into(prof_pic);
                //Glide.with(this).load(photo).into(prof_pic);
                //Picasso.get().load(photo).into(prof_pic);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        

    } // End onCreate

    @Override // // lifecycle 2: Verifica que el usuario haya accedido al login de Firebase
    protected void onStart() {
        super.onStart();
        // Comprueba si el usuario actual, ha iniciado sesión en Firebase(y no es nulo), actualiza su UI en consecuencia
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // 1ro actualiza al usuario en la sesion y luego accede a la App de WaveScore
        updateUI(currentUser);
    }

    public void initWaveScoreApp(){
        intentWS = new Intent(this, com.voltiosx.wavescore.MainActivity.class);
        startActivity(intentWS);
        finish();
    }

    // Token de Facebook
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken==null) {
                prof_pic.setImageResource(0);

                // Visibilidad
                //progress.setVisibility(View.INVISIBLE);
                prof_pic.setVisibility(View.INVISIBLE);
                isologo.setVisibility(View.VISIBLE);

                nameStatus.setText(R.string.name_status);
                emailStatus.setText(R.string.email_status);
                textStatus.setText(R.string.login_status);
                Toast.makeText(LoginActivity.this, R.string.login_out, Toast.LENGTH_LONG).show();
            } else {
                //progress.setVisibility(View.VISIBLE);
                loadProfileFacebook(currentAccessToken);
            }
        }
    };

    // Metodo que manejara el resultado del inicio de sesión de Facebook
    private void loadProfileFacebook(AccessToken newAccesToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccesToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String id = object.getString("id");
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String fullname = first_name+" "+last_name;
                    String email = object.getString("email");
                    String pic_url = "https://graph.facebook.com/"+id+"/picture?type=normal";
                    prof_pic.setImageResource(0);

                    // Actualizo status
                    nameStatus.setText(fullname);
                    emailStatus.setText(email);
                    textStatus.setText(String.format("FB User Id: %s", id));

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    // Visibilidad
                    //progress.setVisibility(View.INVISIBLE);
                    isologo.setVisibility(View.INVISIBLE);
                    prof_pic.setVisibility(View.VISIBLE);

                    Glide.with(LoginActivity.this).load(pic_url).into(prof_pic);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email, id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void checkLoginStatus(){
        if (AccessToken.getCurrentAccessToken()!=null){
            loadProfileFacebook(AccessToken.getCurrentAccessToken());
        }
    }

    // se activa este metodo al ser iniciado por startActivityForResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbCallManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN) { // Resultado devuelto al iniciar la Intent desde GoogleSignInApi.getSignInIntent();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount gaccount = task.getResult(ApiException.class);
                if (gaccount != null) firebaseAuthWithGoogle(gaccount);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                progress.setVisibility(View.INVISIBLE);
                Log.w(GTAG, getString(R.string.login_failed_google), e);
            }
        } else {
            // Inicio de sesion cancelado
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (response==null){
                Log.d(TAG, "onActivityResult: El usuario cancelo el inicio de sesion");
            } else {
                Log.d(TAG, "onActivityResult", response.getError());
            }
        }
    }

    void SignInGoogle(){
        progress.setVisibility(View.VISIBLE);
        Intent googleSignIntent = mGoogleSignInClient.getSignInIntent();
        // Inicia la actividad con tareas y retorna el resultado del login de google en onActivityResult
        startActivityForResult(googleSignIntent, GOOGLE_SIGN);
    }

    // experimental
    /*void SignInFacebook(){
        // progress.setVisibility(View.VISIBLE);
        Intent googleSignIntent = mGoogleSignInClient.getSignInIntent();
        // Inicia la actividad con tareas y retorna el resultado del login de google en onActivityResult
        startActivityForResult(googleSignIntent, GOOGLE_SIGN);
    }*/

    void gLogOut() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this,
                        task -> updateUI(null));
        nameStatus.setText(R.string.name_status);
        emailStatus.setText(R.string.email_status);
        textStatus.setText(R.string.login_status);
        prof_pic.setVisibility(View.INVISIBLE);
        isologo.setVisibility(View.VISIBLE);
    }

    // Metodo que manejara el resultado del inicio de sesión de Google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(GTAG, "ID firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        // Inicia sesion en Firebase con la credencial de Google
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progress.setVisibility(View.INVISIBLE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(GTAG, "Inicio de sesion con credenciales: Exitoso!");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            TextView textStatus = findViewById(R.id.status);
                            //String txts = R.string.login_succes_google+": "+acct.getDisplayName();
                            nameStatus.setText(acct.getDisplayName());
                            emailStatus.setText(acct.getEmail());
                            textStatus.setText(R.string.login_succes_firebase);
                        } else {
                            progress.setVisibility(View.INVISIBLE);
                            // If sign in fails, display a message to the user.
                            Log.w(GTAG, "Inicio de sesion con credenciales: Fallo!", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    // Metodo para actualizar el perfil Firebase del usuario logueado desde Google
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Inicio la App
            initWaveScoreApp();
            this.finish();
            /*String name = user.getDisplayName();
            String email = user.getEmail();
            nameStatus.setText(name);
            emailStatus.setText(email);
            String photo = String.valueOf(user.getPhotoUrl());
            Glide.with(this).load(photo).into(prof_pic);
            isologo.setVisibility(View.INVISIBLE);
            prof_pic.setVisibility(View.VISIBLE);
            googleButton.setVisibility(View.INVISIBLE);
            signOutButton.setVisibility(View.VISIBLE);*/
        } /*else {
            prof_pic.setVisibility(View.INVISIBLE);
            isologo.setVisibility(View.VISIBLE);
            googleButton.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);
        }*/
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.login_succes) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void registerUserFirebase(String email, String pass){
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task1) {
                // Comprobacion de la tarea 1
                if (task1.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", email);
                    map.put("password", pass);
                    wsDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            // Comprobacion de la tarea 1
                            if (task2.isSuccessful()) {
                                Toast.makeText(mcontext, "Usuario autencticado en Firebase", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(mcontext, "No se pudo autenticar el Usuario en Firebase", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(mcontext, "No se pudo registrar el usuario en Firebase", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_LONG).show();
    }
}