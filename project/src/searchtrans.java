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

@WebServlet("/searchtrans")
public class searchtrans extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public searchtrans() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();  
	    
	    String seltype = request.getParameter("seltyp");
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
            
            HttpSession session = request.getSession(false);
            String ssn =(String)session.getAttribute("ssn"); 
            String name= (String)session.getAttribute("name");
            
            if(seltype.equals("all")) {
    	    	out.println("all");
    	    	ArrayList<String> myarray = new ArrayList<String>(); 
    	    	String s = "DECLARE\r\n" + 
    	    			"@SSN numeric(10,0) = "+ssn+"\r\n" + 
    	    			"\r\n" + 
    	    			"select a.STid as Transaction_ID,\r\n" + 
    	    			"	   b.first_name+' '+b.last_name as Name, 	   \r\n" + 
    	    			"       a.amount Amount,\r\n" + 
    	    			"	   a.datetime \"Date & Time\",\r\n" + 
    	    			"	   a.identifier as PhoneNumber,\r\n" + 
    	    			"	   c.email_address as Email,\r\n" + 
    	    			"	   a.status Status, \r\n" + 
    	    			"	   'Send Transaction' as Transaction_Type,\r\n" + 
    	    			"	   a.memo Memo \r\n" + 
    	    			"	   from send_transaction a\r\n" + 
    	    			"	   left join user_account b on a.identifier = b.phone_number\r\n" + 
    	    			"	   left join email c on b.SSN = c.SSN and c.is_primary = 1\r\n" + 
    	    			"	   where a.SSN = @SSN\r\n" + 
    	    			"union\r\n" + 
    	    			"select a.RTid as Transaction_ID,\r\n" + 
    	    			"	   c.first_name+' '+c.last_name as Name,\r\n" + 
    	    			"       a.amount Amount,\r\n" + 
    	    			"	   a.datetime \"Date & Time\",\r\n" + 
    	    			"	   b.identifier as PhoneNumber,\r\n" + 
    	    			"	   d.email_address as Email,\r\n" + 
    	    			"	   a.status Status, \r\n" + 
    	    			"	   'Request Transaction' as Transaction_Type,\r\n" + 
    	    			"	   a.memo Memo\r\n" + 
    	    			"	   from request_transaction a\r\n" + 
    	    			"	   join request_transaction_multiple b on a.RTid = b.RTid\r\n" + 
    	    			"	   left join user_account c on b.identifier = c.phone_number\r\n" + 
    	    			"	   left join email d on c.SSN = d.SSN and d.is_primary = 1\r\n" + 
    	    			"	   where a.SSN = @SSN";
    	    	ResultSet rs = stmt.executeQuery(s);
    	    	int i = 0;
                while ( rs.next()) {
                	i = i +1;
                	myarray.add(Integer.toString(i));
                    myarray.add(rs.getString(1));
                    myarray.add(rs.getString(2));
                    myarray.add(rs.getString(3));
                    myarray.add(rs.getString(4));
                    myarray.add(rs.getString(5));
                    myarray.add(rs.getString(6));
                    myarray.add(rs.getString(7));
                    myarray.add(rs.getString(8));
                    myarray.add(rs.getString(9));
               }
    	    	request.setAttribute("amount", myarray);
    	    	request.setAttribute("msg", "Search Transactions with no Filters");
            	request.setAttribute("answer", "all");
    			request.getRequestDispatcher("search.jsp").forward(request, response);
    	    }
    	    if(seltype.equals("email")) {
    	    	
    	    	String email = request.getParameter("email");
    	    	out.println(email);
    	    	ArrayList<String> myarray = new ArrayList<String>(); 
    	    	String s = "DECLARE\r\n" + 
    	    			"@email nvarchar(50) = '"+email+"',\r\n" + 
    	    			"@SSN numeric(10,0) = "+ssn+"\r\n" + 
    	    			"\r\n" + 
    	    			"\r\n" + 
    	    			"select a.STid as Transaction_ID,\r\n" + 
    	    			"	   b.first_name+' '+b.last_name as Name, 	   \r\n" + 
    	    			"       a.amount Amount,\r\n" + 
    	    			"	   a.datetime \"Date & Time\",\r\n" + 
    	    			"	   a.identifier as PhoneNumber,\r\n" + 
    	    			"	   c.email_address as Email,\r\n" + 
    	    			"	   a.status Status, \r\n" + 
    	    			"	   'Send Transaction' as Transaction_Type,\r\n" + 
    	    			"	   a.memo Memo \r\n" + 
    	    			"	   from send_transaction a\r\n" + 
    	    			"	   left join user_account b on a.identifier = b.phone_number\r\n" + 
    	    			"	   left join email c on b.SSN = c.SSN and c.is_primary = 1\r\n" + 
    	    			"	   where c.email_address = @email and\r\n" + 
    	    			"			 a.SSN = @SSN\r\n" + 
    	    			"union\r\n" + 
    	    			"select a.RTid as Transaction_ID,\r\n" + 
    	    			"	   c.first_name+' '+c.last_name as Name,\r\n" + 
    	    			"       a.amount Amount,\r\n" + 
    	    			"	   a.datetime \"Date & Time\",\r\n" + 
    	    			"	   b.identifier as PhoneNumber,\r\n" + 
    	    			"	   d.email_address as Email,\r\n" + 
    	    			"	   a.status Status, \r\n" + 
    	    			"	   'Request Transaction' as Transaction_Type,\r\n" + 
    	    			"	   a.memo Memo\r\n" + 
    	    			"	   from request_transaction a\r\n" + 
    	    			"	   join request_transaction_multiple b on a.RTid = b.RTid\r\n" + 
    	    			"	   left join user_account c on b.identifier = c.phone_number\r\n" + 
    	    			"	   left join email d on c.SSN = d.SSN and d.is_primary = 1\r\n" + 
    	    			"	   where d.email_address = @email and\r\n" + 
    	    			"			 a.SSN = @SSN";
    	    	ResultSet rs = stmt.executeQuery(s);
    	    	int i = 0;
                while ( rs.next()) {
                	i = i +1;
                	myarray.add(Integer.toString(i));
                    myarray.add(rs.getString(1));
                    myarray.add(rs.getString(2));
                    myarray.add(rs.getString(3));
                    myarray.add(rs.getString(4));
                    myarray.add(rs.getString(5));
                    myarray.add(rs.getString(6));
                    myarray.add(rs.getString(7));
                    myarray.add(rs.getString(8));
                    myarray.add(rs.getString(9));
               }
    	    	request.setAttribute("amount", myarray);
    	    	request.setAttribute("msg", "Search Transaction -- Email Address");
            	request.setAttribute("answer", "email");
    			request.getRequestDispatcher("search.jsp").forward(request, response);
    	    	
    	    }
    	    if(seltype.equals("phone")) {
    	    	
    	    	String phone = request.getParameter("phone");
    	    	out.println(phone);ArrayList<String> myarray = new ArrayList<String>(); 
    	    	String s = "DECLARE\r\n" + 
    	    			"@phone nvarchar(50) = '"+phone+"',\r\n" + 
    	    			"@SSN numeric(10,0) = "+ssn+"\r\n" + 
    	    			"\r\n" + 
    	    			"\r\n" + 
    	    			"select a.STid as Transaction_ID,\r\n" + 
    	    			"	   b.first_name+' '+b.last_name as Name, 	   \r\n" + 
    	    			"       a.amount Amount,\r\n" + 
    	    			"	   a.datetime \"Date & Time\",\r\n" + 
    	    			"	   a.identifier as PhoneNumber,\r\n" + 
    	    			"	   c.email_address as Email,\r\n" + 
    	    			"	   a.status Status, \r\n" + 
    	    			"	   'Send Transaction' as Transaction_Type,\r\n" + 
    	    			"	   a.memo Memo \r\n" + 
    	    			"	   from send_transaction a\r\n" + 
    	    			"	   left join user_account b on a.identifier = b.phone_number\r\n" + 
    	    			"	   left join email c on b.SSN = c.SSN and c.is_primary = 1\r\n" + 
    	    			"	   where b.phone_number = @phone and\r\n" + 
    	    			"			 a.SSN = @SSN\r\n" + 
    	    			"union\r\n" + 
    	    			"select a.RTid as Transaction_ID,\r\n" + 
    	    			"	   c.first_name+' '+c.last_name as Name,\r\n" + 
    	    			"       a.amount Amount,\r\n" + 
    	    			"	   a.datetime \"Date & Time\",\r\n" + 
    	    			"	   b.identifier as PhoneNumber,\r\n" + 
    	    			"	   d.email_address as Email,\r\n" + 
    	    			"	   a.status Status, \r\n" + 
    	    			"	   'Request Transaction' as Transaction_Type,\r\n" + 
    	    			"	   a.memo Memo \r\n" + 
    	    			"	   from request_transaction a\r\n" + 
    	    			"	   join request_transaction_multiple b on a.RTid = b.RTid\r\n" + 
    	    			"	   left join user_account c on b.identifier = c.phone_number\r\n" + 
    	    			"	   left join email d on c.SSN = d.SSN and d.is_primary = 1\r\n" + 
    	    			"	   where c.phone_number = @phone and\r\n" + 
    	    			"			 a.SSN = @SSN";
    	    	ResultSet rs = stmt.executeQuery(s);
    	    	int i = 0;
                while ( rs.next()) {
                	i = i +1;
                	myarray.add(Integer.toString(i));
                    myarray.add(rs.getString(1));
                    myarray.add(rs.getString(2));
                    myarray.add(rs.getString(3));
                    myarray.add(rs.getString(4));
                    myarray.add(rs.getString(5));
                    myarray.add(rs.getString(6));
                    myarray.add(rs.getString(7));
                    myarray.add(rs.getString(8));
                    myarray.add(rs.getString(9));
               }
    	    	request.setAttribute("amount", myarray);
    	    	request.setAttribute("msg", "Search Transaction -- Phone Number");
            	request.setAttribute("answer", "phone");
    			request.getRequestDispatcher("search.jsp").forward(request, response);
    	    	
    	    }
    	    if(seltype.equals("type_trans")) {
    	    	
    	    	String option = request.getParameter("opt");
    	    	out.println(option);
    	    	if(option.equals("sent")) {
    	    		ArrayList<String> myarray = new ArrayList<String>(); 
        	    	String s = "DECLARE\r\n" + 
        	    			"@SSN numeric(10,0) = "+ssn+"\r\n" + 
        	    			"\r\n" + 
        	    			"select a.STid as Transaction_ID,\r\n" + 
        	    			"	   b.first_name+' '+b.last_name as Name, 	   \r\n" + 
        	    			"       a.amount Amount,\r\n" + 
        	    			"	   a.datetime \"Date & Time\",\r\n" + 
        	    			"	   a.identifier as PhoneNumber,\r\n" + 
        	    			"	   c.email_address as Email,\r\n" + 
        	    			"	   a.status Status, \r\n" + 
        	    			"	   'Send Transaction' as Transaction_Type,\r\n" + 
        	    			"	   a.memo Memo \r\n" + 
        	    			"	   from send_transaction a\r\n" + 
        	    			"	   left join user_account b on a.identifier = b.phone_number\r\n" + 
        	    			"	   left join email c on b.SSN = c.SSN and c.is_primary = 1\r\n" + 
        	    			"	   where a.SSN = @SSN\r\n" + 
        	    			"";
        	    	ResultSet rs = stmt.executeQuery(s);
        	    	int i = 0;
                    while ( rs.next()) {
                    	i = i +1;
                    	myarray.add(Integer.toString(i));
                        myarray.add(rs.getString(1));
                        myarray.add(rs.getString(2));
                        myarray.add(rs.getString(3));
                        myarray.add(rs.getString(4));
                        myarray.add(rs.getString(5));
                        myarray.add(rs.getString(6));
                        myarray.add(rs.getString(7));
                        myarray.add(rs.getString(8));
                        myarray.add(rs.getString(9));
                   }
        	    	request.setAttribute("amount", myarray);
        	    	request.setAttribute("msg", "Search for Send Transaction");
                	request.setAttribute("answer", "typetrans");
        			request.getRequestDispatcher("search.jsp").forward(request, response);
    	    	}
    	    	if(option.equals("received")) {
    	    		ArrayList<String> myarray = new ArrayList<String>(); 
        	    	String s = "DECLARE\r\n" + 
        	    			"@SSN numeric(10,0) = "+ssn+""+
        	    			"select a.RTid as Transaction_ID,\r\n" + 
        	    			"	   c.first_name+' '+c.last_name as Name,\r\n" + 
        	    			"       a.amount Amount,\r\n" + 
        	    			"	   a.datetime \"Date & Time\",\r\n" + 
        	    			"	   b.identifier as PhoneNumber,\r\n" + 
        	    			"	   d.email_address as Email,\r\n" + 
        	    			"	   a.status Status, \r\n" + 
        	    			"	   'Request Transaction' as Transaction_Type,\r\n" + 
        	    			"	   a.memo Memo\r\n" + 
        	    			"	   from request_transaction a\r\n" + 
        	    			"	   join request_transaction_multiple b on a.RTid = b.RTid\r\n" + 
        	    			"	   left join user_account c on b.identifier = c.phone_number\r\n" + 
        	    			"	   left join email d on c.SSN = d.SSN and d.is_primary = 1\r\n" + 
        	    			"	   where a.SSN = @SSN";
        	    	ResultSet rs = stmt.executeQuery(s);
        	    	int i = 0;
                    while ( rs.next()) {
                    	i = i +1;
                    	myarray.add(Integer.toString(i));
                        myarray.add(rs.getString(1));
                        myarray.add(rs.getString(2));
                        myarray.add(rs.getString(3));
                        myarray.add(rs.getString(4));
                        myarray.add(rs.getString(5));
                        myarray.add(rs.getString(6));
                        myarray.add(rs.getString(7));
                        myarray.add(rs.getString(8));
                        myarray.add(rs.getString(9));
                   }
        	    	request.setAttribute("amount", myarray);
        	    	request.setAttribute("msg", "Search for Received Transaction");
                	request.setAttribute("answer", "typetrans");
        			request.getRequestDispatcher("search.jsp").forward(request, response);
    	    	}
    	    }
    		if(seltype.equals("time_date")) {
    			
    			String start = request.getParameter("start");
    			String end = request.getParameter("end");
    			out.println(start);
    			out.println(end);
    			ArrayList<String> myarray = new ArrayList<String>(); 
    	    	String s = "DECLARE\r\n" + 
    	    			"@start_date date = '"+start+"',\r\n" + 
    	    			"@end_date date = '"+end+"',\r\n" + 
    	    			"@SSN numeric(10,0) = "+ssn+"\r\n" + 
    	    			"\r\n" + 
    	    			"\r\n" + 
    	    			"select a.STid as Transaction_ID,\r\n" + 
    	    			"	   b.first_name+' '+b.last_name as Name, 	   \r\n" + 
    	    			"       a.amount Amount,\r\n" + 
    	    			"	   a.datetime \"Date & Time\",\r\n" + 
    	    			"	   a.identifier as PhoneNumber,\r\n" + 
    	    			"	   c.email_address as Email,\r\n" + 
    	    			"	   a.status Status, \r\n" + 
    	    			"	   'Send Transaction' as Transaction_Type,\r\n" + 
    	    			"	   a.memo Memo \r\n" + 
    	    			"	   from send_transaction a\r\n" + 
    	    			"	   left join user_account b on a.identifier = b.phone_number\r\n" + 
    	    			"	   left join email c on b.SSN = c.SSN and c.is_primary = 1\r\n" + 
    	    			"	   where a.datetime >= @Start_date and\r\n" + 
    	    			"	         a.datetime <= DATEADD(DAY,1,@End_date) and\r\n" + 
    	    			"			 a.SSN = @SSN\r\n" + 
    	    			"union\r\n" + 
    	    			"select a.RTid as Transaction_ID,\r\n" + 
    	    			"	   c.first_name+' '+c.last_name as Name,\r\n" + 
    	    			"       a.amount Amount,\r\n" + 
    	    			"	   a.datetime \"Date & Time\",\r\n" + 
    	    			"	   b.identifier as PhoneNumber,\r\n" + 
    	    			"	   d.email_address as Email,\r\n" + 
    	    			"	   a.status Status, \r\n" + 
    	    			"	   'Request Transaction' as Transaction_Type,\r\n" + 
    	    			"	   a.memo Memo\r\n" + 
    	    			"	   from request_transaction a\r\n" + 
    	    			"	   join request_transaction_multiple b on a.RTid = b.RTid\r\n" + 
    	    			"	   left join user_account c on b.identifier = c.phone_number\r\n" + 
    	    			"	   left join email d on c.SSN = d.SSN and d.is_primary = 1\r\n" + 
    	    			"	   where a.datetime >= @Start_date and\r\n" + 
    	    			"	         a.datetime <= DATEADD(DAY,1,@End_date) and\r\n" + 
    	    			"			 a.SSN = @SSN";
    	    	ResultSet rs = stmt.executeQuery(s);
    	    	int i = 0;
                while ( rs.next()) {
                	i = i +1;
                	myarray.add(Integer.toString(i));
                    myarray.add(rs.getString(1));
                    myarray.add(rs.getString(2));
                    myarray.add(rs.getString(3));
                    myarray.add(rs.getString(4));
                    myarray.add(rs.getString(5));
                    myarray.add(rs.getString(6));
                    myarray.add(rs.getString(7));
                    myarray.add(rs.getString(8));
                    myarray.add(rs.getString(9));
               }
    	    	request.setAttribute("amount", myarray);
    			request.setAttribute("msg", "Search Transactions - Date Range");
            	request.setAttribute("answer", "timedate");
    			request.getRequestDispatcher("search.jsp").forward(request, response);
    		}
            
	    }catch (SQLException ex) {
        	request.setAttribute("msgerror", "Error Occurred in Transactions per month");
			 	request.getRequestDispatcher("bestuser.jsp").forward(request, response);
        }
        catch (Exception ex) {System.out.println(ex);}
	    
	    
	}

}
