import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/userinfo")
public class userinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public userinfo() {
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
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY, 
                    ResultSet.HOLD_CURSORS_OVER_COMMIT);
            
            ResultSet rs = null;            
            
            float bal = 0;
            String phn = null;
            //System.out.println("Selecting bal, phn");
            String sql = "Select balance, phone_number from user_account where SSN ='"+ ssn + "'";
            rs = stmt.executeQuery(sql);
            //System.out.println(sql);
            while ( rs.next()) {
            	bal = (float)rs.getFloat(1);
            	phn = (String)rs.getString(2);
            	//System.out.println("Selecting bal, phn");
            }
            
            //System.out.println("Selecting bankid");
            ArrayList<String> myarray = new ArrayList<String>();
            String sql2 = "select bank_id, ba_number, balance from user_account where SSN = '"+ ssn +"'";
            String sql3 = "select bank_id, ba_number, balance from has_additional where SSN = '"+ ssn +"'";
            String sql4 = sql2 +" union " + sql3;
            rs = null;
            rs = stmt.executeQuery(sql4);
            
            while ( rs.next()) {
                 for(int q = 1; q < 4; q++) {
                	 myarray.add(rs.getString(q));
                	 //System.out.println("Selecting bankid");
                 }
            }
            
            //System.out.println("selecting email");
            ArrayList<String> myemail = new ArrayList<String>();
            String s2 = "select email_address from email where SSN = '"+ ssn +"'";
            rs = null;
            rs = stmt.executeQuery(s2);
            
            while ( rs.next()) {
                	 myemail.add(rs.getString(1)); 
                	 //System.out.println("selecting email");
            }
            request.setAttribute("arr_email", myemail);           
            request.setAttribute("arr", myarray);
            request.setAttribute("name", name);
        	request.setAttribute("bal",bal );
        	request.setAttribute("phn",phn );
    		request.getRequestDispatcher("accinfo.jsp").forward(request, response);
            con.close(); 
            
	    }catch (Exception ex) {System.out.println(ex);}
        
        
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}

}
