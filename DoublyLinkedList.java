import java.util.*;

public class DoublyLinkedList<E> implements Iterable<E> {
    private static class Node<E> {
        private E data;
        private Node<E> previous;
        private Node<E> next;

        public Node(E data, Node<E> previous, Node<E> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }

        public Node(E data) {
            this(data, null, null);
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        tail = head = null;
        size = 0;
    }

    public E get(int index) {
        checkIndex(index);
        return getNodeAt(index).data;
    }

    public E set(int index, E element) {
        checkIndex(index);
        Node<E> node = getNodeAt(index);
        E oldData = node.data;
        node.data = element;
        return oldData;
    }

    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (index == 0) {
            addFirst(element);
        } else if (index == size) {
            addLast(element);
        } else {
            Node<E> current = getNodeAt(index);
            Node<E> newNode = new Node<>(element, current.previous, current);
            current.previous.next = newNode;
            current.previous = newNode;
            size++;
        }
    }

    public E remove(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<E> current = getNodeAt(index);
            current.previous.next = current.next;
            current.next.previous = current.previous;
            size--;
            return current.data;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private Node<E> getNodeAt(int index) {
        Node<E> current;

        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }

        return current;
    }

    public void addFirst(E element) {
        head = new Node<>(element, null, head);
        size++;

        if (tail == null) {
            tail = head;
        } else {
            head.next.previous = head;
        }
    }

    public void addLast(E element) {
        if (isEmpty()) {
            addFirst(element);
        } else {
            tail = tail.next = new Node<>(element, tail, null);
            size++;
        }
    }

    public boolean add(E element) {
        addLast(element);
        return true;
    }

    public void clear() {
        Node<E> current = head;

        while (current != null) {
            Node<E> next = current.next;
            current.previous = null;
            current.next = null;
            current = next;
        }

        head = tail = null;
        size = 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public boolean remove(Object o) {
        Node<E> current = head;

        while (current != null) {
            if (Objects.equals(current.data, o)) {
                if (current.previous != null) {
                    current.previous.next = current.next;
                } else {
                    head = current.next;
                }

                if (current.next != null) {
                    current.next.previous = current.previous;
                } else {
                    tail = current.previous;
                }

                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }

        E data = head.data;
        head = head.next;
        size--;

        if (head == null) {
            tail = null;
        } else {
            head.previous = null;
        }

        return data;
    }

    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }

        E data = tail.data;
        tail = tail.previous;
        size--;

        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }

        return data;
    }

    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return head.data;
    }

    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return tail.data;
    }

    public int indexOf(Object o) {
        Node<E> current = head;
        int index = 0;

        while (current != null) {
            if (Objects.equals(current.data, o)) {
                return index;
            }
            current = current.next;
            index++;
        }

        return -1;
    }

    public int lastIndexOf(Object o) {
        Node<E> current = tail;
        int index = size - 1;

        while (current != null) {
            if (Objects.equals(current.data, o)) {
                return index;
            }
            current = current.previous;
            index--;
        }

        return -1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Node<E> current = head;

        while (current != null) {
            builder.append(current.data);

            if (current != tail) {
                builder.append(", ");
            }
            current = current.next;
        }
        builder.append("]");
        return builder.toString();
    }

    public String toReverseString() {
        StringBuilder builder = new StringBuilder("[");
        Node<E> current = tail;

        while (current != null) {
            builder.append(current.data);

            if (current != head) {
                builder.append(", ");
            }
            current = current.previous;
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<E> {
        private Node<E> current = head;

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No elements to iterate over.");
            }
            E data = current.data;
            current = current.next;
            return data;
        }
    }
}
