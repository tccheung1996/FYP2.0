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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 16/3/2018.
 */

public class EA_RegistrationDB {
    public static void executeQuery(String name,int userID,int EAID,String command1,String command2,String command3,String command4,String command5,
                                    String command6,String command7,String command8,String command9,String command10,String command11,String command12) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";


        try {
            HttpClient httpClient = new DefaultHttpClient();

            GlobalVir gv = new GlobalVir();
            HttpPost httpPost = new HttpPost(gv.getServerHostName() + "/fyp/RegisterEA.php?name="+name+"&userID="+userID+"&EAID="+EAID+"&command1="+command1
                    +"&command2="+command2+"&command3="+command3+"&command4="+command4+"&command5="+command5+"&command6="+command6+"&command7="+command7
                    +"&command8="+command8+"&command9="+command9+"&command10="+command10+"&command11="+command11+"&command12="+command12);

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
