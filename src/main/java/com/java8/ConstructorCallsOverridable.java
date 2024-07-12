package com.java8;

public class ConstructorCallsOverridable {

	public static void main(String[] args) {
		ParentClass child = new ChildClass();
	}
}

class ParentClass {
	public ParentClass() {
		System.out.println("ParentClass");
		methodA();
	}

	protected void methodA() {
		System.out.println("ParentClass methodA ");
	}
}

class ChildClass extends ParentClass {

	private String childClassString = null;

	public ChildClass() {
		System.out.println("ChildClass");
		childClassString = new String("childClassString");
		methodA();
	}

	protected void methodA() {
		System.out.println("ChildClass methodA " + childClassString);
	}
}
