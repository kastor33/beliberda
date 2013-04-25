package history;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import dao.AccountDAO;

@SuppressWarnings("serial")
public abstract class AbstractHistoryServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(AbstractHistoryServlet.class.getName());
	    protected abstract void showForm(HttpServletRequest req, 
		    HttpServletResponse resp) throws ServletException, IOException;

		protected abstract void handleSubmit(String nick, HttpServletRequest req, 
		    HttpServletResponse resp) throws ServletException, IOException;
		
		protected abstract void handleSubmitCont(String nick, HttpServletRequest req, 
			    HttpServletResponse resp) throws ServletException, IOException;
		
		protected abstract void showHistories(String nick, HttpServletRequest req, 
		    HttpServletResponse resp) throws ServletException, IOException;
		
		protected abstract void createHistory(String nick, HttpServletRequest req, 
			    HttpServletResponse resp) throws ServletException, IOException;
		
		protected abstract void createComment(String nick, String commid, HttpServletRequest req, 
			    HttpServletResponse resp) throws ServletException, IOException;
		
		protected abstract void likeComment(String nick, String commid, HttpServletRequest req, 
			    HttpServletResponse resp) throws ServletException, IOException;
		
		protected abstract void deleteComment(String nick, String commid, HttpServletRequest req, 
			    HttpServletResponse resp) throws ServletException, IOException;
		
		protected abstract void showHistory(String id, String nick, String title, HttpServletRequest req, 
			    HttpServletResponse resp) throws ServletException, IOException;
		
		private AccountDAO dao = new AccountDAO();
		
		String nick="";
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		    throws ServletException, IOException {
				
			UserService userService = UserServiceFactory.getUserService();
	        User user = userService.getCurrentUser();
			nick = req.getParameter("nick");
		    String action = req.getParameter("action");
		    
		    if ("read".equals(action)) {
		    	String id = req.getParameter("id");
		    	if(nick==null) getCurrentNick(user);
		    	String title = req.getParameter("title");
		        showHistory(id, nick, title, req, resp);}
		    if ("create".equals(action)) {
		        createHistory(nick, req, resp);}
		    if ("continue".equals(action)) {
		    	String commid = req.getParameter("commid");
		        createComment(nick, commid, req, resp);}
		    if ("like".equals(action)) {
		    	String commid = req.getParameter("commid");
		    	likeComment(nick, commid, req, resp);}
		    if ("delete".equals(action)) {
		    	String commid = req.getParameter("commid");
		    	deleteComment(nick, commid, req, resp);}
		    if ("history".equals(action)){
		    	if(nick==null) getCurrentNick(user);
		        showHistories(nick, req, resp);
		    }
		    else{ 
		        getCurrentNick(user);	    
		        if(nick.equals(""))showForm(req, resp); else showHistories(nick, req, resp);       
		    }     
		}

		private void getCurrentNick(User user){
			try{
	    		 nick = dao.getByAddr(user.getEmail()).getNick();
	    	     }
	    	  catch(Exception ex){log.warning(ex.getMessage()); nick="";}
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
		    throws ServletException, IOException {
			String action = req.getParameter("action");
			nick = req.getParameter("nick");
			if("continue".equals(action)){
				handleSubmitCont(nick,req, resp);
			}else
			handleSubmit(nick,req, resp);
		}
}
