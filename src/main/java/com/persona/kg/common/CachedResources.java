package com.persona.kg.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.persona.kg.CategoryDAO;
import com.persona.kg.PoiDAO;
import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblCity;
import com.persona.kg.dao.TblDistrict;

public class CachedResources {
	@Autowired
	private CategoryDAO categoryDao;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private PoiDAO poiDao;
	public CategoryDAO getCategoryDao() {
		return categoryDao;
	}
	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}
	public CacheManager getCacheManager() {
		return cacheManager;
	}
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	public PoiDAO getPoiDao() {
		return poiDao;
	}
	public void setPoiDao(PoiDAO poiDao) {
		this.poiDao = poiDao;
	}
	
	public Map<Integer,TblCategory> getCategoryMap(){
		Map<Integer,TblCategory> categories=null;
		if(getCache().get(ApplicationConstants.CATEGORY_CACHE_KEY)!=null){
			categories=(Map<Integer, TblCategory>)getCache().get(ApplicationConstants.CATEGORY_CACHE_KEY).get();
		}else{
			categories=categoryDao.buildCategoryTree();
			getCache().put(ApplicationConstants.CATEGORY_CACHE_KEY, categories);
		}
		return categories;
	}
	
	public List<TblCategory> getCategoryList(){
		List<TblCategory> categories=null;
		if(getCache().get(ApplicationConstants.CATEGORY_LIST_CACHE_KEY)!=null){
			categories=(List<TblCategory>)getCache().get(ApplicationConstants.CATEGORY_LIST_CACHE_KEY).get();
		}else{
			categories=categoryDao.getAvailableCategories();
			getCache().put(ApplicationConstants.CATEGORY_LIST_CACHE_KEY, categories);
		}
		return categories;
	}
	
	public Map<String,String> getMergedPlaceList(){
		Map<String,String> mergedPlaceList=null;
		if(getCache().get(ApplicationConstants.MERGED_PLACE_CACHE_KEY)!=null){
			mergedPlaceList=(Map<String,String>)getCache().get(ApplicationConstants.MERGED_PLACE_CACHE_KEY).get();
		}else{
			mergedPlaceList=new HashMap<String, String>();
			List<TblCity> cityList=poiDao.retrieveCityList();
			List<TblDistrict> districtList=poiDao.retrieveDistrictList();
			Iterator<TblCity> cityIterator=cityList.iterator();
			while(cityIterator.hasNext()){
				TblCity city=cityIterator.next();
				mergedPlaceList.put(""+city.getCityId(),city.getCityName());
			}
			
			Iterator<TblDistrict> districtIterator=districtList.iterator();
			while(districtIterator.hasNext()){
				TblDistrict district=districtIterator.next();
				String city=mergedPlaceList.get(""+district.getTblCity().getCityId());
				mergedPlaceList.put(district.getDistrictId()+","+district.getTblCity().getCityId(), district.getDistrictName()+", "+city);
			}
			
			getCache().put(ApplicationConstants.MERGED_PLACE_CACHE_KEY, mergedPlaceList);
		}
		return mergedPlaceList;
	}
	
	public List<TblCity> getCityList(){
		List<TblCity> mergedPlaceList=null;
		if(getCache().get(ApplicationConstants.CITY_LIST_CACHE_KEY)!=null){
			mergedPlaceList=(List)getCache().get(ApplicationConstants.CITY_LIST_CACHE_KEY).get();
		}else{
			mergedPlaceList=poiDao.retrieveCityList();
			getCache().put(ApplicationConstants.CITY_LIST_CACHE_KEY, mergedPlaceList);
		}
		return mergedPlaceList;
	}
	
	public List<TblDistrict> getDistrictList(){
		List<TblDistrict> mergedPlaceList=null;
		if(getCache().get(ApplicationConstants.DISTRICT_LIST_CACHE_KEY)!=null){
			mergedPlaceList=(List<TblDistrict>)getCache().get(ApplicationConstants.DISTRICT_LIST_CACHE_KEY).get();
		}else{
			mergedPlaceList=poiDao.retrieveDistrictList();
			getCache().put(ApplicationConstants.DISTRICT_LIST_CACHE_KEY, mergedPlaceList);
		}
		return mergedPlaceList;
	}
	
	public TblCategory getCategoryByCategoryName(String categoryName){
		TblCategory category=null;
		Map<Integer, TblCategory> categories=getCategoryMap();
		Iterator<TblCategory> categoryIterator=categories.values().iterator();
		while(categoryIterator.hasNext()){
			TblCategory tmp=categoryIterator.next();
			if(categoryName.equals(tmp.getCategoryName())){
				category=tmp;
				break;
			}
		}
		return category;
	}
	
	public String getPlaceIdByPlaceName(String placeName){
		String placeId=null;
		Map<String, String> places=getMergedPlaceList();
		Iterator<String> placeIterator=places.keySet().iterator();
		while(placeIterator.hasNext()){
			String placeKey=placeIterator.next();
			String placeValue=places.get(placeKey);
			if(placeName.equals(placeValue)){
				placeId=placeKey;
				break;
			}
		}
		return placeId;
	}
	
	public String getCategoryInStatementByCategoryName(String categoryName){
		StringBuilder builder=new StringBuilder();
		TblCategory category=null;
		Map<Integer, TblCategory> categories=getCategoryMap();
		Iterator<TblCategory> categoryIterator=categories.values().iterator();
		while(categoryIterator.hasNext()){
			TblCategory tmp=categoryIterator.next();
			if(categoryName.equals(tmp.getCategoryName())){
				category=tmp;
				break;
			}
		}
		if(category!=null){
			builder.append(getSubcategoryClause(category));
		}
		
		return builder.toString();
	}
	
	private String getSubcategoryClause(TblCategory category){
		StringBuilder builder=new StringBuilder();
		builder.append(category.getCategoryId());
		if(category.getChilds().size()>0){
			Iterator<TblCategory> categoryIterator=category.getChilds().iterator();
			while(categoryIterator.hasNext()){
				builder.append(",").append(getSubcategoryClause(categoryIterator.next()));
			}
		}
		return builder.toString();
	}
	
	private Cache getCache(){
		return cacheManager.getCache("default");
	}
}
