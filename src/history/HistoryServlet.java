package history;


import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;

import dao.HistoryDAO;

import entities.Comment;
import entities.History;

@SuppressWarnings("serial")
public class HistoryServlet extends AbstractHistoryServlet {

	private static final Logger log = Logger.getLogger(HistoryServlet.class.getName());

	private HistoryDAO dao = new HistoryDAO();
	@Override
	protected void showForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	    resp.sendRedirect("/blobstore");
	    log.info("for showForm - OK");
	}

	@Override
	protected void handleSubmit(String nick, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.info("handleSubmit - starting...");
		String genre = req.getParameter("genre");
	    String title = req.getParameter("title");
	    int charlimit =  Integer.parseInt(req.getParameter("charlimit")); 
	    String text = req.getParameter("firstcomment");
	    log.info("parameters was read : "+genre+", "+title+", "+charlimit+", "+text.substring(0, 10)+"...");
	    log.info("Attempt creating entities History and Comment...");
	    Comment com = new Comment(nick,text);
	    History hist = new History(nick,genre,charlimit,title,com.getId());
	    if(dao.saveHist(hist, com)){
	    	log.info("success!");
	    	resp.sendRedirect("/history?action=read&id="+hist.getId()+"&nick="+nick+"&title="+hist.getTitle());
	    	log.info("for handleSubmit - OK");
	    }
	    else{log.info("failure!");
	    resp.sendRedirect("/history?action=history&nick="+nick);}
	    
	}

	@Override
	protected void handleSubmitCont(String nick, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		log.info("handleSubmitCont - starting...");
		String text = req.getParameter("yourcomment");
		String parentid = req.getParameter("parentid");
		Key<Comment> key = Key.create(Comment.class, parentid);
		if(text.equals("")){req.getRequestDispatcher("error.jsp").forward(req, resp);}
		log.info("parameters was read : "+nick+", "+parentid+", "+text.substring(0, 1)+"..."+text.substring(text.length()-1, text.length()));
		log.info("Attempt creating entity Comment...");
		if(dao.isExist(key)){
			Comment c = new Comment(nick,text,key);
			if(dao.save(c)){
				log.info("success!");
				resp.sendRedirect("/history?action=history&nick="+nick);
		    	log.info("for handleSubmitCont - OK");
			}
			else{log.info("failure!");
		    }
		}else{req.getRequestDispatcher("error.jsp").forward(req, resp); }
		
	}
	
	@Override
	protected void showHistories(String nick, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		List<History> lh = dao.getHistories();
		resp.setContentType("text/html");
		resp.getWriter().println("<body background='/resource/imgfon.jpg' style='background-attachment: fixed'>");
        resp.getWriter().println("<b>You are " + nick+"</b>");
        resp.getWriter().println("<right><a href='/index.html'><b>To start page</b></a></right><br/>");
        resp.getWriter().println("<br/><center><div style='font-size:22px;line-height:26px;color:#fff;background:#000080;border:1px solid #878d59;'>HistoriesList:</div><br/>");
        resp.getWriter().println("<table><tr align='center' bgcolor='#808080'><td>CreationDate</td><td>Author</td><td>Title</td><td>Genre</td><td>MaxLenght</td><td>Total</td></tr>");
        for(History h : lh){
        	resp.getWriter().println("<tr align='center' bgcolor='#c0c0c0'><td>"+h.getDate().toString()+"</td>"+
        			                     "<td>"+h.getAuthor()+"</td>" +
        			                     "<td><a href='/history?action=read&id="+h.getId()+"&nick="+nick+"&title="+h.getTitle()+"'>"+h.getTitle()+"</a></td>" +
        			                     "<td>"+h.getGenre()+"</td>" +
        			                     "<td>"+h.getCharlimit()+"</td>"+
        			                     "<td>"+h.getTotal()+"</td>"+
        					"</tr>");
        }
        resp.getWriter().println("</table><br>");
        resp.getWriter().println("<div style='font-size:22px;line-height:26px;color:#fff;background:#afeeee;border:1px solid #878d59;'>" +
        		"<a href='/history?action=create&nick="+nick+"'>Create new history</a></div></center></body>");
	}

	@Override
	protected void showHistory(String id, String nick, String title, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
        History hist = dao.getHistory(id);
        req.setAttribute("firstcommid", hist.getComm());
        req.setAttribute("nick", nick);
        req.setAttribute("title", title);
        req.getRequestDispatcher("history.jsp").forward(req, resp);       
	}

	@Override
	protected void createHistory(String nick, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setAttribute("nick", nick);
	    req.getRequestDispatcher("createhistory.jsp").forward(req, resp);
	}

	@Override
	protected void createComment(String nick, String parentcommid, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("nick", nick);
		req.setAttribute("parentid", parentcommid);
		Comment parent = dao.getComment(parentcommid);
		req.setAttribute("text", parent.getText());
		req.setAttribute("author", parent.getAuthor());
		req.setAttribute("mark", parent.getMark());
		req.setAttribute("date", parent.getDate().toString());
	    req.getRequestDispatcher("continue.jsp").forward(req, resp);

	}

	@Override
	protected void likeComment(String nick, String commid,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Comment com = dao.getComment(commid);
		if(!com.equals(null)){com.setMark(com.getMark()+1); dao.save(com); resp.sendRedirect("/history?action=history&nick="+nick);}
		else{req.getRequestDispatcher("error.jsp").forward(req, resp);}
		
	}

	@Override
	protected void deleteComment(String nick, String commid,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Comment com = dao.getComment(commid);
		if(dao.delete(com)){resp.sendRedirect("/history?action=history&nick="+nick);}
		else{req.getRequestDispatcher("error.jsp").forward(req, resp);}
	}

	
	
}
