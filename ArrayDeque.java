import java.util.*;

public class ArrayDeque<E> implements Iterable<E> {
    private E[] elements;
    private int indexOfFirst;
    private int indexOfLast;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayDeque() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayDeque(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }

        elements = (E[]) new Object[initialCapacity];
        indexOfFirst = indexOfLast = -1;
    }

    public int size() {
        if (indexOfFirst == -1 && indexOfLast == -1) {
            return 0;
        } else if (indexOfFirst <= indexOfLast) {
            return indexOfLast - indexOfFirst + 1;
        } else {
            return (indexOfLast + 1) + (elements.length - indexOfFirst);
        }
    }

    private boolean isFull() {
        return size() == elements.length;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void addFirst(E element) {
        if (isFull()) {
            ensureCapacity(2 * elements.length + 1);
        }
        if (isEmpty()) {
            elements[0] = element;
            indexOfFirst = indexOfLast = 0;
        } else {
            indexOfFirst--;
            
            if (indexOfFirst == -1) {
                indexOfFirst = elements.length - 1;
            }
            
            elements[indexOfFirst] = element;
        }
    }

    public void addLast(E element) {
        if (isFull()) {
            ensureCapacity(2 * elements.length + 1);
        }
        if (isEmpty()) {
            addFirst(element);
        } else {
            indexOfLast = (indexOfLast + 1) % elements.length;
            elements[indexOfLast] = element;
        }
    }

    @SuppressWarnings("unchecked")
    public void ensureCapacity(int desiredCapacity) {
        if (elements.length < desiredCapacity) {
            E[] newArr = (E[]) new Object[desiredCapacity];
            
            for (int i = 0; i < size(); i++) {
                newArr[i] = elements[(indexOfFirst + i) % elements.length];
            }
            
            elements = newArr;
            int currentSize = size();
            indexOfFirst = 0;
            indexOfLast = currentSize - 1;
        }
    }

    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        return elements[indexOfFirst];
    }

    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        return elements[indexOfLast];
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        E result = elements[indexOfFirst];
        elements[indexOfFirst] = null;

        if (indexOfFirst == indexOfLast) {
            indexOfFirst = indexOfLast = -1;
        } else {
            indexOfFirst = (indexOfFirst + 1) % elements.length;
        }
        
        return result;
    }

    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        E result = elements[indexOfLast];
        elements[indexOfLast] = null;

        if (indexOfFirst == indexOfLast) {
            indexOfFirst = indexOfLast = -1;
        } else {
            indexOfLast--;
            
            if (indexOfLast == -1) {
                indexOfLast = elements.length - 1;
            }
        }
        
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size(); i++) {
            sb.append(elements[(indexOfFirst + i) % elements.length]);
            
            if (i < size() - 1) {
                sb.append(", ");
            }
        }
        
        return sb.append("]").toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<E> {
        private int index = indexOfFirst;
        private int counter = 0;

        @Override
        public boolean hasNext() {
            return counter < size();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No elements to iterate.");
            }

            E element = elements[index];
            index = (index + 1) % elements.length;
            counter++;
            return element;
        }
    }
}
