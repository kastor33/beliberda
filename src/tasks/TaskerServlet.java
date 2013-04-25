package tasks;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailService.Message;
import com.google.appengine.api.mail.MailServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

import dao.HistoryDAO;
import entities.History;

@SuppressWarnings("serial")
public class TaskerServlet extends HttpServlet {

	HistoryDAO hdao = new HistoryDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String action = req.getParameter("action");

		if ("find".equals(action)) {
			findBests();
		}
		if ("send".equals(action)) {
			sendMessage(req, resp);
		} else {
			addToQueue(action, req, resp);
		}
	}

	public void sendMessage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HistoryDAO hdao = new HistoryDAO();
		List<History> lh = hdao.getHistories();
		Date date = new Date(System.currentTimeMillis());
		String data = "Date: " + date + ", now in datastore next histories: ";
		int i = 0;
		for (History h : lh) {
			i++;
			data = data + i + ") " + h.getTitle() + " by " + h.getAuthor()
					+ " and Total=" + h.getTotal() + " | ";
		}
		MailService ms = MailServiceFactory.getMailService();
		Message message = new Message("***************",
				"****************", "Current HistoryList", data);
		ms.send(message);
		// ms.sendToAdmins(message);
	}

	public void addToQueue(String action, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Queue def = QueueFactory.getDefaultQueue();
		def.add(TaskOptions.Builder.withUrl("/tasker?action="+action.substring(3)).method(
				Method.GET));
	}

	public void findBests() throws IOException {

		List<History> lh = hdao.getHistories();
		for (History h : lh) {
			hdao.findBest(h.getId());
			// По этому коменту можно собрать всю самую залайканую историю
			// но пока что пусть просто обновляет тотал в истории
		}

	}
	
	public void runInBackEnd(){
		//...	
	}
}
