package adt;

public interface DoublyLinkedListInterface<T> {
    void add(T element);                     // Add to end of list
    
    boolean add(int index, T element);       // Insert at index
    
    T remove();                              // Remove last element
    
    T remove(int index);                     // Remove at index
    
    boolean removeSpecific(T element);       // Remove first occurrence
    
    T get(int index);                        // Get element at index
    
    void set(int index, T newElement);       // Replace element at index
    
    int size();                              // Get size of list
    
    boolean isEmpty();                       // Check if list is empty
    
    int indexOf(T element);                  // Find index of element
    
    boolean contains(T element);             // Check if element exists
    
    void removeAll();                        // Remove all elements
    
    void clear();                            // Alias for removeAll()
    
    void listAll();                          // Display all elements
    
    void display();                          // Alias for listAll()
}