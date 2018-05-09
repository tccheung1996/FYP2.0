package com.example.asus.FYP;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class OneKeyRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    private Spinner spinner5;
    Button btn;
    int ea1;
   int ea2;
    int ea3;
    int ea4;
    int ea5;
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<Integer> EAID = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_registration);

        spinner1 = findViewById(R.id.spin_ea1);
        spinner2 = findViewById(R.id.spin_ea2);
        spinner3 = findViewById(R.id.spin_ea3);
        spinner4 = findViewById(R.id.spin_ea4);
        spinner5 = findViewById(R.id.spin_ea5);
        btn = findViewById(R.id.btn_onekey_confirm);
        name.add("請選擇裝置");
        EAID.add(99);

        GlobalVir gv = (GlobalVir) getApplicationContext();
        try {
            getData(gv.userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(name.size()!=0){
            ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, name);
            aa.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(aa);
            spinner2.setAdapter(aa);
            spinner3.setAdapter(aa);
            spinner4.setAdapter(aa);
            spinner5.setAdapter(aa);

        }
        else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("注意!!");
            dialog.setMessage("您還未註冊任何電器,請先回主頁註冊");
                dialog.setPositiveButton("確定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(OneKeyRegistration.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }

                });
                dialog.show();

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test select", spinner1.getSelectedItem().toString()+"");
                ea1= EAID.get(spinner1.getSelectedItemPosition());
                ea2= EAID.get(spinner2.getSelectedItemPosition());
                ea3= EAID.get(spinner3.getSelectedItemPosition());
                ea4= EAID.get(spinner4.getSelectedItemPosition());
                ea5= EAID.get(spinner5.getSelectedItemPosition());

                if(spinner1.getSelectedItem().toString().equals("請選擇裝置")){
                    ea1 = 0;
                }
                if(spinner2.getSelectedItem().toString().equals("請選擇裝置")){
                    ea2= 0;
                }
                if(spinner3.getSelectedItem().toString().equals("請選擇裝置")){
                    ea3= 0;
                }
                if(spinner4.getSelectedItem().toString().equals("請選擇裝置")){
                    ea4= 0;
                }
                if(spinner5.getSelectedItem().toString().equals("請選擇裝置")){
                    ea5= 0;
                }
                EditText editText = (EditText) findViewById(R.id.et_name);
                final String name = editText.getText().toString();
                if(TextUtils.isEmpty(name.trim())){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(OneKeyRegistration.this);
                    dialog.setTitle("注意");
                    dialog.setMessage("名字不能空白");
                    dialog.setPositiveButton("知道了",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                        }

                    });
                    dialog.show();
                }
                else if(spinner1.getSelectedItem().toString().equals("請選擇裝置") && spinner2.getSelectedItem().toString().equals("請選擇裝置") &&
                        spinner3.getSelectedItem().toString().equals("請選擇裝置") && spinner4.getSelectedItem().toString().equals("請選擇裝置") &&
                                spinner5.getSelectedItem().toString().equals("請選擇裝置")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(OneKeyRegistration.this);
                    dialog.setTitle("注意");
                    dialog.setMessage("最少選擇一項裝置");
                    dialog.setPositiveButton("知道了",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                        }

                    });
                    dialog.show();
                }
                else{
                    final ProgressDialog progressDialog = new ProgressDialog(OneKeyRegistration.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("loading");
                    progressDialog.show();

                    // TODO: Implement your own authentication logic here.

                    new Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    GlobalVir gv = (GlobalVir) OneKeyRegistration.this.getApplicationContext();
                                    OneKeyRegistrationDB.executeQuery(gv.userID,name,ea1,ea2,ea3,ea4,ea5);
                                    Intent intent = new Intent(OneKeyRegistration.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                }
                            }, 3000);

                }
            }
        });

    }
    public void getData(int userID1) throws JSONException {
        String data = ListEADB.executeQuery(userID1);
        JSONArray jsonArray = new JSONArray(data);
        Log.d("json testing", data);
        for(int i=0 ; i< jsonArray.length() ; i++){
            JSONObject jsonData = jsonArray.getJSONObject(i);
            int id = jsonData.getInt("userID");
            String name1 = jsonData.getString("name");
            int rid = jsonData.getInt("registrationID");
            int eaid = jsonData.getInt("EAID");
            name.add(name1);
            EAID.add(eaid);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
