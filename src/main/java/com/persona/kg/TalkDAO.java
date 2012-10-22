package com.persona.kg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblConversation;
import com.persona.kg.dao.TblConversationReply;
import com.persona.kg.dao.TblLandingPage;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblSubscriber;

public class TalkDAO extends BaseDao {
	
	
	public List<Object[]> retrieveConversationsByCityId(final Integer cityId, final int page, final int maxResults) {
		List<Object[]> results = new ArrayList<Object[]>();
		
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session
									.createSQLQuery("select conv.conversation_id, conv.subject,conv.date_started, conv.subscriber_id, concat(subs.name,' ',substring(subs.surname,1,1),'.')  from tbl_conversation conv, tbl_subscriber subs where city_id=? and conv.subscriber_id=subs.subscriber_id order by date_started desc");
							query.setMaxResults(maxResults);
							query.setFirstResult(maxResults*page);
							query.setInteger(0, cityId);
							return query.list();
						}
					});
			results = (List<Object[]>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public List<Object[]> retrieveConversationReplies(final Integer conversationID, final int page, final int maxResults) {
		List<Object[]> results = new ArrayList<Object[]>();
		
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session
									.createSQLQuery("select rep.reply_id as replyid, rep.reply_text as replytext, rep.date_sent as sentdate, rep.subscriber_id as subsId,concat(subs.name,' ',substring(subs.surname,1,1),'.') as subsname  from tbl_conversation_reply rep, tbl_subscriber subs where rep.subscriber_id=subs.subscriber_id and rep.conversation_id=? and rep.status=2 order by date_sent asc").
									addScalar("replyid",Hibernate.INTEGER).
									addScalar("replytext",Hibernate.TEXT).addScalar("sentdate",Hibernate.DATE).addScalar("subsid",Hibernate.INTEGER).addScalar("subsname",Hibernate.STRING);
							query.setMaxResults(maxResults);
							query.setFirstResult(maxResults*page);
							query.setInteger(0, conversationID);
							return query.list();
						}
					});
			results = (List<Object[]>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}

	public List<Object[]> retrieveConversationsByDistrictId(final Integer districtId, final int page, final int maxResults) {
		List<Object[]> results = new ArrayList<Object[]>();
		
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session
									.createSQLQuery("select conv.conversation_id, conv.subject,conv.date_started, conv.subscriber_id, concat(subs.name,' ',substring(subs.surname,1,1),'.')  from tbl_conversation conv, tbl_subscriber subs where conv.district_id=? and conv.subscriber_id=subs.subscriber_id order by date_started desc");
							query.setMaxResults(maxResults);
							query.setFirstResult(maxResults*page);
							query.setInteger(0, districtId);
							return query.list();
						}
					});
			results = (List<Object[]>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public List<Object[]> retrieveConversations( final int page, final int maxResults) {
		List<Object[]> results = new ArrayList<Object[]>();
		
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session
									.createSQLQuery("select conv.conversation_id, conv.subject,conv.date_started, conv.subscriber_id, concat(subs.name,' ',substring(subs.surname,1,1),'.')  from tbl_conversation conv, tbl_subscriber subs where  conv.subscriber_id=subs.subscriber_id order by date_started desc");
							query.setMaxResults(maxResults);
							query.setFirstResult(maxResults*page);
							return query.list();
						}
					});
			results = (List<Object[]>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}

	public List<Object[]> retrieveCityListWithConvCount() {
		List<Object[]> results = new ArrayList<Object[]>();
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session.createSQLQuery("select city.city_id,city.city_name, (select count(*) from tbl_conversation conv where conv.city_id=city.city_id) from tbl_city city order by city.city_name");
							return query.list();
						}
					});
			results = (List<Object[]>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public List<Object[]> retrieveDistrictListWithConvCount(final Integer cityId) {
		List<Object[]> results = new ArrayList<Object[]>();
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session.createSQLQuery("select district.district_id, district.district_name, (select count(*) from tbl_conversation conv where conv.district_id=district.district_id) from tbl_district district where district.city_id=? order by district.district_name");
							query.setInteger(0, cityId);
							return query.list();
						}
					});
			results = (List<Object[]>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	public boolean storeConversation(final TblConversation conversation){
		boolean result=false;
		try {
			 getHibernateTemplate().saveOrUpdate(conversation);
			 result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
		
	}
	
	public boolean storeReply(final TblConversationReply reply){
		boolean result=false;
		try {
			 getHibernateTemplate().saveOrUpdate(reply);
			 result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
		
	}
	
	
	
	public TblConversation retrieveConversationsByConvID(final Integer convId) {
		TblConversation result = null;
		
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session
									.createQuery("from TblConversation conv where conv.conversationId=?");
							query.setInteger(0, convId);
							return query.uniqueResult();
						}
					});
			result = (TblConversation) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
}
