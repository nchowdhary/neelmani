package com.awesome.nc;

import java.util.*;

public class Regex
{


	private static final String REGEX_USA = "^\\d{5}(?:-\\d{4})?$";
	
	public static void main(String[] args)
	{
		
		String regex = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?:-\\[0-9][A-Z][0-9]$";
		regex = "^[A-Za-z]\\d[A-Za-z](?:[ ]\\d[A-Za-z]\\d)?$";
		
		
		List<String> postals = Arrays.asList("J6E 0V1", "J6E", "j6", "J6E ");
		
		
		for (String postalCode : postals)
		{
			System.out.println(postalCode +  ": " + postalCode.matches(regex));

		}
		

	}

}
