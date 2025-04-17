package adt;
import java.util.Comparator;

public interface DoublyLinkedListInterface<T> extends Iterable<T>  {
    void add(T element);
    T remove(int index);
    boolean removeSpecific(T element);
    T get(int index);
    int size();
    boolean isEmpty();
    int indexOf(T element);
    boolean contains(T element);
    void removeAll();
    void clear();
    void replace(int givenPosition, T newEntry); 
    int getSize();
    void mergeSort(Comparator<T> comparator);
    void backup();
    DoublyLinkedList<T> copyList();
}