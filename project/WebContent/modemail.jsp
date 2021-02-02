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
			ArrayList<String> myemail = (ArrayList<String>)request.getAttribute("arr_email");
			out.println("<fieldset>");
			out.println("		<legend>Email Accounts Linked</legend>");
			out.println("		<table id=\"email\">");
			out.println("		  <tr>");
			out.println("		    <th>Email Address</th>");
			out.println("		  </tr>");
			
			 for (int i=0; i<myemail.size(); i+=1) {
				 out.println("<tr>");
	    		 out.println("<td>"+ myemail.get(i) +"</td>");
	    		 out.println("</tr>");
			 }
			
			out.println("		</table>");
			out.println("	</fieldset>");
		%>
		<br>
		<%
		out.println("<form action=\"http://localhost:8080/project/adrm_email\" method=\"post\" >");
	%>
			<fieldset style="margin: 15px 0;">
			<legend>Add or Verify Email Address</legend>
			<p>
			  <label>Email Address</label>
			  <input type = "text" id = "email_address" placeholder="Enter Email address you need to remove or add to the account." name="email"/>
			</p>
			</fieldset>
			
			<div class="clearfix">
				<button type="submit" name = "opt" value="remove" class="cancelbtn">Remove</button>
	        	<button type="submit" name = "opt" value="add" class="verifybtn">Add</button>
		    </div>
		</form>
	</div>
</div>
</div>

</body>
</html>