package com.example.testdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;


public class TestPhoneBook extends Activity{
	private TextView test_TextView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testphonebook);
		
		test_TextView = (TextView)findViewById(R.id.textView1);
		Bundle bundle = getIntent().getExtras();   //得到传过来的bundle  
        String data = bundle.getString("Data");//读出数据    
        
        test_TextView.setText(data);
	}
}


