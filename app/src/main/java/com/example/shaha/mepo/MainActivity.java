package com.example.shaha.mepo;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.shaha.mepo.Adapters.EventsPagerAdapter;
import com.example.shaha.mepo.Utils.FirebaseUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class MainActivity extends AppCompatActivity {
    //local variables for our use
    private static FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;
    private static FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the pageViewer with the pageAdapter and the tabLayout
        EventsPagerAdapter mPagerAdapter = new EventsPagerAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
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
                    default:
                        return true;
                }
            }
        });
        popupMenu.show();
    }
    public void setupFirebaseAuthListener(){

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    //we are logged in
                }else{
                    List<AuthUI.IdpConfig> providers = FirebaseUtils.getProviders();
                    //the user signed off
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false)
                            .setAvailableProviders(providers).build(),RC_SIGN_IN);
                }
            }
        };

    }
}
