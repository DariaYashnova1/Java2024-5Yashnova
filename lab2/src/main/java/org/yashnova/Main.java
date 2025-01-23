package org.yashnova;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(1);
        System.out.println("1 == " + list.size());
    }
}
