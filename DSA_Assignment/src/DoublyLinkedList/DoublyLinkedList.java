/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DoublyLinkedList;

/**
 *
 * @author laiyo
 */
public class DoublyLinkedList<T> {
    private static class Node<T> {
        T data;
        Node<T> prev, next;
        
        Node(T data) {
            this.data = data;
            this.prev = this.next = null;
        }
    }
    
    private Node<T> head, tail;
    private int size;
    
    public DoublyLinkedList() {
        head = tail = null;
        size = 0;
    }
    
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }
    
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }
    
    public T removeFirst() {
        if (head == null) return null;
        T data = head.data;
        head = head.next;
        if (head != null) head.prev = null;
        else tail = null;
        size--;
        return data;
    }
    
    public T removeLast() {
        if (tail == null) return null;
        T data = tail.data;
        tail = tail.prev;
        if (tail != null) tail.next = null;
        else head = null;
        size--;
        return data;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void printForward() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }
    
    public void printBackward() {
        Node<T> temp = tail;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.prev;
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addFirst(10);
        list.addFirst(20);
        list.addLast(30);
        list.printForward();  // 20 10 30
        list.printBackward(); // 30 10 20
        System.out.println("Removed: " + list.removeFirst()); // 20
        System.out.println("Removed: " + list.removeLast());  // 30
        list.printForward();  // 10
    }
}
