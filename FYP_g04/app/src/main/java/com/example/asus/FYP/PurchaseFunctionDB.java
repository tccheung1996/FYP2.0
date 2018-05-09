package com.example.asus.FYP;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 14/3/2018.
 */

public class PurchaseFunctionDB {
    public static void executeQuery(int functionID,String date,int userID,Double price) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";


        try {
            HttpClient httpClient = new DefaultHttpClient();

            GlobalVir gv = new GlobalVir();
            HttpPost httpPost = new HttpPost(gv.getServerHostName() + "/fyp/PurchaseFunction.php?functionID="+functionID+"&userID="+userID+"&date="+date+"&price="+price);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            //nameValuePairs.add(new BasicNameValuePair("email", accountHolder));


            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

        } catch(Exception e) {
            Log.e("log_tag", e.toString());

        }

    }
}
