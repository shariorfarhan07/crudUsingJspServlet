<%@ page import="com.minitwitter.dto.TweetDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.minitwitter.dto.userDto" %><%--
  Created by IntelliJ IDEA.
  User: shariorh
  Date: 3/15/24
  Time: 3:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
  <title>Home Page</title>

</head>
<body>
<jsp:include page="htmlextentions/navbar.jsp" />


<%
  List<TweetDto> tweets  = (List<TweetDto>) request.getAttribute("tweets");
    List<userDto>  follwers=(List<userDto>) request.getAttribute("follwers");
    List<userDto>  userTofollow=(List<userDto>) request.getAttribute("userTofollow");
  String username = (String) session.getAttribute("username");
//  System.out.println(tweets+username);
  if (username==null)response.sendRedirect("login.jsp");
%>
<div class="container pt-3">
  <jsp:include page="htmlextentions/Alerts-messages.jsp" />
  <form class="pt-3" style="margin: 10px;" method="POST"  action="tweet">
    <div class="form-group">
      <label for="tweet">What's on your mind</label>
      <input type="text" class="form-control" id="tweet" name="tweet" >
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
  <div class="pt-3">
<%
   if (tweets != null)
    for (TweetDto tweet: tweets) {
%>
    <div class="card" style="width: 100%;margin: 10px;">
<%--      <img src="..." class="card-img-top" alt="...">--%>
      <div class="card-body ">
        <h5 class="card-title"><%=tweet.getUsername()%></h5>
        <p class="card-text"><%=tweet.getText()%></p>


          <%
              if ( tweet.getUsername().equals(username) ){
          %>

        <a href="tweet/<%=tweet.getId()%>" class="btn btn-success">Update</a>
        <a href="tweet/<%=tweet.getId()%>/delete" class="btn btn-danger">Delete</a>

          <%
              }
          %>
      </div>
    </div>



  <%
      }
  %>
  </div>

</div>

<div class="container">
    <h1> user to follow </h1>
    <div class="row">
        <%
            if (userTofollow != null)
                for (userDto user: userTofollow) {
        %>


        <div class="card col-4" >
            <img class="card-img-top" src="https://png.pngtree.com/png-clipart/20191122/original/pngtree-user-icon-isolated-on-abstract-background-png-image_5192004.jpg" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title"><%=user.getUserName()%></h5>
                <a href="follow/<%=user.getUserId()%>" class="btn btn-primary">follow</a>
            </div>
        </div>
        <%
                }
        %>


    </div>
</div>


<div class="container">
    <h1> following </h1>
    <div class="row">

        <%
            if (follwers != null)
                for (userDto user: follwers) {
        %>

        <div class="card col-4" >
            <img class="card-img-top" src="https://png.pngtree.com/png-clipart/20191122/original/pngtree-user-icon-isolated-on-abstract-background-png-image_5192004.jpg" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">Card title</h5>
                <a href="#" class="btn btn-danger">unfollow</a>
            </div>
        </div>
        <%
                }
        %>

    </div>
</div>







<%--<%--%>
<%--  if (tweets != null)--%>
<%--  for (TweetDto t :tweets) {--%>
<%--%>--%>
<%--<!-- Output the value of 'i' within HTML -->--%>
<%--<p>Iteration <%= t.toString() %></p>--%>
<%--<%--%>
<%--  } // End of the for loop--%>
<%--%>--%>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
</body>
</html>

