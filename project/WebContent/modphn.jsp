<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="style.css">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<style>


/* Float cancel and signup buttons and add an equal width */
.verifybtn, .cancelbtn {
  float: left;
  width: 50%;
}

/* Extra styles for the cancel button */
.cancelbtn {
  padding: 14px 20px;
  background-color: #f44336;
}

input[type=text], input[type=password], input[type=tel] {
    width: 97%;
    padding: 15px;
    margin: 5px 0 22px 0;
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
		String phn = (String)request.getAttribute("phoneno");
		out.println("<form action=\"http://localhost:8080/project/upd_phn\" method=\"post\" >");
		out.println("<fieldset>");
		out.println("	<legend>Update Your Phone Number</legend>");
		out.println("	<p>");
		out.println("	  <label>Phone Number</label>");
		out.println("<input type=\"hidden\" name=\"oldphn\" value=\""+phn+"\" </input>");
		out.println("	  <input type = \"tel\" id = \"phone_number\" name = \"phone\" value=\""+phn+"\"/>");
		out.println("	</p>");
		out.println("	</fieldset>");
		out.println("	<div class=\"clearfix\">");
		out.println("        <button type=\"submit\" name = \"opt\" value=\"home\" class=\"cancelbtn\">Cancel</button>");
		out.println("        <button type=\"submit\" name = \"opt\" value=\"update\" class=\"verifybtn\">Update Number</button>");
		out.println("    </div>");
		out.println("</form>");
	%>
	</div>
</div>
</div>

</body>
</html>