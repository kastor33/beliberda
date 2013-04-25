package account;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public abstract class AbstractUploadServlet extends HttpServlet {

	protected abstract void showForm(String message, HttpServletRequest req, 
		    HttpServletResponse resp) throws ServletException, IOException;

		protected abstract void handleSubmit(HttpServletRequest req, 
		    HttpServletResponse resp) throws ServletException, IOException;

		protected abstract void showRecord(String nick, HttpServletRequest req, 
		    HttpServletResponse resp) throws ServletException, IOException;
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		    throws ServletException, IOException {
			
		    String action = req.getParameter("action");
		    if ("display".equals(action)) {
		        String nick = req.getParameter("nick");
		        showRecord(nick, req, resp);} 
		    else {
		    	String message=req.getParameter("message");
		    	if(message==null)message="";
		    	showForm(message, req, resp);	   } 
		}

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
		    throws ServletException, IOException {
		    handleSubmit(req, resp);
		}
}