package com.controllor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.util.DBConnectionFactory;

/**
 * Servlet implementation class AttendanceManagement
 */
public class AttendanceManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AttendanceManagement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String operation = request.getParameter("action");
		if(operation.equalsIgnoreCase("branch"))
		{
			getBranch(request,response);
		}
		else if(operation.equalsIgnoreCase("sem"))
		{
			getSem(request,response);
		}
		else if(operation.equalsIgnoreCase("section"))
		{
			getSection(request,response);
		}
		else if(operation.equalsIgnoreCase("subject"))
		{
			getSubject(request,response);
		}
		else if(operation.equalsIgnoreCase("info"))
		{
			getStudentInfo(request,response);
		}
		else if(operation.equalsIgnoreCase("gethours"))
		{
			getHours(request,response);
		}
		else if(operation.equalsIgnoreCase("insert"))
		{
			insertAttendance(request,response);
		}

	}

	private void insertAttendance(HttpServletRequest request,
			HttpServletResponse response) {
		
		Connection con=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		PrintWriter pw=null;
		String branchId=request.getParameter("bid");
		String semId=request.getParameter("semid");
		String sectionId=request.getParameter("secid");
		String dayId=request.getParameter("day");
		String hourId=request.getParameter("hid");
		String subjectId=request.getParameter("subid");
		String student=request.getParameter("student");
		String lecId=request.getParameter("lid");
		try {
			JSONArray studentJsonArray=new JSONArray(student);
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yy");
			String currentDate=dateFormat.format(new Date());
			String query="INSERT INTO ATTENDANCE VALUES(?,?,?,?,?,?,?,'"+currentDate+"',?,?)";
			System.out.println(query);
			con=DBConnectionFactory.getConnectionFactory();
			st=con.prepareStatement(query);
			pw=response.getWriter();
			int count=0;
			for (int i = 0; i < studentJsonArray.length(); i++) {
				
				st.setString(1, semId);//,branchId,sectionId,subjectId,hourId,dayId,s
				st.setString(2, branchId);
				st.setString(3, sectionId);
				st.setString(4, subjectId);
				st.setString(5, hourId);
				st.setString(6, dayId);
				st.setString(7, studentJsonArray.getJSONObject(i).getString("id"));
				st.setString(8,studentJsonArray.getJSONObject(i).getString("status"));
				st.setString(9, lecId);
				int k=st.executeUpdate();
				if(k>0)
				{
					count++;
				}
			}
			if(studentJsonArray.length()==count)
			{
				pw.print("success");
			}
			else
			{
				pw.print("fail");
			}
			
			
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getLocalizedMessage().contains("ORA-00001: unique constraint"))
			{
			pw.print("Attendance is already taken..");	
			}
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

	private void getHours(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		PrintWriter pw=null;
		String branchId=request.getParameter("bid");
		String semId=request.getParameter("semid");
		String sectionId=request.getParameter("secid");
		String dayId=request.getParameter("day");
		con=DBConnectionFactory.getConnectionFactory();
		JSONArray timeTableJsonArray=new JSONArray();
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="SELECT S.SUBJECT_NAME,T.SUBJECT_ID,T.HOUR_NAME FROM TIME_TABLE T,SUBJECT S WHERE T.BRANCH_ID='"+branchId+"' AND T.SEMESTER_ID='"+semId+"' AND T.SECTION_ID='"+sectionId+"' AND T.DAY_NAME='"+dayId+"' AND T.SUBJECT_ID=S.SUBJECT_ID" ;
			System.out.println(query);
			rs=st.executeQuery(query);
			while(rs.next())
			{
				JSONObject timeTableJsonObject=new JSONObject();
				
		
				timeTableJsonObject.put("subname", rs.getString("SUBJECT_NAME"));
				timeTableJsonObject.put("subid", rs.getString("SUBJECT_ID"));
				timeTableJsonObject.put("hid", rs.getString("HOUR_NAME"));
				timeTableJsonArray.put(timeTableJsonObject);
				
				System.out.println("got the details");
			}
			pw.print(timeTableJsonArray);
			
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

	private void getStudentInfo(HttpServletRequest request,
			HttpServletResponse response) {
		
		// TODO Auto-generated method stub
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		PrintWriter pw=null;
		String branchId=request.getParameter("bid");
		String semId=request.getParameter("semid");
		String sectionId=request.getParameter("secid");
		con=DBConnectionFactory.getConnectionFactory();
		JSONArray studentJsonArray=new JSONArray();
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="SELECT * FROM STUDENT WHERE BRANCH_ID='"+branchId+"' AND SEMESTER_ID='"+semId+"' AND SECTION_ID='"+sectionId+"'" ;
			System.out.println(query);
			rs=st.executeQuery(query);
			while(rs.next())
			{
				JSONObject studentJsonObject=new JSONObject();
				
		
				studentJsonObject.put("rollno", rs.getString("STUDENT_ID"));
				studentJsonObject.put("name", rs.getString("STUDENT_NAME"));
				studentJsonArray.put(studentJsonObject);
				
				System.out.println("got the details");
			}
			pw.print(studentJsonArray);
			
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

	private void getSubject(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		PrintWriter pw=null;
		con=DBConnectionFactory.getConnectionFactory();
		JSONArray subjectJsonArray=new JSONArray();
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="SELECT * FROM SUBJECT" ;
			System.out.println(query);
			rs=st.executeQuery(query);
			while(rs.next())
			{
				JSONObject subjectJsonObject=new JSONObject();
				
		
				subjectJsonObject.put("id", rs.getString("subject_id"));
				subjectJsonObject.put("name", rs.getString("subject_name"));
				subjectJsonArray.put(subjectJsonObject);
				
				System.out.println("got the details");
			}
			pw.print(subjectJsonArray);
			
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

	private void getSection(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		PrintWriter pw=null;
		con=DBConnectionFactory.getConnectionFactory();
		JSONArray sectionJsonArray=new JSONArray();
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="SELECT * FROM SECTION ORDER BY SECTION_ID" ;
			System.out.println(query);
			rs=st.executeQuery(query);
			while(rs.next())
			{
				JSONObject sectionJsonObject=new JSONObject();
				
		
				sectionJsonObject.put("id", rs.getString("section_id"));
				sectionJsonObject.put("name", rs.getString("section_name"));
				sectionJsonArray.put(sectionJsonObject);
				
				System.out.println("got the details");
			}
			pw.print(sectionJsonArray);
			
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

	private void getSem(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		PrintWriter pw=null;
		con=DBConnectionFactory.getConnectionFactory();
		JSONArray semJsonArray=new JSONArray();
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="SELECT * FROM SEMESTER ORDER BY SEMESTER_ID " ;
			System.out.println(query);
			rs=st.executeQuery(query);
			while(rs.next())
			{
				JSONObject semJsonObject=new JSONObject();
				
		
				semJsonObject.put("id", rs.getString("semester_id"));
				semJsonObject.put("name", rs.getString("semester_name"));
				semJsonArray.put(semJsonObject);
				
				System.out.println("got the details");
			}
			pw.print(semJsonArray);
			
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

	private void getBranch(HttpServletRequest request,
			HttpServletResponse response) {

		Connection con=null;
		Statement st=null;
		ResultSet rs=null; 
		PrintWriter pw=null;
		con=DBConnectionFactory.getConnectionFactory();
		JSONArray branchJsonArray=new JSONArray();
	
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="SELECT * FROM BRANCH ORDER BY BRANCH_ID" ;
			System.out.println(query);
			rs=st.executeQuery(query);
			while(rs.next())
			{
				JSONObject branchJsonObject=new JSONObject();
				
				
				
				branchJsonObject.put("id", rs.getString("branch_id"));
				branchJsonObject.put("name", rs.getString("branch_name"));
				branchJsonArray.put(branchJsonObject);
				
				System.out.println("got the details");
				
			}
			
		pw.print(branchJsonArray);
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			DBConnectionFactory.closeConnection(con, st, rs);
		}
		
		
		
	}

	
	

}
