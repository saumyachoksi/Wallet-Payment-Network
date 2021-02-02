<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="style.css">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

 
</head>
<body>
<div class="wallet homepage">
    <div class="container">
	<% 
	out.println("<nav id=\"nav\">");
	out.println("    <ul id=\"navigation\">");
	out.println("        <li><a href=\"#\" >Home</a></li>");
	out.println("        <li><a href=\"#\" class=\"active\">Profile and Settings &raquo;</a>");
	out.println("        	<ul>");
	out.println("	            <li><a href=\"http://localhost:8080/project/userinfo\">Account Information</a></li>");
	out.println("	            <li><a href=\"#home\">Modify Personal Details &raquo;</a>");
	out.println("		            <ul>");
	out.println("		            	<li><a href=\"http://localhost:8080/project/dis_bankacc\">Link/Unlink Bank Account</a></li>");
	out.println("		                <li><a href=\"http://localhost:8080/project/dis_email\">Add/remove email address</a></li>");
	out.println("		                <li><a href=\"http://localhost:8080/project/dis_phn\">Update phone number</a></li>");
	out.println("		            </ul>");
	out.println("		        </li>");
	out.println("	        </ul>");
	out.println("        </li>");
	out.println("        <li><a href=\"#\">Payments &raquo;</a>");
	out.println("        	<ul>  ");
	out.println("        		<li><a href=\"http://localhost:8080/project/valibank\">Send Money</a></li>");
	out.println("  				<li><a href=\"http://localhost:8080/project/valibank2\">Request Money</a></li>");
	out.println("  				<li><a href=\"search.jsp\">Search Transactions</a></li>");
	out.println("  			</ul>");
	out.println("  		</li>");
	out.println("  		<li><a href=\"#\">Statement &raquo;</a>");
	out.println("  			<ul>");
	out.println("				<li><a href=\"sortdaterange.jsp\">Sort By Date Range</a></li>");
	out.println("				<li><a href=\"amtmonth.jsp\">Transactions per month</a></li>");
	out.println("				<li><a href=\"largetrans.jsp\">Large Transactions</a></li>");
	out.println("				<li><a href=\"bestuser.jsp\">Best Users</a></li>");
	out.println("  			</ul>");
	out.println("  		</li>");
	out.println("<button class=\"signoutbtn\" onclick=\"window.location.href='http://localhost:8080/project/signout'\">Sign Out <i class='fas fa-sign-out-alt'></i></button>");
	out.println("  	</ul>");
	out.println("</nav>");
		%>
	<div style="padding-left:16px;margin-top: 85px;">
	<% 
	String name = (String)request.getAttribute("name");
	Float bal = (Float)request.getAttribute("bal");
	String phn = (String)request.getAttribute("phn");
	
	out.println("<h3 style=\"text-align: center;\">Name : "+name+"</h3>");
	out.println("  <h3 style=\"text-align: center;\">Current Account Balance : "+bal+"</h3>");
	out.println("  <h3 style=\"text-align: center;\">Phone Number : "+phn+"</h3>");

	ArrayList<String> myemail = (ArrayList<String>)request.getAttribute("arr_email");
	
	out.println("<fieldset>");
	out.println("	<legend>Email Accounts Linked</legend>");
	out.println("	<table id=\"email\">");
	out.println("	  <tr>");
	out.println("	    <th>Email Address</th>");
	out.println("	  </tr>");
	
	 for (int i=0; i<myemail.size(); i+=1) {
		 out.println("<tr>");
		 out.println("<td>"+ myemail.get(i) +"</td>");
		 out.println("</tr>");
	 }
	
	out.println("	</table>");
	out.println("	</fieldset>");
		
		ArrayList<String> arr = (ArrayList<String>)request.getAttribute("arr");
		out.println("<fieldset style=\"margin: 15px 0;\">");
		out.println("	<legend>Bank Accounts Linked</legend>");
		out.println("<table id=\"bankacc\">");
		out.println("	  <tr>");
		out.println("	    <th>Bank ID</th>");
		out.println("	    <th>Bank Account Number</th>");
		out.println("	    <th>Bank Balance</th> ");
		out.println("	  </tr>");
		for (int i=0; i<arr.size(); i+=3) {
			out.println("<tr>");
			out.println("<td>"+ arr.get(i) +"</td>");
			out.println("<td>"+ arr.get(i+1) +"</td>");
			out.println("<td>"+ arr.get(i+2) +"</td>");
			out.println("</tr>");				
		}
		out.println("	</table>");
		out.println("</fieldset>");
		out.println("</div>");
	%>
	</div>
</div>
</div>

</body>
</html>