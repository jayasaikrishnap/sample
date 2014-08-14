package com.attendance;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DayListActivity extends Activity {
	ListView dayList;
	private String semesterId;
	private String branchId;
	private String sectionId;
	private String subjectId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_layout);
		dayList=(ListView) findViewById(R.id.listView1);
		
		Intent intent=getIntent();
		semesterId=intent.getStringExtra("semid");
		branchId=intent.getStringExtra("bid");
		sectionId=intent.getStringExtra("secid");
		subjectId=intent.getStringExtra("subid");
		
		
		
		
		final ArrayList<String> dayName=new ArrayList<String>();
		dayName.add("Monday");
		dayName.add("Tuesday");
		dayName.add("Wednesday");
		dayName.add("Thursday");
		dayName.add("Friday");
		dayName.add("Saturday");
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(DayListActivity.this, android.R.layout.simple_list_item_1,dayName);
		dayList.setAdapter(adapter);
		
		dayList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				Toast
				.makeText(DayListActivity.this, "selected"+ dayName.get(index),
						3000).show();
		Intent intent = new Intent(DayListActivity.this,
				HoursActivity.class);
		intent.putExtra("bid", branchId);
		intent.putExtra("semid", semesterId);
		intent.putExtra("secid", sectionId);
		intent.putExtra("subid", subjectId);
		intent.putExtra("day", dayName.get(index));
		
		startActivity(intent);
				
			}
		});
		
		
		
		
	}

}
