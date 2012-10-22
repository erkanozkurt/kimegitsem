package com.persona.kg;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.persona.kg.dao.TblLandingPage;
import com.persona.kg.dao.TblLandingPagePoi;

public class LandingPageDAO extends BaseDao {
	public void logTransaction(String ip, String email, String comment,
			String poiType, int actionType) {
		TblLandingPage tblLandingPage = new TblLandingPage();
		tblLandingPage.setAction(actionType);
		tblLandingPage.setEmail(email);
		tblLandingPage.setUserIp(ip);
		tblLandingPage.setComment(comment);
		tblLandingPage.setPoiType(poiType);
		tblLandingPage.setActionDate(new Date());
		getHibernateTemplate().save(tblLandingPage);
	}

	public TblLandingPagePoi findPoiByContext(final String context) {
		TblLandingPagePoi poi = null;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session
									.createQuery("from TblLandingPagePoi o where o.poiType like ?");

							query.setString(0, context);
							return query.uniqueResult();
						}
					});
			poi = (TblLandingPagePoi) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return poi;
	}

	public void savePoi(final TblLandingPagePoi context) {
		try {
			getHibernateTemplate().saveOrUpdate(context);
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
	}
	
	public List<?> getDailyStats(final String context) {
		List<?> result=null;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							SQLQuery query = session
									.createSQLQuery("SELECT count(*),sum(action),date_format(action_date,'%d.%m.%Y') FROM tbl_landing_page where poi_type=? group by date_format(action_date,'%d.%m.%Y') order by date_format(action_date,'%d.%m.%Y')");
							query.setString(0, context);
							return query.list();
						}
					});
			result = (List<?>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}	
	
	public List<?> getStatDetail(final String context, final String date) {
		List<?> result=null;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							String queryStr="SELECT email,comment FROM tbl_landing_page where poi_type=? and action=2 ";
							if(date!=null && date.length()>0)
								queryStr+=" and date_format(action_date,'%d.%m.%Y')=?";
							SQLQuery query = session.createSQLQuery(queryStr);
							
							query.setString(0, context);
							if(date!=null && date.length()>0)
								query.setString(1, date);
							return query.list();
						}
					});
			result = (List<?>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}	
}
