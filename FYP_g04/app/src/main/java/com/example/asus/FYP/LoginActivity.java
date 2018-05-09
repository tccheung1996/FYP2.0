package com.example.asus.FYP;

import android.Manifest;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    Boolean isNetworkAvaliable;

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    GlobalVir gv = new GlobalVir();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED) {

            Log.d("check1", "onResult:listening ");
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.INTERNET)) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},
                    1);
            Log.d("check", "onResult: ");
        }
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        EditText etEmail = (EditText) findViewById(R.id.input_email);
        EditText etPwd = (EditText) findViewById(R.id.input_password);
        SharedPreferences setting =
                getSharedPreferences("atm", MODE_PRIVATE);
        etEmail.setText(setting.getString("loginEmail", ""));


        if(setting.getBoolean("AUTO_ISCHECK", false))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
    }

    public void login() throws JSONException {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        SharedPreferences setting = getSharedPreferences("atm", MODE_PRIVATE);
        if(setting.getBoolean("AUTO_ISCHECK", true))
        {
            setting.edit().putBoolean("AUTO_ISCHECK",false).commit();


        }
        _loginButton.setEnabled(false);



        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();

                    }
                }, 3000);
        String data = FindUserDB.executeQuery(_emailText.getText().toString(), _passwordText.getText().toString()); //check user have been register or not
        JSONArray jsonArray = new JSONArray(data);

        setting.edit().putString("loginEmail", _emailText.getText().toString()).commit();
        setting.edit().putBoolean("AUTO_ISCHECK",true).commit();

        JSONObject jsonData = jsonArray.getJSONObject(0);
        String username = jsonData.getString("name");
        String chatbot = CheckChatbotDB.executeQuery(jsonData.getInt("userID")).trim();
        Log.d("testing", chatbot);
        if(chatbot.equals("[{\"chatbotName\":\"\"}]")||chatbot.equals("[{\"chatbotName\":null}]")){
            //check user have create assistent or not

            Intent intent = new Intent(this,InitializeChatbotName.class);
            intent.putExtra("name",username);
            setting.edit().putInt("userID",jsonData.getInt("userID")).commit();
            setting.edit().putString("name",jsonData.getString("name")).commit();
            setting.edit().putInt("PhoneNumber",jsonData.getInt("PhoneNumber")).commit();
            setting.edit().putString("chatbotName",jsonData.getString("chatbotName")).commit();
            Register.executeQueryFunction(jsonData.getInt("userID"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("name",username);
            setting.edit().putInt("userID",jsonData.getInt("userID")).commit();
            setting.edit().putString("name",jsonData.getString("name")).commit();
            setting.edit().putInt("PhoneNumber",jsonData.getInt("PhoneNumber")).commit();
            setting.edit().putString("chatbotName",jsonData.getString("chatbotName")).commit();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }



    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        //finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        if(isConnectedToServer()){
            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _emailText.setError("enter a valid email address");
                valid = false;
            } else {
                _emailText.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                _passwordText.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                _passwordText.setError(null);
            }

            try {
                String data = FindUserDB.executeQuery(_emailText.getText().toString(), _passwordText.getText().toString());
                if(data.equals("[]\n")){
                    Msg("The email or Password incorrect");

                    valid=false;
                }else{

                    valid=true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            networkErrorMsg("");

        return valid;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    private void Msg(String Msg){

        AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);
        MyAlertDialog.setTitle("");
        MyAlertDialog.setMessage(Msg);
        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
            }
        };
        MyAlertDialog.setNegativeButton("OK",OkClick );
        MyAlertDialog.show();
    }

    public  boolean isConnectedToServer(){
        try{


            HttpPost httpPost = new HttpPost(gv.getServerHostName()+"/fyp/CheckUser.php");
            HttpParams httpParameters = new BasicHttpParams();
// Set the timeout in milliseconds until a connection is established.
// The default value is zero, that means the timeout is not used.
            int timeoutConnection = 3000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
// Set the default socket timeout (SO_TIMEOUT)
// in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 5000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            Log.d("Network","OK");
            isNetworkAvaliable = true;

        }
        catch (Exception e){
            Log.d("network","error");
            e.printStackTrace();
            Log.d("network","error");
            isNetworkAvaliable = false;
        }

        return isNetworkAvaliable;
    }
    private void networkErrorMsg(String Msg){

        AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);
        MyAlertDialog.setTitle("NETWORK_ERROR");
        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {

            }
        };
        MyAlertDialog.setNegativeButton("OK",OkClick );
        MyAlertDialog.show();
    }
}
