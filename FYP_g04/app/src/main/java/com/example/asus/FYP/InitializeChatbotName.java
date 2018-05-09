package com.example.asus.FYP;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

public class InitializeChatbotName extends AppCompatActivity {
private ImageView iv;
private EditText et;
private Button btn_chatbot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_chatbot_name);

        iv = findViewById(R.id.imageView2);
        iv.setImageResource(R.drawable.initchatbot);

        et = findViewById(R.id.et1);
        btn_chatbot = findViewById(R.id.btn_chatbot);

        String chatBotName = et.getText().toString();


        btn_chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                String chatBotName = et.getText().toString();
                SharedPreferences setting = getSharedPreferences("atm", MODE_PRIVATE);
                int userID = setting.getInt("userID",0);
                UpdateChatBotName.executeQuery(userID,chatBotName);
                setting.edit().putString("chatbotName",chatBotName).commit();
                Intent intent = new Intent(InitializeChatbotName.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
