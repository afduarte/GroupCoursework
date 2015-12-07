
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Matrix</title>
</head>
<body>
    <form>
        <% if (! request.getAttribute("error").equals("")) %>
            <p style="color: red"><%= request.getAttribute("error") %></p>
        <label for="word">Word to look for:</label>
        <input type="text" name="word" id="word" required/>

        <label for="set">Character set to rotate:</label>
        <input type="text" name="set" id="set" value="abcdefghijklmnopqrstuvwxyz" required/>

        <label for="cols">Columns:</label>
        <input type="number" min="1" max="1000" name="cols" id="cols" required value="200">

        <label for="rows">Rows:</label>
        <input type="number" min="1" max="1000" name="rows" id="rows" required value="200">

        <input type="submit">
    </form>
</body>
</html>
