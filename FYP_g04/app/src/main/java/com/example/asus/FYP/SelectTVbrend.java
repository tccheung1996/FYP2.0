package com.example.asus.FYP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectTVbrend extends AppCompatActivity {
    public String[] str = {"新北市","台北市","台中市","台南市","高雄市"};
    public ArrayList<String> brand = new ArrayList<String>();
    public ArrayList<Integer>BrandID = new ArrayList<Integer>();

    public void getData() throws JSONException{
        String data = SelectBrandDB.executeQuery();
        JSONArray jsonArray = new JSONArray(data);
        Log.d("json", data);
        for(int i=0 ; i< jsonArray.length() ; i++){
            String price2;
            JSONObject jsonData = jsonArray.getJSONObject(i);
            int id = jsonData.getInt("TVBrandID");
            String name1 = jsonData.getString("BrandName");

            BrandID.add(id);
            brand.add(name1);

        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tvbrend);

        ListView listview = (ListView) findViewById(R.id.listview);
        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //android.R.layout.simple_list_item_1 為內建樣式，還有其他樣式可自行研究


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, brand){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text size 25 dip for ListView each item
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);

                // Return the view
                return view;
            }
        };
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onClickListView);
    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast 快顯功能 第三個參數 Toast.LENGTH_SHORT 2秒  LENGTH_LONG 5秒
            Toast.makeText(SelectTVbrend.this,"點選第 "+(position +1) +" 個 \n內容："+brand.get(position), Toast.LENGTH_SHORT).show();
            GlobalVir gv = (GlobalVir)getApplicationContext();
            gv.EA_name = brand.get(position);
            Intent intent = new Intent(SelectTVbrend.this,SelectTVModel.class);
            intent.putExtra("brandID",BrandID.get(position));
            startActivity(intent);
        }
    };
}
