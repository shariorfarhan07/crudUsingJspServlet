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
  <title>Home Page</title>

</head>
<body>
<jsp:include page="extentions/navbar.jsp" />
<div class="container pt-3">
  <jsp:include page="extentions/Alerts-messages.jsp" />
  <form>
    <div class="form-group">
      <label for="tweet">What's on your mind</label>
      <input type="text" class="form-control" id="tweet" name="tweet" >
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
  <div class="">

    <div class="card" style="width: 100%;">
<%--      <img src="..." class="card-img-top" alt="...">--%>
      <div class="card-body ">
        <h5 class="card-title">Card title</h5>
        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
        <a href="#" class="btn btn-primary">Go somewhere</a>
      </div>
    </div>


  </div>



</div>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
</body>
</html>

