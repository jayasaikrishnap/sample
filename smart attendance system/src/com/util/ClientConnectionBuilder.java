package com.util;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Build;
import android.os.StrictMode;



public class ClientConnectionBuilder {
	
	static String serverUrl="http://172.16.1.61:8086/AttendanceManagementServer/";//10.0.2.2
	public static String sendRequest(String urlString)
	{
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(tp);
        }
		try {
			urlString=serverUrl+urlString;
			URL url=new URL(urlString);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			InputStream  is=connection.getInputStream();
			int i=0;
			StringBuilder completeResponse=new StringBuilder();
			while ((i=is.read())!=-1) {
				completeResponse.append((char)i);
				
			}
			System.out.println(completeResponse);
			return completeResponse.toString();
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlString;
		
	}
	
	
	

}
