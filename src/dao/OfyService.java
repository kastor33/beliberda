package dao;


import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import entities.Account;
import entities.Comment;
import entities.History;

public class OfyService {
    static {
        factory().register(Account.class);
        factory().register(History.class);
        factory().register(Comment.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
