package com.example.asus.FYP;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommandSetting extends AppCompatActivity {
ImageButton btn_power,btn_ch_plus,btn_ch_down,btn_vol_up,btn_vol_down,btn_up,btn_left,btn_right,btn_down,btn_play,btn_pause;
Button btn_enter;
    public String ir_power,ir_ch_plus,ir_ch_down,ir_vol_plus,ir_vol_down,ir_up,ir_down,ir_left,ir_right,ir_enter,ir_play,ir_pause,
            command1,command2,command3,command4,command5,
    command6,command7,command8,command9,command10,command11,command12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("語音指令設定");
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        int modelID = intent.getIntExtra("modelID",0);
        String EA_type = intent.getStringExtra("EA_type");
        int VGID = intent.getIntExtra("VGID",0);

        GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
        command1 = "開";
        command2 = "教大聲";
        command3 = "教細聲";
        command4 = "下一個台";
        command5 = "上一個台";
        command6 = "向上";
        command7 = "向下";
        command8 = "向左";
        command9 = "向右";
        command10 = "確定";
        command11 = "播放";
        command12 = "暫停";
        try {
            switch(EA_type){
                case "TV":
                    getData(modelID);
                    break;
                case "VG":
                    getVGData(VGID);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("extra", modelID+"");
        btn_power = findViewById(R.id.btn_power);
        btn_ch_plus = findViewById(R.id.btn_ch_plus);
        btn_ch_down = findViewById(R.id.btn_ch_down);
        btn_vol_up = findViewById(R.id.btn_vol_plus);
        btn_vol_down = findViewById(R.id.btn_vol_down);
        btn_up = findViewById(R.id.btn_up);
        btn_down = findViewById(R.id.btn_down);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);
        btn_enter = findViewById(R.id.btn_enter);
        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);

        if(ir_power.equals("")){

            btn_power.setClickable(false);
            btn_power.setColorFilter(Color.parseColor("#E6E6E6"));
            command1="";
        }
        else{
            btn_power.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定電源指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);

                    editText.setText(command1);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if(TextUtils.isEmpty(name.trim())){
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                                command1 = name;
                                Toast.makeText(getApplicationContext(), "己設定電源指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }

        if(ir_ch_plus.equals("")){
            btn_ch_plus.setClickable(false);
            btn_ch_plus.setColorFilter(Color.parseColor("#E6E6E6"));
            command4="";
        }
        else {
            btn_ch_plus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定頻道+指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command4);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);

                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                                command4 = name;
                                Toast.makeText(getApplicationContext(), "己設定頻道+指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }
        if(ir_ch_down.equals("")) {
            btn_ch_down.setClickable(false);
            btn_ch_down.setColorFilter(Color.parseColor("#E6E6E6"));
            command5="";
        }
        else{
            btn_ch_down.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定頻道-指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command5);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {

                                command5 = name;
                                Toast.makeText(getApplicationContext(), "己設定頻道-指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }
        if(ir_vol_plus.equals("")){
            btn_vol_up.setClickable(false);
            btn_vol_up.setColorFilter(Color.parseColor("#E6E6E6"));
            command2="";
        }
        else {
            btn_vol_up.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定音量+指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command2);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                command2 = name;
                                Toast.makeText(getApplicationContext(), "己設定音量+指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }
        if(ir_vol_down.equals("")){
            btn_vol_down.setClickable(false);
            btn_vol_down.setColorFilter(Color.parseColor("#E6E6E6"));
            command3="";
        }
        else {
            btn_vol_down.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定音量-指令");
                    dialog.setView(item);EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command3);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                command3 = name;
                                Toast.makeText(getApplicationContext(), "己設定音量-指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }
        if(ir_up.equals("")){
            btn_up.setClickable(false);
            btn_up.setColorFilter(Color.parseColor("#E6E6E6"));
            command6="";
        }
        else {
            btn_up.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定方向上指令");
                    dialog.setView(item);EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command6);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                command6 = name;
                                Toast.makeText(getApplicationContext(), "己設定方向上指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }
        if(ir_down.equals("")){
            btn_down.setClickable(false);
            btn_down.setColorFilter(Color.parseColor("#E6E6E6"));
            command7="";
        }
        else {
            btn_down.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定方向下指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command7);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                command7 = name;
                                Toast.makeText(getApplicationContext(), "己設定方向下指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }
        if(ir_left.equals("")){
            btn_left.setClickable(false);
            btn_left.setColorFilter(Color.parseColor("#E6E6E6"));
            command8="";
        }
        else {
            btn_left.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定方向左指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command8);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                command8 = name;
                                Toast.makeText(getApplicationContext(), "己設定方向左指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }
        if(ir_right.equals("")){
            btn_right.setClickable(false);
            btn_right.setColorFilter(Color.parseColor("#E6E6E6"));
            command9="";
        }
        else {
            btn_right.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定方向右指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command9);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                command9 = name;
                                Toast.makeText(getApplicationContext(), "己設定方向右指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }
        if(ir_enter.equals("")){
            btn_enter.setClickable(false);
            btn_enter.setBackgroundColor(Color.parseColor("#E6E6E6"));
            btn_enter.setTextColor(Color.parseColor("#E6E6E6"));
            command10="";
        }
        else {
            btn_enter.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定ENTER指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command10);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                command10 = name;
                                Toast.makeText(getApplicationContext(), "己設定ENTER指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }
        if(ir_play.equals("")){
            btn_play.setClickable(false);
            btn_play.setColorFilter(Color.parseColor("#F2F2F2"));
            command11="";

        }
        else{
            btn_play.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定播放指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command11);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if(TextUtils.isEmpty(name.trim())){
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                command11 = name;
                                Toast.makeText(getApplicationContext(), "己設定播放指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }

        if(ir_pause.equals("")){
            btn_pause.setClickable(false);
            btn_pause.setColorFilter(Color.parseColor("#E6E6E6"));
            command12="";
        }
        else {
            btn_pause.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final View item = LayoutInflater.from(CommandSetting.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommandSetting.this);

                    dialog.setTitle("設定暫停指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)CommandSetting.this.getApplicationContext();
                    editText.setText(command12);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                command12 = name;
                                Toast.makeText(getApplicationContext(), "己設定暫停指令", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();

                }


            });
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_command_setting, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this,EA_RegistrationConfrim.class);
                intent.putExtra("command1",command1);
                intent.putExtra("command2",command2);
                intent.putExtra("command3",command3);
                intent.putExtra("command4",command4);
                intent.putExtra("command5",command5);
                intent.putExtra("command6",command6);
                intent.putExtra("command7",command7);
                intent.putExtra("command8",command8);
                intent.putExtra("command9",command9);
                intent.putExtra("command10",command10);
                intent.putExtra("command11",command11);
                intent.putExtra("command12",command12);
                startActivity(intent);
        }
        Thread.interrupted();
        return super.onOptionsItemSelected(item);
    }

    public void getData(int modelID) throws JSONException {
        String data = CheckCommandDB.executeQuery(modelID);
        JSONArray jsonArray = new JSONArray(data);
        Log.d("json", data);
        for(int i=0 ; i< jsonArray.length() ; i++){

            JSONObject jsonData = jsonArray.getJSONObject(i);
            ir_power = jsonData.getString("Ir_power");
            ir_ch_plus = jsonData.getString("Ir_ch+");
            ir_ch_down =jsonData.getString("Ir_ch-");
            ir_vol_plus = jsonData.getString("Ir_vol+");
            ir_vol_down= jsonData.getString("Ir_vol-");
            ir_up =jsonData.getString("Ir_up");
            ir_down =jsonData.getString("Ir_down");
            ir_left =jsonData.getString("Ir_left");
            ir_right= jsonData.getString("Ir_right");
            ir_enter= jsonData.getString("Ir_enter");
            ir_play=jsonData.getString("Ir_play");
            ir_pause=jsonData.getString("Ir_pause");

        }
    }
    public void getVGData(int VGID) throws JSONException {
        String data = CheckCommandVGDB.executeQuery(VGID);
        JSONArray jsonArray = new JSONArray(data);
        Log.d("json", data);
        for(int i=0 ; i< jsonArray.length() ; i++){

            JSONObject jsonData = jsonArray.getJSONObject(i);
            ir_power = jsonData.getString("Ir_power");
            ir_ch_plus = jsonData.getString("Ir_ch+");
            ir_ch_down =jsonData.getString("Ir_ch-");
            ir_vol_plus = jsonData.getString("Ir_vol+");
            ir_vol_down= jsonData.getString("Ir_vol-");
            ir_up =jsonData.getString("Ir_up");
            ir_down =jsonData.getString("Ir_down");
            ir_left =jsonData.getString("Ir_left");
            ir_right= jsonData.getString("Ir_right");
            ir_enter= jsonData.getString("Ir_enter");
            ir_play=jsonData.getString("Ir_play");
            ir_pause=jsonData.getString("Ir_pause");

        }
    }

}
