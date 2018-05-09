package com.example.asus.FYP;



import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */

public class Shop extends Fragment {
    private GridView gridView;
    private String[] imgText = {"TV Control", "Projector", "Music", "Weather", "Spotify","Hang Seng Index"};
    private Object[] img = {R.mipmap.ic_tv_control,R.mipmap.ic_projector_control,R.mipmap.ic_music,R.mipmap.ic_weather,R.mipmap.ic_spotify,R.mipmap.ic_finance};

    private String[] functionDetail = {"You can select your tv brand,then select the model","You can select your projector brand,then select the model","你可以在助手模式內,說出指令\"播歌\",之後說出歌名就能播放手機內的音樂",
    "你可以在助手模式內,說出指令\"今日天氣\",便可獲得即時天氣","你可以在助手模式內,說出指令\"在spotify播歌\",之後說出歌名便可以播放在spotify內的音樂","你可以在助手模式內,說出指令\"今日恆生指數\",之後便得到相關資訊"};
    //private String[] availability={"己解鎖","己解鎖","己解鎖","免費","$8.00"};
    private ArrayList<String> name =new ArrayList<String>();
    private ArrayList<Integer> functionID = new ArrayList<Integer>();
    private ArrayList<String> price = new ArrayList<String>();
    private ArrayList<Integer> availability = new ArrayList<Integer>();
    private ArrayList<Double> functionPrice = new ArrayList<Double>();

    public Shop() {
        // Required empty public constructor

    }
    public void getData() throws JSONException {

        String data = ListShopFunctionDB.executeQuery();
        JSONArray jsonArray = new JSONArray(data);

        SharedPreferences setting = this.getActivity().getSharedPreferences("atm", MODE_PRIVATE);
        String data1 = CheckUsersFunctionDB.executeQuery(setting.getInt("userID",0));
        if(data1.equals("[]\n")){
            Log.d("testing", data1);
        }
        JSONArray jsonArray1 = new JSONArray(data1);
        for(int i=0 ; i< jsonArray.length() ; i++) {
            String price2;
            JSONObject jsonData = jsonArray.getJSONObject(i);
            int id = jsonData.getInt("functionID");
            String name1 = jsonData.getString("name");
            Double price1 = jsonData.getDouble("price");
            if (price1 == 0) {
                price2 = "免費";
            } else {
                price2 = "$" + price1;
            }
            JSONObject jsonData1 = jsonArray1.getJSONObject(i);
            int status = jsonData1.getInt("status");
            Log.d("status", status + "");

            if (status == 1) {
                price2 = "己解鎖";
            }
            functionID.add(id);
            name.add(name1);
            price.add(price2);
            availability.add(status);
            functionPrice.add(price1);
        }
        Log.d("setText", "json ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {



        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        price.add("免費");
        name.add("Hang Seng Index");
        availability.add(0);
        functionID.add(12356);
        functionPrice.add(123564.0);

        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < imgText.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", img[i]);
            item.put("text", name.get(i)+  "\n"+ price.get(i));
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this.getActivity(),
                items, R.layout.grid_item, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        gridView = (GridView)getView().findViewById(R.id.main_page_gridview);
        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(Shop.this.getActivity(), "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(Shop.this.getContext());
                dialog.setTitle(name.get(position));
                dialog.setMessage(functionDetail[position]);
                if(availability.get(position)== 1){

                    dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub

                        }

                    });
                    dialog.setPositiveButton("詳情",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub

                        }

                    });
                    dialog.show();
                }
                else{
                    dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            Toast.makeText(Shop.this.getActivity(), "我還尚未了解",Toast.LENGTH_SHORT).show();
                        }

                    });
                    AlertDialog.Builder builder = dialog.setPositiveButton("解鎖", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            int idHolder = functionID.get(position);
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String formattedDate = df.format(c);
                            SharedPreferences setting = Shop.this.getActivity().getSharedPreferences("atm", MODE_PRIVATE);
                            PurchaseFunctionDB.executeQuery(idHolder,formattedDate,setting.getInt("userID",0),functionPrice.get(position));

                            final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                                    R.style.AppTheme_Dark_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Unlocking");
                            progressDialog.show();

                            // TODO: Implement your own authentication logic here.

                            new Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(getContext(),MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                    }, 3000);


                        }
                    });


                    dialog.show();
                }



            }
        });
    }

}
