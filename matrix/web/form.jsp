
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Matrix</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<form>

    <h1>THE MATRIX - DIGITAL RAIN</h1>

    <h3>You take the blue pill, the story ends here, you wake
        up and believe whatever you want to believe. You take
        the red pill...and I'll show you just how deep the rabbit hole goes.</h3>

    <br><br>

    <div id="main">
        <p><b>Rules</b></p>

        <p>- The number of Columns and Rows must be at least 1<br>
            - You always need at least a word to find<br>
            - The number of Columns must always be equal or greater than the length of the word.<br>
            - If the set of characters doesn't include all the characters of your word,<br>
              you are not going to find much!</p>

        <% if (! request.getAttribute("error").equals("")) %>
        <p style="color: red"><%= request.getAttribute("error") %></p>
        <label for="word">Word to look for:</label>
        <input type="text" name="word" id="word" size="50" required/>
        <br><br><br>

        <label for="set">Character set to rotate:</label>
        <input type="text" name="set" id="set" value="abcdefghijklmnopqrstuvwxyz" size="30" required/>

        <br><br>

        <%! public Integer getColumnSize(String word)
        {
            if(word == null)
                return 1;
            else
                return word.length();
        }%>
        <div id="sizes">
            <label for="cols">Columns:</label>
            <br>
            <input type="number" min="<%= getColumnSize(request.getParameter("word")+1)%>"
                   max="150" name="cols" id="cols" required value="40" size = "10">
            <br>
            <label for="rows">Rows:</label>
            <br>
            <input type="number" min="1" max="150" name="rows" id="rows" required value="20">
        </div>

        <br><br>

        <div id="limits">
            <label for="limit">Limit:</label>
            <br>
            <input type="number" min="10" max="1000" name="limit" id="limit" required value="200">
            <br>
            <label for="interval">Interval:</label>
            <br>
            <input type="number" min="1" max="50" name="interval" id="interval" required value="15">
        </div>

        <br><br>

        <div id="colors">

            <script src="jscolor.js"></script>

            <label for="bgColor">Background Color:</label>
            <br>
            <input class='jscolor' name="bgColor" id="bgColor" value="000000"/>
            <br>
        </div>

        <div id="checkbox">
            <label for="iters">Display Iterations?</label>
            <input type="checkbox" name="iters" id="iters" value="true">
            <label for="cheat">Cheat?</label>
            <input type="checkbox" name="cheat" id="cheat" value="true">
            <br>
            <label for="intro">Show Intro</label>
            <input type="checkbox" name="intro" id="intro" value="true" checked>
            <label for="outro">Show Outro</label>
            <input type="checkbox" name="outro" id="outro" value="true" checked>
            <label for="results">Show Results</label>
            <input type="checkbox" name="results" id="results" value="true" checked>
            <br>
            <label for="standard">Standard Text</label>
            <input type="radio" name="radios" id="standard" value="0" checked>
            <label for="varsize">Variable Char Size</label>
            <input type="radio" name="radios" id="varsize" value="1">
            <label for="varJam">Character Jam</label>
            <input type="radio" name="radios" id="varJam" value="2">
            <br><br>
        </div>

        <div id="button">
            <button type="submit" name="submit" value="submit">Take the Red Pill</button>
        </div>
    </div>
</form>
</body>
</html>