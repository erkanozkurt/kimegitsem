package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:05 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class TblLandingPagePoi.
 * @see com.persona.kg.dao.TblLandingPagePoi
 * @author Hibernate Tools
 */
public class TblLandingPagePoiHome {

	private static final Log log = LogFactory
			.getLog(TblLandingPagePoiHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(TblLandingPagePoi transientInstance) {
		log.debug("persisting TblLandingPagePoi instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblLandingPagePoi instance) {
		log.debug("attaching dirty TblLandingPagePoi instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblLandingPagePoi instance) {
		log.debug("attaching clean TblLandingPagePoi instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblLandingPagePoi persistentInstance) {
		log.debug("deleting TblLandingPagePoi instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblLandingPagePoi merge(TblLandingPagePoi detachedInstance) {
		log.debug("merging TblLandingPagePoi instance");
		try {
			TblLandingPagePoi result = (TblLandingPagePoi) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblLandingPagePoi findById(java.lang.Integer id) {
		log.debug("getting TblLandingPagePoi instance with id: " + id);
		try {
			TblLandingPagePoi instance = (TblLandingPagePoi) sessionFactory
					.getCurrentSession().get(
							"com.persona.kg.dao.TblLandingPagePoi", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TblLandingPagePoi instance) {
		log.debug("finding TblLandingPagePoi instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.persona.kg.dao.TblLandingPagePoi")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
