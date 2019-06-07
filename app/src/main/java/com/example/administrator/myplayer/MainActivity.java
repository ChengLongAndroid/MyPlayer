package com.example.administrator.myplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.app.LoaderManager.LoaderCallbacks;

import com.example.administrator.myplayer.fragment.EditionFragment;
import com.example.administrator.myplayer.fragment.MusicFragment;
import com.example.administrator.myplayer.fragment.NetMusicFragment;
import com.example.administrator.myplayer.fragment.NetworkVideoFragment;
import com.example.administrator.myplayer.fragment.VideoFragment;
import android.content.CursorLoader;

import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,LoaderCallbacks<Cursor>  {

    private static final int REQUEST_READ_CONTACTS = 0;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private VideoFragment frag1;
    private MusicFragment frag2;
    private NetworkVideoFragment frag3;
    private NetMusicFragment frag4;
    private EditionFragment frag5;


    List<String> mPermissionList = new ArrayList<>();

    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateAutoComplete();

        frag1=new VideoFragment();
        frag2=new MusicFragment();
        frag3=new NetworkVideoFragment();
        frag4=new NetMusicFragment();
        frag5=new EditionFragment();
        changeTransaction(frag1,"Add");




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void changeTransaction(Fragment fragment,String msg){
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        switch (msg){
            case "Add" :transaction.add(R.id.layout,fragment);
                break;
            case "Replace" :transaction.replace(R.id.layout,fragment);
                break;
            default:  break;

        }

        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            changeTransaction(frag1,"Replace");
        } else if (id == R.id.nav_gallery) {
            //add(),remove(),replace()
            changeTransaction(frag2,"Replace");

        } else if (id == R.id.nav_slideshow) {
            changeTransaction(frag3,"Replace");
        } else if (id == R.id.nav_manage) {
            changeTransaction(frag4,"Replace");
        } else if (id == R.id.nav_share) {
            changeTransaction(frag5,"Replace");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
        getLoaderManager().initLoader(0, null, this);

    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED&&checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            return true;
        }
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }

        for (int i = 0; i < permissions.length; i++){
//            if (shouldShowRequestPermissionRationale(permissions[i])) {
//                Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                        .setAction(android.R.string.ok, new View.OnClickListener() {
//                            @Override
//                            @TargetApi(Build.VERSION_CODES.M)
//                            public void onClick(View v) {
//                                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
//
//                                requestPermissions(new String[]{READ_EXTERNAL_STORAGE,READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                            }
//                        });
//            } else {
                requestPermissions(new String[]{READ_EXTERNAL_STORAGE,READ_CONTACTS}, REQUEST_READ_CONTACTS);
//            }
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
