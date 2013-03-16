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

import java.util.ArrayList;
import android.provider.ContactsContract.CommonDataKinds.Phone; 
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.content.ContentResolver;  
import android.content.ContentUris;  
import android.content.Context; 
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri; 
import android.text.TextUtils;

public class MainActivity extends Activity {
	
	private Button BtnSendMsg;
	private EditText EdtMsgContent;
	private EditText EdtMsgNumber;
	
	private Button BtnOpenActivity;
	private Intent intent;

	Context mContext = null;
	private static final String[] PHONES_PROJECTION = new String[] {Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID }; 
  
    /**联系人显示名称**/  
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
      
    /**电话号码**/  
    private static final int PHONES_NUMBER_INDEX = 1;  
      
    /**头像ID**/  
    private static final int PHONES_PHOTO_ID_INDEX = 2;  
     
    /**联系人的ID**/  
    private static final int PHONES_CONTACT_ID_INDEX = 3; 
    
    /**联系人名称**/  
    private ArrayList<String> mContactsName = new ArrayList<String>();  
      
    /**联系人头像**/  
    private ArrayList<String> mContactsNumber = new ArrayList<String>();  
  
    /**联系人头像**/  
    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnSendMsg = (Button) findViewById(R.id.Send);
        EdtMsgContent = (EditText) findViewById(R.id.editText3);
        EdtMsgNumber = (EditText) findViewById(R.id.editText2);
        
        BtnOpenActivity = (Button) findViewById(R.id.button1);
       
        //Send message
        BtnSendMsg.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View arg0) {
        		String Msg = EdtMsgContent.getText().toString();
        		String Number = EdtMsgNumber.getText().toString();
        		smsSender(Number, Msg);
        	}
        });
        
        
        // Open Phone book activity
        
        
        
        intent = new Intent(this, TestPhoneBook.class);
        BtnOpenActivity.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View arg0) {
        		getPhoneContacts();
        		startActivity(intent);

        	}
        });
    }  
     
    private void getPhoneContacts() {  
        ContentResolver resolver = mContext.getContentResolver();  

      
        // 获取手机联系人  
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);  
      
      
        if (phoneCursor != null) {  
            while (phoneCursor.moveToNext()) {  
      
            //得到手机号码  
            	String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
            //当手机号码为空的或者为空字段 跳过当前循环  
            	if (TextUtils.isEmpty(phoneNumber))  
            		continue;  
              
            //得到联系人名称  
            	String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  
              
            //得到联系人ID  
            	Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);  
      
            //得到联系人头像ID  
            	Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);  
              
            //得到联系人头像Bitamp  
/*            Bitmap contactPhoto = null;  
      
            //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的  
            if(photoid > 0 ) {  
                Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);  
                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);  
                contactPhoto = BitmapFactory.decodeStream(input);  
            }else {  
                contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.contact_photo);  
            }  
*/              
            	mContactsName.add(contactName);  
            	mContactsNumber.add(phoneNumber);  
 //           	mContactsPhonto.add(contactPhoto);  
            }  
      
            phoneCursor.close();  
        }  
    }
 

    private void smsSender(String phone, String message) {
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
