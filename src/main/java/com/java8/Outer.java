package com.java8;

public class Outer {
    private String thoughts = "My outer thoughts";

    class Inner {
        private String innerThoughts = "My inner thoughts";

        public void print() {
            // inner class can access outer class private attribute.
            System.out.println(innerThoughts);
            System.out.println(thoughts);
        }
    }

    public static void main(String[] args) {
        Outer.Inner inner = new Outer().new Inner();
        inner.print();

    }
}