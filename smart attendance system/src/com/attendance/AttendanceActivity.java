package com.attendance;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.util.ClientConnectionBuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AttendanceActivity extends Activity {
	Spinner semesterSpinner, branchSpinner, sectionSpinner;
	Button okButton;
	String semesterId, branchId, sectionId, subjectId;
	ArrayList<String> branchArrayList;
	ArrayList<String> branchIdArrayList;
	private ArrayList<String> semArrayList;
	private ArrayList<String> semIdArrayList;
	private ArrayList<String> sectionArrayList;
	private ArrayList<String> sectionIdArrayList;
	private ArrayList<String> subjectArrayList;
	private ArrayList<String> subjectIdArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance_layout);
		init();
		// ---------------------------------------------------------branch-------------------------------------------
		String urlString = "AttendanceManagement?action=branch";
		String response = ClientConnectionBuilder.sendRequest(urlString);
		try {
			JSONArray branchJsonArray = new JSONArray(response);
			branchArrayList = new ArrayList<String>();
			branchIdArrayList = new ArrayList<String>();
			for (int i = 0; i < branchJsonArray.length(); i++) {

				branchArrayList.add(branchJsonArray.getJSONObject(i).getString(
						"name"));
				branchIdArrayList.add(branchJsonArray.getJSONObject(i)
						.getString("id"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayAdapter<String> branchAdapter = new ArrayAdapter<String>(
				AttendanceActivity.this, android.R.layout.simple_spinner_item,
				branchArrayList);
		branchSpinner.setAdapter(branchAdapter);
		branchSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {

				branchId = branchIdArrayList.get(index);
				Toast.makeText(AttendanceActivity.this, branchId, 3000).show();

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		// -------------------------------------sem-----------------------------------------------------------
		urlString = "AttendanceManagement?action=sem";
		response = ClientConnectionBuilder.sendRequest(urlString);
		try {
			JSONArray semJsonArray = new JSONArray(response);
			semArrayList = new ArrayList<String>();
			semIdArrayList = new ArrayList<String>();
			for (int i = 0; i < semJsonArray.length(); i++) {

				semArrayList.add(semJsonArray.getJSONObject(i)
						.getString("name"));
				semIdArrayList.add(semJsonArray.getJSONObject(i)
						.getString("id"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayAdapter<String> semAdapter = new ArrayAdapter<String>(
				AttendanceActivity.this, android.R.layout.simple_spinner_item,
				semArrayList);
		semesterSpinner.setAdapter(semAdapter);
		semesterSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {

				semesterId = semIdArrayList.get(index);
				Toast.makeText(AttendanceActivity.this, semesterId, 3000).show();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// ----------------------------------------------------section----------------------------------------------
		urlString = "AttendanceManagement?action=section";
		response = ClientConnectionBuilder.sendRequest(urlString);
		try {
			JSONArray sectionJsonArray = new JSONArray(response);
			sectionArrayList = new ArrayList<String>();
			sectionIdArrayList = new ArrayList<String>();
			for (int i = 0; i < sectionJsonArray.length(); i++) {

				sectionArrayList.add(sectionJsonArray.getJSONObject(i)
						.getString("name"));
				sectionIdArrayList.add(sectionJsonArray.getJSONObject(i)
						.getString("id"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayAdapter<String> sectionAdapter = new ArrayAdapter<String>(
				AttendanceActivity.this, android.R.layout.simple_spinner_item,
				sectionArrayList);
		sectionSpinner.setAdapter(sectionAdapter);
		sectionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				sectionId = sectionIdArrayList.get(index);
				Toast.makeText(AttendanceActivity.this, sectionId, 3000).show();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// ------------------------------------------------subject---------------------------------------------------

		urlString = "AttendanceManagement?action=subject";
		response = ClientConnectionBuilder.sendRequest(urlString);
		try {
			JSONArray subjectJsonArray = new JSONArray(response);
			subjectArrayList = new ArrayList<String>();
			subjectIdArrayList = new ArrayList<String>();
			for (int i = 0; i < subjectJsonArray.length(); i++) {

				subjectArrayList.add(subjectJsonArray.getJSONObject(i)
						.getString("name"));
				subjectIdArrayList.add(subjectJsonArray.getJSONObject(i)
						.getString("id"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(
				AttendanceActivity.this, android.R.layout.simple_spinner_item,
				subjectArrayList);
		subjectSpinner.setAdapter(subjectAdapter);*/
		/*subjectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				subjectId = subjectIdArrayList.get(index);
				Toast.makeText(AttendanceActivity.this, subjectId, 3000).show();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
*/
		// semester=(Spinner) findViewById(R.id.semestersp);
		// branch=(Spinner) findViewById(R.id.branchsp);
		// section=(Spinner) findViewById(R.id.sectionsp);
		// subject=(Spinner) findViewById(R.id.subjectsp);
		// okButtons=(Button) findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (validation()) {

					Toast
							.makeText(AttendanceActivity.this, "successfull",
									3000).show();
					Intent intent = new Intent(AttendanceActivity.this,
							DayListActivity.class);
					intent.putExtra("bid", branchId);
					intent.putExtra("semid", semesterId);
					intent.putExtra("secid", sectionId);
					intent.putExtra("subid", subjectId);
					
					startActivity(intent);

				}
			}
		});

	}

	protected boolean validation() {
		boolean flag = true;
		if (branchId.equalsIgnoreCase("0")) {
			flag = false;
			Toast.makeText(AttendanceActivity.this, "plz select branch", 3000)
					.show();
		}
		else if(semesterId.equalsIgnoreCase("0")){
			flag=false;
			Toast.makeText(AttendanceActivity.this, "plz select semester", 3000).show();
		}
		else if(sectionId.equalsIgnoreCase("0")){
			flag=false;
			Toast.makeText(AttendanceActivity.this,"plz select section", 3000).show();
		}
		/*else if(subjectId.equalsIgnoreCase("0")){
				flag=false;
				Toast.makeText(AttendanceActivity.this,"plz select subject", 3000).show();
			
		}*/
		
		
		
		
		
		
		
		

		return flag;
	}

	private void init() {
		// TODO Auto-generated method stub
		semesterSpinner = (Spinner) findViewById(R.id.semestersp);
		branchSpinner = (Spinner) findViewById(R.id.branchsp);
		sectionSpinner = (Spinner) findViewById(R.id.sectionsp);
		//subjectSpinner = (Spinner) findViewById(R.id.subjectsp);
		okButton = (Button) findViewById(R.id.ok);
	}

}
