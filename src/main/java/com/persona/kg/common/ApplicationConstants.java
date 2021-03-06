package com.persona.kg.common;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.persona.kg.dao.TblCategory;

public class ApplicationConstants {
	public final static String USER_CONTEXT_KEY="userContext";
	public final static String CATEGORY_ID_KEY="category";
	public final static String USER_ID_KEY="fbid";
	public final static String PROFILE_ID_KEY="profileId";
	public final static String COMMENT_ID_KEY="commentId";
	public final static String CATEGORY_CACHE_KEY="categoryTree";
	public final static String CATEGORY_LIST_CACHE_KEY="categoryList";
	public final static String MERGED_PLACE_CACHE_KEY="mergedPlacesKey";
	public final static String CITY_LIST_CACHE_KEY="cityListKey";
	public final static String DISTRICT_LIST_CACHE_KEY="districtListKey";
	public final static String SUBDISTRICT_LIST_CACHE_KEY="subdistrictListKey";
	public final static String TALK_CITY_KEY="cityId";
	public final static String TALK_DISTRICT_KEY="districtId";
	public final static String CITY_LIST_KEY="cityListKey";
	public final static String DISTRICT_LIST_KEY="districtListKey";
	public final static String TALK_ID_KEY="talkId";
	public final static String POI_ID="poiId";
	public final static String SUBSCRIBER_ID="subscriberId";
	public final static String FRIENDLIST_KEY="friendList";
	public final static short POI_TYPE=2;
	public final static boolean DEV_MODE=false;
	public final static short SUBSCRIBER_TYPE=1;
	public final static short TALK_TYPE=3;
	public static String context;
	public static String domain;



	public static String getFacebookApiKey(){
		return getProperty("facebookApiKey");
	}
	public static String getGoogleMapsKey(){
		return getProperty("googleMapsApiKey");
	}
	public static String getDomainName(){
		if(domain==null)
			domain=getProperty("domainName");
		return domain;
	}

	public static String getImageDir(){
		return getProperty("imageDir");
	}
	public static String getImageUrl(){
		return getDomainName()+"/content/";
	}
	public static String getContext(){
		if(context==null){
			context=getProperty("context");
			if(context!=null && context.length()>0)
				context+="/";
			context=getDomainName()+"/"+context;
		}
		return context;
	}



	public static String getProperty(String key){
		Context initialContext=null;
		try {
			initialContext = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String value = null;
		if(initialContext!=null)
			try {
				value=(String) initialContext.lookup("java:comp/env/"+key);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return value;
	}

}