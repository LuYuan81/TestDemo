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
  
    /**��ϵ����ʾ����**/  
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
      
    /**�绰����**/  
    private static final int PHONES_NUMBER_INDEX = 1;  
      
    /**ͷ��ID**/  
    private static final int PHONES_PHOTO_ID_INDEX = 2;  
     
    /**��ϵ�˵�ID**/  
    private static final int PHONES_CONTACT_ID_INDEX = 3; 
    
    /**��ϵ������**/  
    private ArrayList<String> mContactsName = new ArrayList<String>();  
      
    /**��ϵ��ͷ��**/  
    private ArrayList<String> mContactsNumber = new ArrayList<String>();  
  
    /**��ϵ��ͷ��**/  
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

      
        // ��ȡ�ֻ���ϵ��  
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);  
      
      
        if (phoneCursor != null) {  
            while (phoneCursor.moveToNext()) {  
      
            //�õ��ֻ�����  
            	String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
            //���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��  
            	if (TextUtils.isEmpty(phoneNumber))  
            		continue;  
              
            //�õ���ϵ������  
            	String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  
              
            //�õ���ϵ��ID  
            	Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);  
      
            //�õ���ϵ��ͷ��ID  
            	Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);  
              
            //�õ���ϵ��ͷ��Bitamp  
/*            Bitmap contactPhoto = null;  
      
            //photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�  
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
