<% String nick = (String) request.getAttribute("nick"); %>
<% String status = (String) request.getAttribute("status"); %>
<%@page import="entities.History"%>
<%@page import="java.util.List"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=Windows-1251" />
        <style>
           .data{font-size:20px;line-height:22px;color:#fff;background:#6a5acd;border:1px solid #878d59;padding:5px; }
        </style>
    </head>
    <body background="/resource/imgfon.jpg" style="background-attachment: fixed">
    <h2 align="right"><a href="/index.html">To start page</a>|<a href="/history?action=history">HistoryService</a></h2>
    <div class="data">
    <img src="/blobstore?action=display&nick=<%=nick%>" align="left" width="200" height="200" style="margin: 7px 7px 7px 0px"/>
    Nickname: <b><%= nick %></b><br />
    Status: <b><%= status %></b>
    <div>
    <jsp:useBean id="hdao" scope="session" class="dao.HistoryDAO" />
    <% List<History> lh = hdao.getHistoriesByAuthor(nick);%>
    <center><div style="font-size:22px;line-height:26px;color:#fff;background:#000080;border:1px solid #878d59;">HistoriesList:</div><br/>
    <table><tr align="center" bgcolor="#808080"><td>CreationDate</td><td>Title</td><td>Genre</td><td>MaxLenght</td><td>Total</td></tr>
     <% 
        for(History h: lh) {%>
            <tr align="center" bgcolor="#c0c0c0">
                  <td><%=h.getDate().toString()%></td>
        		  <td><a href="/history?action=read&id=<%=h.getId()%>&title=<%=h.getTitle()%>"><%=h.getTitle()%></a></td>
        		  <td><%=h.getGenre()%></td>
        		  <td><%=h.getCharlimit()%></td>
        	      <td><%=h.getTotal()%></td>
            </tr>
                         <%}%>
     </table>
     </center>
    </body>	
</html>