package com.example.rami.statistics_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.rami.statistics_pro.Adapters.EventsPagerAdapter;
import com.example.rami.statistics_pro.Tasks.FirebaseGetRafflesFunctionTask;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {
    //local variables for our use
    private static StatisticsProUser currentUser;
    private static FirebaseAuth mFirebaseAuth;
    private SharedPreferences mSharedPreferences;
    private String mUsername;
    private FirebaseUser mFirebaseUser;
    private String mPhotoUrl;
    private GoogleApiClient mGoogleApiClient;
    private String TAG = this.getClass().getName();
    private String ANONYMOUSE = "anonymous";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mUsername = ANONYMOUSE;

        initFirebaseAuth();

        FirebaseGetRafflesFunctionTask.getRafflesFromCloud();

        if (mFirebaseAuth.getCurrentUser() != null){
            currentUser = new StatisticsProUser(mFirebaseAuth.getCurrentUser());
        }

        //get current user from firebase
        //set the pageViewer with the pageAdapter and the tabLayout
        EventsPagerAdapter mPagerAdapter = new EventsPagerAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
            int a =1;
    }

    private void initFirebaseAuth(){
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.open_sign_out_menu:
                openPopupMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openPopupMenu() {
        View menuItem = findViewById(R.id.open_sign_out_menu);
        PopupMenu popupMenu = new PopupMenu(this,menuItem);
        popupMenu.getMenuInflater().inflate(R.menu.sign_out_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sign_out_item:
                        mFirebaseAuth.signOut();
                        finish(); //finish Main Activity return to FirebaseAuth
                    default:
                        return true;
                }
            }
        });
        popupMenu.show();
    }

    public static StatisticsProUser getCurrentUser() {
        return currentUser;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

    }
}
