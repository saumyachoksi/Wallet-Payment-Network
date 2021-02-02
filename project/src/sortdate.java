import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/sortdate")
public class sortdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public sortdate() {
        super();        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(false);
	    
        String ssn =(String)session.getAttribute("ssn"); 
        String name= (String)session.getAttribute("name");
        
        String startdate = request.getParameter("start");
        String enddate = request.getParameter("end");
        String seltype = request.getParameter("select");
        
        out.println(ssn);
        out.println(startdate);
        out.println(enddate);
        out.println(seltype);
        try {
	    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    	String connectionUrl = "jdbc:sqlserver://wallet.database.windows.net:1433;"
	        		+ "database=wallet;"
	        		+ "user=akilesh123@wallet;"
	        		+ "password=DBMSproject123*;"
	        		+ "encrypt=true;"
	        		+ "trustServerCertificate=false;"
	        		+ "hostNameInCertificate=*.database.windows.net;"
	        		+ "loginTimeout=30";
	    	Connection con = DriverManager.getConnection(connectionUrl);
            Statement stmt = con.createStatement(); 
            
            String amnt = null;
            if(seltype.equals("sent")) {
            	String s1 = "DECLARE\r\n" + 
            			"@SSN numeric(10,0) = "+ssn+",\r\n" + 
            			"@Start_date date = '"+startdate+"',\r\n" + 
            			"@End_date date = '"+enddate+"'\r\n" + 
            			"\r\n" + 
            			"--Total amount sent\r\n" + 
            			"\r\n" + 
            			"select\r\n" + 
            			"(select ISNULL(SUM(amount),0) from send_transaction \r\n" + 
            			"where SSN = @SSN and \r\n" + 
            			"	  status = 'Completed' and\r\n" + 
            			"	  datetime >= @Start_date and\r\n" + 
            			"	  datetime <= DATEADD(DAY,1,@End_date))\r\n" + 
            			"+\r\n" + 
            			"(select ISNULL(SUM(amount),0) from request_transaction A\r\n" + 
            			"join request_transaction_multiple B on A.RTid = B.RTid\r\n" + 
            			"where B.identifier = (select phone_number from user_account where SSN = @SSN) and\r\n" + 
            			"	  A.status = 'Completed' and\r\n" + 
            			"	  datetime >= @Start_date and\r\n" + 
            			"	  datetime <= DATEADD(DAY,1,@End_date)) as Total_Amount_Sent";
            	
            	ResultSet rs = stmt.executeQuery(s1);
                while ( rs.next()) {
                    amnt = (String)rs.getString(1);
               }
            	request.setAttribute("amount", amnt);
            	request.setAttribute("answer", "sent");
    			request.getRequestDispatcher("sortdaterange.jsp").forward(request, response);
            }
            if(seltype.equals("received")) {
            	String s1 = "DECLARE\r\n" + 
            			"@SSN numeric(10,0) = "+ssn+",\r\n" + 
            			"@Start_date date = '"+startdate+"',\r\n" + 
            			"@End_date date = '"+enddate+"'\r\n" + 
            			"\r\n" +
            			"--Total amount received\r\n" + 
            			"\r\n" + 
            			"select\r\n" + 
            			"(select ISNULL(SUM(amount),0) from request_transaction \r\n" + 
            			"where status = 'Completed' and\r\n" + 
            			"	  SSN = @SSN and \r\n" + 
            			"	  datetime >= @Start_date and\r\n" + 
            			"	  datetime <= DATEADD(DAY,1,@End_date))\r\n" + 
            			"+\r\n" + 
            			"(select ISNULL(SUM(amount),0) from send_transaction\r\n" + 
            			"where identifier = (select phone_number from user_account where SSN = @SSN) and\r\n" + 
            			"	  status = 'Completed' and\r\n" + 
            			"	 datetime >= @Start_date and\r\n" + 
            			"	  datetime <= DATEADD(DAY,1,@End_date)) as Total_Amount_Received";
            	
            	ResultSet rs = stmt.executeQuery(s1);
                while ( rs.next()) {
                    amnt = (String)rs.getString(1);
               }
            	request.setAttribute("amount", amnt);
            	request.setAttribute("answer", "recieved");
    			request.getRequestDispatcher("sortdaterange.jsp").forward(request, response);
            }            
            
            con.close();
            
	    }
        catch (SQLException ex) {
        	request.setAttribute("msgerror", "Error Occurred in sort by date range");
			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
        }
        catch (Exception ex) {System.out.println(ex);}
        
        
	}

}
