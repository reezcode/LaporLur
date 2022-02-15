package com.reaz.laporlur;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Ceunah extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    FrameLayout saybtn;
    CardView botbox, userbox;
    TextView chatbot, usersay;
    Animation fromside, fromside2, fromleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceunah);
        saybtn = findViewById(R.id.saybtn);
        botbox = findViewById(R.id.botbox);
        userbox = findViewById(R.id.userbox);
        chatbot = findViewById(R.id.chatbot_txt);
        usersay = findViewById(R.id.usersay_txt);
        fromside = AnimationUtils.loadAnimation(this,R.anim.fromside);
        fromside2 = AnimationUtils.loadAnimation(this,R.anim.fromside2);
        fromleft = AnimationUtils.loadAnimation(this,R.anim.fromside3);
        userbox.setAnimation(fromside);
        saybtn.setAnimation(fromside2);
        botbox.setAnimation(fromleft);
        saybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

    }

    private void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bicaralah Sesuatu");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Maaf, perangkatmu tidak mendukung Ceunah",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String userQuery=result.get(0);
                    usersay.setText("Kamu : " + userQuery);
                    RetrieveFeedTask retrieveFeedTask = new RetrieveFeedTask();
                    retrieveFeedTask.execute(userQuery);

                }
                break;
            }
        }
    }

    public String GetText (String query) throws UnsupportedEncodingException {

        String text = "";
        BufferedReader reader = null;

        try {

            URL url = new URL("https://api.dialogflow.com/v1/query?v=20150910");

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Authorization", "Bearer a94b49c4a00540bb8312eceede272dce");
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonParam = new JSONObject();
            JSONArray queryArray = new JSONArray();
            queryArray.put(query);
            jsonParam.put("query", queryArray);
            jsonParam.put("lang", "id");
            jsonParam.put("sessionId", "1234567890");

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            Log.d("karma", "after conversion is" + jsonParam.toString());
            wr.write(jsonParam.toString());
            wr.flush();
            Log.d("karma", "json is" + jsonParam);

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");

            }

            text = sb.toString();
            JSONObject object1 = new JSONObject(text);
            JSONObject object = object1.getJSONObject("result");
            JSONObject fulfillment = null;
            String speech = null;

            fulfillment = object.getJSONObject("fulfillment");

            speech = fulfillment.optString("speech");

            Log.d("karma", "response is" + text);
            return speech;
        } catch (Exception ex) {
            Log.d("karma", "exception at last" + ex);

        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
            }
        }
        return null;
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... voids) {
            String s = null;

            try {
                s = GetText(voids[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d("karma", "exception occured" + e);
            }

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            chatbot.setText("Ceunah : " + s);
        }
    }
}
