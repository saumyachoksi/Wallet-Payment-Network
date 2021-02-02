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

@WebServlet("/dis_bankbal")
public class dis_bankbal extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public dis_bankbal() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
    	response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(false);
	    
        String ssn =(String)session.getAttribute("ssn"); 
        String name= (String)session.getAttribute("name");
        
        ArrayList<String> myarray = new ArrayList<String>();
        ArrayList<String> myarr = new ArrayList<String>();
        
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
            
            //String sql1 = "insert into elec_address values('"+name[4]+"',1),('"+name[3]+"',1)";
            String sql2 = "select bank_id, ba_number, balance from user_account where SSN = '"+ ssn +"'";
            String sql3 = "select bank_id, ba_number, balance from has_additional where SSN = '"+ ssn +"'";
            String sql4 = sql2 +" union " + sql3;
           
            ResultSet rs = stmt.executeQuery(sql4);
            
            while ( rs.next()) {
                 for(int q = 1; q < 4; q++) {
                	 myarray.add(rs.getString(q));                       
                 }
            }
            
            String pg = null;
            for (int i=0; i<myarray.size(); i+=3) {
    			pg = myarray.get(i) + " " + myarray.get(i+1) + " " + myarray.get(i+2);
    			myarr.add(pg);
    		}
            
            request.setAttribute("arr1", myarr);
            request.setAttribute("ssn", ssn);
            request.setAttribute("name", name);
    		request.getRequestDispatcher("sendmoney.jsp").forward(request, response);
            
    		con.close(); 
            
	    }catch (Exception ex) {System.out.println(ex);}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}

}
