package com.example.asus.FYP;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.myapplication.R;

public class EA_Registration extends AppCompatActivity {
    EditText et ;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ea__registration);

        GlobalVir gv = (GlobalVir)getApplicationContext();
        btn = findViewById(R.id.button2);
        String name = gv.EA_name;
        et = findViewById(R.id.et1);
        et.setText(name);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity

                GlobalVir gv = (GlobalVir)EA_Registration.this.getApplicationContext();
                String data = CheckEANameDB.executeQuery(et.getText().toString(),gv.userID);
                Log.d("asd", data);
                if (data.equals("")) {

                    gv.EA_name = et.getText().toString();
                    Toast.makeText(EA_Registration.this,"Saved", Toast.LENGTH_SHORT).show();
                    Intent i = EA_Registration.this.getIntent();
                    int modelID = i.getIntExtra("modelID",0);
                    String EA_type = i.getStringExtra("EA_type");
                    int VGID = i.getIntExtra("VGID",0);
                    Intent intent = new Intent(EA_Registration.this,CommandSetting.class);
                    intent.putExtra("modelID",modelID);
                    intent.putExtra("EA_type",EA_type);
                    intent.putExtra("VGID",VGID);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(EA_Registration.this);
                    MyAlertDialog.setTitle("此名稱己被使用");
                    MyAlertDialog.setMessage("請選擇其他名稱");
                    DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    };
                    MyAlertDialog.setNegativeButton("OK",OkClick );
                    MyAlertDialog.show();
                }
            }
        });
    }




}
