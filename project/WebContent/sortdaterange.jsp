<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="style.css">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<style>


input[type=date], select{
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
	<%
	out.println("<nav id=\"nav\">");
	out.println("    <ul id=\"navigation\">");
	out.println("        <li><a href=\"#\">Home</a></li>");
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
		out.println("<form action=\"http://localhost:8080/project/sortdate\" method=\"post\" >");
		out.println("	<fieldset>");
		out.println("			<legend> Statements in Date Range Between </legend>");
		out.println("		<div class=\"statement-search-container\">");
		out.println("			  <label for=\"trans\"></label>");
		out.println("				<div id=\"div1\" style='display: inline-block;'>");
		out.println("					Start Date: <input type=\"date\" name =\"start\"/> End Date: <input type=\"date\" name=\"end\" value=\"2020-05-06\">");
		out.println("					<select id=\"state_type\" name=\"select\" required>");
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
			String amt = (String)request.getAttribute("amount");
			if(opt != null){
				out.println("<br><fieldset>");
				out.println("			<legend> Total amount based on range of dates </legend>");
				out.println("			<p> <b> Total amount of money "+opt+" :: "+amt+"</b> </p>");
				out.println("	</fieldset>");
			}
		%>
	</div>
</div>
</div>

</body>
</html>