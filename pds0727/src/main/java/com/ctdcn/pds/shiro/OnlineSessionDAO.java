package com.ctdcn.pds.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author zjblague
 *         2015-06-07 16:24.
 */
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {

    @Autowired
    private SimpleSessionFactory simpleSessionFactory;

    @Override
    protected Session doReadSession(Serializable sessionId) {

        return super.doReadSession(sessionId);
    }

    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
    }
}
