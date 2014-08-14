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
import android.widget.Button;

public class GraphActivity extends Activity implements OnClickListener {
	Button barGraph;
static ArrayList<Double> percentage;
static ArrayList<String> dateList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reports);
		barGraph = (Button) findViewById(R.id.BarGraph);
		barGraph.setOnClickListener(this);
		Intent intent=getIntent();
		String branchId=intent.getStringExtra("bid");
		String semId=intent.getStringExtra("semid");
		String secId=intent.getStringExtra("secid");
		String subId=intent.getStringExtra("subid");
		
		percentage=new ArrayList<Double>();
		dateList=new ArrayList<String>();
		String urlString="AttendanceReportServlet?action=info&bid="+branchId+"&semid="+semId+"&secid="+secId+"&subid="+subId;
		String response=ClientConnectionBuilder.sendRequest(urlString);
		try {
			JSONArray reportJsonArray=new JSONArray(response);
			
			for (int i = 0; i < reportJsonArray.length(); i++) {
				dateList.add(reportJsonArray.getJSONObject(i).getString("date"));
				percentage.add(reportJsonArray.getJSONObject(i).getDouble("per"));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		

	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.BarGraph:
			BarGraph bar = new BarGraph();
			Intent barIntent = bar.getIntent(GraphActivity.this);
			startActivity(barIntent);
			break;
		}

	}
}