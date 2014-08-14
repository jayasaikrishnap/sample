package com.attendance;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import com.util.ClientConnectionBuilder;
import com.util.HoursAdapter;
import com.util.Student;
import com.util.TimeTable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HoursActivity extends Activity{
	ListView hourListView;
	private String semesterId;
	private String branchId;
	private String sectionId;
	private String subjectId;
	private String dayId;
	private ArrayList<TimeTable> TimeTableArrayList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_layout);
		hourListView=(ListView) findViewById(R.id.listView1);
		Intent intent=getIntent();
		semesterId=intent.getStringExtra("semid");
		branchId=intent.getStringExtra("bid");
		sectionId=intent.getStringExtra("secid");
		//subjectId=intent.getStringExtra("subid");
		dayId=intent.getStringExtra("day");
		
		String urlString="AttendanceManagement?action=gethours&bid="+branchId+"&semid="+semesterId+"&secid="+sectionId+"&day="+dayId;
		String response=ClientConnectionBuilder.sendRequest(urlString);
		try {
			System.out.println(response);
			JSONArray TimeTableJsonArray=new JSONArray(response);
			TimeTableArrayList=new ArrayList<TimeTable>();
			Map sortingHours=new TreeMap<Integer, TimeTable>();
			for (int i = 0; i < TimeTableJsonArray.length(); i++) {
				TimeTable timeTable=new TimeTable();
				timeTable.setHour(TimeTableJsonArray.getJSONObject(i).getString("hid"));
				timeTable.setSubjectId(TimeTableJsonArray.getJSONObject(i).getString("subid"));
				timeTable.setSubjectName(TimeTableJsonArray.getJSONObject(i).getString("subname"));
				
				sortingHours.put(Integer.parseInt(TimeTableJsonArray.getJSONObject(i).getString("hid")),timeTable);
				
				
				
			}
			
			Set<Integer> keys=sortingHours.keySet();
			for (Integer key:keys) {
				TimeTableArrayList.add((TimeTable)sortingHours.get(key));
			}
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HoursAdapter a=new HoursAdapter(getApplicationContext(), R.layout.hour_subject_layout, TimeTableArrayList);
		hourListView.setAdapter(a);
		
		hourListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, final int index,
					long arg3) {
				
				
				AlertDialog.Builder builder=new AlertDialog.Builder(HoursActivity.this);
				builder.setTitle("Selection");
				builder.setMessage("choose");
				builder.setPositiveButton("Take Attendance", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(HoursActivity.this,
								StudentActivity.class);
						intent.putExtra("bid", branchId);
						intent.putExtra("semid", semesterId);
						intent.putExtra("secid", sectionId);
						intent.putExtra("day", dayId);
						intent.putExtra("subid", TimeTableArrayList.get(index).getSubjectId());
						intent.putExtra("hid", TimeTableArrayList.get(index).getHour());
						
						startActivity(intent);
						dialog.dismiss();

					}
				});
				builder.setNegativeButton("Reports", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intent=new Intent(HoursActivity.this,GraphActivity.class);
						intent.putExtra("bid", branchId);
						intent.putExtra("semid", semesterId);
						intent.putExtra("secid", sectionId);
						intent.putExtra("subid", TimeTableArrayList.get(index).getSubjectId());
						intent.putExtra("hid", TimeTableArrayList.get(index).getHour());
						startActivity(intent);
						dialog.dismiss();
					}
				});
				builder.show();
				
				
				Toast
				.makeText(HoursActivity.this, "selected"+ TimeTableArrayList.get(index).getSubjectName()+"  "+TimeTableArrayList.get(index).getHour(),
						3000).show();
						
				
			}
		});
		
		
		
		
		
		
	}

}
