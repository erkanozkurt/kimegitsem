package com.persona.kg.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class HibernateUtil {
	 
    public static <T> T unproxy(T entity) {
        if (entity == null) {
            return null;
        }
  
        if (entity instanceof HibernateProxy) {
            Hibernate.initialize(entity);
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
 
        return entity;
    }
    
    public static List deproxyList(List sourceList){
		List targetList=new ArrayList();
		if(sourceList!=null){
			Iterator sourceIterator=sourceList.iterator();
			while(sourceIterator.hasNext()){
				targetList.add(unproxy(sourceIterator.next()));
			}
		}
		return targetList;
	}
}
