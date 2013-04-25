<% String nick = (String) request.getAttribute("nick"); %>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=Windows-1251" />
        <style>
          .header{font-size:24px;line-height:30px;color:#fff;background:#000080;border:1px solid #878d59;padding:5px;}
          .data{font-size:20px;line-height:22px;color:#fff;background:#6a5acd;border:1px solid #878d59;padding:5px; }
        </style>
    </head>
    <body background="/resource/imgfon.jpg" style="background-attachment: fixed">
        <div class="header"><b>Author:<%= nick%></b></div>	
        <form method="POST">
            <table class="data">
                <tr><td>Genre:</td>	
                    <td><input type="text" name="genre" /></td>
                </tr>
                <tr>	
                    <td>Title:</td>
                    <td><input type="text" name="title" /></td>
                </tr>
                <tr>	
                    <td>Charlimit:</td>
                    <td><input type="text" name="charlimit"/></td>
                </tr>
            </table>
            <div class="header">Write first part of common text:</div>
            <textarea rows="10" cols="100" name="firstcomment" 
                style="font-size:18px;line-height:20px;color:#101010;background:#ffd60d;border:1px solid #878d59">
            </textarea><br /> 
            <input type="submit" value="Create" class="header"/>  
        </form>
    </body>	
</html>