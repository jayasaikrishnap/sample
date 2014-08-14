package com.controllor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.util.DBConnectionFactory;

/**
 * Servlet implementation class AttendanceReportServlet
 */
public class AttendanceReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendanceReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		String operation = request.getParameter("action");
		
	 if(operation.equalsIgnoreCase("info"))
		{
		 getAttendanceReport(request,response);
		}
		
		
	}

	private void getAttendanceReport(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		PrintWriter pw=null;
		String branchId=request.getParameter("bid");
		String semId=request.getParameter("semid");
		String sectionId=request.getParameter("secid");
		String subjectId=request.getParameter("subid");
		con=DBConnectionFactory.getConnectionFactory();
		JSONArray reportJsonArray=new JSONArray();
		int totalClassCount=0;
		
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="select count(*) from Student where BRANCH_ID='"+branchId+"' AND SEMESTER_ID='"+semId+"' AND SECTION_ID='"+sectionId+"'";
			System.out.println(query);
			rs=st.executeQuery(query);
			
			if(rs.next())
			{
					totalClassCount=rs.getInt(1);
			}
			
			
			query="select to_char(A_DATE,'dd-mon-yyyy'),count(*) from Attendance where BRANCH_ID='"+branchId+"' AND SEMESTER_ID='"+semId+"' AND SECTION_ID='"+sectionId+"' AND SUBJECT_ID='"+subjectId+"' AND STATUS='present' group by to_char(A_DATE,'dd-mon-yyyy') order by to_char(A_DATE,'dd-mon-yyyy')";
			System.out.println(query);
			rs=st.executeQuery(query);
			ArrayList<String> dateList=new ArrayList<String>();
			ArrayList<Integer> totalPresenties=new ArrayList<Integer>();
			while(rs.next())
			{
				dateList.add(rs.getString(1));
				totalPresenties.add(rs.getInt(2));
			}
			double percentage;
			
			for (int i = 0; i < dateList.size(); i++) {
				
				percentage=((double)totalPresenties.get(i)/(double)totalClassCount)*100;
				String string=String.format("%.2f%n", percentage);
				System.out.println(string+"**********************8");
				
				JSONObject dailyReportJsonObject=new JSONObject();
				dailyReportJsonObject.put("date", dateList.get(i));
				dailyReportJsonObject.put("per", string);
				reportJsonArray.put(dailyReportJsonObject);
			}
		/*	
		int count=6;
		int weekpercentage=0;
for (int i = 0; i < dateList.size(); i=i+count) {
			for (int j = i; j < count++; j++) {
				weekpercentage+=totalPresenties.get(j);
			}	
			
				percentage=((double)weekpercentage/(double)totalClassCount*count)*100;
				JSONObject dailyReportJsonObject=new JSONObject();
				dailyReportJsonObject.put("date", dateList.get(i));
				dailyReportJsonObject.put("per", percentage);
				reportJsonArray.put(dailyReportJsonObject);
			}
			*/
			
			
			pw.print(reportJsonArray);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally
		{
			DBConnectionFactory.closeConnection(con, st, rs);
		}
		
		
		
	}

	
		
	}


