package com.example.asus.FYP;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.ConsumerIrManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.asus.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManualControl extends AppCompatActivity {
    public String ir_power,ir_ch_plus,ir_ch_down,ir_vol_plus,ir_vol_down,ir_up,ir_down,ir_left,ir_right,ir_enter,ir_play,ir_pause,
            command1,command2,command3,command4,command5,
            command6,command7,command8,command9,command10,command11,command12;
    ImageButton btn_power,btn_ch_plus,btn_ch_down,btn_vol_up,btn_vol_down,btn_up,btn_left,btn_right,btn_down,btn_play,btn_pause;
    Button btn_enter;
    private ConsumerIrManager irManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_control);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("手動搖控");
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        int modelID = intent.getIntExtra("modelID",0);
        String EA_type = intent.getStringExtra("EA_type");
        int VGID = intent.getIntExtra("VGID",0);
        int EAID = intent.getIntExtra("ID",0);
        irManager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);


        GlobalVir gv = (GlobalVir)ManualControl.this.getApplicationContext();
        try {

            if(EAID ==16 || EAID==15){
                getVGData(EAID);
            }
            else
                getData(EAID);

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
                    IRCommand cmd = hex2ir(ir_power);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);

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
                    IRCommand cmd = hex2ir(ir_ch_plus);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);

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
                    IRCommand cmd = hex2ir(ir_ch_down);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);
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
                    IRCommand cmd = hex2ir(ir_vol_plus);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);

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
                    IRCommand cmd = hex2ir(ir_vol_down);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);
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
                    IRCommand cmd = hex2ir(ir_up);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);
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
                    IRCommand cmd = hex2ir(ir_down);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);

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
                    IRCommand cmd = hex2ir(ir_left);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);

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
                    IRCommand cmd = hex2ir(ir_right);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);

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
                    IRCommand cmd = hex2ir(ir_enter);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);

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
                    IRCommand cmd = hex2ir(ir_play);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);

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
                    IRCommand cmd = hex2ir(ir_pause);
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);

                }


            });
        }


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
    private IRCommand hex2ir(final String irData) {
        List<String> list = new ArrayList<String>(Arrays.asList(irData.split(" ")));
        list.remove(0); // dummy
        int frequency = Integer.parseInt(list.remove(0), 16); // frequency
        list.remove(0); // seq1
        list.remove(0); // seq2

        frequency = (int) (1000000 / (frequency * 0.241246));
        int pulses = 1000000 / frequency;
        int count;

        int[] pattern = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            count = Integer.parseInt(list.get(i), 16);
            pattern[i] = count * pulses;
        }

        return new IRCommand(/*frequency*/38400, pattern);
    }

    private class IRCommand {
        private final int freq;
        private final int[] pattern;

        private IRCommand(int freq, int[] pattern) {
            this.freq = freq;
            this.pattern = pattern;
        }
    }


}
