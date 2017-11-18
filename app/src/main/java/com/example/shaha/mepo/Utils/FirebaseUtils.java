package com.example.shaha.mepo.Utils;


import android.support.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class FirebaseUtils {
    private static FirebaseDatabase mFirebaseDatabase;
    private static FirebaseAuth mFirebaseAuth;
    private static List<AuthUI.IdpConfig> providers;

    /**
     * Connect to the database in firebase and load the Auth providers
     */
    public static void connectToFirebaseDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        setProviders();
    }

    public static void goOffLine() {
        mFirebaseDatabase.goOffline();
    }


    public static DatabaseReference getFirebaseReference(String reference) {
        return mFirebaseDatabase.getReference(reference);
    }


    private static void setProviders() {
        providers = new ArrayList<>();
        providers.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
        providers.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
        providers.add(new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());
    }



    public static List<AuthUI.IdpConfig> getProviders() {
        return providers;
    }

    public static FirebaseAuth getmFirebaseAuth() {
        return mFirebaseAuth;
    }
}
