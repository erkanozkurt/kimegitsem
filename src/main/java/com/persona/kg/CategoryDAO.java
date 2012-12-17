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
import com.persona.kg.dao.TblLandingPage;
import com.persona.kg.dao.TblLandingPagePoi;

public class CategoryDAO extends BaseDao {
	public List<TblCategory> retrieveSubCategories(String parent) {
		List<TblCategory> results = new ArrayList<TblCategory>();
		Integer parentId = 0;
		if (parent != null) {
			try {
				parentId = Integer.parseInt(parent);
			} catch (Exception e) {
				logger.warn("Parent id is not valid integer: " + parent, e);
			}
		}
		final Integer id = parentId;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session
									.createQuery("from TblCategory cat where cat.parentId=?");

							// SQLQuery query = session
							// .createSQLQuery("SELECT * FROM tbl_category where parent_id=?");
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

	public TblCategory findCategoryById(String categoryId) {
		TblCategory result = null;
		Integer parentId = 0;
		if (categoryId != null) {
			try {
				parentId = Integer.parseInt(categoryId);
			} catch (Exception e) {
				logger.warn("Parent id is not valid integer: " + categoryId, e);
			}
		}
		final Integer id = parentId;
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session
									.createQuery("from TblCategory cat where cat.categoryId=?");
							query.setInteger(0, id);
							return query.uniqueResult();
						}
					});
			result = (TblCategory) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return result;
	}

	public Map<Integer, TblCategory> buildCategoryTree() {
		Map<Integer, TblCategory> results = new HashMap<Integer, TblCategory>();
		List<TblCategory> tempList = new ArrayList<TblCategory>();
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session
									.createQuery("from TblCategory cat order by cat.parentId");
							return query.list();
						}
					});
			tempList = (List<TblCategory>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}

		if (tempList != null) {
			Iterator<TblCategory> categoryIterator = tempList.iterator();
			TblCategory topCategory = new TblCategory();
			topCategory.setCategoryId(0);
			topCategory.setCategoryName("Top");
			results.put(0, topCategory);
			while (categoryIterator.hasNext()) {
				TblCategory tmp = categoryIterator.next();
				TblCategory parentCat = results.get(tmp.getParentId());
				tmp.setParent(parentCat);
				parentCat.addChild(tmp);
				results.put(tmp.getCategoryId(), tmp);
			}
		}
		return results;
	}
	
	public List<TblCategory> getAvailableCategories() {
		List<TblCategory>  results = new ArrayList<TblCategory>();
		
		try {
			Object obj = getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session
									.createQuery("from TblCategory cat where cat.status=1 order by cat.categoryName");
							return query.list();
						}
					});
			results = (List<TblCategory>) obj;
		} catch (Exception exp) {
			logger.error("Database Exception", exp);
		}
		return results;
	}
	

}
