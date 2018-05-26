package com.example.rami.statistics_pro.Utils;


import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
         providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());
//                new AuthUI.IdpConfig.PhoneBuilder().build(),
//                new AuthUI.IdpConfig.GoogleBuilder().build(),
//                new AuthUI.IdpConfig.FacebookBuilder().build());

    }



    public static List<AuthUI.IdpConfig> getProviders() {
        return providers;
    }

    public static FirebaseAuth getmFirebaseAuth() {
        return mFirebaseAuth;
    }
}
