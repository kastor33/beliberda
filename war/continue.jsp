<% String parentid = (String) request.getAttribute("parentid"); %>
<% String nick = (String) request.getAttribute("nick"); %>
<% String text = (String) request.getAttribute("text"); %>
<% Integer mark = (Integer) request.getAttribute("mark"); %>
<% String author = (String) request.getAttribute("author"); %>
<% String date = (String) request.getAttribute("date"); %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=Windows-1251" />
        <style>
          .leftta {
               float:left; /* Выравнивание по левому краю */
               margin: 7px 7px 7px 0px; /* Отступы  */
               font-size:18px;line-height:20px;color:#000;background:#ffd65d;border:1px solid #878d59;}
          .header{font-size:22px;line-height:26px;color:#fff;background:#000080;border:1px solid #878d59;}
        </style>           
    </head>
    <body background="/resource/imgfon.jpg" style="background-attachment: fixed">
    <h2 align="right"><a href="/index.html">To start page</a>|<a href="/history?action=history&nick=<%= nick %>">HistoryService</a></h2>
    <center><textarea rows="10" cols="70" name="firstcomment" class="leftta" readonly><%=text%></textarea>
           <div class="header">
           <b>Properties:</b><br />
           <img src="/blobstore?action=display&nick=<%=author%>" width="120" height="120"/><br />   
           Author: <%=author%><br />
           Mark: <%=mark%><br />
           Date: <%=date%><br /></div>
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <img src="resource\delimiter.jpg"/><br />     
    <div class="header"><b>Write here your cont:</b></div>
     <form method="POST" action="history?action=continue&nick=<%=nick%>&parentid=<%=parentid%>">
      <textarea rows="10" cols="100" name="yourcomment" style="font-size:18px;line-height:20px;color:#101010;background:#ffd60d;border:1px solid #878d59"></textarea><br />
      <input type="submit" value="Save" style="font-size:22px; color:#fff; background:#000080"></center>
      </form>
    </body>	
</html>