package entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

@Entity@Unindex
public class Account {

	@Id
	private String nick;
	@Index
	private String addr;
	private String status;
    private String photoPath;
    
    
    public Account(){};
    
    public Account(String nick, String addr){
    	this.nick=nick;
    	this.addr=addr;
    	this.status="...";
    	this.photoPath="/resource/nophoto.jpg";
    }

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
