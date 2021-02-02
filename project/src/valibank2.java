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

@WebServlet("/valibank2")
public class valibank2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public valibank2() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    HttpSession session = request.getSession(false);
	    
        String ssn =(String)session.getAttribute("ssn"); 
        String name= (String)session.getAttribute("name");
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
            			"@SSN numeric(10,0) = '"+ssn+"'\r\n" + 
            			"\r\n" + 
            			"select count(1) from \r\n" + 
            			"(select bank_id,ba_number,balance from [dbo].[user_account] where SSN = @SSN and ba_number is not null\r\n" + 
            			"union\r\n" + 
            			"select bank_id,ba_number,balance from [dbo].[has_additional] where SSN = @SSN) a";
            	String n = null;
            	ResultSet rs = stmt.executeQuery(sql1);
            	while ( rs.next()) {
            		n = rs.getString(1);
               }
            	//System.out.println(n);
            	//request.setAttribute("name", name);
                //request.setAttribute("msg", "Account Linked Successfull");
            	if(n.equals("0")) {
            		request.setAttribute("msgerror", "Link Bank Account");
            		request.getRequestDispatcher("homepage.jsp").forward(request, response);
            	}else {
            		//System.out.println("greater");
            		request.getRequestDispatcher("requestmoney.jsp").forward(request, response);
            	}
       			

    		con.close();                 
	    }
        catch (SQLException ex) {
        	request.setAttribute("name", name);
        	request.setAttribute("msgerror", "Error Occurred in Validating Bank");
			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
        }
        catch (Exception ex) {System.out.println(ex);}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}

}
