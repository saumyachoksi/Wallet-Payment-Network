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


@WebServlet("/unlk_bankacc")
public class unlk_bankacc extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public unlk_bankacc() {
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
	    
	    String bid = request.getParameter("bankid");
	    String bno = request.getParameter("bankno");
	    String getbal = request.getParameter("bankbal");
	    float bal = Float.parseFloat(getbal);
	    String option = request.getParameter("opt");
	    
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
                
                
                if(option.equals("link")) {                	
                	String sql1 = "DECLARE \r\n" + 
                			"@SSN numeric(10,0)= '"+ssn+"',\r\n" + 
                			"@bank_id varchar(20) = '"+bid+"',\r\n" + 
                			"@ba_number varchar(20) = '"+bno+"',\r\n" + 
                			"@balance numeric(20,2) = "+bal+",\r\n" + 
                			"@count1 numeric(10,0),\r\n" + 
                			"@count2 numeric(10,0)\r\n" + 
                			"\r\n" + 
                			"select @count1 = count(1) from bank_account\r\n" + 
                			"\r\n" + 
                			"insert into [dbo].[bank_account](bank_id,ba_number)\r\n" + 
                			"values(@bank_id,@ba_number)\r\n" + 
                			"\r\n" + 
                			"select @count2 = count(1) from bank_account\r\n" + 
                			"\r\n" + 
                			"if((@count2 - @count1) > 0)\r\n" + 
                			"BEGIN\r\n" + 
                			"	if((select bank_id from user_account where SSN = @SSN) is NULL)\r\n" + 
                			"		update user_account\r\n" + 
                			"		set bank_id = @bank_id,\r\n" + 
                			"			ba_number = @ba_number,\r\n" + 
                			"			ba_verified = 1,\r\n" + 
                			"			balance = @balance\r\n" + 
                			"		where SSN = @SSN\r\n" + 
                			"	else\r\n" + 
                			"		insert into has_additional(SSN,bank_id,ba_number,balance,verified)\r\n" + 
                			"		values(@SSN,@bank_id,@ba_number,@balance,1)\r\n" + 
                			"END";
                	
                		stmt.execute(sql1);
                		request.setAttribute("name", name);
                    	request.setAttribute("msg", "Account Linked Successfull");
           			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);

                }
                
                
                if(option.equals("unlink")) {                    
                    String sql = "DECLARE \r\n" + 
                    		"@SSN numeric(10,0)= '"+ssn+"',\r\n" + 
                    		"@bank_id varchar(20) = '"+bid+"',\r\n" + 
                    		"@ba_number varchar(20) = '"+bno+"'\r\n" + 
                            "\r\n" + 
                            "if((select bank_id from [dbo].[user_account] where SSN = @SSN) = @bank_id and \r\n" + 
                            "   (select ba_number from [dbo].[user_account] where SSN = @SSN) = @ba_number)\r\n" + 
                            "   BEGIN\r\n" + 
                            "   if((select count(1) from [dbo].[has_additional] where SSN = @SSN) > 0)\r\n" + 
                            "		BEGIN\r\n" + 
                            "		update user_account\r\n" + 
                            "		set bank_id = (select top 1 bank_id from has_additional where SSN = @SSN),\r\n" + 
                            "			ba_number = (select top 1 ba_number from has_additional where SSN = @SSN),\r\n" + 
                            "			balance = (select top 1 balance from has_additional where SSN = @SSN)\r\n" + 
                            "		where SSN = @SSN\r\n" + 
                            "		delete from has_additional where bank_id = (select bank_id from user_account where SSN = @SSN) and\r\n" + 
                            "										 ba_number = (select ba_number from user_account where SSN = @SSN)\r\n" + 
                            "		delete from bank_account where bank_id = @bank_id and ba_number = @ba_number\r\n" + 
                            "		END\r\n" + 
                            "	else\r\n" + 
                            "		BEGIN\r\n" + 
                            "		update user_account\r\n" + 
                            "		set bank_id = NULL,\r\n" + 
                            "			ba_number = NULL,\r\n" + 
                            "			balance = 0\r\n" + 
                            "		where SSN = @SSN\r\n" + 
                            "		delete from bank_account where bank_id = @bank_id and ba_number = @ba_number\r\n" + 
                            "		END\r\n" + 
                            "	END\r\n" + 
                            "else\r\n" + 
                            "	BEGIN\r\n" + 
                            "	delete from has_additional where SSN = @SSN and bank_id = @bank_id and ba_number = @ba_number\r\n" + 
                            "	delete from bank_account where bank_id = @bank_id and ba_number = @ba_number\r\n" + 
                            "	END";
                    	stmt.execute(sql);
                		request.setAttribute("name", name);
                    	request.setAttribute("msg", "Account Un-Linked Successfull");
           			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);                    
            }          
        		con.close();                 
    	    }
            catch (SQLException ex) {
            	request.setAttribute("name", name);
            	request.setAttribute("msgerror", "Error Occurred in Link/Unlink Bank Account");
   			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
            }
            catch (Exception ex) {System.out.println(ex);}
        
	}

}





