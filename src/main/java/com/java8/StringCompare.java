package com.java8;

public class StringCompare {

	public static String s1 = "Hello World";
	public static String s2 = "Hello World";
	public static String s3 = new String("Hello World");
	public static String s4 = new String("Hello World");

	public static void main(String[] args) {
		System.out.println(s1 == s2);
		System.out.println(s1.equals(s2));
		System.out.println(s3 == s4);
		System.out.println(s3.equals(s4));

	}
}
