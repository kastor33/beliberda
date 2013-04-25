<% String uploadUrl = (String) request.getAttribute("uploadUrl"); %>
<% String message = (String) request.getAttribute("message"); %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=Windows-1251" />
    </head>
    <body>		
        <form method="POST" action="<%= uploadUrl %>" enctype="multipart/form-data">
        <img src="/resource/nophoto.jpg" align="left" width="150" height="150"/>
            <table>
                <tr>	
                    <td>Nickname</td>
                    <td><input type="text" name="nickname" /></td>
                </tr>
                <tr>	
                    <td>Status</td>
                    <td><input type="text" name="status" /></td>
                </tr>
                <tr>	
                    <td>Photo</td>
                    <td><input type="file" name="file" /></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Create"/></td>
                </tr>				
            </table>
            <b><%=message%></b>
        </form>
    </body>	
</html>