package com.example.asus.FYP;


import android.os.StrictMode;
import android.util.Log;

import com.example.asus.myapplication.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class Register {
    public static void executeQuery(String email, String password,String name,String mobileNo) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";
        String accountHolder = email;
        String passwordHolder = password;
        String nameHolder = name;
        String mobileNoHolder = mobileNo;

        try {
            HttpClient httpClient = new DefaultHttpClient();

            GlobalVir gv = new GlobalVir();
            HttpPost httpPost = new HttpPost(gv.getServerHostName() + "/fyp/registerAc.php?email="+accountHolder+"&password="+passwordHolder+"&name="+nameHolder+"&mobileNo="+mobileNoHolder);
            Log.d("testing", "testing");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

        } catch(Exception e) {
            Log.e("log_tag", e.toString());

        }

    }
    public static void executeQueryFunction(int userID) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        try {
            HttpClient httpClient = new DefaultHttpClient();

            GlobalVir gv = new GlobalVir();
            HttpPost httpPost = new HttpPost(gv.getServerHostName() + "/fyp/initUser.php?userID="+userID);
            Log.d("testing", "testing");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

        } catch(Exception e) {
            Log.e("log_tag", e.toString());

        }

    }

}
