
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Matrix</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="jquery.simple-color.js"></script>
    <script  type="text/javascript">
        $(document).ready(function(){
            $('.simple_color_live_preview').simpleColor({ livePreview: true,
                displayColorCode: true});

            $('.simple_color_callback').simpleColor({
                onSelect: function(hex, element) {
                    alert("You selected #" + hex + " for input #" + element.attr('class'));
                }
            });

            $('.simple_color_mouse_enter').simpleColor({
                onCellEnter: function(hex, element) {
                    console.log("You just entered #" + hex + " for input #" + element.attr('class'));
                }
            });
        });
    </script>
</head>
<body>
<form>

    <h1>THE MATRIX</h1>

    <h3>You take the blue pill, the story ends here, you wake
        up and believe whatever you want to believe. You take
        the red pill...and I'll show you just how deep the rabbit hole goes.</h3>

    <br><br>

    <div id="main">
        <p><b>Rules</b></p>

        <p>- The number of Columns and Rows must be at least 1<br>
            - You always need at least a word to find<br>
            - The number of Columns must always be equal to the length of the word.</p>

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
            <input type="number" min="1" max="50" name="interval" id="interval" required value="5">
        </div>

        <br><br>

        <div id="colors">

            <script src="jscolor.js"></script>

            <label for="bgColor">Background Color:</label>
            <br>
            <input class='jscolor' name="bgColor" id="bgColor" value="000000"/>
            <br>
            <label for="fgColor">Foreground Color:</label>
            <br>
            <input class='jscolor' name="fgColor" id="fgColor" value="00FF00"/>
        </div>

        <div id="checkbox">
            <label for="cheat">Cheat?</label>
            <input type="checkbox" name="cheat" id="cheat" value="true">
            <label for="varsize">Variable Size?</label>
            <input type="checkbox" name="varsize" id="varsize" value="true">
            <label for="varint">Variable Color Intensity?</label>
            <input type="checkbox" name="varint" id="varint" value="true"><br><br>
        </div>

        <div id="button">
            <button type="submit" name="submit" value="submit">Take the Red Pill</button>
        </div>
    </div>
</form>
</body>
</html>