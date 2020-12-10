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
    <script src="master.js?4" type="text/javascript"></script>
    <link href="/main.css" rel="stylesheet" type="text/css">
    <link rel="icon" href="https://solarsystem.nasa.gov/system/basic_html_elements/11561_Sun.png">
  </head>
  <body>
    <h1>Infectious Disease Simulator</h1>
    <p>
      This project models the transmission rates for an infectious disease.
      The project specifically models the transmission of an infectious disease who's transmission chance falls with distance
      among people in a shared space (Room). However, this model can also be used to predict transmission more generally across
      geographic regions, in which case the "Room" would represent for instance a country and a "Person" a city.
      This more general view works well with our model because each "Person" is assigned a chance of catching the disease,
      which can be seen as the percentage of people in the are with the disease.
      <br/>
      There is a relatively simple and reasonable mathematical model that can be applied to the spread of diseases, upon which we improve.
      In this model, all people are constantly in contact, and there is a chance of disease transmission whenever a healthy and infected individual meet.
      <br/>
      Mathematically, let I(t) be the number of infected individuals. Then
      <br/>
      dI/dt = k(I)(P-I),
      <br/>
      where k is the transmission coefficient and P is the total population size. It can be shown that this model gives a logistic equation for the spread of the disease.
      <br/>
      Our model uses simulation to generalize one of the most limiting assumptions of the above mathematical model: that people are always mutually in contact.
      We use a matrix model to represent the spread of disease, where people sufficiently far from each other have no chance of transmitting the disease,
      and where more generally transmission chance is distance dependent. This naturally loses some accuracy, since the passage of time must be broken into discrete chunks.
      We solve this problem by simulating transmission a small time interval to accurately approximate results.
      <br/>
      Transmission chances and the simulation more generally are modelled off of medical data about COVID-19.
      <br/>
      Enjoy!
    </p>

    <p>The decimals in the Results matrix represent the chance of the person in that location becoming infected.</p>

    <form method="get" action="Simulate">
      <h4>Enter the dimension for the Room, an integer between 1 and 100</h4>
      <input type="number" id="roomSizeChooser" name="roomSize" />
      <h4>Enter the numbers where you want people to be placed from 0 to the size of the Room squared minus 1. Squares are indexed from left to right, and then from top to bottom.</h4>
      <h4>The first input is the people originally carrying the disease, the second is for those originally healthy.</h4>
      <input type="String" id="infectedPersonSet" name="infectedPersonSet" />
      <input type="String" id="nonInfectedPersonSet" name="nonInfectedPersonSet" />

      <h4>Alternatively, type "autofill" in the first box for a full Room with people who may or may not have the disease at random.</h4>

      <h4>The final parameter is the time, in hours, for which the people are in the room</h4>
      <input type="number" id="timeInterval" name="timeInterval" value = "1"/>
      <input type="submit" value="Submit" />
    </form>
    <h3>The chances of each person contracting the disease will be printed below!</h3>
  <h2 id = "dimension"> Room Size: <%= request.getParameter("roomSize")%> </h2>
    <h2 id = "originalMatrix"> Input Room: <br/> <%= request.getAttribute("originalMatrix")%> </h2>
    <h2 id = "chanceMatrix"> Result: <br/> <%= request.getAttribute("chanceMatrix")%> </h2>
  </body>
</html>
