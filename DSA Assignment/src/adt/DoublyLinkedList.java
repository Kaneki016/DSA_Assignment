package adt;
import java.util.Iterator;

public class DoublyLinkedList<T> implements DoublyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data) {
            this.data = data;
        }
    }

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public boolean add(int index, T element) {
        if (index < 0 || index > size) return false;

        Node<T> newNode = new Node<>(element);

        if (index == 0) { // Insert at head
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (size == 0) {
                tail = newNode;
            }
        } else if (index == size) { // Insert at tail
            add(element);
            return true;
        } else { // Insert at middle
            Node<T> current = getNodeAt(index);
            newNode.next = current;
            newNode.prev = current.prev;
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
        }
        size++;
        return true;
    }

    @Override
    public T remove() {
        if (head == null) {
            return null;
        }
        return remove(0);
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node<T> toRemove;
        if (index == 0) { // Remove head
            toRemove = head;
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null; // List is now empty
            }
        } else if (index == size - 1) { // Remove tail
            toRemove = tail;
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else { // Remove middle
            toRemove = getNodeAt(index);
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
        }
        size--;
        return toRemove.data;
    }

    @Override
    public boolean removeSpecific(T element) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                if (current == head) {
                    remove(0);
                } else if (current == tail) {
                    remove(size - 1);
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    size--;
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeAt(index);
        return (node != null) ? node.data : null;
    }

    @Override
    public void set(int index, T newElement) {
        Node<T> node = getNodeAt(index);
        if (node != null) {
            node.data = newElement;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int indexOf(T element) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(element)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    @Override
    public void removeAll() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void clear() {
        removeAll();
    }

    @Override
    public void listAll() {
        display();
    }

    @Override
    public void display() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            current = current.next;
        }
        System.out.println();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    private Node<T> getNodeAt(int index) {
        if (index < 0 || index >= size) return null;
        Node<T> current;
        if (index < size / 2) { // Search from head
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else { // Search from tail
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }
    
    @Override
    public void replace(int index, T newItem) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        current.data = newItem;  // Replace the data in the node
    }
    
        @Override
    public int getSize() {
        return size;
    }
}
