package beliberda;
import java.io.IOException;
import javax.servlet.http.*;

import java.util.Date;
import java.util.List;
import java.util.logging.*;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class BeliberdaServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(BeliberdaServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		    log.info("Attempt to get current google user...");
	        UserService userService = UserServiceFactory.getUserService();
	        User user = userService.getCurrentUser();
	        
	        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	        Entity guestRecord=null;
	        
	        if (user != null) {
	        	log.info("success!");
	            resp.setContentType("text/html");
	            resp.setCharacterEncoding("Windows-1251");
	            resp.getWriter().println("<b>Hello, " + user.getNickname()+"!<br/>");
	            resp.getWriter().println("<a href='/index.html'>To start page</a><br/>");
	            resp.getWriter().println("<a href='"+userService.createLogoutURL(req.getRequestURI())+"'>LogOut </a></b>");
	            resp.getWriter().println("(Attention: exit from ALL service google!)<br/>");
	            resp.getWriter().println("*He обращайте внимания на эту страницу, она нужна чтобы разлогиниться или шпионить за вами.<br/>");
	            resp.getWriter().print("<h2>Основная функция приложения там - в <a href='/history'>HistoryService</a><br/></h2>");
	            resp.getWriter().println("**Русский язык в историях пока что не поддерживается =(");
	            //create new record
	            guestRecord=new Entity("Guest");
	            guestRecord.setProperty("Nickname", user.getNickname());
	            guestRecord.setProperty("Date",new Date(System.currentTimeMillis()));
	            datastore.put(guestRecord);
	            //get recordlist
	            Query guestQuery = new Query("Guest");
	            List<Entity> res = datastore.prepare(guestQuery).asList(FetchOptions.Builder.withDefaults());
	            resp.getWriter().println("<br/><center>GuestList(last 15):<br/>");
	            resp.getWriter().println("<table><tr><td>GuestName</td><td>Date</td></tr>");
	            List<Entity> last20 = res.subList(res.size()-15, res.size()-1);
	            for(Entity en : last20){
	            	resp.getWriter().println("<tr><td>"+en.getProperty("Nickname").toString()+
	            			"</td><td>"+en.getProperty("Date").toString()+"</td></tr>");
	            }
	            resp.getWriter().println("</table></center><br/>");
	        } else {
	        	log.info("...unhappy. Redirect to LogIn google service...");
	            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
	        }
	        
	}
}
