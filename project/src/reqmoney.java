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

@WebServlet("/reqmoney")
public class reqmoney extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public reqmoney() {
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
        String option = request.getParameter("opt");
        
        if(option.equals("request")) {
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
                
                String phone = request.getParameter("phone");                 
                String email = request.getParameter("email"); 
                String amt = request.getParameter("amount");               
                float amt1 = Float.parseFloat(amt);
                String memo = request.getParameter("memo");
                if(memo.isEmpty()) {
                	memo = "NULL";
                }
                
                String s1 = "select SSN from user_account where phone_number ='"+phone+"'";
                String s_ssn =null;
                ResultSet rs = stmt.executeQuery(s1);
                System.out.println("query executed");
                while ( rs.next()) {
                    s_ssn = rs.getString(1);
               }
                System.out.println(s_ssn);
                
                if(s_ssn != null) {
                	if(s_ssn.equals(ssn)) {
                		System.out.println("same user");
                		request.setAttribute("name", name);
                    	request.setAttribute("msgerror", "You can't request money to yourself");
           			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
                	}else {
                		System.out.println("diff same user");
                		String sql1 = "\r\n" + 
                				"DECLARE\r\n" + 
                				"@Requester_SSN numeric(10,0) = '"+ssn+"',\r\n" + 
                				"@amount numeric(10,2) = "+amt1+",\r\n" + 
                				"@Sender_phone nvarchar(30) = '"+phone+"',\r\n" + 
                				"@Sender_email nvarchar(30) = '"+email+"',\r\n" + 
                				"@Memo nvarchar(50) = '"+memo+"'\r\n" + 
                				"\r\n" + 
                				"\r\n" + 
                				"if exists (select * from elec_address where identifier in(@Sender_phone,@Sender_email))\r\n" + 
                				"BEGIN\r\n" + 
                				"	if ((select balance from user_account where phone_number = @Sender_phone) >= @amount)\r\n" + 
                				"	BEGIN\r\n" + 
                				"		insert into [dbo].[request_transaction](amount,datetime,memo,SSN,status)\r\n" + 
                				"		values(@amount,SYSDATETIME(),@Memo,@Requester_SSN,'Completed')\r\n" + 
                				"\r\n" + 
                				"		insert into [dbo].[request_transaction_multiple](RTid,identifier,percentage)\r\n" + 
                				"		values((select max(RTid) from request_transaction),@Sender_phone,100)\r\n" + 
                				"\r\n" + 
                				"		update user_account\r\n" + 
                				"		set balance = balance - @amount\r\n" + 
                				"		where phone_number = @Sender_phone\r\n" + 
                				"\r\n" + 
                				"		update user_account\r\n" + 
                				"		set balance = balance + @amount\r\n" + 
                				"		where SSN = @Requester_SSN\r\n" + 
                				"	END\r\n" + 
                				"	else\r\n" + 
                				"	BEGIN\r\n" + 
                				"		insert into [dbo].[request_transaction](amount,datetime,memo,SSN,status)\r\n" + 
                				"		values(@amount,SYSDATETIME(),@Memo,@Requester_SSN,'Failed')\r\n" + 
                				"\r\n" + 
                				"		insert into [dbo].[request_transaction_multiple](RTid,identifier,percentage)\r\n" + 
                				"		values((select max(RTid) from request_transaction),@Sender_phone,100)\r\n" + 
                				"	END\r\n" + 
                				"END\r\n" + 
                				"else\r\n" + 
                				"BEGIN\r\n" + 
                				"	insert into [dbo].[request_transaction](amount,datetime,memo,SSN,status)\r\n" + 
                				"	values(@amount,SYSDATETIME(),@Memo,@Requester_SSN,'Pending')\r\n" + 
                				"\r\n" + 
                				"	insert into [dbo].[request_transaction_multiple](RTid,identifier,percentage)\r\n" + 
                				"	values((select max(RTid) from request_transaction),@Sender_phone,100)\r\n" + 
                				"END";
                    	
                    		stmt.execute(sql1);
                    		request.setAttribute("name", name);
                        	request.setAttribute("msg", "Money Request Sent");
               			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
                	}            		
            	}else {
            		System.out.println("No user");
            		String sql1 = "\r\n" + 
            				"DECLARE\r\n" + 
            				"@Requester_SSN numeric(10,0) = '"+ssn+"',\r\n" + 
            				"@amount numeric(10,2) = "+amt1+",\r\n" + 
            				"@Sender_phone nvarchar(30) = '"+phone+"',\r\n" + 
            				"@Sender_email nvarchar(30) = '"+email+"',\r\n" + 
            				"@Memo nvarchar(50) = '"+memo+"'\r\n" + 
            				"\r\n" + 
            				"\r\n" + 
            				"if exists (select * from elec_address where identifier in(@Sender_phone,@Sender_email))\r\n" + 
            				"BEGIN\r\n" + 
            				"	if ((select balance from user_account where phone_number = @Sender_phone) >= @amount)\r\n" + 
            				"	BEGIN\r\n" + 
            				"		insert into [dbo].[request_transaction](amount,datetime,memo,SSN,status)\r\n" + 
            				"		values(@amount,SYSDATETIME(),@Memo,@Requester_SSN,'Completed')\r\n" + 
            				"\r\n" + 
            				"		insert into [dbo].[request_transaction_multiple](RTid,identifier,percentage)\r\n" + 
            				"		values((select max(RTid) from request_transaction),@Sender_phone,100)\r\n" + 
            				"\r\n" + 
            				"		update user_account\r\n" + 
            				"		set balance = balance - @amount\r\n" + 
            				"		where phone_number = @Sender_phone\r\n" + 
            				"\r\n" + 
            				"		update user_account\r\n" + 
            				"		set balance = balance + @amount\r\n" + 
            				"		where SSN = @Requester_SSN\r\n" + 
            				"	END\r\n" + 
            				"	else\r\n" + 
            				"	BEGIN\r\n" + 
            				"		insert into [dbo].[request_transaction](amount,datetime,memo,SSN,status)\r\n" + 
            				"		values(@amount,SYSDATETIME(),@Memo,@Requester_SSN,'Failed')\r\n" + 
            				"\r\n" + 
            				"		insert into [dbo].[request_transaction_multiple](RTid,identifier,percentage)\r\n" + 
            				"		values((select max(RTid) from request_transaction),@Sender_phone,100)\r\n" + 
            				"	END\r\n" + 
            				"END\r\n" + 
            				"else\r\n" + 
            				"BEGIN\r\n" + 
            				"	insert into [dbo].[request_transaction](amount,datetime,memo,SSN,status)\r\n" + 
            				"	values(@amount,SYSDATETIME(),@Memo,@Requester_SSN,'Pending')\r\n" + 
            				"\r\n" + 
            				"	insert into [dbo].[request_transaction_multiple](RTid,identifier,percentage)\r\n" + 
            				"	values((select max(RTid) from request_transaction),@Sender_phone,100)\r\n" + 
            				"END";
                	
                		stmt.execute(sql1);
                		request.setAttribute("name", name);
                    	request.setAttribute("msgerror", "Transaction Pending, Wallet User not Found!!");
           			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
            	}            
                	 
        		con.close(); 
                
    	    }
            catch (SQLException ex) {
            	request.setAttribute("msgerror", "Transaction Failed, Requested User has no bank account.");
    			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
            }
            catch (Exception ex) {System.out.println(ex);}
        	
        }
        if (option.equals("home")) {
        	request.setAttribute("name", name);
       		request.getRequestDispatcher("homepage.jsp").forward(request, response); 
        }
	}

}
