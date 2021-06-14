package com.awesome.nc;

import java.math.*;

public class TestJava
{

	public static void main(String[] args)
	{
		System.out.println(removeNumbersFromCityName("Lipton 234"));
		
		
	}
	
	private static String removeNumbersFromCityName(String cityName)
	{
		if(null != cityName) {
		 cityName = cityName.replaceAll("\\d", "").trim();
		}
		
		return cityName;
	}
	

	static void decimal()
	{
		BigDecimal latInput = new BigDecimal(13.12);
		BigDecimal latResponse = new BigDecimal(13.122333);
		
		System.out.println(latInput.scale());
		
		
		System.out.println(latInput.setScale(latInput.scale(), RoundingMode.CEILING));
		System.out.println(latResponse.setScale(latInput.scale(), RoundingMode.CEILING));
		
	}
	
	


}
