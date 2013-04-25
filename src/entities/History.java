package entities;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

@Entity@Unindex
public class History {

	@Id
	private String id;
	@Index
	private String author;
	@Index
	private String genre;
	private String title;
	private Date date;
	private int total;
	@Index
	private String comm;
	private int charlimit;
	
	public History(){}
	
	@SuppressWarnings("deprecation")
	public History(String author, String genre, int limit, String title, String comm){

		this.setAuthor(author);
		this.setGenre(genre);
		this.setTotal(0);
		this.setCharlimit(limit);
		this.setTitle(title);
		this.setDate(new Date(System.currentTimeMillis()));
		this.setId(getTitle()+"|"+getDate().getYear()+"|"+getDate().getMonth()+"|"+getDate().getDate());
		this.setComm(comm);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public int getCharlimit() {
		return charlimit;
	}

	public void setCharlimit(int charlimit) {
		this.charlimit = charlimit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}




