package com.java8;

import java.util.List;

/*
 * Both methods have the same name and arguments. 
 * Although their arguments have different generic type, the compiler 
 * still treats them the same and throws erasure 
 */
public class OverloadingWithGeneric {

	// public void methodA( List<String> list ) {}

	// public void methodA( List<Integer> list ) {}

	public static void main(String[] args) {
	}
}
