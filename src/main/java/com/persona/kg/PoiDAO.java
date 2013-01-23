package com.persona.kg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblCity;
import com.persona.kg.dao.TblComment;
import com.persona.kg.dao.TblDistrict;
import com.persona.kg.dao.TblSubdistrict;
import com.persona.kg.dao.TblImage;
import com.persona.kg.dao.TblLandingPage;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblPoi;
import com.persona.kg.dao.TblPoiAdministrator;
import com.persona.kg.dao.TblPoiCategory;
import com.persona.kg.dao.TblSubscriber;
import com.persona.kg.dao.TblWatchList;

public class PoiDAO extends BaseDao {
	public List<TblCategory> retrieveSubCategories(String parent){
		List<TblCategory> results=new ArrayList<TblCategory>();
		Integer parentId=0;
		if(parent!=null){
			try{
				parentId=Integer.parseInt(parent);
			}catch (Exception e) {
				logger.warn("Parent id is not valid integer: "+parent,e);
			}
		}
		final Integer id=parentId;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblCategory cat where cat.parentId=?");
							
							//SQLQuery query = session
							//		.createSQLQuery("SELECT * FROM tbl_category where parent_id=?");
							query.setInteger(0, id);
							return query.list();
						}
					});
			results = (List<TblCategory>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public TblPoi findPoiByContext(final String context){
		TblPoi result=null;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblPoi poi where poi.uniqueIdentifier=?");
							
							//SQLQuery query = session
							//		.createSQLQuery("SELECT * FROM tbl_category where parent_id=?");
							query.setString(0,context);
							return query.uniqueResult();
						}
					});
			result = (TblPoi) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	
	
	public TblPoi findPoiById(final Integer poiId){
		TblPoi result=null;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblPoi poi where poi.poiId=?");
							query.setInteger(0, poiId);
							return query.uniqueResult();
						}
					});
			result = (TblPoi) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	public List<TblCategory> retrieveCategoriesByPoi(final TblPoi poi){
		List<TblCategory> results=new ArrayList<TblCategory>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblCategory cat where cat.categoryId in (select pc.id.categoryId from TblPoiCategory pc where pc.id.poiId=?)");
							query.setInteger(0, poi.getPoiId());
							return query.list();
						}
					});
			results = (List<TblCategory>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public List<TblImage> retrieveImagesByPoi(final TblPoi poi){
		List<TblImage> results=new ArrayList<TblImage>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblImage img where img.status=3 and img.imgType=?)");
							query.setInteger(0, poi.getPoiId());
							return query.list();
						}
					});
			results = (List<TblImage>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public List<TblComment> retrieveCommentsByPoi(final TblPoi poi){
		List<TblComment> results=new ArrayList<TblComment>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblComment com where com.status=2 and com.tblPoi.poiId=?)");
							query.setInteger(0, poi.getPoiId());
							return query.list();
						}
					});
			results = (List<TblComment>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public boolean addComment(final TblComment comment){
		boolean result=false;
		try {
			Object obj = getHibernateTemplate().save(comment);
			result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	
	public boolean addImage(final TblImage image){
		boolean result=false;
		try {
			Object obj = getHibernateTemplate().save(image);
			result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	
	public List<TblPoi> retrievePoisByCategory(final TblCategory category, final int limit){
		List<TblPoi> results=new ArrayList<TblPoi>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblPoi poi where poi.poiId in (select pc.id.poiId from TblPoiCategory pc where pc.id.categoryId=?)");
							query.setInteger(0, category.getCategoryId());
							query.setMaxResults(limit);
							return query.list();
						}
					});
			results = (List<TblPoi>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public List<TblPoi> retrievePoisByCategoryWithSubcategories(final String categoryList, final int limit){
		List<TblPoi> results=new ArrayList<TblPoi>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblPoi poi where poi.poiId in (select pc.id.poiId from TblPoiCategory pc where pc.id.categoryId in ("+categoryList+"))");
							query.setMaxResults(limit);
							return query.list();
						}
					});
			results = (List<TblPoi>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	
	public List<TblPoi> searchPoiByName(final String name, final int limit){
		List<TblPoi> results=new ArrayList<TblPoi>();
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblPoi poi where poi.poiName like ?)");
							query.setString(0, "%"+name+"%");
							query.setMaxResults(limit);
							return query.list();
						}
					});
			results = (List<TblPoi>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	
	public List<TblCity> retrieveCityList(){
		List<TblCity> results=new ArrayList<TblCity>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblCity tc order by tc.cityName)");
							return query.list();
						}
					});
			results = (List<TblCity>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public List<TblDistrict> retrieveDistrictList(){
		List<TblDistrict> results=new ArrayList<TblDistrict>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblDistrict tc order by tc.districtName)");
							return query.list();
						}
					});
			results = (List<TblDistrict>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public List<TblSubdistrict> retrieveSubdistrictList(){
		List<TblSubdistrict> results=new ArrayList<TblSubdistrict>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblSubdistrict tc order by tc.subdistrictName)");
							return query.list();
						}
					});
			results = (List<TblSubdistrict>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public List<TblPoi> searchPoi(final String categoryClause, final String placeId, final int start, final int limit){
		List<TblPoi> results=new ArrayList<TblPoi>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							String querySQL="from TblPoi tc";
							if(categoryClause!=null && categoryClause.length()>0){
								querySQL+=" where tc.poiId in (select pc.id.poiId from TblPoiCategory pc where pc.id.categoryId in ("+categoryClause+"))";
							}
							
							if(placeId!=null && placeId.length()>0){
								if(categoryClause!=null && categoryClause.length()>0){
									querySQL+=" and";
								}else{
									querySQL+=" where";
								}
								
								if(placeId.indexOf(",")>-1){
									String distrcitId=placeId.substring(0,placeId.indexOf(","));
									querySQL+=" tc.tblDistrict.districtId="+distrcitId;
								}else{
									querySQL+=" tc.tblDistrict.districtId in (select td.districtId from TblDistrict td where td.cityId ="+placeId+")";
								}
							
							}
							
							Query query=session.createQuery(querySQL);
							query.setMaxResults(limit);
							query.setFirstResult(start*limit);
							return query.list();
						}
					});
			results = (List<TblPoi>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	public TblSubscriber retrievePoiAdministrator(final Integer poiId){
		TblSubscriber result=null;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblSubscriber subs where subs.id=(select admin.tblSubscriber.subscriberId from TblPoiAdministrator admin where admin.tblPoi.poiId=?)");
							query.setInteger(0,poiId);
							return query.uniqueResult();
						}
					});
			result = (TblSubscriber)obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	public boolean setAdmin(final TblPoiAdministrator admin){
		boolean result=false;
		try {
			Object obj = getHibernateTemplate().save(admin);
			result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	
	public List<TblPoi> retrievePoisBySubscriber(final Integer subscriberId){
		List<TblPoi> results=new ArrayList<TblPoi>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblPoi poi where poi.poiId in (select admin.tblPoi.poiId from TblPoiAdministrator admin where admin.tblSubscriber.subscriberId=?)");
							query.setInteger(0, subscriberId);
							return query.list();
						}
					});
			results = (List<TblPoi>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	
	public boolean addPoi(final TblPoi poi){
		boolean result=false;
		try {
			TblPoi tmp=null;
			while ((tmp=findPoiByContext(poi.getUniqueIdentifier()))!=null){
				poi.setUniqueIdentifier(poi.getUniqueIdentifier()+new Random().nextInt(100));
			}
			Object obj = getHibernateTemplate().save(poi);
			result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	
	public boolean updatePoi(final TblPoi poi){
		boolean result=false;
		try {
			
			getHibernateTemplate().update(poi);
			result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	
	public boolean addPoiCategory(final TblPoiCategory poiCategory){
		boolean result=false;
		try {
			Object obj = getHibernateTemplate().save(poiCategory);
			result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}
	
	public boolean setPoiLogo(final Integer poiId, final String logo){
		 boolean result=false;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createSQLQuery("update tbl_poi set profile_image=? where poi_id=?");
							query.setString(0, logo);
							query.setInteger(1, poiId);
							query.executeUpdate();
							return true;
						}
					});
			result=true;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;	
	}
	
	public List<TblDistrict> retrieveDistrictListByCityId(final int cityId){
		List<TblDistrict> results=new ArrayList<TblDistrict>();
		Integer parentId=0;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblDistrict tc where tc.cityId=? order by tc.districtName)");
							query.setInteger(0, cityId);
							return query.list();
						}
					});
			results = (List<TblDistrict>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	
	public List<TblSubdistrict> retrieveSubdistrictListByDistrictId(final int districtId){
		List<TblSubdistrict> results=new ArrayList<TblSubdistrict>();
		
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=session.createQuery("from TblSubdistrict tc where tc.districtId=? order by tc.subdistrictName)");
							query.setInteger(0, districtId);
							return query.list();
						}
					});
			results = (List<TblSubdistrict>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}

}
