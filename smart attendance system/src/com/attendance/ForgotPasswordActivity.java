package com.attendance;

import java.net.URLEncoder;

import com.util.ClientConnectionBuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordActivity extends Activity
{
	EditText lecturer_id,phonenumber;
	Button submitButton;
	String username,phnum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpasswordlayout);
		init();
		submitButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				collectingData();
				if(validation())
				{
				setUrlEncoding();
				String urlString="ProfileManagement?action=forgotpassword&username="+username+"&phonenumber="+phnum;
				String response=ClientConnectionBuilder.sendRequest(urlString);
				if(!response.equalsIgnoreCase("try again"))
				{
					AlertDialog.Builder alertDialog=new AlertDialog.Builder(ForgotPasswordActivity.this);
					
					alertDialog.setTitle("Forgot Password");
					alertDialog.setMessage("Your password is "+response);
					alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					alertDialog.show();
					Intent intent=new Intent(ForgotPasswordActivity.this,Login.class);
					startActivity(intent);
				}else {
					Toast.makeText(ForgotPasswordActivity.this,"Plz enter valid password or username",3000).show();
				}
				
				}
			}
		});
	}
	protected void setUrlEncoding() {
		// TODO Auto-generated method stub
		username=URLEncoder.encode(username);
		phnum=URLEncoder.encode(phnum);	
		
	}
	protected boolean validation() {
		// TODO Auto-generated method stub
		boolean flag=true;
		if(username.length()==0)
		{
			flag=false;
			lecturer_id.setError("plz enter username");
		}
		if(phnum.length()==0&&phnum.length()>10&&phnum.length()<10)
		{
			flag=false;
			phonenumber.setError("plz enter valid phnumber");
		}
		
		return flag;
	}
	protected void collectingData() {
		// TODO Auto-generated method stub
		username=lecturer_id.getText().toString();
		phnum=phonenumber.getText().toString();
	}
	private void init() {
		// TODO Auto-generated method stub
		lecturer_id=(EditText) findViewById(R.id.lecturer_id);
		phonenumber=(EditText) findViewById(R.id.phonenumber);
		submitButton=(Button) findViewById(R.id.submit);
	}

	
}
