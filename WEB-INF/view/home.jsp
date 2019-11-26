<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<div class="container" >
	
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		  <a class="navbar-brand" href="/springMVC">
		  	BlogWorld
		  	<c:if test="${ user.getRole() == 2 }">
		  		<small style="font-weight:bold;color:red">Hello User</small>
		  	</c:if>
		  	<c:if test="${ user.getRole() == 1 }">
		  		<small style="font-weight:bold;color:red">Hello Admin</small>
		  	</c:if>
		  </a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="collapsibleNavbar">
		    <ul class="navbar-nav">
		    	<c:if test="${ user.getRole() == 1 }">
			  		<li class="nav-link">
			  			<a href="/springMVC/users">users list</a>
			  		</li>
			  	  </c:if>
		      <li class="nav-item dropdown">
			      <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
			        Setting
			      </a>
			      <div class="dropdown-menu">
			      	<a class="dropdown-item" href="/springMVC/changepassword">change password</a>
			        <form method="POST" action="/springMVC/logout">
			        	<button class="btn-link dropdown-item" type="submit">logout</button>
			        </form>
			      </div>
			    </li>    
		    </ul>
		  </div>  
		</nav>
	
	<div class="jumbotron">
	  <center><h1>Let's Discuss here</h1>
	  <p>we are ready to anwser you</p>
	  <c:if test="${ user.getRole() == 1 }">
	  	<a class="btn btn-primary" href="sections">Create New Section</a></center>
	  </c:if>
	</div>
	
	<div style="padding:2px 10px;border-left:4px solid orange">
		<h3>List of Sections
			<a class="btn btn-primary" href="sections">Click here to View All</a>
		</h3>
			<div>
				Click on section link to view topics
			</div>
	</div><br/>
	<div class="list-group">
		
		<c:if test="${ not empty sections }">
  			<c:forEach items="${ sections }" var="section">
	  			<a href="/springMVC/topics/${ section.id }" class="list-group-item list-group-item-action">${ section.name }</a>
	  	
	  		</c:forEach>
  		</c:if>
		
  		<c:if test="${ empty sections }">
  			<center><h3 style="background-color:white;padding:10px">no sections found</h3></center>
  		</c:if>
  	</div>
		
	<br/>
	<div style="padding:2px 10px;border-left:4px solid green">
		<h3>Recent Topics</h3>
		<div>
			Click on Topics link to view posts
		</div>
	</div><br/>
	<div class="list-group">
		<c:if test="${ not empty topics }">
			<c:forEach items="${ topics }" var="topic">
	  		<a href="/springMVC/posts/${ topic.id }" class="list-group-item list-group-item-action">
	  			${ topic.text }
	  			 <span class="badge badge-primary badge-pill">${ topic.sections_name }</span>
	  		</a>
	  		</c:forEach>
  		</c:if>
  		<c:if test="${ empty topics }">
  			<center><h3 style="background-color:white;padding:10px">no topics found</h3></center>
  		</c:if>
  	</div>
  	<br/>
  	<br/>
</div>

</body>
</html>
