package com.util;

import java.util.ArrayList;

import com.attendance.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HoursAdapter extends ArrayAdapter<String> {

	Context context; int textViewResourceId;ArrayList<TimeTable> timeTableList;
	public HoursAdapter(Context context, int textViewResourceId,ArrayList<TimeTable> timeTableList) {
		super(context, textViewResourceId);

	this.context=context;
	this.textViewResourceId=textViewResourceId;
	this.timeTableList=timeTableList;
	
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return timeTableList.size();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView=inflater.inflate(textViewResourceId, null,false);
		TextView hourTimeTextView=(TextView) convertView.findViewById(R.id.textView1);
		TextView subjectTextView=(TextView) convertView.findViewById(R.id.textView2);
		hourTimeTextView.setText(timeTableList.get(position).getHour());
		subjectTextView.setText(timeTableList.get(position).getSubjectName());
		
		return convertView;
	}

}
