<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>

    <!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="style.css">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<style>


input[type=month], select{
	width:auto;
    padding: 15px;
    margin: 5px 10px 22px 0;
    display: inline-block;
    border: none;
    background: #f1f1f1;
}

</style>
</head>
<body>
<div class="wallet homepage">
    <div class="container">
    <% out.println("<nav id=\"nav\">");
	out.println("    <ul id=\"navigation\">");
	out.println("        <li><a href=\"#\" >Home</a></li>");
	out.println("        <li><a href=\"#\">Profile and Settings &raquo;</a>");
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
	out.println("  		<li><a href=\"#\" class=\"active\">Statement &raquo;</a>");
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
		out.println("<form action=\"http://localhost:8080/project/bstur\" method=\"post\" >");
		out.println("	<fieldset>");
		out.println("			<legend>Best User</legend>");
		out.println("		<div class=\"statement-search-container\">");
		out.println("			  <label for=\"trans\"></label>");
		out.println("				<div id=\"div1\" style='display: inline-block;'>");
		out.println("					<select id=\"state_type\" name=\"opt\" required>");
		out.println("						<option value=\"\" >Select Option</option>");
		out.println("						<option value=\"sent\" >Sent Money</option>");
		out.println("						<option value=\"received\">Received Money</option>");
		out.println("					</select>");
		out.println("				</div>");
		out.println("		      <button type=\"submit\" style='width:auto;'><i class=\"fa fa-search\"></i></button>");
		out.println("		</div>");
		out.println("	</fieldset>");
		out.println("	</form>");
		%>
		
		<%
			String opt = (String)request.getAttribute("answer");
			ArrayList<String> arr1 = (ArrayList<String>)request.getAttribute("amount");
			if(opt != null){
				out.println("<br><fieldset>");
				out.println("			<legend> Best Users who have "+opt+" the maximum total amount of money </legend>");
				
				out.println("<table id=\"email\" style=\"margin-top: 2%;\">");
				out.println("		  <tr>");
				out.println("		    <th>No</th>");
				out.println("		    <th>Name</th> ");
				out.println("		    <th>Total Amount</th> ");
				out.println("		  </tr>");
				
				for (int i=0; i<arr1.size(); i+=2) {
					out.println("<tr>");
					out.println("<td>"+ i+1 +"</td>");
					out.println("<td>"+ arr1.get(i) +"</td>");
					out.println("<td>"+ arr1.get(i+1) +"</td>");
					out.println("</tr>");				
				}		
				
				out.println("		</table>");
				out.println("	</fieldset>");
			}
		%>
		
	</div>
</div>
</div>

</body>
</html>