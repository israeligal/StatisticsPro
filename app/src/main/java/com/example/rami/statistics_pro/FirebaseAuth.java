package com.example.rami.statistics_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.rami.statistics_pro.Utils.FirebaseUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class FirebaseAuth extends AppCompatActivity {
    private static com.google.firebase.auth.FirebaseAuth mFirebaseAuth;
    private static com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_auth);

        setupFirebaseConnection();
    }

    private void setupFirebaseConnection() {
        FirebaseUtils.connectToFirebaseDatabase();
        mFirebaseAuth = FirebaseUtils.getmFirebaseAuth();
        setupFirebaseAuthListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    /**
     * Connect to the Firebase services and set up the Auth listener
     */
    public void setupFirebaseAuthListener() {
        mAuthStateListener = new com.google.firebase.auth.FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull com.google.firebase.auth.FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //we are logged in
                    Intent intent = new Intent(FirebaseAuth.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    List<AuthUI.IdpConfig> providers = FirebaseUtils.getProviders();
                    //the user signed off
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false)
                            .setAvailableProviders(providers).build(), RC_SIGN_IN);
                }
            }
        };
    }
}
