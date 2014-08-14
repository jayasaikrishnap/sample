package com.attendance;

import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.util.ClientConnectionBuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfileActivity extends Activity {
	EditText oldpasswordEditText,newpasswordEditText,confirmpasswordEditText;
	Button submitButton;
	
	TextView userNameTextView;
	String username,oldpassword,newpassword,confirmpassword,mypassword;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_layout);
		init();
		
		
		String urlString="ProfileManagement?action=view&username="+Login.username;
		String response=ClientConnectionBuilder.sendRequest(urlString);
		try {
			JSONObject profileObject=new JSONObject(response);
			
			username=profileObject.getString("name");
			mypassword=profileObject.getString("password");
			userNameTextView.setText(username);
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				collectingData();
				if(validation())
				{
				setUrlEncoding();
				String urlString="ProfileManagement?action=changepassword&username="+Login.username+"&oldpwd="+oldpassword+"&newpwd="+newpassword;
				String response=ClientConnectionBuilder.sendRequest(urlString);
				if(response.equalsIgnoreCase("password updated"))
				{
					Intent intent=new Intent(MyProfileActivity.this,AttendanceActivity.class);
					startActivity(intent);
				}else {
					Toast.makeText(MyProfileActivity.this,"Plz enter valid password or username",3000).show();
				}
				}
				
				
			}
		});
	}
	


	protected void setUrlEncoding() {
		// TODO Auto-generated method stub
		username=URLEncoder.encode(username);
		oldpassword=URLEncoder.encode(oldpassword);
		newpassword=URLEncoder.encode(newpassword);
		confirmpassword=URLEncoder.encode(confirmpassword);
		
		
	}



	protected boolean validation() {
		// TODO Auto-generated method stub
		boolean flag=true;
		if(oldpassword.length()==0)
		{
			flag=false;
			oldpasswordEditText.setError("plz enter password");
		}
		if(newpassword.length()==0)
		{
			flag=false;
			newpasswordEditText.setError("plz enter password");
		}
		if(confirmpassword.length()==0)
		{
			flag=false;
			confirmpasswordEditText.setError("plz enter password");
		}
		if(!mypassword.equals(oldpassword))
		{
			oldpasswordEditText.setText("");
			oldpasswordEditText.setError("plll..............");
		}
		if(!confirmpassword.equals(newpassword))
		{
			flag=false;
			confirmpasswordEditText.setText("");
			newpasswordEditText.setText("");
			confirmpasswordEditText.setError("please check password");
			newpasswordEditText.setError("please check password");
		}
		
		return flag;
		
	}



	protected void collectingData() {
		// TODO Auto-generated method stub
		username=userNameTextView.getText().toString();
		oldpassword=oldpasswordEditText.getText().toString();
		newpassword=newpasswordEditText.getText().toString();
		confirmpassword=confirmpasswordEditText.getText().toString();
		
	}



	private void init() {
		// TODO Auto-generated method stub
		userNameTextView=(TextView) findViewById(R.id.usernametv);
		oldpasswordEditText=(EditText) findViewById(R.id.oldpwdedt);
		newpasswordEditText=(EditText) findViewById(R.id.newpwdedt);
		confirmpasswordEditText=(EditText) findViewById(R.id.cpwdedt);
		submitButton=(Button) findViewById(R.id.submitbtn);
		
	}

}
