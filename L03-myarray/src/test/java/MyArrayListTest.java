import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest extends MyArrayList {

    @Test
    public void test_EmptyConstructor(){
        try {
            MyArrayList<Object> myArrayList1 = new MyArrayList<Object>();
            MyArrayList<Integer> myArrayList2 = new MyArrayList<Integer>();
            MyArrayList<String> myArrayList3 = new MyArrayList<String>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_Constructor(){
        try {
            MyArrayList<Object> myArrayList1 = new MyArrayList<Object>(10);
            MyArrayList<Integer> myArrayList2 = new MyArrayList<Integer>(20);
            MyArrayList<String> myArrayList3 = new MyArrayList<String>(30);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_GetSize(){
        try {
            int initialSize = 20;
            MyArrayList<Integer> myArrayList2 = new MyArrayList<Integer>(initialSize);
            System.out.printf("size: %s",myArrayList2.size());
            assertEquals(initialSize,myArrayList2.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    @Test
    void size() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void add() {
    }
    */
}