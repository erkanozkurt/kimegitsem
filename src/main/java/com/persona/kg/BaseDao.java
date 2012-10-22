package com.persona.kg;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.persona.kg.dao.TblWatchList;

public class BaseDao extends HibernateDaoSupport 
{
	protected Log logger=LogFactory.getLog(BaseDao.class);
	public boolean addWatch(final TblWatchList watch){
		boolean result=false;
		try {
			Object obj = getHibernateTemplate().save(watch);
			result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
}
