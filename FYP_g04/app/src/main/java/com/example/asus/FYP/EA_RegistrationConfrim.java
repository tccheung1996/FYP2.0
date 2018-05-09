package com.example.asus.FYP;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import static java.lang.System.exit;

public class EA_RegistrationConfrim extends AppCompatActivity {
    TextView tv;
    Button btn;
    public String command1,command2,command3,command4,command5,command6,command7,command8,command9,command10,command11,command12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ea__registration_confrim);

        tv = findViewById(R.id.tv_confirm);
        btn = findViewById(R.id.btn_confirm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("指令確定");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);
        Intent intent = this.getIntent();
        command1 = intent.getStringExtra("command1");
        command2 = intent.getStringExtra("command2");
        command3 = intent.getStringExtra("command3");
        command4 = intent.getStringExtra("command4");
        command5 = intent.getStringExtra("command5");
        command6 = intent.getStringExtra("command6");
        command7 = intent.getStringExtra("command7");
        command8 = intent.getStringExtra("command8");
        command9 = intent.getStringExtra("command9");
        command10 = intent.getStringExtra("command10");
        command11 = intent.getStringExtra("command11");
        command12 = intent.getStringExtra("command12");

        GlobalVir gv = (GlobalVir)getApplicationContext();
        tv.setText("名稱:"+gv.EA_name+"\n"+
                    "電源指令:"+command1+"\n"+
                "聲量加指令:"+command2+"\n"+
                "聲量減指令:"+command3+"\n"+
                "頻道加指令:"+command4+"\n"+
                "頻道減指令:"+command5+"\n"+
                "方向上指令:"+command6+"\n"+
                "方向下指令:"+command7+"\n"+
                "方向左指令:"+command8+"\n"+
                "方向右指令:"+command9+"\n"+
                "Enter指令:"+command10+"\n"+
                "播放指令:"+command11+"\n"+
                "暫停指令:"+command12+"\n");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity


                final ProgressDialog progressDialog = new ProgressDialog(EA_RegistrationConfrim.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("loading");
                progressDialog.show();

                // TODO: Implement your own authentication logic here.

                new Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                GlobalVir gv = (GlobalVir) EA_RegistrationConfrim.this.getApplicationContext();
                                EA_RegistrationDB.executeQuery(gv.EA_name,gv.userID,gv.EAID,command1,command2,command3,command4,command5,command6,command7,command8,command9,
                                        command10,command11,command12);

                                Intent intent = new Intent(EA_RegistrationConfrim.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        }, 3000);

            }
        });
    }
}
