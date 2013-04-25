package account;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import dao.AccountDAO;

import entities.Account;

@SuppressWarnings("serial")
public class BlobstoreUploadServlet extends AbstractUploadServlet {

	private static final Logger log = Logger
			.getLogger(BlobstoreUploadServlet.class.getName());

	private BlobstoreService blobService = BlobstoreServiceFactory
			.getBlobstoreService();

	private AccountDAO dao = new AccountDAO();

	@Override
	protected void showForm(String message, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String uploadUrl = blobService.createUploadUrl("/blobstore");
		req.setAttribute("uploadUrl", uploadUrl);
		req.setAttribute("message", message);
		req.getRequestDispatcher("blobstore.jsp").forward(req, resp);
		log.info("showForm - OK");
	}

	@Override
	protected void handleSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.info("handleSubmit - starting...");
		String photoPath = "/resource/nophoto.jpg";

		Map<String, List<BlobKey>> blobs = blobService.getUploads(req);
		try {
			BlobKey blobKey = blobs.get(blobs.keySet().iterator().next())
					.iterator().next();
			photoPath = blobKey.getKeyString();
		} catch (NoSuchElementException ex) {
			log.warning("not found photo" + ex.getMessage());
		}
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String nickname = req.getParameter("nickname");
		String status = req.getParameter("status");
		log.info("parameters was read : " + photoPath + " and " + nickname);
		Account acc = new Account(nickname, user.getEmail());
		log.info("Entity acc create...");
		acc.setStatus(status);
		acc.setPhotoPath(photoPath);
		int save = dao.save(acc);
		if (save == 0) {
			log.info("Entity acc was succesfully created");
			resp.sendRedirect("/history?action=history&nick="+acc.getNick());
		} else if (save == 1) {
			log.warning("Entity: " + acc.getClass() + " " + acc.getNick()
					+ " wasnt save!");
			resp.sendRedirect("/blobstore?message=failure_saving");
		} else {
			log.warning("Entity " + acc.getClass() + " with " + acc.getNick()
					+ " or " + acc.getAddr() + "already exist!");
			resp.sendRedirect("/blobstore?message=" + acc.getNick()
					+ "_already_exist");
		}
		log.info("handleSubmit - OK");
	}

	@Override
	protected void showRecord(String nick, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		log.info("showRecord starting...nick:" + nick);
		Account acc = dao.getByNick(nick);
		log.info("showRecord - OK");
		if(acc.getPhotoPath().equals("/resource/nophoto.jpg")){resp.setContentType("text/html"); resp.getWriter().print(acc.getPhotoPath());}
		else{
		blobService.serve(new BlobKey(acc.getPhotoPath()), resp);}
	}
}
