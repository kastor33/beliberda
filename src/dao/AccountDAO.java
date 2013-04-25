package dao;

import com.googlecode.objectify.NotFoundException;

import entities.Account;
import static dao.OfyService.ofy;

public class AccountDAO{

    public int save(Account acc) {
    	if(isExist(acc)){return 2;}
    	else{
    	try{
    	   ofy().save().entity(acc).now();
    	   return 0;
    	}catch(Exception ex){System.out.println(ex); return 1;}
    	}
    }
    public boolean update(Account acc) {
    	try{
     	   ofy().save().entity(acc).now();
     	   return true;
     	}catch(Exception ex){System.out.println(ex); return false;}
    }

    public Account getByNick(String nick){
    	try{
    	return ofy().load().type(Account.class).id(nick).get();
    	}catch(NotFoundException ex){return null;}
    }
    
    public Account getByAddr(String addr){
    	return ofy().load().type(Account.class).filter("addr", addr).first().get();
    }
    
    public boolean isExist(Account acc){
    	Account ac = null;
    	try{ac = ofy().load().type(Account.class).id(acc.getNick()).get();}
    	catch(NullPointerException ex){ac = null;}
    	if(ac!=null){return true;}//this nickname already are used
    	else return false;//allright, you may create your account
    }
    
    public boolean delete(Account acc){
    	try{
    		ofy().delete().entity(acc);return true;
    	}catch(Exception ex){System.out.println(ex);return false;}   	
    }
}