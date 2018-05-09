package com.example.asus.FYP;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Setting extends Fragment {
    public String[] title = {"Name","Phone Number","Assistent Name","Change Password","Contact us"};
    public String userName="";
    public int phoneNum;
    public String chatbotName;
    public String password;
    public int userID;
    public String input;
    String old_password ;
    String new_password ;
    String new_comfirm;
    public Setting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listview = (ListView) getView().findViewById(R.id.listview);
        SharedPreferences sp = this.getActivity().getSharedPreferences("atm", MODE_PRIVATE);
        userID = sp.getInt("userID",0);
        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //android.R.layout.simple_list_item_1 為內建樣式，還有其他樣式可自行研究


        ArrayAdapter adapter = new ArrayAdapter(Setting.this.getContext(), android.R.layout.simple_list_item_1, title);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onClickListView);

    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    Toast.makeText(Setting.this.getContext(),"點選第 "+(position +1) +" 個 \n內容："+ title[position], Toast.LENGTH_SHORT).show();
                    final View item = LayoutInflater.from(Setting.this.getContext()).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Setting.this.getContext());

                    dialog.setTitle(title[position]);

                    dialog.setView(item);
                    EditText editText = (EditText) item.findViewById(R.id.edit_input);

                    editText.setText(userName+"");
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.edit_input);
                            input = editText.getText().toString();
                            if(TextUtils.isEmpty(input.trim())){
                                Toast.makeText(Setting.this.getContext().getApplicationContext(), "不能空白", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder dialogBox = new AlertDialog.Builder(Setting.this.getContext());
                                dialogBox.setTitle("注意");
                                dialogBox.setMessage("確定要更新這個項目嗎?");
                                dialogBox.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {


                                    }

                                });
                                dialogBox.setPositiveButton("確定",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {



                                        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                                                R.style.AppTheme_Dark_Dialog);
                                        progressDialog.setIndeterminate(true);
                                        progressDialog.setMessage("Updating");

                                        progressDialog.show();
                                        SettingDB.executeQueryUpdate(input,phoneNum+"",chatbotName,userID);

                                        // TODO: Implement your own authentication logic here.

                                        new Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        progressDialog.dismiss();
                                                        Intent i = new Intent(Setting.this.getActivity(),MainActivity.class);
                                                        getActivity().finish();
                                                        startActivity(i);
                                                    }
                                                }, 3000);

                                    }
                                });
                                dialogBox.show();

                            }
                        }
                    });
                    dialog.show();
                    break;
                case 1:
                    Toast.makeText(Setting.this.getContext(),"點選第 "+(position +1) +" 個 \n內容："+ title[position], Toast.LENGTH_SHORT).show();
                    final View item2 = LayoutInflater.from(Setting.this.getContext()).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(Setting.this.getContext());

                    dialog2.setTitle(title[position]);

                    dialog2.setView(item2);
                    EditText editText2 = (EditText) item2.findViewById(R.id.edit_input);

                    editText2.setText(phoneNum+"");
                    dialog2.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item2.findViewById(R.id.edit_input);
                            input = editText.getText().toString();
                            if(TextUtils.isEmpty(input.trim())){
                                Toast.makeText(Setting.this.getContext().getApplicationContext(), "不能空白", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder dialogBox = new AlertDialog.Builder(Setting.this.getContext());
                                dialogBox.setTitle("注意");
                                dialogBox.setMessage("確定要更新這個項目嗎?");
                                dialogBox.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {


                                    }

                                });
                                dialogBox.setPositiveButton("確定",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {



                                        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                                                R.style.AppTheme_Dark_Dialog);
                                        progressDialog.setIndeterminate(true);
                                        progressDialog.setMessage("Updating");

                                        progressDialog.show();
                                        SettingDB.executeQueryUpdate(userName,input,chatbotName,userID);

                                        // TODO: Implement your own authentication logic here.

                                        new Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        progressDialog.dismiss();
                                                        Intent i = new Intent(Setting.this.getActivity(),MainActivity.class);
                                                        getActivity().finish();
                                                        startActivity(i);
                                                    }
                                                }, 3000);

                                    }
                                });
                                dialogBox.show();

                            }
                        }
                    });
                    dialog2.show();
                    break;
                case 2:
                    Toast.makeText(Setting.this.getContext(),"點選第 "+(position +1) +" 個 \n內容："+ title[position], Toast.LENGTH_SHORT).show();
                    final View item3 = LayoutInflater.from(Setting.this.getContext()).inflate(R.layout.input, null);
                    AlertDialog.Builder dialog3 = new AlertDialog.Builder(Setting.this.getContext());

                    dialog3.setTitle(title[position]);

                    dialog3.setView(item3);
                    EditText editText3 = (EditText) item3.findViewById(R.id.edit_input);

                    editText3.setText(chatbotName);
                    dialog3.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item3.findViewById(R.id.edit_input);
                            input = editText.getText().toString();
                            if(TextUtils.isEmpty(input.trim())){
                                Toast.makeText(Setting.this.getContext().getApplicationContext(), "不能空白", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder dialogBox = new AlertDialog.Builder(Setting.this.getContext());
                                dialogBox.setTitle("注意");
                                dialogBox.setMessage("確定要更新這個項目嗎?");
                                dialogBox.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {


                                    }

                                });
                                dialogBox.setPositiveButton("確定",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {



                                        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                                                R.style.AppTheme_Dark_Dialog);
                                        progressDialog.setIndeterminate(true);
                                        progressDialog.setMessage("Updating");

                                        progressDialog.show();
                                        SettingDB.executeQueryUpdate(userName,phoneNum+"",input,userID);

                                        // TODO: Implement your own authentication logic here.

                                        new Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        progressDialog.dismiss();
                                                        Intent i = new Intent(Setting.this.getActivity(),MainActivity.class);
                                                        getActivity().finish();
                                                        startActivity(i);
                                                    }
                                                }, 3000);

                                    }
                                });
                                dialogBox.show();

                            }
                        }
                    });
                    dialog3.show();
                    break;
                case 3:
                    Toast.makeText(Setting.this.getContext(),"點選第 "+(position +1) +" 個 \n內容："+ title[position], Toast.LENGTH_SHORT).show();
                    final View item4 = LayoutInflater.from(Setting.this.getContext()).inflate(R.layout.input2, null);
                    AlertDialog.Builder dialog4 = new AlertDialog.Builder(Setting.this.getContext());

                    dialog4.setTitle(title[position]);

                    dialog4.setView(item4);
                    dialog4.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText etOld = (EditText) item4.findViewById(R.id.etOld);
                            EditText etNew = (EditText) item4.findViewById(R.id.etNew);
                            EditText etNewConfirm = (EditText) item4.findViewById(R.id.etNewConfirm);
                            etOld.setText(password);

                            new_password = etNew.getText().toString();
                            new_comfirm = etNewConfirm.getText().toString();
                            if(TextUtils.isEmpty(old_password.trim())||TextUtils.isEmpty(new_password.trim())||TextUtils.isEmpty(new_comfirm.trim())){
                                Toast.makeText(Setting.this.getContext().getApplicationContext(), "不能空白", Toast.LENGTH_SHORT).show();
                            }else if(!(etNew.getText().toString().equals(new_comfirm))){
                                AlertDialog.Builder dialogBox = new AlertDialog.Builder(Setting.this.getContext());
                                dialogBox.setTitle("注意");
                                dialogBox.setMessage("密碼不符");
                                dialogBox.show();
                            }else {
                                AlertDialog.Builder dialogBox = new AlertDialog.Builder(Setting.this.getContext());
                                dialogBox.setTitle("注意");
                                dialogBox.setMessage("確定要更新這個項目嗎?");
                                dialogBox.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {


                                    }

                                });
                                dialogBox.setPositiveButton("確定",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {



                                        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                                                R.style.AppTheme_Dark_Dialog);
                                        progressDialog.setIndeterminate(true);
                                        progressDialog.setMessage("Updating");
                                        progressDialog.show();
                                        SettingDB.executeQueryPassword(new_password,userID);

                                        // TODO: Implement your own authentication logic here.

                                        new Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        progressDialog.dismiss();
                                                        Intent i = new Intent(Setting.this.getActivity(),MainActivity.class);
                                                        getActivity().finish();
                                                        startActivity(i);
                                                    }
                                                }, 3000);

                                    }
                                });
                                dialogBox.show();

                            }
                        }
                    });
                    dialog4.show();
                    break;
                case 4:
                    String phone = "+85292088697";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);


            }



        }
    };

    public void getData() throws JSONException{

        String data = SettingDB.executeQuery(userID);
        Log.d("json", data);
        try {
            JSONArray jsonArray = null;
            jsonArray = new JSONArray(data);
            JSONObject jsonData = jsonArray.getJSONObject(0);
            userName=jsonData.getString("name");
            phoneNum=jsonData.getInt("PhoneNumber");
            chatbotName=jsonData.getString("chatbotName");
            password = jsonData.getString("password");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
