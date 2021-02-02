import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/adrm_email")
public class adrm_email extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public adrm_email() {
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
        
        String mail = request.getParameter("email");
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
            
            if(option.equals("add")) {            	
            	String sql1 = "insert into elec_address values('"+mail+"',1)";
            	String sql2 = "insert into email values('"+mail+"',"+ssn+",0)";
            	
            	stmt.executeUpdate(sql1);
            	stmt.executeUpdate(sql2);
            	request.setAttribute("name", name);
                request.setAttribute("msg", "Email Added Successfull");
           		request.getRequestDispatcher("homepage.jsp").forward(request, response);                   	
            }
            
            if(option.equals("remove")) {                
                String sql3 = "DECLARE\r\n" + 
                		"@email nvarchar(50) = '"+mail+"',\r\n" + 
                		"@SSN numeric(10,0),\r\n" + 
                		"@new_email nvarchar(50)\r\n" + 
                		"\r\n" + 
                		"select @SSN = SSN from email where email_address = @email\r\n" + 
                		"drop table if exists #t\r\n" + 
                		"select top 1 email_address into #t from email where SSN = @SSN and is_primary = 0\r\n" + 
                		"select @new_email = email_address from #t\r\n" + 
                		"\r\n" + 
                		"if((select is_primary from email where email_address = @email) = 1)\r\n" + 
                		"	BEGIN\r\n" + 
                		"		delete from [dbo].[email]\r\n" + 
                		"		where email_address = @email\r\n" + 
                		"\r\n" + 
                		"		update email\r\n" + 
                		"		set is_primary = 1\r\n" + 
                		"		where email_address = @new_email\r\n" + 
                		"	END\r\n" + 
                		"else\r\n" + 
                		"delete from [dbo].[email]\r\n" + 
                		"where email_address = @email\r\n" + 
                		"\r\n" + 
                		"delete from [dbo].[elec_address]\r\n" + 
                		"where identifier = @email\r\n" + 
                		"";
                
                stmt.execute(sql3);
            	request.setAttribute("name", name);
                request.setAttribute("msg", "Email Removed Successfull");
       			request.getRequestDispatcher("homepage.jsp").forward(request, response);
                
        }          
    		con.close(); 
            
	    }
        catch (SQLException ex) {
        	request.setAttribute("msgerror", "Error Occurred in ADD/Remove Email");
			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
        }
        catch (Exception ex) {System.out.println(ex);}
        
	}

}
