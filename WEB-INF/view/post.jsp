<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
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
		
		<br/>
			<c:if test="${ not empty error }">
				<div class="card bg-danger text-white">
				   <div class="card-body">${ error }</div>
				</div>
			</c:if>
			<c:if test="${ not empty success }">
				<div class="card bg-success text-white">
				   <div class="card-body">${ success }</div>
				</div>
			</c:if>
			<div style="padding:2px 10px;border-left:4px solid green">
				<h3>${ topic }</h3>
			</div><br/>
			<div class="list-group">
			<c:if test="${ not empty posts }">
				<c:forEach items="${ posts }" var="post">
		  		<div class="list-group-item">
		  			<div class="commentTxt">
		  				${ post.text }
		  			</div>
		  			<small style="color:gray">created by : ${ post.username }</small>
		  			<br/>
		  			<c:if test="${ user.getId() == post.users_id || user.getRole() == 1 }">
		  			<div class="row">
		  				<c:if test="${ user.getId() == post.users_id}">
		  				<div class="col-sm-3">
		  					<a class="btn btn-default editBtn" data-text="${ post.text }" data-id="${ post.id }" href="#updateSection" >Edit</a>
		  				</div>
		  				</c:if>
		  				<div class="col-sm-3">
		  					<form method="POST" action="/springMVC/posts/postDelete/${ id }">
			  					<input type="hidden" name="delete_id" value="${ post.id }" />
			  					<button type="submit" class="btn btn-danger deleteBtn">Delete</button>
			  				</form>
		  				</div>
		  			</div>
		  			</c:if>
		  		</div>
		  		</c:forEach>
		  	</c:if>
		  	<c:if test="${ empty posts }">
		  		<center><h3 style="background-color:white;padding:10px">no post found</h3></center>
		  	</c:if>
		  	</div>
		<br/>
		<div id="commentSection" style="background-color:rgb(235,235,235);padding:10px 30px;">
			<form method="POST" action="/springMVC/posts/${id}">
				<div class="form-group">
				  <label for="comment">Comment:</label>
				  <input type="hidden" name="comment_id" value="${ id }" />
				  <textarea class="form-control" name="post_txt" rows="5" id="comment" required></textarea>
				</div> 
			  <button type="submit" class="btn btn-info">Reply</button></center>
			</form>
		</div>
		<div id="updateSection" style="display:none;background-color:rgb(235,235,235);padding:10px 30px;">
			<form method="POST" action="/springMVC/posts/postUpdate/${id}">
				<div class="form-group">
				  <label for="commentTextarea">Comment:</label>
				  <input id="updatePostId" type="hidden" name="post_id" value=""/>
				  <textarea class="form-control" name="post_update_txt" rows="5" id="commentTextarea" required></textarea>
				</div> 
			  <button type="submit" class="btn btn-info">Update Post</button></center>
			  <button type="button" id="cancelBtn" class="btn btn-danger">Cancel</button>
			</form>
		</div>
	</div>
	<script>
		$(document).ready(function(){
			$('#cancelBtn').click(function(){
				$('#updateSection').hide();
				$('#commentSection').show();
			});
			$('.editBtn').click(function(){
				var data = $(this).attr("data-text");
				$('#updatePostId').val($(this).attr('data-id'));
				data = data.trim();
				$('#commentTextarea').html(data);
				$('#updateSection').show();
				$('#commentSection').hide();
			});
		});
	</script>
</body>
</html>