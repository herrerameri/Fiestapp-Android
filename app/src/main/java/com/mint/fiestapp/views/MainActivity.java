package com.mint.fiestapp.views;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mint.fiestapp.R;
import com.mint.fiestapp.presenters.misfiestas.IMisFiestasPresenter;
import com.mint.fiestapp.presenters.misfiestas.MisFiestasPresenter;

public class MainActivity extends BaseActivity  {
    private final int DURACION_SPLASH = 3000; // 3 segundos
    private LoginButton btnLoginFacebook;
    CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private boolean isInit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null)
        {
            callbackManager = CallbackManager.Factory.create();
            btnLoginFacebook = (LoginButton)findViewById(R.id.btnLoginFacebook);
            btnLoginFacebook.setReadPermissions("email", "public_profile");
            btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>(){

                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(), R.string.cancelo_login, Toast.LENGTH_LONG);
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_LONG);
                }
            });
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    ocultarLogin();
                    iniciarApp();
                } else {
                    // User is signed out
                }
            }
        };
    }

    private void handleFacebookAccessToken(AccessToken token) {
        ocultarLogin();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.error_login), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    private void ocultarLogin(){
        LinearLayout linLogin = (LinearLayout)findViewById(R.id.linLogin);
        linLogin.setVisibility(View.GONE);
    }

    private void iniciarApp(){
        if(!isInit){
            isInit = true;
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    if (firebaseAuthListener != null) {
                        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
                    }
                    IMisFiestasPresenter misFiestasPresenter = new MisFiestasPresenter();
                    misFiestasPresenter.iniciarActivity(MainActivity.this);
                    finish();
                };
            }, DURACION_SPLASH);
        }
    }
}
