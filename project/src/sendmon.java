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

@WebServlet("/sendmon")
public class sendmon extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public sendmon() {
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
        
        if(option.equals("send")) {
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
                
                String bid = null, bno = null, bal = null;
                float at = 0;
                String dropvalue = request.getParameter("drop");
                String[] arr = dropvalue.split(" ");    
                
                bid = arr[0];
                bno = arr[1];
                bal = arr[2];
                at = Float.parseFloat(bal);               
                
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
                while ( rs.next()) {
                    s_ssn = (String)rs.getString(1);
               }
                
                if(amt1>at) {
                	request.setAttribute("name", name);
                	request.setAttribute("msgerror", "Not Sufficient Balance / No Bank Account Linked. Try Again");
       			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
                } else {
                	if(s_ssn != null) {
                		if(s_ssn.equals(ssn)) {
                        	request.setAttribute("name", name);
                            request.setAttribute("msgerror", "You can't send money to yourself");
                   			request.getRequestDispatcher("homepage.jsp").forward(request, response);
                        }else {
                        	
                        	String sql1 ="DECLARE\r\n" + 
                            		"@Sender_SSN numeric(10,0) = "+ssn+",\r\n" + 
                            		"@amount numeric(10,2) = "+amt1+",\r\n" + 
                            		"@Receiver_phone nvarchar(30) = '"+phone+"',\r\n" + 
                            		"@Receiver_email nvarchar(30) = '"+email+"',\r\n" + 
                            		"@Sender_bank_id nvarchar(20) = '"+bid+"',\r\n" + 
                            		"@Sender_ba_number nvarchar(20) = '"+bno+"',\r\n" + 
                            		"@Memo nvarchar(50) = '"+memo+"'\r\n" + 
                                	"\r\n" + 
                                	"if exists (select * from elec_address where identifier = @Receiver_phone)\r\n" + 
                                	"BEGIN\r\n" + 
                                	"	insert into [dbo].[send_transaction](amount,datetime,memo,identifier,SSN,status)\r\n" + 
                                	"	values(@amount,SYSDATETIME(),@Memo,@Receiver_phone,@Sender_SSN,'Completed')\r\n" + 
                                	"	if((select bank_id from [dbo].[user_account] where SSN = @Sender_SSN) = @Sender_bank_id and \r\n" + 
                                	"	   (select ba_number from [dbo].[user_account] where SSN = @Sender_SSN) = @Sender_ba_number)\r\n" + 
                                	"	   BEGIN\r\n" + 
                                	"			update user_account\r\n" + 
                                	"			set balance = balance - @amount\r\n" + 
                                	"			where SSN = @Sender_SSN\r\n" + 
                                	"	   END\r\n" + 
                                	"	else \r\n" + 
                                	"		BEGIN\r\n" + 
                                	"			update has_additional\r\n" + 
                                	"			set balance = balance - @amount\r\n" + 
                                	"			where SSN = @Sender_SSN and\r\n" + 
                                	"				  bank_id = @Sender_bank_id and\r\n" + 
                                	"				  ba_number = @Sender_ba_number\r\n" + 
                                	"		END\r\n" + 
                                	"	update user_account\r\n" + 
                                	"	set balance = balance + @amount\r\n" + 
                                	"	where phone_number = @Receiver_phone\r\n" + 
                                	"END\r\n" + 
                                	"else\r\n" + 
                                	"BEGIN\r\n" + 
                                	"	insert into [dbo].[send_transaction](amount,datetime,memo,identifier,SSN,status)\r\n" + 
                                	"	values(@amount,SYSDATETIME(),@Memo,@Receiver_phone,@Sender_SSN,'Pending')\r\n" + 
                                	"END";
                        	
                        		stmt.execute(sql1);
                        		request.setAttribute("name", name);
                            	request.setAttribute("msg", "Money Sent Successfully");
                   			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);  
                        }
                	}else {
                    		String sql1 = "DECLARE\r\n" + 
                            		"@Sender_SSN numeric(10,0) = "+ssn+",\r\n" + 
                            		"@amount numeric(10,2) = "+amt1+",\r\n" + 
                            		"@Receiver_phone nvarchar(30) = '"+phone+"',\r\n" + 
                            		"@Receiver_email nvarchar(30) = '"+email+"',\r\n" + 
                            		"@Sender_bank_id nvarchar(20) = '"+bid+"',\r\n" + 
                            		"@Sender_ba_number nvarchar(20) = '"+bno+"',\r\n" + 
                            		"@Memo nvarchar(50) = '"+memo+"'\r\n" + 
                                	"\r\n" + 
                                	"if exists (select * from elec_address where identifier = @Receiver_phone)\r\n" + 
                                	"BEGIN\r\n" + 
                                	"	insert into [dbo].[send_transaction](amount,datetime,memo,identifier,SSN,status)\r\n" + 
                                	"	values(@amount,SYSDATETIME(),@Memo,@Receiver_phone,@Sender_SSN,'Completed')\r\n" + 
                                	"	if((select bank_id from [dbo].[user_account] where SSN = @Sender_SSN) = @Sender_bank_id and \r\n" + 
                                	"	   (select ba_number from [dbo].[user_account] where SSN = @Sender_SSN) = @Sender_ba_number)\r\n" + 
                                	"	   BEGIN\r\n" + 
                                	"			update user_account\r\n" + 
                                	"			set balance = balance - @amount\r\n" + 
                                	"			where SSN = @Sender_SSN\r\n" + 
                                	"	   END\r\n" + 
                                	"	else \r\n" + 
                                	"		BEGIN\r\n" + 
                                	"			update has_additional\r\n" + 
                                	"			set balance = balance - @amount\r\n" + 
                                	"			where SSN = @Sender_SSN and\r\n" + 
                                	"				  bank_id = @Sender_bank_id and\r\n" + 
                                	"				  ba_number = @Sender_ba_number\r\n" + 
                                	"		END\r\n" + 
                                	"	update user_account\r\n" + 
                                	"	set balance = balance + @amount\r\n" + 
                                	"	where phone_number = @Receiver_phone\r\n" + 
                                	"END\r\n" + 
                                	"else\r\n" + 
                                	"BEGIN\r\n" + 
                                	"	insert into [dbo].[send_transaction](amount,datetime,memo,identifier,SSN,status)\r\n" + 
                                	"	values(@amount,SYSDATETIME(),@Memo,@Receiver_phone,@Sender_SSN,'Pending')\r\n" + 
                                	"END";
                        	
                        		stmt.execute(sql1);
                        		request.setAttribute("name", name);
                            	request.setAttribute("msgerror", "Transaction Pending, Wallet User Not Found !!");
                   			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);                   	
                    }                	
                }
                
                
                              
                
        		con.close(); 
                
    	    }
            catch (SQLException ex) {
            	request.setAttribute("msgerror", "Error Occurred in sending money");
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
