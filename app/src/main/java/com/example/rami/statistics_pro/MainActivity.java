package com.example.rami.statistics_pro;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.rami.statistics_pro.Utils.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //local variables for our use
    private static StatisticsProUser currentUser;
    private static FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        //get current user from firebase
        mFirebaseAuth = FirebaseUtils.getmFirebaseAuth();
        currentUser = new StatisticsProUser(mFirebaseAuth.getCurrentUser());
        //set the pageViewer with the pageAdapter and the tabLayout
        EventsPagerAdapter mPagerAdapter = new EventsPagerAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }
//FirebaseFunctions.getInstance()
//        .getHttpsCallable("myCoolFunction")
//    .call(optionalObject)
//    .addOnFailureListener {
//        Log.wtf("FF", it)
//    }
//    .addOnSuccessListener {
//        toast(it.data.toString())
//    }
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

    private void openPopupMenu() { // TODO why do we need this func? we have options item selected
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
}
