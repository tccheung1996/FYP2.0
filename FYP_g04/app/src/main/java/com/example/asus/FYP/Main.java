package com.example.asus.FYP;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ai.api.android.AIService;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class Main extends Fragment  {

    private GridView gridView;
    private int image ;
    private int imageTV = R.drawable.ic_tv;
    private int imageVG = R.drawable.ic_videogame;
    private String[] imgText = { "TV", "Video Game" };
    private ArrayList<Integer> userID = new ArrayList<Integer>();
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<Integer> EAID = new ArrayList<Integer>();
    private ArrayList<Integer> RegisterID = new ArrayList<Integer>();
    private TextView tv2;
private int id;
    public Main() {
        // Required empty public constructor

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv2 = getView().findViewById(R.id.textView3);
        GlobalVir gv = (GlobalVir)this.getContext().getApplicationContext();
        SharedPreferences setting = this.getActivity().getSharedPreferences("atm", MODE_PRIVATE);
        int userid = setting.getInt("userID",0);
        tv2 = getView().findViewById(R.id.tv2);



        try {
            getData(userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(name.size() != 0){
            Log.d("name size", name.size()+"");
            tv2.setText("");
        }
        gv.chatbotName = setting.getString("chatbotName","");

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this.getContext(),SelectEA.class);
                startActivity(intent);
            }
        });

        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < name.size(); i++) {
            Map<String, Object> item = new HashMap<>();
            if(EAID.get(i) == 15 || EAID.get(i) ==16){
                image = imageVG;
            }
            else
                image = imageTV;

            item.put("image", image);
            item.put("text", name.get(i));
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this.getContext(),
                items, R.layout.grid_item, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        gridView = (GridView)getView().findViewById(R.id.main_grid);
        registerForContextMenu(gridView);

        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main.this.getContext(),ManualControl.class);
                intent.putExtra("ID",EAID.get(position));

                startActivity(intent);

                Toast.makeText(Main.this.getContext(), "你選擇了" + name.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            this.getActivity().getMenuInflater().inflate(R.menu.menu_main_delete, menu);
            menu.setHeaderTitle(name.get(info.position));
            id = RegisterID.get(info.position);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                Intent i = new Intent(Main.this.getActivity(),UpdateCommand.class);
                i.putExtra("rid",id);
                startActivity(i);
                Toast.makeText(Main.this.getContext(), ""+id, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.option2:
                AlertDialog.Builder dialog = new AlertDialog.Builder(Main.this.getContext());
                dialog.setTitle("注意");
                dialog.setMessage("確定要刪除這個項目嗎?");
                    dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                    dialog.setPositiveButton("確定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Deleting");
                        progressDialog.show();

                        DeleteEA.executeQuery(id);
                        // TODO: Implement your own authentication logic here.

                        new Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        Intent i = new Intent(Main.this.getActivity(),MainActivity.class);
                                        getActivity().finish();
                                        startActivity(i);
                                    }
                                }, 3000);
                        }
                    });
                    dialog.show();

                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }
    public void getData(int userID1) throws JSONException {
        String data = ListEADB.executeQuery(userID1);
        JSONArray jsonArray = new JSONArray(data);
        Log.d("json", data);
        for(int i=0 ; i< jsonArray.length() ; i++){

            JSONObject jsonData = jsonArray.getJSONObject(i);
            int id = jsonData.getInt("userID");
            String name1 = jsonData.getString("name");
            int rid = jsonData.getInt("registrationID");
            int eaid = jsonData.getInt("EAID");

            userID.add(id);
            name.add(name1);
            EAID.add(eaid);
            RegisterID.add(rid);
        }
    }





}
