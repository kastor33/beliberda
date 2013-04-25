<script src="scripts/jquery-1.9.0.min.js" type="text/javascript"></script>
<% String firstcommid = (String) request.getAttribute("firstcommid"); %>
<% String nick = (String) request.getAttribute("nick"); %>
<% String title = (String) request.getAttribute("title");%>
<%@page import="entities.Comment"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Iterator"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=Windows-1251" />
        <style>
          .container{font-size:18px;line-height:20px;color:#000;background:#ffd65d;border:1px solid #878d59;width:600px;padding:5px;}
          .header{font-size:24px;line-height:30px;color:#fff;background:#000080;border:1px solid #878d59;padding:5px;}
        </style>
    </head>
    <body background="/resource/imgfon.jpg" style="background-attachment: fixed">
    <h2 align="right"><a href="/index.html">To start page</a>|<a href="/history?action=history&nick=<%= nick %>">HistoryService</a></h2>
    <jsp:useBean id="hdao" scope="session" class="dao.HistoryDAO" />
    <% TreeMap<String, Comment> tree = hdao.getCommentList(firstcommid);%>
    <center><div class="header"><%=title%></div>
    <table>
    <tr bgcolor="#808080" align="center"><td>Level</td><td>Author</td><td>Mark</td><td>Continue</td><td>Delete</td></tr>
    <% 
        Iterator i = tree.entrySet().iterator();
        while (i.hasNext()) {
          Map.Entry entry = (Map.Entry)i.next();
          Comment com = (Comment) entry.getValue();%>

          <tr bgcolor="#c0c0c0" align="center"><td><input type="button" 
                                value="<%= entry.getKey().toString() %>" 
                                onclick="$('#<%= entry.getKey().toString()%>').slideToggle('slow');"></td>
              <td><a href="/profile?nick=<%= com.getAuthor() %>"><%= com.getAuthor() %></a></td> 
              <td><%if(!nick.equals(com.getAuthor())){%>
              <a href="/history?action=like&commid=<%=com.getId()%>&nick=<%=nick%>">
              <%= com.getMark() %></a>
              <%}else{%>
              <%= com.getMark()%>
              <% }%>
              </td> 
              <td><a href="/history?action=continue&commid=<%=com.getId()%>&nick=<%=nick%>">continue</a></td> 
              <td><%if(nick.equals(com.getAuthor())&&!entry.getKey().toString().equals("1")){%>
              <a href="/history?action=delete&commid=<%=com.getId()%>&nick=<%=nick%>">
              delete</a>
              <%}else{%>
              restricted
              <%}%>
              </td>
          </tr>   
          <tr><td colspan="5"><div class="container" id="<%= entry.getKey().toString()%>"><%=com.getText()%></div></td></tr>
              <% } %>
    </table></center>
    </body>	
</html>