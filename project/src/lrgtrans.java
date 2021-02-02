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

@WebServlet("/lrgtrans")
public class lrgtrans extends HttpServlet {
	private static final long serialVersionUID = 1L;
  public lrgtrans() {
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
        
        String month = request.getParameter("month");
        int mth = Integer.parseInt(month);
        
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
            
            ArrayList<String> myarray = new ArrayList<String>(); 
            ArrayList<String> myarray1 = new ArrayList<String>(); 
            
            String s1 = "DECLARE\r\n" + 
                    "@month int = "+mth+",\r\n" + 
                    "@SSN numeric(10,0) = "+ssn+"\r\n" + 
                    "\r\n" + 
                    "--Send Transactions\r\n" + 
                    "select b.first_name+' '+b.last_name as Sent_to,a.amount,a.memo,a.status  from send_transaction a\r\n" +  
                    "join user_account b on a.identifier = b.phone_number\r\n" + 
                    "where amount = (select max(amount) from send_transaction \r\n" + 
                    "                where MONTH(datetime) = @month and\r\n" + 
                    "				      status = 'Completed') and \r\n" + 
                    "	  MONTH(datetime) = @month and\r\n" + 
                    "	  status = 'Completed' and\r\n" + 
                    "	  a.SSN = @SSN\r\n";
            	
            rs = stmt.executeQuery(s1);
            while ( rs.next()) {
                myarray.add(rs.getString(1));
                myarray.add(rs.getString(2));
                myarray.add(rs.getString(3));
                myarray.add(rs.getString(4));
           }
            
            String s2 = "DECLARE\r\n" + 
                    "@month int = "+mth+",\r\n" + 
                    "@SSN numeric(10,0) = "+ssn+"\r\n" + 
            		"select c.first_name+' '+c.last_name as Requested_from,a.amount,a.memo,a.status  from request_transaction a\r\n" +  
                    "join request_transaction_multiple b on a.RTid = b.RTid\r\n" + 
                    "join user_account c on b.identifier = c.phone_number\r\n" + 
                    "where amount = (select MAX(amount) from request_transaction\r\n" + 
                    "				where MONTH(datetime) = @month and\r\n" + 
                    "				      status = 'Completed') and\r\n" + 
                    "	  MONTH(datetime) = @month and\r\n" + 
                    "	  status = 'Completed' and\r\n" + 
                    "	  a.SSN = @SSN";
            rs = null;
            rs = stmt.executeQuery(s2);
            while ( rs.next()) {
                myarray1.add(rs.getString(1));
                myarray1.add(rs.getString(2));
                myarray1.add(rs.getString(3));
                myarray1.add(rs.getString(4));
           }
             
            request.setAttribute("arr1", myarray);
            request.setAttribute("arr2", myarray1);
            request.setAttribute("answer", "done");
    		request.getRequestDispatcher("largetrans.jsp").forward(request, response);
            con.close();
            
	    }
        catch (SQLException ex) {
        	request.setAttribute("msgerror", "Error Occurred in Transactions per month");
			 	request.getRequestDispatcher("homepage.jsp").forward(request, response);
        }
        catch (Exception ex) {System.out.println(ex);}
        
	}

}
