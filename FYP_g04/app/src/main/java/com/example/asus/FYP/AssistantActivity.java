package com.example.asus.FYP;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.hardware.ConsumerIrManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.asus.myapplication.R;
import com.google.gson.JsonElement;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;


public class AssistantActivity extends AppCompatActivity implements AIListener{
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;
    public AIService aiService;

    private TextView tv;
    private TextView tv2;
    private boolean isError=false;
    private PulsatorLayout mPulsator;
    private ImageView iv;
    boolean isMine = true;
    private ListView listView;
    private ChatMessage chatMessage;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;
    private TextToSpeech tts;

    MediaPlayer mp = new MediaPlayer();

    public ArrayList<String> command1 = new ArrayList<String>();
    public ArrayList<String> command2 = new ArrayList<String>();
    public ArrayList<String> command3 = new ArrayList<String>();
    public ArrayList<String> command4 = new ArrayList<String>();
    public ArrayList<String> command5 = new ArrayList<String>();
    public ArrayList<String> command6 = new ArrayList<String>();
    public ArrayList<String> command7 = new ArrayList<String>();
    public ArrayList<String> command8 = new ArrayList<String>();
    public ArrayList<String> command9 = new ArrayList<String>();
    public ArrayList<String> command10 = new ArrayList<String>();
    public ArrayList<String> command11 = new ArrayList<String>();
    public ArrayList<String> command12 = new ArrayList<String>();
    public ArrayList<String> ir1= new ArrayList<String>();
    public ArrayList<String> ir2= new ArrayList<String>();
    public ArrayList<String> ir3= new ArrayList<String>();
    public ArrayList<String> ir4= new ArrayList<String>();
    public ArrayList<String> ir5= new ArrayList<String>();
    public ArrayList<String> ir6= new ArrayList<String>();
    public ArrayList<String> ir7= new ArrayList<String>();
    public ArrayList<String> ir8= new ArrayList<String>();
    public ArrayList<String> ir9= new ArrayList<String>();
    public ArrayList<String> ir10= new ArrayList<String>();
    public ArrayList<String> ir11= new ArrayList<String>();
    public ArrayList<String> ir12= new ArrayList<String>();
    public ArrayList<String> registeredEAName= new ArrayList<String>();
    public ArrayList<Integer> EAID = new ArrayList<Integer>();
    private String sendIR="";
    public String speech;


    private ConsumerIrManager irManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);

        tv2 = findViewById(R.id.textView3);
        iv = findViewById(R.id.image1);
        chatMessages = new ArrayList<>();
        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        irManager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);
        initTTS();






        /*bt = getView().findViewById(R.id.button11);
        getView().findViewById(R.id.button11).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenButtonOnClick();
            }
        });
            */ //kepp this  code for none speech
        listView = findViewById(R.id.list_msg);
        adapter = new MessageAdapter(this, R.layout.item_chat_left, chatMessages);
        listView.setAdapter(adapter);
        mPulsator = (PulsatorLayout)findViewById(R.id.pulsator);


        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            public void run() {
                if (isError){
                    try {
                        Thread.sleep(200);  //1000ms = 1 sec

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    aiService.startListening();

                    isError=false;
                }
                handler.postDelayed(this, 1000);
            }
        };
        // trigger first time
        handler.post(runnable);
        openBeep();
        mPulsator.start();
        initApiAi();
    }

    @Override
    protected void onPause() {

        super.onPause();
        openBeep();

        mp.release();
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
        openBeep();

        mp.release();
        finish();
    }
    @Override
    protected void onStop() {

        super.onStop();
        openBeep();

        mp.release();
        finish();

    }

    @Override
    public void onResult(AIResponse response) {
        final Result AIresult = response.getResult();
        GlobalVir gv = (GlobalVir)getApplicationContext();
        // Get parameters
        Log.d("chatbotname", gv.chatbotName);
        String parameterString = "";
        if (AIresult.getParameters() != null && !AIresult.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : AIresult.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
        }
        Log.d("testing", AIresult.getResolvedQuery());

        if (AIresult.getResolvedQuery().substring(0).toLowerCase().contains(gv.chatbotName)) {
            openBeep();
            chatMessage = new ChatMessage(AIresult.getResolvedQuery(), isMine);
            chatMessages.add(chatMessage);
            if(AIresult.getResolvedQuery().contains("今日天氣")){
                try {
                    isMine = false;
                    chatMessage = new ChatMessage(checkWeather(), isMine);
                    speech = checkWeather();
                    chatMessages.add(chatMessage);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            else if(checkCommand(AIresult.getResolvedQuery().toLowerCase())){
                IRCommand cmd = hex2ir(gv.sendIR);
                isMine = false;
                chatMessage = new ChatMessage("冇問題", isMine);
                speech = "冇問題";
                android.util.Log.d("Remote", "frequency: " + cmd.freq);
                android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                chatMessages.add(chatMessage);
                adapter.notifyDataSetChanged();
                irManager.transmit(cmd.freq, cmd.pattern);
            }
            else if(AIresult.getResolvedQuery().contains("今日恆生指數")){

                isMine = false;
                try {
                    chatMessage = new ChatMessage(checkStock(), isMine);
                    chatMessages.add(chatMessage);
                    adapter.notifyDataSetChanged();
                    speech = checkStock();
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
            else{
                isMine = false;
                chatMessage = new ChatMessage(AIresult.getFulfillment().getSpeech(), isMine);
                chatMessages.add(chatMessage);
                speech = AIresult.getFulfillment().getSpeech();
                openBeep();
            }


            iv.setImageResource(R.drawable.ic_mic_white);


            tts.speak(speech,TextToSpeech.QUEUE_FLUSH, null);

            adapter.notifyDataSetChanged();
            aiService.startListening();
        } else if (AIresult.getResolvedQuery().equals("關閉")) {

            Thread.interrupted();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else{
            aiService.startListening();
            closeBeep();
        }



    }






    public void closeBeep(){
        AudioManager amanager=(AudioManager)this.getSystemService(this.AUDIO_SERVICE);

        amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        Log.d("qweasdzxc", "closeBeep: ");
    }

    public void openBeep(){
        AudioManager amanager=(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

        Log.d("asdqwe", "openBeep: ");
        amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);

    }

    public void initApiAi() {

        try{

            final AIConfiguration config = new AIConfiguration("e399e82324ad49139934e931eb67e829",
                    AIConfiguration.SupportedLanguages.ChineseHongKong,
                    AIConfiguration.RecognitionEngine.System);
            aiService = AIService.getService(this, config);
            aiService.setListener(this);
            aiService.startListening();
            Log.d("check1", "onResult:listening ");

        }
        catch (Exception e){
            Thread.interrupted();
        }

    }

    @Override
    public void onError(final AIError error) {
        isError=true;
        closeBeep();
        Log.d("check", error.getMessage());

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
    private void initTTS() {

        if (tts == null) {
            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int arg0) {
                    // TTS 初始化成功
                    if (arg0 == TextToSpeech.SUCCESS) {
                        // 指定的語系: 英文(美國)
                        Locale l = new Locale("yue", "HK");  // 不要用 Locale.ENGLISH, 會預設用英文(印度)
                        int  result = tts.setLanguage(	Locale.	TRADITIONAL_CHINESE	);
                        String s = null;
                        if (result == TextToSpeech.LANG_MISSING_DATA ||result == TextToSpeech.LANG_NOT_SUPPORTED) {s +=	 "	TRADITIONAL_CHINESE	 not supported<br>"	;}else{s+="	TRADITIONAL_CHINESE	 supported<br>";}
                        // 目前指定的【語系+國家】TTS, 已下載離線語音檔, 可以離線發音
                        Log.d("checking", s);
                        if (tts.isLanguageAvailable(l) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                            tts.setLanguage(l);
                        }
                    }
                }
            }
            );
        }
    }
    private IRCommand hex2ir(String irData) {
        List<String> list = new ArrayList<String>(Arrays.asList(irData.split(" ")));
        list.remove(0); // dummy
        Log.d("check", list.size()+"");
        int frequency = Integer.parseInt(list.remove(0), 16); // frequency
        list.remove(0); // seq1
        list.remove(0); // seq2

        frequency = (int) (1000000 / (frequency * 0.241246));
        int pulses = 1000000 / frequency;
        int count;

        int[] pattern = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            count = Integer.parseInt(list.get(i), 16);
            pattern[i] = count * pulses;
        }

        return new IRCommand(/*frequency*/38400, pattern);
    }

    private class IRCommand {
        private final int freq;
        private final int[] pattern;

        private IRCommand(int freq, int[] pattern) {
            this.freq = freq;
            this.pattern = pattern;
        }
    }
    public void getData() throws JSONException {

        SharedPreferences setting = getSharedPreferences("atm", MODE_PRIVATE);

        String data = getCommandDB.executeQuery(setting.getInt("userID",0));
        JSONArray jsonArray = new JSONArray(data);
        Log.d("json", data);
        for(int i =0 ; i<jsonArray.length();i++){
            JSONObject jsonData = jsonArray.getJSONObject(i);
            if(jsonData.getString("command1").equals("")){
                command1.add("654123");
            }
            else{
                command1.add(jsonData.getString("command1"));
            }
            if(jsonData.getString("command2").equals("")){
                command2.add("653241");
            }
            else{
                command2.add(jsonData.getString("command2"));
            }
            if(jsonData.getString("command3").equals("")){
                command3.add("986542");
            }
            else{
                command3.add(jsonData.getString("command3"));
            }
            if(jsonData.getString("command4").equals("")){
                command4.add("9865421++++");
            }
            else{
                command4.add(jsonData.getString("command4"));
            }
            if(jsonData.getString("command5").equals("")){
                command5.add("75445678");
            }
            else{
                command5.add(jsonData.getString("command5"));
            }
            if(jsonData.getString("command6").equals("")){
                command6.add("86545");
            }
            else{
                command6.add(jsonData.getString("command6"));
            }
            if(jsonData.getString("command7").equals("")){
                command7.add("854364");
            }
            else{
                command7.add(jsonData.getString("command7"));
            }
            if(jsonData.getString("command8").equals("")){
                command8.add("786432");
            }
            else{
                command8.add(jsonData.getString("command8"));
            }
            if(jsonData.getString("command9").equals("")){
                command9.add("78564");
            }
            else{
                command9.add(jsonData.getString("command9"));
            }
            if(jsonData.getString("command10").equals("")){
                command10.add("785634");
            }
            else{
                command10.add(jsonData.getString("command10"));
            }
            if(jsonData.getString("command11").equals("")){
                command11.add("785626524521");
            }
            else{
                command11.add(jsonData.getString("command11"));
            }
            if(jsonData.getString("command12").equals("")){
                command12.add("78534514521496+");
            }
            else{
                command12.add(jsonData.getString("command12"));
            }
            registeredEAName.add(jsonData.getString("name"));
            EAID.add(jsonData.getInt("EAID"));
            if(jsonData.getInt("EAID")==15 ||jsonData.getInt("EAID")==16){
                String data2 = getCommandDB.executeQueryManualVG((jsonData.getInt("EAID")));
                JSONArray jsonArray2 = new JSONArray(data2);
                JSONObject jsonData2 = jsonArray2.getJSONObject(0);
                ir1.add(jsonData2.getString("Ir_power"));
                ir2.add(jsonData2.getString("Ir_vol+"));
                ir3.add(jsonData2.getString("Ir_vol-"));
                ir4.add(jsonData2.getString("Ir_ch+"));
                ir5.add(jsonData2.getString("Ir_ch-"));
                ir6.add(jsonData2.getString("Ir_up"));
                ir7.add(jsonData2.getString("Ir_down"));
                ir8.add(jsonData2.getString("Ir_left"));
                ir9.add(jsonData2.getString("Ir_right"));
                ir10.add(jsonData2.getString("Ir_enter"));
                ir11.add(jsonData2.getString("Ir_play"));
                ir12.add(jsonData2.getString("Ir_pause"));
            }
            else{
                String data2 = getCommandDB.executeQueryManualTV(jsonData.getInt("EAID"));
                JSONArray jsonArray2 = new JSONArray(data2);
                JSONObject jsonData2 = jsonArray2.getJSONObject(0);
                ir1.add(jsonData2.getString("Ir_power"));
                ir2.add(jsonData2.getString("Ir_vol+"));
                ir3.add(jsonData2.getString("Ir_vol-"));
                ir4.add(jsonData2.getString("Ir_ch+"));
                ir5.add(jsonData2.getString("Ir_ch-"));
                ir6.add(jsonData2.getString("Ir_up"));
                ir7.add(jsonData2.getString("Ir_down"));
                ir8.add(jsonData2.getString("Ir_left"));
                ir9.add(jsonData2.getString("Ir_right"));
                ir10.add(jsonData2.getString("Ir_enter"));
                ir11.add(jsonData2.getString("Ir_play"));
                ir12.add(jsonData2.getString("Ir_pause"));
            }

        }

    }
    public boolean checkCommand(String request){
        boolean isTrue = false;
        GlobalVir gv = (GlobalVir)getApplicationContext();
        for(int i=0 ; i<registeredEAName.size();i++){
            if(request.contains(registeredEAName.get(i).toLowerCase())){
                if(request.contains(command1.get(i))){
                    gv.sendIR = ir1.get(i);
                    Log.d("irTesting", sendIR);
                    Log.d("check", "command1");
                    isTrue=true;
                }
                else if(request.contains(command2.get(i))){
                    gv.sendIR = ir2.get(i);
                    Log.d("irTesting2", sendIR);
                    Log.d("check", "command2");
                    isTrue=true;
                }
                else if(request.contains(command3.get(i))){
                    sendIR = ir3.get(i);
                    Log.d("check", "command3");
                    isTrue=true;
                }
                else if(request.contains(command4.get(i))){
                    sendIR = ir4.get(i);
                    Log.d("check", "command4");
                    isTrue=true;
                }
                else if(request.contains(command5.get(i))){
                    sendIR = ir5.get(i);
                    Log.d("check", "command5");
                    isTrue=true;
                }
                else if(request.contains(command6.get(i))){
                    sendIR = ir6.get(i);
                    Log.d("check", "command6");
                    isTrue=true;
                }
                else if(request.contains(command7.get(i))){
                    sendIR = ir7.get(i);
                    Log.d("check", "command7");
                    isTrue=true;
                }
                else if(request.contains(command8.get(i))){
                    sendIR = ir8.get(i);
                    Log.d("check", "command8");
                    isTrue=true;
                }
                else if(request.contains(command9.get(i))){
                    sendIR = ir9.get(i);
                    Log.d("check", "command9");
                    isTrue=true;
                }
                else if(request.contains(command10.get(i))){
                    sendIR = ir10.get(i);
                    Log.d("check", "command10");
                    isTrue=true;
                }
                else if(request.contains(command11.get(i))){
                    sendIR = ir11.get(i);
                    Log.d("check", "command11");
                    isTrue=true;
                }
                else if(request.contains(command12.get(i))){
                    sendIR = ir12.get(i);
                    Log.d("check", "command12");
                    isTrue=true;
                }
                else{
                    isTrue=false;
                }
            }
        }
        return isTrue;
    }
    public String checkWeather() throws JSONException{
        String weather;
        String data = WeatherJson.executeQuery();
        JSONObject json =  new JSONObject(data);

        String data2 = json.getString("HeWeather6");
        JSONArray array = new JSONArray(data2);
        JSONObject json2 = array.getJSONObject(0);
        String data4 = json2.getString("now");
        JSONObject json3 = new JSONObject(data4);


        String tmp =  json3.getString("tmp");
        String cond_code  = json3.getString("cond_txt");
        String wind_dir = json3.getString("wind_dir");
        String hum = json3.getString("hum");
        String pcpn = json3.getString("pcpn");

        weather= "現時香港天氣" + tmp +"度, 天氣"+cond_code+", 相對濕度為百分之"+hum+
                ", 吹"+wind_dir + ", 降水量為" + pcpn;

        return weather;
    }
    public String checkStock() throws JSONException{
        String HSI;
        String data = StockHSI.checkStock();
        JSONArray array = new JSONArray(data);
        JSONObject json1 = array.getJSONObject(0);
        String data1 = json1.getString("last");

        HSI = "現時恆生指數是"+ data1;
        return HSI;
    }
    static class WeatherJson {
        public static String executeQuery() {

            String result = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("https://free-api.heweather.com/s6/weather/now?key=5bcb66f3be3c4842a7ff4dba1b1b8973&location=hongkong&lang=hk");
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();

                BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                inputStream.close();
                result = builder.toString();
            } catch (Exception e) {
            }
            return result;
        }
    }
}
