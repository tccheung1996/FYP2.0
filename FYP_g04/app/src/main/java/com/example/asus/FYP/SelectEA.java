package com.example.asus.FYP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectEA extends AppCompatActivity {
    private GridView gridView;
    private int[] image = { R.drawable.ic_tv, R.drawable.ic_videogame,R.drawable.ic_projector,R.drawable.ic_air,R.drawable.ic_fan,R.drawable.ic_dvd
    ,R.drawable.ic_light};
    private String[] imgText = { "TV", "Video Game" ,"Projector","Air Conditioner","Fan","DVD","Light"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_e);



        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", image[i]);
            item.put("text", imgText[i]);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                items, R.layout.grid_item, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        gridView = (GridView)findViewById(R.id.ea);
        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(imgText[position].equals("TV")){
                    GlobalVir gv = (GlobalVir) SelectEA.this.getApplicationContext();
                    gv.EAID =1;
                    Intent intent = new Intent(SelectEA.this,SelectTVbrend.class);

                    startActivity(intent);
                }
                else if(imgText[position].equals("Video Game")){
                    GlobalVir gv = (GlobalVir) SelectEA.this.getApplicationContext();
                    gv.EAID =2;
                    Intent intent = new Intent(SelectEA.this,SelectVG.class);
                    startActivity(intent);
                }
                Toast.makeText(SelectEA.this, "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show();
            }
        });

    }
}
