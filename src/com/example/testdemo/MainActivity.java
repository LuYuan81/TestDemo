package com.example.testdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.view.View;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.app.PendingIntent;
import android.util.Log;

public class MainActivity extends Activity {
	
	private Button BtnSendMsg;
	private EditText EdtMsgContent;
	private EditText EdtMsgNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnSendMsg = (Button) findViewById(R.id.Send);
        EdtMsgContent = (EditText) findViewById(R.id.editText3);
        EdtMsgNumber = (EditText) findViewById(R.id.editText2);
       
        BtnSendMsg.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View arg0) {
        		String Msg = EdtMsgContent.getText().toString();
        		String Number = EdtMsgNumber.getText().toString();
        		smsSender(Number, Msg);
        	}
        });
       
    }

    void smsSender(String phone, String message) {
    	SmsManager sms = SmsManager.getDefault();
    	//PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
    	try {
    		sms.sendTextMessage(phone, null, message, null, null);
    	}catch(Exception e){
    		Log.e("SmsSending","SendException", e );
    	}

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
