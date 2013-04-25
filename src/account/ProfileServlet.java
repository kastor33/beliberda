package account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import entities.Account;

@SuppressWarnings("serial")
public class ProfileServlet extends HttpServlet {

	AccountDAO adao = new AccountDAO();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		
		String nick = req.getParameter("nick");
		Account acc = adao.getByNick(nick);
		req.setAttribute("nick", acc.getNick());
		req.setAttribute("status", acc.getStatus());
		req.getRequestDispatcher("profile.jsp").forward(req, resp);
	}
}
