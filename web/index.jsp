<%--
  Created by IntelliJ IDEA.
  User: janbuzek
  Date: 12/3/20
  Time: 1:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Infectious Disease Simulator</title>
    <script type="text/javascript" src="//code.jquery.com/jquery-latest.js"></script>
    <script src="master.js?3" type="text/javascript"></script>
    <link rel="icon" href="https://solarsystem.nasa.gov/system/basic_html_elements/11561_Sun.png">
  </head>
  <body>
    <h1>Running IDS through Heroku</h1>
    <form method="get" action="Simulate">
      <input type="number" id="roomSizeChooser" name="roomSize" />
      <input type="submit" value="Submit" />
    </form>
  <h2> <%= request.getParameter("roomSize")%> </h2>
    <h2> <%= request.getAttribute("this")%> </h2>
  </body>
</html>
