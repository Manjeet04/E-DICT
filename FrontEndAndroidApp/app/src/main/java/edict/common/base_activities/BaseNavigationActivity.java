package edict.common.base_activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edict.R;
import edict.services.home.AboutActivity;
import edict.services.home.usersign_services.EdictLoginActivity;


public abstract class BaseNavigationActivity extends BaseActivity implements MenuItem.OnMenuItemClickListener {

    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    private Toolbar toolbar;

    private TextView aboutWeather;
    private TextView weatherTemp;
    private ImageView weatherImage;
    private FirebaseAuth mAuth;


    private TextView userName;
    private ImageView userImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_navigation);

        mAuth = FirebaseAuth.getInstance();

        initialiseToolbar();

        initialiseNavigationBar();

    }


    private void initialiseToolbar() {



        view_stub = (FrameLayout) findViewById(R.id.view_stub);
        navigation_view = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



    }


    protected void initialiseNavigationBar() {



        userName = (TextView) navigation_view.getHeaderView(0).findViewById(R.id.userNameInNavigationDrawerHeader);

        SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE);

        userName.setText("Welcome :)" + "\n" + pref.getString("email",null));




        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerMenu = navigation_view.getMenu();

        for(int i = 0; i < drawerMenu.size(); i++) {
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }

       // if(UserStorage.isUserLoggedIn()) {
      //      loggedInUserDrawerSetup();
     //   }
     //   else {
        //    loggedOutUserDrawerSetup();
     //   }

   }

    private void loggedInUserDrawerSetup() {


        //drawerMenu.findItem(R.id.user_account).setVisible(true);
        //drawerMenu.findItem(R.id.log_out).setVisible(true);


     //   userName.setText(UserStorage.getUserName());
      //  viewUtils.loadImage(userImage, UserStorage.getUserImageUrl());


        SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE);

        userName.setText("Welcome :)" + "\n" + pref.getString("email",null));

    }

    private void loggedOutUserDrawerSetup() {

        //drawerMenu.findItem(R.id.log_out).setVisible(false);
        //..drawerMenu.findItem(R.id.user_account).setVisible(false);



        SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE);

        userName.setText("Welcome :)" + "\n" + pref.getString("email",null));


    }


    public void onUiMessageEvent() {



                loggedOutUserDrawerSetup();



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setContentView(int layoutResID) {

        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }

        initialiseToolbar();

        initialiseNavigationBar();

    }

    public void setContentViewWithoutViewStub(int layoutResID) {

        super.setContentView(layoutResID);

        initialiseToolbar();

        initialiseNavigationBar();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        Intent intent;
        boolean showAnimation = true;

        switch (item.getItemId()) {





            case R.id.share_app:
                   try {
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT,
                        String.format(getString(R.string.promo_msg_template),
                                ("https://drive.google.com/open?id=1proYreHRF-7ekqv48cAhgbROStIaLN_q")));
                startActivity(intent2);
            } catch (Exception e) {
                Toast.makeText(this,"error occured, nahi deni kisi ko ( Personal )",Toast.LENGTH_SHORT).show();
            }


                break;

            case R.id.settings:
                Intent intent2 = new Intent(this, AboutActivity.class);

                startActivity(intent2);




                // handle it
                break;
            case R.id.log_out:

                SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("islogin",false);
                editor.apply();


                mAuth.signOut();

                Intent intent1 = new Intent(this, EdictLoginActivity.class);

                startActivity(intent1);



                break;

        }

        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT, showAnimation);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
        else {
            super.onBackPressed();
        }
    }

}