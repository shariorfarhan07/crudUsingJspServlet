<%@ page import="com.minitwitter.dto.TweetDto" %><%--<html>--%>
<%--<body>--%>
<%--<h2>Hello World!</h2>--%>
<%--</body>--%>
<%--</html>--%>


<%--
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
  <title>Login</title>

</head>
<body>
<jsp:include page="htmlextentions/navbar.jsp" />

<div class="container pt-3">
  <jsp:include page="htmlextentions/Alerts-messages.jsp" />

    <%
TweetDto tweet = (TweetDto) request.getAttribute("tweet");
      if (tweet !=null){
      %>
  <form class="pt-3" style="margin: 10px;" method="POST"  action="updatetweet">
    <div class="form-group">
      <label for="tweet"> update the tweet</label>
      <input type="text" class="form-control" id="tweet" name="tweet"  value="<%=tweet.getText()%>">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
    <%
      }
      %>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
</body>
</html>

