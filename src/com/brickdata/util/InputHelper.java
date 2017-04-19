package com.brickdata.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InputHelper {

	public static String getInput(String prompt) {
		BufferedReader stdin = new BufferedReader(
				new InputStreamReader(System.in));

		System.out.print(prompt);
		System.out.flush();

		try {
			return stdin.readLine();
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}

	public static double getDoubleInput(String prompt) throws NumberFormatException {
		String input = getInput(prompt);
		return Double.parseDouble(input);

	}

	public static int getIntegerInput(String prompt) throws NumberFormatException {
		String input = getInput(prompt);
		return Integer.parseInt(input);	
	}
	
	public static java.sql.Date getDateInput(String prompt) throws NumberFormatException {
		String expectedPattern = "MM/dd/yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
		String input = getInput(prompt);
		Date date = null;
		try {
			date = formatter.parse(input);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		java.sql.Date sqlDate = convertJavaDateToSqlDate(date);
		return sqlDate;
		//long date = Date.parse(input);
		//return DateFormat.parse(input);	
	}
	
	public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
	    return new java.sql.Date(date.getTime());
	}
	
}

