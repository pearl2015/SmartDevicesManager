package com.pearl.smartdevicemanager.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import com.pearl.smartdevicemanager.R;
import com.pearl.smartdevicemanager.beans.IoTUser;
import com.pearl.smartdevicemanager.deviceCenter.DeviceFragment;
import com.pearl.smartdevicemanager.userCenter.UserFragment;

//必须实现
public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UserFragment.OnFragmentInteractionListener,DeviceFragment.OnFragmentInteractionListener,MainView {
//
//    @BindView(R.id.fab) FloatingActionButton fab;
//    @BindView(R.id.drawer_layout) DrawerLayout drawer;
//    @BindView(R.id.nav_view) NavigationView navigationView;
//    @BindView(R.id.username_tv) TextView usernname_tv;
      private String musername;
      private TextView m_username;
      private String memail;
      private IoTUser local_user;


    //返回键退出
    private long exitTime = 0;
    //
    private MainPresenter myPresenter;

    //fragment
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        m_username = (TextView)header.findViewById(R.id.header_username_tv);

        //从登陆界面获得用户名
        Intent intent = getIntent();
        local_user = new IoTUser();
        local_user = (IoTUser)intent.getSerializableExtra("user_info");
        //显示
        m_username.setText(local_user.getUsername());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();
            if(System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {  //监听导航栏
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        if (id == R.id.user_ic) {
            // Handle the camera action
            UserFragment userFragment = new UserFragment();
            transaction.replace(R.id.fragment_container, userFragment).commit();
        } else if (id == R.id.find_ic) {

        } else if (id == R.id.health_ic) {

        } else if (id == R.id.devices_ic) {
            DeviceFragment deviceFragment = new DeviceFragment();
            transaction.replace(R.id.fragment_container, deviceFragment).commit();
        } else if (id == R.id.share_ic) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
