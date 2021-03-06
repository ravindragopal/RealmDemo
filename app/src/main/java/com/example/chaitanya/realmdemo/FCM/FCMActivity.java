package com.example.chaitanya.realmdemo.FCM;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chaitanya.realmdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.atomic.AtomicInteger;

public class FCMActivity extends AppCompatActivity {


    String TAG = FCMActivity.class.getSimpleName();
    Button subscribeButton, unsubscribeButton, sendUpstreamMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);

        subscribeButton = (Button) findViewById(R.id.subscribeButton);
        unsubscribeButton = (Button) findViewById(R.id.unsubscribeButton);
        sendUpstreamMsg = (Button) findViewById(R.id.sendUpstreamMsg);

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeTopic();
            }
        });

        unsubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsubscribeTopic();
            }
        });

        sendUpstreamMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpstream();
            }
        });

        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

    }

    private void sendUpstream() {

        /*FirebaseMessaging fm = FirebaseMessaging.getInstance();
        String to = "aUniqueKey"; // the notification key
        AtomicInteger msgId = new AtomicInteger();
        fm.send(new RemoteMessage.Builder(to)
                .setMessageId(""+msgId)
                .addData("hello", "world")
                .build());*/


        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        AtomicInteger msgId = new AtomicInteger();
        fm.send(new RemoteMessage.Builder("291113693012" + "@gcm.googleapis.com")
                .setMessageId(Integer.toString(msgId.incrementAndGet()))
                .addData("my_message", "Hello World")
                .addData("my_action", "SAY_HELLO")
                .build());
    }

    private void subscribeTopic() {

        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "msg_subscribed";
                        if (!task.isSuccessful()) {
                            msg = "msg_subscribe_failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(FCMActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void unsubscribeTopic() {

        FirebaseMessaging.getInstance().unsubscribeFromTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "msg_subscribed";
                        if (!task.isSuccessful()) {
                            msg = "msg_subscribe_failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(FCMActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void test() {

    }

}
