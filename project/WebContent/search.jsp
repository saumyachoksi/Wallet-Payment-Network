<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>

    
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="style.css">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<script type="text/javascript">
function showfield(name){
  if(name=='all')document.getElementById('div1').innerHTML='<input type="hidden" name="all">';
  else if(name=='email')document.getElementById('div1').innerHTML='<input type="email" placeholder="Search by Email" name="email">';
  else if(name=='phone')document.getElementById('div1').innerHTML='<input type="number" min="999999999" max="9999999999" placeholder="Search by Phone Number" name="phone">';
  else if(name=='type_trans')document.getElementById('div1').innerHTML='<select id="trans_type" name="opt"> <option value="sent" selected>Sent Transactions</option><option value="received">Received Transactions</option>';
  else if(name=='time_date')document.getElementById('div1').innerHTML='Start Date: <input type="date" name="start"> End Date: <input type="date" name="end" value="2020-05-06">';
}
</script>
<style>

input[type=date]{
	width:auto;
    padding: 15px;
    margin: 5px 10px 22px 0;
    display: inline-block;
    border: none;
    background: #f1f1f1;
}

input[type=text], input[type=password], input[type=number], input[type=email], select{
    width: 150%;
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
	out.println("        <li><a href=\"#\" class=\"active\">Payments &raquo;</a>");
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
		out.println("<form action=\"http://localhost:8080/project/searchtrans\" method=\"post\" >");
		out.println("	<fieldset>");
		out.println("			<legend>Transactions</legend>");
		out.println("		<div class=\"search-container\">");
		out.println("			  <label for=\"trans\">Search Transactions based on:</label>");
		out.println("				<select id=\"trans\" style='width:20%' name =\"seltyp\" onchange=\"showfield(this.options[this.selectedIndex].value)\">");
		out.println("				  <option value=\"\">Choose</option>");
		out.println("				  <option value=\"all\">Search All</option>");
		out.println("				  <option value=\"email\">Email Address</option>");
		out.println("				  <option value=\"phone\">Phone Number</option>");
		out.println("				  <option value=\"type_trans\">Type Of Transactions</option>");
		out.println("				  <option value=\"time_date\">Date Range</option>");
		out.println("				</select>");
		out.println("				<div id=\"div1\" style='display: inline-block;'>");
		out.println("				</div>");
		out.println("		      <button type=\"submit\" style='width:auto;'><i class=\"fa fa-search\"></i></button>");
		out.println("		</div>");
		out.println("	</fieldset>");
		out.println("	</form>");
	%>
		
	<%
			String opt = (String)request.getAttribute("answer");
			String msg = (String)request.getAttribute("msg");
			ArrayList<String> arr1 = (ArrayList<String>)request.getAttribute("amount");
			
			if(opt != null){
				out.println("<br><fieldset>");
				out.println("			<legend> "+msg+" </legend>");
				
				out.println("<table id=\"email\" style=\"margin-top: 2%;\">");
				out.println("		  <tr>");
				out.println("		    <th>No</th>");
				out.println("		    <th>Transaction ID</th> ");
				out.println("		    <th>Name</th>");
				out.println("		    <th>Amount</th> ");
				out.println("		    <th>Date & Time</th>");
				out.println("		    <th>Phone No</th> ");
				out.println("		    <th>Email</th>");
				out.println("		    <th>Status</th> ");
				out.println("		    <th>Transaction Type</th>");
				out.println("		    <th>Memo</th> ");
				out.println("		  </tr>");
				
				for (int i=0; i<arr1.size(); i+=10) {
					out.println("<tr>");
					out.println("<td>"+ arr1.get(i) +"</td>");
					out.println("<td>"+ arr1.get(i+1) +"</td>");
					out.println("<td>"+ arr1.get(i+2) +"</td>");
					out.println("<td>"+ arr1.get(i+3) +"</td>");
					out.println("<td>"+ arr1.get(i+4) +"</td>");
					out.println("<td>"+ arr1.get(i+5) +"</td>");
					out.println("<td>"+ arr1.get(i+6) +"</td>");
					out.println("<td>"+ arr1.get(i+7) +"</td>");
					out.println("<td>"+ arr1.get(i+8) +"</td>");
					out.println("<td>"+ arr1.get(i+9) +"</td>");
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