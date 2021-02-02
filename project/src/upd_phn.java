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

@WebServlet("/upd_phn")
public class upd_phn extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public upd_phn() {
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
        
        String oldphn = request.getParameter("oldphn");
        oldphn = oldphn.trim();
        String phone = request.getParameter("phone");
        phone = phone.trim();
        String option = request.getParameter("opt");
        
        if(option.equals("update")) {
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
                	String sql3 ="update [dbo].[elec_address]\r\n" + 
                			"set identifier = '"+phone+"'\r\n" + 
                			"where identifier = '"+oldphn+"'\r\n" + 
                			"\r\n" + 
                			"update [dbo].[send_transaction]\r\n" + 
                			"set identifier = '"+phone+"'\r\n" + 
                			"where identifier = '"+oldphn+"'\r\n" + 
                			"\r\n" + 
                			"update [dbo].[request_transaction_multiple]\r\n" + 
                			"set identifier = '"+phone+"'\r\n" + 
                			"where identifier = '"+oldphn+"'\r\n" + 
                			"";
                	stmt.executeUpdate(sql3);
                	request.setAttribute("name", name);
                    request.setAttribute("msg", "Phone Number Updated Successfull");
               		request.getRequestDispatcher("homepage.jsp").forward(request, response);    
        		con.close(); 
                
    	    }
            catch (SQLException ex) {
            	request.setAttribute("msgerror", "Error Occurred in Updation of Phone Number");
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
