import java.util.*;
/*
ДЗ 03. MyArrayList
Написать свою реализацию ArrayList на основе массива.
Проверить, что на ней работают методы java.util.Collections
*/

public class MyArrayList<E> implements List<E> {

    private static final int DEFAULT_SIZE = 10;
    private E[] values;
    private int size = 0;
    //boolean isEmpty();
    //boolean contains(Object o);



    public MyArrayList(){
        this.values = ((E[]) new Object[DEFAULT_SIZE]);
    }

    public MyArrayList(int initialSize) {
        if (initialSize > 0) {
            this.values =  (E[]) new Object[initialSize];
            this.size = initialSize;
        } else {
            throw new IllegalArgumentException("Illegal initial size: "+
                    initialSize);
        }
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.size==0?true:false);
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
