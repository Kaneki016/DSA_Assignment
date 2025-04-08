package adt;
import java.util.Comparator;

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
    void replace(int givenPosition, T newEntry);  // <-- Add this
    int getSize();
    void mergeSort(Comparator<T> comparator);
    void backup();
    DoublyLinkedList<T> copyList();
}