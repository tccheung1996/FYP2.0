package com.example.asus.FYP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class OneKey extends AppCompatActivity {
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<Integer> oneKeyID = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);

        toolbar.setTitle("一鍵開機");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        GlobalVir gv = (GlobalVir) getApplicationContext();
        try {
            getData(gv.userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListView listview = (ListView) findViewById(R.id.listview_one);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, name){
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
            GlobalVir gv = (GlobalVir)getApplicationContext();

            Intent intent = new Intent(OneKey.this,OneKeyManual.class);
            intent.putExtra("oneKeyID",oneKeyID.get(position));
            startActivity(intent);
        }
    };
    public void getData(int userID) throws JSONException {
        String data = OneKeyRegistrationDB.executeQueryList(userID);
        JSONArray jsonArray = new JSONArray(data);
        Log.d("json", data);
        for(int i=0 ; i< jsonArray.length() ; i++){
            JSONObject jsonData = jsonArray.getJSONObject(i);
            int id = jsonData.getInt("onekeyID");
            String name1 = jsonData.getString("oneKeyName");

            oneKeyID.add(id);
            name.add(name1);

        }
    }
}

