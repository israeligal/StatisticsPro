package com.example.rami.statistics_pro.Tasks;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;


public class FirebaseGetRafflesFunctionTask {

    public static void getRafflesFromCloud() {
        FirebaseFunctions.getInstance()
                .getHttpsCallable("helloWorld")
                .call()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("\n\n\n\nI've failed myself \n\n\n\n" + e.getMessage());

                    }

                }).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                        @Override
                        public void onSuccess(HttpsCallableResult httpsCallableResult) {
                            System.out.println("\n\n\n\\n\n\nSuncesssssssssssssssss\n\n\n\n\n");
                            System.out.println(httpsCallableResult.getData().toString());
                        }
                });

    }
}
