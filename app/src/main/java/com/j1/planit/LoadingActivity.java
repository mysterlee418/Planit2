package com.j1.planit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoadingActivity extends AppCompatActivity {
    SignInButton googleLogin;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 10;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        googleLogin = findViewById(R.id.googlelogin);








        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();


                mGoogleApiClient = new GoogleApiClient.Builder(LoadingActivity.this).enableAutoManage(LoadingActivity.this, onConnectionFailedListener).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);



            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoadingActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {



//            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MyCalendarActivity.class);
            String email = user.getEmail();

            String serverUrl = "http://mysterlee418.dothome.co.kr/Planit/planitCreateACC.php";

            SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("test100", response);
                    //insertDB.php의 echo결과 보여주기
                    new android.support.v7.app.AlertDialog.Builder(LoadingActivity.this).setMessage(response).setPositiveButton("OK", null).create().show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoadingActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            multiPartRequest.addStringParam("email", email);

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            requestQueue.add(multiPartRequest);



            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        } else {


        }
    }



    public void clickCreateAcc(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("회원 가입");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_loading_createacc, null);

        builder.setView(view);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast t = Toast.makeText(LoadingActivity.this, "OK", Toast.LENGTH_LONG);
                t.show();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast t = Toast.makeText(LoadingActivity.this, "CANCEL", Toast.LENGTH_SHORT);
                t.show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();


    }













    GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
    };
}
