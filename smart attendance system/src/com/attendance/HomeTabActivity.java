package com.attendance;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HomeTabActivity extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		TabHost  host=getTabHost();
		TabSpec tabSpecProfile=host.newTabSpec("Profile");
		tabSpecProfile=tabSpecProfile.setIndicator("My Profile", getResources().getDrawable(R.drawable.ic_launcher));
		tabSpecProfile=tabSpecProfile.setContent(new Intent(HomeTabActivity.this,MyProfileActivity.class));
		TabSpec tabSpecBranch=host.newTabSpec("Attendance");
		tabSpecBranch=tabSpecBranch.setIndicator("Class Attendance", getResources().getDrawable(R.drawable.ic_launcher));
		tabSpecBranch=tabSpecBranch.setContent(new Intent(HomeTabActivity.this,AttendanceActivity.class));
		
		host.addTab(tabSpecProfile);
		host.addTab(tabSpecBranch);
		
		
		
	}
	
	
	
}
