package com.controllor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.util.DBConnectionFactory;

/**
 * Servlet implementation class ProfileManagement
 */
public class ProfileManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ProfileManagement() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doPost(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//login
		//view details
		//change password
		//forgot password
		
		
		
		String operation=request.getParameter("action");
		if(operation.equalsIgnoreCase("login"))
		{
			checkLogin(request,response);
		}
		else if(operation.equalsIgnoreCase("view"))
		{
			getUserDetails(request,response);
		}
		else if(operation.equalsIgnoreCase("changepassword"))
		{
			changeMyPassword(request,response);
		}
		else if(operation.equalsIgnoreCase("forgotpassword"))
		{
		getMyForgotPassword(request,response);	
		}
		
		
		
		
	}

	private void getMyForgotPassword(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String userName=request.getParameter("username");
		String phonenumber=request.getParameter("phonenumber");
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		PrintWriter pw=null;
		con=DBConnectionFactory.getConnectionFactory();
		pw=response.getWriter();
		try {
			st=con.createStatement();
			String query="SELECT PASSWORD FROM Lecturer where Lecturer_id='"+userName+"'and Mobile_number='"+phonenumber+"'";
			System.out.println(query);
			rs=st.executeQuery(query);
			if(rs.next())
			{
				pw.print("your password is "+rs.getString(1));
				System.out.println("password changed");
			}
			else
			{
				pw.print("try again");
				System.out.println("try again");
			}
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			DBConnectionFactory.closeConnection(con, st, rs);
		}
	//	select password from Lecturer where Lecturer_id='"+userName+"' and Mobile_number='++';
		
	}

	private void changeMyPassword(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String userName=request.getParameter("username");
		String newPassword=request.getParameter("newpwd");
		String oldPassword=request.getParameter("oldpwd");
		Connection con=null;
		Statement st=null;
		PrintWriter pw=null;
		con=DBConnectionFactory.getConnectionFactory();
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="UPDATE Lecturer set password='"+newPassword+"' where Lecturer_id='"+userName+"' AND password='"+oldPassword+"'";
			System.out.println(query);
           int i= st.executeUpdate(query);
           if(i>0)
           {
        	   pw.print("password updated");
        	   System.out.println("password updated");
           }
           else 
           {
        	   pw.print("password not updated");
        	   System.out.println("password not updated"); 
           }
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		//update Lecturer set password='"+password+"' where Lecturer_id='"+userName+"';
		
	}

	private void getUserDetails(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		// TODO Auto-generated method stub
		String userName=request.getParameter("username");
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		PrintWriter pw=null;
		con=DBConnectionFactory.getConnectionFactory();
		JSONObject profileObject=new JSONObject();
	
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="SELECT * FROM Lecturer WHERE Lecturer_id='"+userName+"'" ;
			System.out.println(query);
			rs=st.executeQuery(query);
			if(rs.next())
			{
				profileObject.put("id", rs.getString("Lecturer_id"));
				profileObject.put("password", rs.getString("password"));
				profileObject.put("name", rs.getString("lecturer_name"));
				profileObject.put("phono", rs.getString("Mobile_number"));
				
				
				pw.print(profileObject);
				System.out.println("got the details");
				
			}
			else
			{
				pw.print(" details not found");
				System.out.println("details not found");
			}
		
			
		} 
		catch (SQLException e) {
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

	
	private void checkLogin(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		String userName=request.getParameter("username");
		String password=request.getParameter("password");
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		PrintWriter pw=null;
		
		
		
		con=DBConnectionFactory.getConnectionFactory();
		try {
			
			pw=response.getWriter();
			
			
			
			st=con.createStatement();
			String query="SELECT * FROM Lecturer WHERE Lecturer_id='"+userName+"' AND PASSWORD='"+password+"'";
			System.out.println(query);
			rs=st.executeQuery(query);
			if(rs.next())
			{
				pw.print("valid");
				System.out.println("valid");
			}
			else
			{
				pw.print("invalid");
				System.out.println("invalid");
			}
						
		} catch (SQLException e) {
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
