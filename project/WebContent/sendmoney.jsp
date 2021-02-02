<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet" href="style.css">
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box;}

/* Full-width input fields */
input[type=text], input[type=email], input[type=tel], textarea, select, input[type=number]{
  resize: none;
  width: 100%;
  padding: 15px;
  margin: 5px 0 22px 0;
  display: inline-block;
  border: none;
  background: #f1f1f1;
}

/* Add a background color when the inputs get focus */
input[type=text]:focus, input[type=email]:focus, input[type=tel]:focus, textarea:focus, select:focus,input[type=number]:focus{
  background-color: #ddd;
  outline: none;
}

/* Set a style for all buttons */
button {
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
  opacity: 0.9;
}

button:hover {
  opacity:1;
}

/* Extra styles for the cancel button */
.cancelbtn {
  padding: 14px 20px;
  background-color: #f44336;
}

/* Float cancel and sendmoney buttons and add an equal width */
.cancelbtn, .sendmoneybtn {
  float: left;
  width: 50%;
}

/* Add padding to container elements */
.container {
  padding: 16px;
}

/* The wallet (background) */
.wallet {
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: #474e5d;
  padding-top: 50px;
}

/* wallet Content/Box */
.wallet-sendmoney-content {
  background-color: #fefefe;
  margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
  border: 1px solid #888;
  width: 80%; /* Could be more or less, depending on screen size */
}

/* Style the horizontal ruler */
hr {
  border: 1px solid #f1f1f1;
  margin-bottom: 25px;
}


/* Clear floats */
.clearfix::after {
  content: "";
  clear: both;
  display: table;
}

</style>
<body>
	<div class="wallet sendmoney">
	<%
		ArrayList<String> myarray = (ArrayList<String>)request.getAttribute("arr1");
		
		out.println("<form class=\"wallet-sendmoney-content\" action=\"http://localhost:8080/project/sendmon\" method=\"post\" >");
		out.println("<div class=\"container\">");
		out.println("<h1>Send Money through Wallet</h1>");
		out.println("     <p>Please fill in this form to send money.</p>");
		out.println("     <hr>");
		
		out.println("  <label><b>Select your Bank Account</b></label>");
		
		out.println("  <select name=\"drop\" required>");
		out.println("	<option value=\"\">BankId		BankACC		BankBal</option>");
		 for (int i=0; i<myarray.size(); i++) { 
			 out.println("	<option value=\""+myarray.get(i)+"\">"+myarray.get(i)+"</option>"); 			
 		}
		out.println("  </select>");
		
		out.println("  <label for=\"phone\"><b>Enter Recipient phone number</b></label>");
		out.println("     <input type=\"number\" max=\"9999999999\" name=\"phone\" placeholder=\"Enter Recipient Phone Number \"  required>");
		
		out.println("      <label for=\"email\"><b>Recipient Email Address</b></label>");
		out.println("     <input type=\"email\" placeholder=\"Enter Recipient Email\" name=\"email\" required>");
		
		out.println("      <label for=\"amounr\"><b>Amount</b></label>");
		out.println("     <input type=\"number\" min = \"0\" step=\"0.01\" placeholder=\"Enter Amount\" name=\"amount\" required>");
		
		out.println("      <label for=\"memo\"><b>Memo</b></label>");
		out.println("     <textarea rows=\"4\" cols=\"50\" name=\"memo\" placeholder=\"Any Comment\" style=\"\"></textarea>");
			
		out.println("      <div class=\"clearfix\">");
		out.println("       <button type=\"submit\" name = \"opt\" value=\"home\" class=\"cancelbtn\">Cancel</button>");
		out.println("       <button type=\"submit\" name = \"opt\" value=\"send\" class=\"sendmoneybtn\">Send Money</button>");
		out.println("     </div>");
		
		out.println("   </div>");
		out.println("</form>");
%>
	</div>

</body>
</head>
</html>