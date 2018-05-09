package com.example.asus.FYP;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.asus.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private TextToSpeech tts;
    public TextView tv_header;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Drawable d = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_mic_white);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setOverflowIcon(d);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"your icon was clicked",Toast.LENGTH_SHORT).show();
            }
        });


        SharedPreferences setting = getSharedPreferences("atm", MODE_PRIVATE);

        GlobalVir gv = (GlobalVir) getApplicationContext();
        gv.userID = setting.getInt("userID",0);

        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        View hView =  nvDrawer.getHeaderView(0);
        tv_header = (TextView)hView.findViewById(R.id.nav_userName);
        tv_header.setText("Welcome "+ setting.getString("name",""));
        setupDrawerContent(nvDrawer);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,0,0){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Fragment fragment = null;
        try {
            fragment = (Fragment) Main.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case R.id.action_settings:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) //Check user have permission or not
                        == PackageManager.PERMISSION_GRANTED) {
                    enableAssistantMode();
                    Log.d("check1", "onResult:listening ");
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.RECORD_AUDIO)) {

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                            REQUEST_RECORD_AUDIO_PERMISSION);
                    Log.d("check", "onResult: ");
                }
                initTTS();
                return true;
            case R.id.action_onekey:
                Intent intent = new Intent(this, OneKey.class);
                startActivity(intent);

                return true;
        }
        Thread.interrupted();
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;      //store a class which want to change
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = Main.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = Shop.class;
                break;
            case R.id.nav_third_fragment:
                SharedPreferences setting = getSharedPreferences("atm", MODE_PRIVATE);
                if(setting.getBoolean("AUTO_ISCHECK", true))
                {
                    setting.edit().putBoolean("AUTO_ISCHECK",false).commit();
                    fragmentClass = Main.class;
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.nav_forth_fragment:
                fragmentClass = Setting.class;
                break;
            case R.id.nav_fifth_fragment:
                Intent intent = new Intent(this, OneKeyRegistration.class);
                startActivity(intent);
            default:
                fragmentClass = Main.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
    private void initTTS() {

        if (tts == null) {
            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int arg0) {

                    if (arg0 == TextToSpeech.SUCCESS) {

                        Locale l = new Locale("yue", "HK");
                        int result = tts.setLanguage(Locale.TRADITIONAL_CHINESE);
                        String s = null;
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            s += "	TRADITIONAL_CHINESE	 not supported<br>";
                            noTTS();
                        } else {
                            s += "	TRADITIONAL_CHINESE	 supported<br>";
                        }

                        Log.d("checking", s);
                        if (tts.isLanguageAvailable(l) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                            tts.setLanguage(l);
                        }
                    }
                }
            }
            );
        }
    }
    public void noTTS(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("注意");
        dialog.setMessage("你現在的文字轉語音引擎不支援中文,這可能會影響軟件部份功能");
        dialog.setNegativeButton("繼續",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

            }

        });
        dialog.setPositiveButton("立即獲取", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

                Toast.makeText(getApplicationContext(), "立即獲取",Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.tts");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        });

        dialog.show();
    }
    public void enableAssistantMode(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("注意");
        dialog.setMessage("開始助手模式後,你不能再按任何按鈕,你可以向助手說\"關閉\"以離開模式");
        dialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "我還尚未了解",Toast.LENGTH_SHORT).show();
            }

        });
        dialog.setPositiveButton("YES",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "我了解了",Toast.LENGTH_SHORT).show();

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                Intent intent = new Intent(MainActivity.this,AssistantActivity.class);
                startActivity(intent);
                // TODO: Implement your own authentication logic here.

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {

                                progressDialog.dismiss();
                            }
                        }, 3000);

            }

        });

        dialog.show();
    }


}
