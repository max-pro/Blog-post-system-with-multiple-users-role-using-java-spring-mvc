<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body style="background-color:rgb(248, 249, 250)">  
<div class="container">
<br/><br/>
  <div class="row">
    <div class="col-3">
    </div>
    <div class="col-6">
    	<div class="card">
		  <div class="card-header bg-primary" style="color:white;font-size:28px;"><center><b>Create New Account</b></center></div>
		  <div class="card-body">
		  
		  	<c:if test="${not empty error}">
			   <div class="card bg-danger text-white">
				   <div class="card-body">${error}</div>
				</div> 
			</c:if>
		  	
		  	 <form action="register" method="POST">
			  <div class="form-group">
			    <label for="username">Username:</label>
			    <input type="text" name="username" class="form-control" id="username" required>
			  </div>
			  <div class="form-group">
			    <label for="pwd">Password:</label>
			    <input type="password" name="password" class="form-control" id="pwd" required>
			  </div>
			  <button type="submit" class="btn btn-primary">Register</button>
			  <br/><br/>
			  <center>
			  	<a href="login">Already have account</a>
			  </center>
			</form> 
		  </div>
		</div>
    </div>
    <div class="col-3">
    </div>
  </div>
</div>

</body>
</html>
