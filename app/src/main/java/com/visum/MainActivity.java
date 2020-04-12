package com.visum;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.visum.dto.OutCommunityShortDto;
import com.visum.listener.CommunityUpdateListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CommunityFragment.OnListFragmentInteractionListener
{

    private static final int COMMUNITY_CARD_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame,  InboxFragment.newInstance());
        ft.commit();

        // Add Burger
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Register Listener in NavigationView
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        Intent intent = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_public_community:
                fragment = CommunityFragment.newInstance();
                break;
            case R.id.nav_my_groups:
                //fragment = SentItemsFragment.newInstance();
                break;
            case R.id.nav_inbox:
                fragment = InboxFragment.newInstance();
                break;
            case R.id.nav_help:
                intent = new Intent(this, HelpActivity.class);
                break;
            case R.id.nav_feedback:
                intent = new Intent(this, FeedbackActivity.class);
                break;
            default:
                return false;
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else {
            startActivity(intent);
        }

        //Close NavigationView
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // If Navigation Panel is opened, back button should close Navigation Panel
    @Override
    public void onBackPressed()  {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onListFragmentInteraction(OutCommunityShortDto item) {
        Intent intent = new Intent(this, CommunityCardActivity.class);
        intent.putExtra(CommunityCardActivity.EXTRA_COMMUNITY_ID, item.getId());
        intent.putExtra(CommunityCardActivity.EXTRA_EDIT_MODE, false);

        //startActivity(intent);
        startActivityForResult(intent, COMMUNITY_CARD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COMMUNITY_CARD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                String returnString = data.getStringExtra(Intent.EXTRA_TEXT);

            }
        }
    }
}
