package com.persona.kg;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDAO {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		LandingPageDAO dao=(LandingPageDAO)context.getBean("landingPageDao");
		List<?> result=dao.getDailyStats("doktor");
		System.out.println(result.size());
	}
}
