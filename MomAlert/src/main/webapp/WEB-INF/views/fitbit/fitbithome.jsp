<dependency>
            <groupId>org.apache.tiles</groupId>
            <artifactId>tiles-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.tiles</groupId>
            <artifactId>tiles-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.tiles</groupId>
            <artifactId>tiles-jsp</artifactId>
        </dependency><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Fitbit Home</title>
</head>
<body>
<h1>
	Add A Fitbit device
</h1>
<div id="global">
<form action="addFitbit" method="post">
    <fieldset>
        <legend>Add a devie</legend>
        <p>
            <label for="name">Fitbit User Name/Email: </label>
            <input type="text" id="name" name="name" 
                tabindex="1">
        </p>
        <p id="buttons">
            <input id="submit" type="submit" tabindex="5" 
                value="Add">
        </p>
    </fieldset>
</form>
</div>
</body>
</html>
