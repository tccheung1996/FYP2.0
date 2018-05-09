package com.example.asus.FYP;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

public class SelectTVModel extends AppCompatActivity {
    TextView tv ;
    int brandID;
    String type;;
    public ArrayList<String> model = new ArrayList<String>();
    public ArrayList<Integer>modelID = new ArrayList<Integer>();
    public ArrayList<Integer>EAID = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tvmodel);

        tv = findViewById(R.id.tvModel);
        Intent intent = this.getIntent();
        brandID = intent.getIntExtra("brandID", 0);
        ListView listview = (ListView) findViewById(R.id.listTVModel);


        try {
           getData();
       } catch (JSONException e) {
            e.printStackTrace();
        }
        //android.R.layout.simple_list_item_1 為內建樣式，還有其他樣式可自行研究


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, model){
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
            Toast.makeText(SelectTVModel.this,"點選第 "+(position +1) +" 個 \n內容："+model.get(position), Toast.LENGTH_SHORT).show();


            type ="TV";
            Intent intent = new Intent(SelectTVModel.this,EA_Registration.class);
            GlobalVir gv = (GlobalVir)getApplicationContext();
            gv.EAID = EAID.get(position);
            intent.putExtra("modelID",modelID.get(position));
            intent.putExtra("EA_type",type);
            Log.d("modelID", modelID.get(position)+"");
            startActivity(intent);
        }
    };

    public void getData() throws JSONException{
        String data = SelectTvModelDB.executeQuery(brandID);
        JSONArray jsonArray = new JSONArray(data);
        Log.d("json", data);
        for(int i=0 ; i< jsonArray.length() ; i++){

            JSONObject jsonData = jsonArray.getJSONObject(i);
            int id = jsonData.getInt("TVModelID");
            String name1 = jsonData.getString("name");

            int eaid = jsonData.getInt("EAID");

            modelID.add(id);
            model.add(name1);
            EAID.add(eaid);






        }
    }
}
