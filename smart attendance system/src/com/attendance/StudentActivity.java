package com.attendance;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.util.AttendanceAdapter;
import com.util.ClientConnectionBuilder;
import com.util.Student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class StudentActivity extends Activity{
	ListView studentListView;
	String semesterId, branchId, sectionId, subjectId;
	ArrayList<Student> studentArrayList;
	Button submitButton;
	private String hourId;
	private String dayId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentlayout);
		studentListView=(ListView) findViewById(R.id.studentList);
		submitButton=(Button) findViewById(R.id.submit);
		Intent intent=getIntent();
		semesterId=intent.getStringExtra("semid");
		branchId=intent.getStringExtra("bid");
		sectionId=intent.getStringExtra("secid");
		subjectId=intent.getStringExtra("subid");
		dayId=intent.getStringExtra("day");
		hourId=intent.getStringExtra("hid");
		String urlString="AttendanceManagement?action=info&bid="+branchId+"&semid="+semesterId+"&secid="+sectionId;
		String response=ClientConnectionBuilder.sendRequest(urlString);
		try {
			System.out.println(response);
			JSONArray studentJsonArray=new JSONArray(response);
			studentArrayList=new ArrayList<Student>();
			for (int i = 0; i < studentJsonArray.length(); i++) {
				Student student=new Student();
				student.setRollNo(studentJsonArray.getJSONObject(i).getString("rollno"));
				student.setName(studentJsonArray.getJSONObject(i).getString("name"));
				student.setStatus(false);
				studentArrayList.add(student);
				
			}
			
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AttendanceAdapter adapter=new AttendanceAdapter(this, R.layout.attendance_status, studentArrayList);
		studentListView.setAdapter(adapter);
		submitButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				try {
					final JSONArray studentJsonArray=new JSONArray();
					int presentCount=0;
					int absentCount=0;
					for (int i = 0; i < studentArrayList.size(); i++) {
						JSONObject studentJsonObject=new JSONObject();
						studentJsonObject. put("id",studentArrayList.get(i).getRollNo());
						
						if(studentArrayList.get(i).isStatus())
						{
							presentCount++;
							studentJsonObject.put("status","present");	
						}
						else
						{
							absentCount++;
							studentJsonObject.put("status","absent");	
						}
						studentJsonArray.put(studentJsonObject);
						
					}
					
					AlertDialog.Builder builder=new AlertDialog.Builder(StudentActivity.this);
					builder.setTitle("Attendance Information");
					builder.setMessage("Presenties is  "+presentCount+"\n Absenties is "+absentCount);
					builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							

							String urlString="AttendanceManagement?action=insert&bid="+branchId+"&semid="+semesterId+"&secid="+sectionId+"&day="+dayId+"&hid="+hourId+"&subid="+subjectId+"&lid="+Login.username+"&student="+URLEncoder.encode(studentJsonArray.toString());
							String response=ClientConnectionBuilder.sendRequest(urlString);
							Toast.makeText(StudentActivity.this, response, 3000).show();
							if("success".equalsIgnoreCase(response))
							{
								Intent intent=new Intent(StudentActivity.this,AttendanceActivity.class);
								startActivity(intent);
							}
							else
							{
								AlertDialog.Builder builder=new AlertDialog.Builder(StudentActivity.this);
								builder.setTitle("Notice");
								builder.setMessage(""+response);
								builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								});
								builder.show();
							}
							dialog.dismiss();
							
						}
					});
					
					builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					
					builder.show();
					
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				
				
				
			}
		});
		
	}

}
