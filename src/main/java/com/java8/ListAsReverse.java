package com.java8;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * List
 * Reference : https://www.baeldung.com/java-list-iterate-backwards
 */
public class ListAsReverse {

	public static void main(String[] args) {

		List<String> list = List.of("A", "B", "C", "D", "E");
		listForward(list);
		listBackward(list);
		forLoopForward(list);
		forLoopBackward(list);
		streamForward(list);
		streamBackward(list);
	}

	private static void listForward(List<String> list) {
		System.out.println("---- listForward ----");
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}

	private static void listBackward(List<String> list) {
		System.out.println("---- listBackward ----");
		ListIterator<String> li = list.listIterator(list.size());
		while (li.hasPrevious()) {
			System.out.println(li.previous());
		}
	}

	private static void forLoopForward(List<String> list) {
		System.out.println("---- forLoopForward ----");
		for (String s : list) {
			System.out.println(s);
		}
	}

	private static void forLoopBackward(List<String> list) {
		System.out.println("---- forLoopBackward ----");
		for (String s : list.reversed()) {
			System.out.println(s);
		}
	}

	private static void streamForward(List<String> list) {
		System.out.println("---- streamForward ----");
		list.stream().forEach(System.out::println);
	}

	private static void streamBackward(List<String> list) {
		System.out.println("---- streamBackward ----");
		list.reversed().stream().forEach(s -> System.out.println(s));
	}

}
