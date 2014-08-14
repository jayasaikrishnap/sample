package com.attendance;

import java.net.URLEncoder;

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

public class Login extends Activity{
	EditText lecturer_idEditText,passwordEditText;
	Button loginButton;
	TextView forgotPasswordTextView;
	static String username;
	String pwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		init();
		
		forgotPasswordTextView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(Login.this,"Plz enter valid password or username",3000).show();
				Intent intent=new Intent(Login.this,ForgotPasswordActivity.class);
				startActivity(intent);
			}
			
		});
		
		
		
		loginButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				collectingData();
				if(validation())
				{
				setUrlEncoding();
				String urlString="ProfileManagement?action=login&username="+username+"&password="+pwd;
				String response=ClientConnectionBuilder.sendRequest(urlString);
				if(response.equalsIgnoreCase("valid"))
				{
					Intent intent=new Intent(Login.this,HomeTabActivity.class);
					startActivity(intent);
				}else {
					Toast.makeText(Login.this,"Plz enter valid password or username",3000).show();
				}
				
				}
				
				
			}
		});
		
		
		
	}
	protected void setUrlEncoding() {
		username=URLEncoder.encode(username);
		pwd=URLEncoder.encode(pwd);	
	}
	protected boolean validation() {
		boolean flag=true;
		if(username.length()==0)
		{
			flag=false;
			lecturer_idEditText.setError("plz enter username");
		}
		if(pwd.length()==0)
		{
			flag=false;
			passwordEditText.setError("plz enter password");
		}
		return flag;
	}
	protected void collectingData() {
		username=lecturer_idEditText.getText().toString();
		pwd=passwordEditText.getText().toString();
		
	}
	private void init() {
		lecturer_idEditText=(EditText) findViewById(R.id.lecturer_id);
		passwordEditText=(EditText) findViewById(R.id.password);
		loginButton=(Button) findViewById(R.id.login);
		forgotPasswordTextView=(TextView) findViewById(R.id.forgot_password);
	}
	
}
