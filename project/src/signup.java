import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    String[] name = new String[10];
	    String[] param = new String[]{"fname", "lname", "ssn", "mail", "phone","uname","psw"};
	    
	    for(int i = 0; i < 7; ++i) {
            name[i] = request.getParameter(param[i]);
        }
		
		/*
		out.println("<br> fname :: "+ name[0]);
		out.println("<br> lname :: "+ name[1]);
		out.println("<br> ssn :: "+ name[2]);
		out.println("<br> mail :: "+ name[3]);
		out.println("<br> phn :: "+ name[4]);
		out.println("<br> user :: "+ name[5]);
		out.println("<br> password ::"+ name[6]); */
		
		
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
            
            String sql1 = "DECLARE\r\n" + 
            		"@SSN numeric(10,0) = '"+name[2]+"',\r\n" + 
            		"@phone nvarchar(30) = '"+name[4]+"',\r\n" + 
            		"@email nvarchar(30) = '"+name[3]+"',\r\n" + 
            		"@fname nvarchar(30) = '"+name[0]+"',\r\n" + 
            		"@lname nvarchar(30) = '"+name[1]+"',\r\n" + 
            		"@username nvarchar(30) = '"+name[5]+"',\r\n" + 
            		"@password nvarchar(30) = '"+name[6]+"'\r\n" + 
            		"\r\n" + 
            		"insert into [dbo].[elec_address](identifier,verified)\r\n" + 
            		"values(@phone,1),(@email,1)\r\n" + 
            		"\r\n" + 
            		"insert into [dbo].[phone](phone_no)\r\n" + 
            		"values(@phone)\r\n" + 
            		"\r\n" + 
            		"insert into [dbo].[user_account](SSN,first_name,last_name,phone_number,balance,bank_id,ba_number,ba_verified,username,password)\r\n" + 
            		"values(@SSN,@fname,@lname,@phone,NULL,NULL,NULL,NULL,@username,@password)\r\n" + 
            		"\r\n" + 
            		"insert into [dbo].[email](email_address,SSN,is_primary)\r\n" + 
            		"values(@email,@SSN,1)";
            
            
            
            stmt.execute(sql1);            
            request.getRequestDispatcher("loginpage.jsp").forward(request, response);
            //System.out.println(sql1);
            //System.out.println(sql2);
            //System.out.println(sql3);
            
            //String sql2 = "insert into login values('sau1','sau1')";
            //ResultSet rs2 = stmt.executeQuery(sql2);
            //System.out.println(rs2);
            //int i = stmt.executeUpdate(sql2);
            //System.out.println("i::" + i);
            
           /* while ( rs.next())
            	out.println(rs.getString(1));   */ 
    		con.close(); 
            
	    }catch (Exception ex) {System.out.println(ex);}
	}

}
