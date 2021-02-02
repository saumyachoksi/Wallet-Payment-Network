<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {font-family: Arial, Helvetica, sans-serif;background-color:#040d57;}

/* Full-width input fields */
input[type=text], input[type=password] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
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
}

button:hover {
  opacity: 0.8;
}

.container {
  padding: 16px;
}

.center {
  margin: auto;
  width: 60%;
  padding: 10px;
}

/* wallet Content/Box */
.wallet-login-content {
  background-color: #fefefe;
  margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
  border: 1px solid #888;
  width: 80%; /* Could be more or less, depending on screen size */
}

</style>
</head>
<body>

<div class="wallet center">
	<br><br>
  <center><h2 style="color:white">Wallet Login Form</h2></center>
  <form class="wallet-login-content" action="http://localhost:8080/project/login" method="post">

    <div class="container">
      <label for="uname"><b>Username</b></label>
      <input type="text" placeholder="Enter Username" name="user" required>

      <label for="psw"><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="pwd" required>
        
	<%
		    String msg = (String)request.getAttribute("loginmessage");
		    //System.out.print(msg);
    		  if(msg != null){
    			  out.println("<span style=\"color:red\">Login Failed, Try again !! </span>");
    		  }
	  %>
      
      <button type="submit">Login</button>

    </div>

    <div class="container" style="background-color:#f1f1f1;text-align: right;">
      <span class="signup">Don't have an account? <a href="signup.html">Sign up</a></span>
    </div>
  </form>
</div>

</body>
</html>
