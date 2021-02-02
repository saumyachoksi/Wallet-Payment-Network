<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!doctype html>
<html lang="en">
<head>
<link rel="stylesheet" href="style.css">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<script src='https://kit.fontawesome.com/a076d05399.js'></script> 
 
    <!-- IE6-8 support of HTML5 elements --> <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
 
</head>
<body>
<div class="wallet homepage">
    <div class="container">
	<%
		out.println("<nav id=\"nav\">");
		out.println("    <ul id=\"navigation\">");
		out.println("        <li><a href=\"#\" class=\"active\">Home</a></li>");
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
	<%
		
    	String ssn = (String)request.getAttribute("ssn");
    	String name = (String)request.getAttribute("name");
    	String msg = (String)request.getAttribute("msg");
    	String msgerr = (String)request.getAttribute("msgerror");
    	
		out.println("<div style=\"padding-left:16px;\">");
		out.println("<br><br><h2 style=\"text-align: center;\">Welcome "+name+"</h2>");
	    //System.out.print(msg);
		  if(msg != null){
			  out.println("<h3><font color=\"green\">"+msg+"</font></h3> ");
		  }
		  if(msgerr != null){
			  out.println("<h3><font color=\"red\">"+msgerr+"</font></h3> ");
		  }
		out.println("</div>");
	%>
	  
</div>
</div>

</body>
</html>