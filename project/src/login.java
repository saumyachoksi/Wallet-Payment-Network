import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public login() {
        super();
    }
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());	
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    String user = request.getParameter("user");
	    String pwd = request.getParameter("pwd"); 
	    
		String userid = null;
		String password = null;
		String fname = null;
		String ssn = null;
		float bal = 0;
	    
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
            
            String sql = "Select username, password, first_name,balance, SSN from user_account where username ='"+ (String)user + "'";
            ResultSet rs = stmt.executeQuery(sql);
            //System.out.println(sql);
            while ( rs.next()) {
            	userid = (String)rs.getString(1);    
            	password = (String)rs.getString(2);   
            	fname = (String)rs.getString(3);
            	bal = (float)rs.getFloat(4);
            	ssn = (String)rs.getString(5);
            }            	
    		con.close();
    		
    		if (user.equals(userid) && pwd.contentEquals(password)) {
    			//System.out.println("user :: "+ fname);
    			//System.out.println("ssn ::"+ ssn);
    			
    			HttpSession session = request.getSession();
    			session.setAttribute("name", fname);
    			session.setAttribute("ssn", ssn);
    			
    			request.setAttribute("message","pass");
        		request.setAttribute("name", fname);
        		request.setAttribute("bal", bal);
        		request.setAttribute("ssn", ssn);
        		request.getRequestDispatcher("homepage.jsp").forward(request, response);
    		}else {
    			request.setAttribute("loginmessage","fail");
    			request.getRequestDispatcher("loginpage.jsp").forward(request, response);
    			out.println("<br> <h1> Error </h1>");
    		}
	    }catch (Exception ex) {System.out.println(ex);} 
	}
}
