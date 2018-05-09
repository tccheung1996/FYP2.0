package com.example.asus.FYP;

import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 12/4/2018.
 */

public class SettingDB {
    static GlobalVir gv = new GlobalVir();
    public static String executeQuery(int userID) {
        String result = "";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(gv.getServerHostName()+"/fyp/SelectInformation.php?userID=" + userID);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //view_account.setText(httpResponse.getStatusLine().toString());
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = bufReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            inputStream.close();
            result = builder.toString();
        } catch(Exception e) {
            //   Log.e("log_tag", e.toString());
        }

        return result;
    }
    public static void executeQueryUpdate(String name, String phone, String chatbox, int userID) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";


        try {
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(gv.getServerHostName()+"/fyp/UpdateInformation.php?name=" + name + "&userID=" + userID + "&chatbox=" + chatbox + "&phone=" + phone);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            //nameValuePairs.add(new BasicNameValuePair("email", accountHolder));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

        } catch (Exception e) {

        }

    }
    public static void executeQueryPassword(String password, int userID) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";


        try {
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(gv.getServerHostName()+"/fyp/UpdatePassword.php?userID=" + userID + "&password=" + password);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            //nameValuePairs.add(new BasicNameValuePair("email", accountHolder));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

        } catch (Exception e) {

        }

    }
}
