package com.example.asus.FYP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import org.json.JSONException;

public class SelectVG extends AppCompatActivity {
    public String[] str = {"Xbox360","PlayStation4"};
    int VGID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tvmodel);


        ListView listview = (ListView) findViewById(R.id.listTVModel);



        //android.R.layout.simple_list_item_1 為內建樣式，還有其他樣式可自行研究


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, str){
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

            GlobalVir gv = (GlobalVir)getApplicationContext();
            gv.EA_name = str[position];

            switch (str[position]){
                case "Xbox360":
                    VGID=1;
                    gv.EAID=15;
                    break;
                case "PlayStation4":
                    VGID=2;
                    gv.EAID=16;
                    break;
            }
            Intent intent = new Intent(SelectVG.this,EA_Registration.class);
            intent.putExtra("VGID",VGID);
            intent.putExtra("EA_type","VG");
            startActivity(intent);
        }
    };
}
