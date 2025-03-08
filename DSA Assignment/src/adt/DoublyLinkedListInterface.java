package adt;

public interface DoublyLinkedListInterface<T> extends Iterable<T>  {
    void add(T element);
    boolean add(int index, T element);
    T remove();
    T remove(int index);
    boolean removeSpecific(T element);
    T get(int index);
    void set(int index, T newElement);
    int size();
    boolean isEmpty();
    int indexOf(T element);
    boolean contains(T element);
    void removeAll();
    void clear();
    void listAll();
    void display();
}