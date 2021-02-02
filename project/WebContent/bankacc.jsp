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
.linkbtn, .unlinkbtn {
  float: left;
  width: 50%;
}

/* Extra styles for the cancel button */
.unlinkbtn {
  padding: 14px 20px;
  background-color: #f44336;
}

input[type=text], input[type=password], input[type=tel], input[type=number] {
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
	<!--  <h2>Bank Accounts Linked</h2> -->
	<br>

	<%
		ArrayList<String> arr = (ArrayList<String>)request.getAttribute("arr");
			
		out.println("<fieldset>");
		out.println("<legend>Existing Bank Account Details</legend><br>");

		out.println("<table id=\"bacc\">");
		out.println("  <tr>");
		out.println("    <th>Bank ID Number</th>");
		out.println("    <th>Bank Account Number</th> ");
		out.println("	 <th>Bank Balance</th>");
		out.println("  </tr>");
		
		for (int i=0; i<arr.size(); i+=3) {
			out.println("<tr>");
			out.println("<td>"+ arr.get(i) +"</td>");
			out.println("<td>"+ arr.get(i+1) +"</td>");
			out.println("<td>"+ arr.get(i+2) +"</td>");
			out.println("</tr>");				
		}
		
		out.println("</table>");
		out.println("</fieldset>");
%>	
	<br>
	
	<%
		out.println("<form action=\"http://localhost:8080/project/unlk_bankacc\" method=\"post\" >");
	%>
		<fieldset>
		<legend>LInk/ Unlink Bank Account</legend>
		<p>
		  <label>Bank ID Number</label>
		  <input type = "text" id = "ba_id" placeholder = "Please Enter your Bank ID here" name="bankid" required />
		</p>
		<p>
		  <label>Bank Account Number</label>
		  <input type = "text" id = "ba_acc" placeholder = "Please Enter your Bank Account number which you need to Link/Unlink here" name="bankno" required/>
		</p>
		<p>
		  <label>Bank Balance</label>
		  <input type = "number" min = "0" id = "ba_bal" placeholder = "Please Enter your Balance Amount" name="bankbal" required />
		</p>
		
		<p>
		  	<label>Account Type</label>
		    <input type="radio" id="saving" name="type" value="saving">
  			<label for="saving">Saving</label>
  			<input type="radio" id="checking" name="type" value="shecking">
  			<label for="checking">Checking</label>
		</p>
		</fieldset>
		<div class="clearfix">
	        <button type="submit" name = "opt" value="unlink" class="unlinkbtn">Unlink</button>
	        <button type="submit" name = "opt" value="link" class="linkbtn">Link Button</button>
	    </div>
	</form>
	</div>
</div>
</div>

</body>
</html>