package com.persona.kg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblFriendship;
import com.persona.kg.dao.TblFriendshipId;
import com.persona.kg.dao.TblLandingPage;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblRate;
import com.persona.kg.dao.TblSubscriber;
import com.persona.kg.dao.TblWatchList;

public class SubscriberDAO extends BaseDao {
	public TblSubscriber retrieveFacebookSubscriber(final String facebookId){
		TblSubscriber result=null;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblSubscriber subs where subs.facebookId=?");
							query.setString(0,facebookId);
							return query.uniqueResult();
						}
					});
			result = (TblSubscriber)obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	public TblSubscriber retrieveUserById(final Integer subscriberId){
		TblSubscriber result=null;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblSubscriber subs where subs.subscriberId=?");
							query.setInteger(0,subscriberId);
							return query.uniqueResult();
						}
					});
			result = (TblSubscriber)obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	
	public boolean storeUser(final TblSubscriber subscriber){
		boolean result=false;
		try {
			 getHibernateTemplate().saveOrUpdate(subscriber);
			 result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
		
	}
	
	public boolean createFriend(final int initiatorId, final int friendId, final short status){
		boolean result=false;
		try {
			 TblFriendship friendship=new TblFriendship();
			 TblFriendshipId id=new TblFriendshipId();
			 id.setFriendId(friendId);
			 id.setInitiatorId(initiatorId);
			 id.setStatus(status);
			 friendship.setId(id);
			 getHibernateTemplate().saveOrUpdate(friendship);
			 result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
		
	}
	
	public List<TblWatchList> retrieveWathListBySubscriber(final Integer subscriberId){
		List<TblWatchList> results=new ArrayList<TblWatchList>();
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblWatchList watch where watch.tblSubscriber.subscriberId=s?");
							query.setInteger(0, subscriberId);
							return query.list();
						}
					});
			results = (List<TblWatchList>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
		
	}
	
	public Map<Integer, Object[]> retrievePoiWatchListBysubscriberId(final Integer subscriberId){
		 Map<Integer, Object[]> results=new HashMap<Integer,Object[]>();
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createSQLQuery("select poi.poi_id, poi.poi_name,poi.unique_identifier, list.watch_list_id from tbl_poi poi, tbl_watch_list list where poi.poi_id = list.item_id and list.item_type=2 and list.owner_id=?");
							query.setInteger(0, subscriberId);
							return query.list();
						}
					});
			List<Object[]> queryResult=(List<Object[]>) obj;
			results=getMapFromList(queryResult);
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
		
	}
	
	public Map<Integer, Object[]> retrieveSubscriberWatchListBysubscriberId(final Integer subscriberId){
		 Map<Integer, Object[]> results=new HashMap<Integer,Object[]>();
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createSQLQuery("select subscriber.subscriber_id, concat(subscriber.name,' ',substring(subscriber.surname,1,1),'.'),  list.watch_list_id from tbl_subscriber subscriber, tbl_watch_list list where subscriber.subscriber_id = list.item_id and list.item_type=1 and list.owner_id=?");
							query.setInteger(0, subscriberId);
							return query.list();
						}
					});
			List<Object[]> queryResult=(List<Object[]>) obj;
			results=getMapFromList(queryResult);
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;	
	}
	
	public Map<Integer, Object[]> retrieveTalkWatchListBysubscriberId(final Integer subscriberId){
		 Map<Integer, Object[]> results=new HashMap<Integer,Object[]>();
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createSQLQuery("select talk.conversation_id, talk.subject, list.watch_list_id from tbl_conversation talk, tbl_watch_list list where talk.conversation_id = list.item_id and list.item_type=3 and list.owner_id=?");
							query.setInteger(0, subscriberId);
							return query.list();
						}
					});
		
			List<Object[]> queryResult=(List<Object[]>) obj;
			results=getMapFromList(queryResult);
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
		
	}
	
	private Map<Integer, Object[]> getMapFromList(List<Object[]> sourceList){
		Map<Integer, Object[]> resultMap=new HashMap<Integer, Object[]>();
		if(sourceList!=null && sourceList.size()>0){
			Iterator<Object[]> sourceListIterator=sourceList.iterator();
			while(sourceListIterator.hasNext()){
				Object[] item=sourceListIterator.next();
				resultMap.put((Integer)item[0],item);
			}
		}
		return resultMap;
	}
	
	public boolean storeRate(final TblRate rate){
		boolean result=false;
		try {
			 getHibernateTemplate().saveOrUpdate(rate);
			 result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;	
	}
	
	public boolean checkRate(final int userId, final int commentId){
		boolean result=false;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblRate rate where rate.ownerId=? and rate.itemId=? and rate.rateType in (1,2)");
							query.setInteger(0,userId);
							query.setInteger(1,commentId);
							return query.uniqueResult();
						}
					});
			if(obj!=null){
				result=true;
			}
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
}
