package com.example.asus.FYP;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ASUS on 9/4/2018.
 */

public class UpdateCommandDB {
    public static String executeQueryCheck(int registrationID) {
        String result = "";
        GlobalVir gv = new GlobalVir();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(gv.getServerHostName()+"/fyp/CheckRegisteredCommand.php?registrationID="+registrationID);//this is connect to server but the will change
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            HttpResponse httpResponse = httpClient.execute(httpPost);
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
            Log.d("log_tag", e.toString());
        }

        return result;
    }
    public static String executeQueryUpdate(int registerID,String command1,String command2,String command3,String command4,String command5,String command6,String command7,String command8
            ,String command9,String command10,String command11,String command12) {
        String result = "";
        GlobalVir gv = new GlobalVir();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(gv.getServerHostName()+"/fyp/UpdateCommand.php?registerID="+registerID+"&command1="+command1+"&command10="+command10+"&command2="+command2+"&command3="+command3+"&" +
                    "command4="+command4+"&command5="+command5+"&command6="+command6+"&command7="+command7+"&command8="+command8+"&command9="+command9+"&command11="+command11+"&command12="+command12);
            //this is connect to server but the will change
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            HttpResponse httpResponse = httpClient.execute(httpPost);
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
            Log.d("log_tag", e.toString());
        }

        return result;
    }
}
