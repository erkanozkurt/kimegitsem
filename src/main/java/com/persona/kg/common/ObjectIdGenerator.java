package com.persona.kg.common;

import java.util.Date;
import java.util.Random;

public class ObjectIdGenerator {
	public static long getObjectId(){
		Random r=new Random();
		return new Date().getTime()+r.nextInt();
	}
}