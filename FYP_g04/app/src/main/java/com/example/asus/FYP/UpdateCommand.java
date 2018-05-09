package com.example.asus.FYP;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.asus.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateCommand extends AppCompatActivity {
    ImageButton btn_power,btn_ch_plus,btn_ch_down,btn_vol_up,btn_vol_down,btn_up,btn_left,btn_right,btn_down,btn_play,btn_pause;
    Button btn_enter;
    public String ir_power,ir_ch_plus,ir_ch_down,ir_vol_plus,ir_vol_down,ir_up,ir_down,ir_left,ir_right,ir_enter,ir_play,ir_pause,
            command1,command2,command3,command4,command5,
            command6,command7,command8,command9,command10,command11,command12;
    public int EAID;
    public int RegisterID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_command);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("更改語音指令");
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();

        String EA_type = intent.getStringExtra("EA_type");
        int VGID = intent.getIntExtra("VGID",0);
        RegisterID = intent.getIntExtra("rid",0);

        GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
        command1="";
        command2="";
        command3="";
        command4="";
        command5="";
        command6="";
        command7="";
        command8="";
        command9="";
        command10="";
        command11="";
        command12="";

        try {
            getCommand(RegisterID);
            if(EAID ==16 || EAID==15){
                getVGData(EAID);
            }
            else
                getData(EAID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

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
                                GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定頻道+指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
                    editText.setText(command4);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);

                            String name = editText.getText().toString();
                            if (TextUtils.isEmpty(name.trim())) {
                                Toast.makeText(getApplicationContext(), "沒有輸入正確指令", Toast.LENGTH_SHORT).show();
                            } else {
                                GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定頻道-指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定音量+指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定音量-指令");
                    dialog.setView(item);EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定方向上指令");
                    dialog.setView(item);EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定方向下指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定方向左指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定方向右指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定ENTER指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定播放指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                    final View item = LayoutInflater.from(UpdateCommand.this).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);

                    dialog.setTitle("設定暫停指令");
                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);
                    GlobalVir gv = (GlobalVir)UpdateCommand.this.getApplicationContext();
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateCommand.this);
                dialog.setTitle("注意");
                dialog.setMessage("確定要更新這些指令嗎?");
                dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }

                });
                dialog.setPositiveButton("確定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {



                        final ProgressDialog progressDialog = new ProgressDialog(UpdateCommand.this,
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Updating");
                        progressDialog.show();

                        UpdateCommandDB.executeQueryUpdate(RegisterID,command1,command2,command3,command4,
                                command5,command6,command7,command8,command9,command10,command11,command12);
                        // TODO: Implement your own authentication logic here.

                        new Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        Intent i = new Intent(UpdateCommand.this,MainActivity.class);
                                        UpdateCommand.this.finish();
                                        startActivity(i);
                                    }
                                }, 3000);

                    }
                });
                dialog.show();
        }
        Thread.interrupted();
        return super.onOptionsItemSelected(item);
    }

    public void getData(int modelID) throws JSONException {
        String data = getCommandDB.executeQueryManualTV(modelID);
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
        String data = getCommandDB.executeQueryManualVG(VGID);
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
    public void getCommand(int RegisterID) throws JSONException {
        String data = UpdateCommandDB.executeQueryCheck(RegisterID);
        JSONArray jsonArray = new JSONArray(data);
        Log.d("json", data);
        for(int i=0 ; i< jsonArray.length() ; i++){

            JSONObject jsonData = jsonArray.getJSONObject(i);
            command1 = jsonData.getString("command1");
            command2 = jsonData.getString("command2");
            command3 = jsonData.getString("command3");
            command4 = jsonData.getString("command4");
            command5 = jsonData.getString("command5");
            command6 = jsonData.getString("command6");
            command7 = jsonData.getString("command7");
            command8 = jsonData.getString("command8");
            command9 = jsonData.getString("command9");
            command10 = jsonData.getString("command10");
            command11 = jsonData.getString("command11");
            command12 = jsonData.getString("command12");
            EAID = jsonData.getInt("EAID");
        }
    }

}
