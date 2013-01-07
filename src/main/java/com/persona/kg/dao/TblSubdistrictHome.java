package com.persona.kg.dao;
// Generated Dec 6, 2012 6:25:50 PM by Hibernate Tools 3.1.0.beta4

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;



/**
 * Home object for domain model class TblSubdistrict.
 * @see com.persona.kgadmin.dao.TblSubdistrict
 * @author Hibernate Tools
 */
public class TblSubdistrictHome {

    private static final Log log = LogFactory.getLog(TblSubdistrictHome.class);

    private final SessionFactory sessionFactory = getSessionFactory();
    
    protected SessionFactory getSessionFactory() {
        try {
            return (SessionFactory) new InitialContext().lookup("SessionFactory");
        }
        catch (Exception e) {
            log.error("Could not locate SessionFactory in JNDI", e);
            throw new IllegalStateException("Could not locate SessionFactory in JNDI");
        }
    }
    
    public void persist(TblSubdistrict transientInstance) {
        log.debug("persisting TblSubdistrict instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void attachDirty(TblSubdistrict instance) {
        log.debug("attaching dirty TblSubdistrict instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(TblSubdistrict instance) {
        log.debug("attaching clean TblSubdistrict instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void delete(TblSubdistrict persistentInstance) {
        log.debug("deleting TblSubdistrict instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public TblSubdistrict merge(TblSubdistrict detachedInstance) {
        log.debug("merging TblSubdistrict instance");
        try {
            TblSubdistrict result = (TblSubdistrict) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public TblSubdistrict findById( int id) {
        log.debug("getting TblSubdistrict instance with id: " + id);
        try {
            TblSubdistrict instance = (TblSubdistrict) sessionFactory.getCurrentSession()
                    .get("com.persona.kgadmin.dao.TblSubdistrict", id);
            if (instance==null) {
                log.debug("get successful, no instance found");
            }
            else {
                log.debug("get successful, instance found");
            }
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(TblSubdistrict instance) {
        log.debug("finding TblSubdistrict instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.persona.kgadmin.dao.TblSubdistrict")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    } 

}