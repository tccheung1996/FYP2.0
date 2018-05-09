package com.example.asus.FYP;

import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OneKeyManual extends AppCompatActivity {
    private ArrayList<Integer> EAID = new ArrayList<Integer>();
    private ArrayList<String> ircode = new ArrayList<String>();
    private ConsumerIrManager irManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_manual);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);

        toolbar.setTitle("一鍵開機");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        irManager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);

        ImageView iv = findViewById(R.id.imageView3);
        Intent intent = this.getIntent();
        int onekeyID = intent.getIntExtra("oneKeyID",0);
        try {
            getData(onekeyID);
            getIr();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<ircode.size();i++){
                    IRCommand cmd = hex2ir(ircode.get(i));
                    android.util.Log.d("Remote", "frequency: " + cmd.freq);
                    android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                    irManager.transmit(cmd.freq, cmd.pattern);
                }
                Toast.makeText(OneKeyManual.this,"己開機", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getData(int onekeyID) throws JSONException {
        String data = OneKeyRegistrationDB.executeQueryByID(onekeyID);
        JSONArray jsonArray = new JSONArray(data);
        Log.d("json", data);
        for(int i=0 ; i< jsonArray.length() ; i++){
            JSONObject jsonData = jsonArray.getJSONObject(i);
            int id = jsonData.getInt("EAID");
            int id2 = jsonData.getInt("EAID2");
            int id3 = jsonData.getInt("EAID3");
            int id4 = jsonData.getInt("EAID4");
            int id5 = jsonData.getInt("EAID5");

            EAID.add(id);
            EAID.add(id2);
            EAID.add(id3);
            EAID.add(id4);
            EAID.add(id5);
        }
    }
    public void getIr() throws JSONException {
        for(int i =0 ; i< EAID.size();i++){
            if(EAID.get(i)!=0){
                String data = OneKeyRegistrationDB.executeQueryByEAID(EAID.get(i));
                JSONArray jsonArray = new JSONArray(data);
                Log.d("json", data);
                JSONObject jsonData = jsonArray.getJSONObject(0);
                String irHolder = jsonData.getString("Ir_power");
                ircode.add(irHolder);
            }
            else if(EAID.get(i)==15){
                ircode.add("0000 0073 0000 0021 0060 0020 0010 0010 0010 0010 0010 0020 0010 0020 0030 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0010 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0020 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0010 0010 0020 0010 0010 0010 09AC");
            }

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
