package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.googlecode.objectify.Key;

import entities.Comment;
import entities.History;
import static dao.OfyService.ofy;

@SuppressWarnings("serial")
public class HistoryDAO implements Serializable {

	public boolean saveHist(History h, Comment firstcomm) {
		try {
			ofy().save().entities(h, firstcomm).now();
			return true;
		} catch (Exception ex) {
			System.out.println(ex);
			return false;
		}
	}

	public boolean save(Comment c) {
		try {
			ofy().save().entity(c).now();
			return true;
		} catch (Exception ex) {
			System.out.println(ex);
			return false;
		}
	}

	public boolean delete(Comment c) {
		List<Comment> lc = new ArrayList<Comment>();
		Key<Comment> parent = Key.create(Comment.class, c.getId());
		lc = ofy().load().type(Comment.class).filter("parent =", parent).list();
		if (lc.isEmpty()) {
			try {
				ofy().delete().entity(c).now();
				return true;
			} catch (Exception ex) {
				System.out.println(ex);
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean isExist(Key<Comment> key) {
		Comment com = null;
		try {
			com = ofy().load().key(key).get();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		if (com.equals(null)) {
			return false;
		} else {
			return true;
		}
	}

	public List<History> getHistories() {
		List<History> histories = null;
		try {
			histories = ofy().load().type(History.class).list();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return histories;
	}
	
	public List<History> getHistoriesByAuthor(String author) {
		List<History> histories = null;
		try {
			histories = ofy().load().type(History.class).filter("author",author).list();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return histories;
	}

	public History getHistory(String id) {
		try {
			return ofy().load().type(History.class).id(id).get();
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}

	public Comment getComment(String id) {
		try {
			return ofy().load().type(Comment.class).id(id).get();
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}

	public TreeMap<String, Comment> getCommentList(String firstcommid) {

		TreeMap<String, Comment> mc = new TreeMap<String, Comment>();
		if (!firstcommid.equals("")) {
			Comment comm = ofy().load().type(Comment.class).id(firstcommid)
					.get();
			Key<Comment> parent = Key.create(Comment.class, comm.getId());
			mc.put("1", comm);
			String curkey = "1";
			getTMComment(curkey, parent, mc);
		}
		return mc;
	}

	public void getTMComment(String curkey, Key<Comment> parent,
			TreeMap<String, Comment> mc) {
		int i = 1;
		List<Comment> lc = new ArrayList<Comment>();
		try {
			lc = ofy().load().type(Comment.class).filter("parent =", parent)
					.list();
		} catch (Exception ex) {
			lc.clear();
		}
		if (!lc.isEmpty() || !lc.equals(null)) {
			for (Comment c : lc) {
				mc.put(curkey + i, c);
				getTMComment(curkey + i, Key.create(Comment.class, c.getId()),
						mc);
				i++;
			}
		}
	}

	// ищет самую залайканую историю среди всех возможных вариантов этой
	// истории,
	// путем перебора всех конечных коментов и сравнивая суммы лайков по
	// цепочкам
	// возвращает финальный комент самой лучшей истории.
	public Comment findBest(String histId) {

		TreeMap<Integer, Comment> marks = new TreeMap<Integer, Comment>();
		History history = ofy().load().type(History.class).id(histId).get();
		Comment first = ofy().load().type(Comment.class).id(history.getComm())
				.get();
		Key<Comment> parent = Key.create(Comment.class, first.getId());
		marks.put(first.getMark(), first);
		getSumMarks(first.getMark(), parent, marks);
		history.setTotal(marks.navigableKeySet().last());// пишем в тотал
															// истории
															// максимальную
															// сумму
		ofy().save().entity(history);
		return marks.get(marks.navigableKeySet().last());
	}

	public void getSumMarks(int sum, Key<Comment> parent,
			TreeMap<Integer, Comment> mc) {
		List<Comment> lc = new ArrayList<Comment>();
		try {
			lc = ofy().load().type(Comment.class).filter("parent =", parent)
					.list();
		} catch (Exception ex) {
			lc.clear();
		}
		if (!lc.isEmpty() || !lc.equals(null)) {
			for (Comment c : lc) {
				mc.put(sum + c.getMark(), c);
				getSumMarks(sum + c.getMark(),
						Key.create(Comment.class, c.getId()), mc);
			}
		}
	}
}
