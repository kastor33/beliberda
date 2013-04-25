package entities;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

@Entity@Unindex
public class Comment {
	
	@Id
	private String id;
	@Index
    private String author;
    private String text;
    private Date date;
    @Index
    private Key<Comment> parent;
    @Index
    private int mark;
    
    public Comment(){}
    
    public Comment(String author, String text, Key<Comment> parent){
    	this.setAuthor(author);
    	this.setMark(0);
    	this.setText(text);
    	this.setDate(new Date(System.currentTimeMillis()));
    	this.setId(getAuthor()+getDate());
    	this.parent=parent;
    }
    public Comment(String author, String text){
    	this.setAuthor(author);
    	this.setMark(0);
    	this.setText(text);
    	this.setDate(new Date(System.currentTimeMillis()));
    	this.setId(getAuthor()+getDate());
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

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
