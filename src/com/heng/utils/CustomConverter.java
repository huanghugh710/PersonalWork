package com.heng.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class CustomConverter implements Converter<String, Date>{

	@Override
	public Date convert(String source) {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
