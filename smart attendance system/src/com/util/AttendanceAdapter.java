package com.util;

import java.util.ArrayList;

import com.attendance.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AttendanceAdapter extends ArrayAdapter<Student> {
	Context context; int textViewResourceId;ArrayList<Student> studentList;
	public AttendanceAdapter(Context context, int textViewResourceId,ArrayList<Student> studentList) {
		super(context, textViewResourceId);
		this.context=context;
		this.textViewResourceId=textViewResourceId;
		this.studentList=studentList;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return studentList.size();
	}
@Override
public View getView(final int position, View convertView, ViewGroup parent) {
	
	
	LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	convertView=inflater.inflate(textViewResourceId, null, false);
	final TextView studentNameTextView=(TextView) convertView.findViewById(R.id.nametv);
	final TextView rollNoTextView=(TextView) convertView.findViewById(R.id.rollnotv);
	RadioButton presentRadioButton=(RadioButton) convertView.findViewById(R.id.presentrbt);
	RadioButton absentRadioButton=(RadioButton) convertView.findViewById(R.id.absentrbt);
	studentNameTextView.setText(studentList.get(position).getName());
	rollNoTextView.setText(studentList.get(position).getRollNo());
	presentRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(isChecked)
			{
				studentList.get(position).setStatus(true);
				rollNoTextView.setBackgroundColor(Color.GREEN);
				studentNameTextView.setBackgroundColor(Color.GREEN);
			}
			else {
				studentList.get(position).setStatus(false);
				rollNoTextView.setBackgroundColor(Color.RED);
				studentNameTextView.setBackgroundColor(Color.RED);
			}
		}
	});
	System.out.println(studentList.get(position).isStatus()+"            present");
	System.out.println();
	presentRadioButton.setChecked(studentList.get(position).isStatus());
	if(studentList.get(position).isStatus())
	{
		rollNoTextView.setBackgroundColor(Color.GREEN);
		studentNameTextView.setBackgroundColor(Color.GREEN);
	}
	else
	{
		rollNoTextView.setBackgroundColor(Color.RED);
		studentNameTextView.setBackgroundColor(Color.RED);
	}
	absentRadioButton.setChecked(!studentList.get(position).isStatus());
	return convertView;
}
}
