
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
        <input type="number" min="1" max="150" name="cols" id="cols" required value="40">

        <label for="rows">Rows:</label>
        <input type="number" min="1" max="150" name="rows" id="rows" required value="20">

        <br>
        <label for="limit">Limit:</label>
        <input type="number" min="10" max="1000" name="limit" id="limit" required value="200">


        <label for="interval">Interval:</label>
        <input type="number" min="1" max="50" name="interval" id="interval" required value="5">

        <br>
        <label for="cheat">Cheat?</label>
        <input type="checkbox" name="cheat" id="cheat" value="true">

        <br>

        <input type="submit">
    </form>
</body>
</html>
