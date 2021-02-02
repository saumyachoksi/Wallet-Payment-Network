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

@WebServlet("/bstur")
public class bstur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public bstur() {
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
        
        String seltype = request.getParameter("opt");
        
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
            
            ArrayList<String> myarray = new ArrayList<String>(); 
            if(seltype.equals("sent")) {
            	String s1 = "DECLARE\r\n" + 
            			"@SSN numeric(10,0) = "+ssn+"\r\n" + 
            			"\r\n" + 
            			"--User who has received the maxium amount of money\r\n" + 
            			"\r\n" + 
            			"select top 1 b.first_name+' '+b.last_name Name,sum(amount) Total_Amount_Received from send_transaction a\r\n" + 
            			"left join user_account b on a.identifier = b.phone_number\r\n" + 
            			"where a.SSN = @SSN and status = 'Completed'\r\n" + 
            			"group by b.first_name+' '+b.last_name\r\n" + 
            			"order by sum(amount) desc";
            	
            	ResultSet rs = stmt.executeQuery(s1);
            	
                while ( rs.next()) {
                    myarray.add(rs.getString(1));
                    myarray.add(rs.getString(2));
               }
            	request.setAttribute("amount", myarray);
            	request.setAttribute("answer", "sent");
    			request.getRequestDispatcher("bestuser.jsp").forward(request, response);
            }
            
            if(seltype.equals("received")) {
            	//System.out.println("recieved");
            	String s2 = "DECLARE\r\n" + 
            			"@SSN numeric(10,0) = "+ssn+"\r\n" + 
            			"\r\n" + 
            			"select top 1 c.first_name+' '+c.last_name Name, sum(amount) Total_Amount_sent from request_transaction a\r\n" + 
            			"join request_transaction_multiple b on a.RTid=b.RTid\r\n" + 
            			"left join user_account c on b.identifier = c.phone_number\r\n" + 
            			"where a.SSN = @SSN and status = 'Completed'\r\n" + 
            			"group by c.first_name+' '+c.last_name\r\n" + 
            			"order by sum(amount) desc";
            	
            	ResultSet rs = stmt.executeQuery(s2);
                while ( rs.next()) {
                    myarray.add(rs.getString(1));
                    myarray.add(rs.getString(2));
               }
            	request.setAttribute("amount", myarray);
            	request.setAttribute("answer", "recieved");
    			request.getRequestDispatcher("bestuser.jsp").forward(request, response);
            }            
	}catch (SQLException ex) {
        	request.setAttribute("msgerror", "Error Occurred in Transactions per month");
			 	request.getRequestDispatcher("bestuser.jsp").forward(request, response);
        }
        catch (Exception ex) {System.out.println(ex);}
	}
}
