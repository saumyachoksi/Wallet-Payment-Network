import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/amountmonth")
public class amountmonth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public amountmonth() {
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
        
        String month = request.getParameter("month");
        int mth = Integer.parseInt(month);
        String seltype = request.getParameter("optval");
        
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
            
            ArrayList<String> myarray = new ArrayList<String>(); 
            if(seltype.equals("sent")) {
            	String s1 = "DECLARE\r\n" + 
            			"@SSN numeric(10,0) = "+ssn+",\r\n" + 
            			"@month int = "+mth+"\r\n" + 
            			"\r\n" + 
            			"--Total/average amount sent\r\n" + 
            			"\r\n" + 
            			"select\r\n" + 
            			"(select ISNULL(SUM(amount),0) from send_transaction \r\n" + 
            			"where SSN = @SSN and \r\n" + 
            			"	  status = 'Completed' and\r\n" + 
            			"	  MONTH(datetime) = @month)\r\n" + 
            			"+\r\n" + 
            			"(select ISNULL(SUM(amount),0) from request_transaction A\r\n" + 
            			"join request_transaction_multiple B on A.RTid = B.RTid\r\n" + 
            			"where B.identifier = (select phone_number from user_account where SSN = @SSN) and\r\n" + 
            			"	  A.status = 'Completed' and\r\n" + 
            			"	  MONTH(datetime) = @month) as Total_Amount_Sent,\r\n" + 
            			"\r\n" + 
            			"((select ISNULL(CAST(AVG(amount) as numeric(10,2)),0)  from send_transaction \r\n" + 
            			"where SSN = @SSN and \r\n" + 
            			"	  status = 'Completed' and\r\n" + 
            			"	  MONTH(datetime) = @month)\r\n" + 
            			"+\r\n" + 
            			"(select ISNULL(CAST(AVG(amount) as numeric(10,2)),0)  from request_transaction A\r\n" + 
            			"join request_transaction_multiple B on A.RTid = B.RTid\r\n" + 
            			"where B.identifier = (select phone_number from user_account where SSN = @SSN) and\r\n" + 
            			"	  A.status = 'Completed' and\r\n" + 
            			"	  MONTH(datetime) = @month))/2 as Average_Amount_Sent";
            	
            	ResultSet rs = stmt.executeQuery(s1);
                while ( rs.next()) {
                    myarray.add(rs.getString(1));
                    myarray.add(rs.getString(2));
               }
            	request.setAttribute("amount", myarray);
            	request.setAttribute("answer", "sent");
    			request.getRequestDispatcher("amtmonth.jsp").forward(request, response);
            }
            
            if(seltype.equals("received")) {
            	System.out.println("recieved");
            	String s2 = "DECLARE\r\n" + 
            			"@SSN numeric(10,0) = "+ssn+",\r\n" + 
            			"@month int = "+mth+"\r\n" + 
            			"\r\n" + 
            			"--Total/average amount received\r\n" + 
            			"\r\n" + 
            			"select\r\n" + 
                        "(select ISNULL(SUM(amount),0) from request_transaction \r\n" + 
                        "where status = 'Completed' and\r\n" + 
                        "	  SSN = @SSN and \r\n" + 
                        "	  MONTH(datetime) = @month)\r\n" + 
                        "+\r\n" + 
                        "(select ISNULL(SUM(amount),0) from send_transaction\r\n" + 
                        "where identifier = (select phone_number from user_account where SSN = @SSN) and\r\n" + 
                        "	  status = 'Completed' and\r\n" + 
                        "	  MONTH(datetime) = @month) as Total_Amount_Received,\r\n" + 
                        "\r\n" + 
                        "(select ISNULL(CAST(AVG(amount) as numeric(10,2)),0) from request_transaction \r\n" + 
                        "where status = 'Completed' and\r\n" + 
                        "	  SSN = @SSN and \r\n" + 
                        "	  MONTH(datetime) = @month)\r\n" + 
                        "+\r\n" + 
                        "(select ISNULL(CAST(AVG(amount) as numeric(10,2)),0) from send_transaction\r\n" + 
                        "where identifier = (select phone_number from user_account where SSN = @SSN) and\r\n" + 
                        "	  status = 'Completed' and\r\n" + 
                        "	  MONTH(datetime) = @month) as Average_Amount_Received";
            	System.out.println("recieved1");
            	ResultSet rs = stmt.executeQuery(s2);
            	System.out.println("Query Executed");
                while ( rs.next()) {
                    myarray.add(rs.getString(1));
                    myarray.add(rs.getString(2));
               }
            	request.setAttribute("amount", myarray);
            	request.setAttribute("answer", "recieved");
    			request.getRequestDispatcher("amtmonth.jsp").forward(request, response);
            }            
            
            con.close();
            
	    }
        catch (SQLException ex) {
        	request.setAttribute("msgerror", "Error Occurred in Transactions per month");
			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
        }
        catch (Exception ex) {System.out.println(ex);}
        
  
        
	}

}
