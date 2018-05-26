import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestMemEstimator extends MemEstimator{

    private static final Long base_strlen_j8 = 48L;

    @Test
    public void test_int() {
        try {
            Long res = Estimator(10);
            assertEquals(20L, res, 0.01);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_short() {
        try {
            Long res = Estimator((short)10);
            assertEquals(18L, res, 0.01);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_array_of_int() {
        try {
            int[] arr =  new int[]{1,2,4,4,2};
            Long res = Estimator(arr);
            assertEquals(20L+4L*arr.length, res, 0.01);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_array_of_long() {
        try {
            long[] arr =  new long[]{1,2,4,4,2};
            Long res = Estimator(arr);
            assertEquals(40L+4L*arr.length, res, 0.01);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    /*
    Определить размер пустой строки
    */

    @Test
    public void test_EmptyStrings(){
        try {

            Long res0 = Estimator("");
            assertEquals(base_strlen_j8, res0, 0.01);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /*
    Определить рост размера контейнера от количества элементов в нем.
    */
    @Test
    public void test_Strings(){
        try {

            Long res1 = Estimator("a");
            assertEquals(base_strlen_j8+2L, res1, 0.01);

            Long res2 = Estimator("ab");
            assertEquals(base_strlen_j8+4L, res2, 0.01);

            String testline = "abcdefg";
            Long res7 = Estimator(testline);
            assertEquals(base_strlen_j8+testline.length()*2, res7, 0.01);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /*
    Определить размер пустых контейнеров.
    Определить рост размера контейнера от количества элементов в нем.
    --
    ArrayList с параметрами падает с
    java.lang.reflect.InaccessibleObjectException: Unable to make field private final java.lang.String java.lang.module.ModuleDescriptor.name accessible: module java.base does not "opens java.lang.module" to unnamed module @61a485d2
    так как java 9 module strong incapsulation
    The exception is caused by the Java Platform Module System that was introduced in Java 9, particularly its implementation of strong encapsulation. It only allows access under certain conditions, the most prominent ones are:
    https://stackoverflow.com/questions/41265266/how-to-solve-inaccessibleobjectexception-unable-to-make-member-accessible-m
    workaround:
     - use -XaddExports:java.base/jdk.internal.loader=ALL-UNNAMED
     - or switch to java 8
    */
    @Test
    public void test_ArrayList(){
        try {

            Long res0 = Estimator(new ArrayList<Integer>());
            assertEquals(48+0L, res0, 0.01);

            ArrayList<Integer> list1 = new ArrayList<Integer>();
            list1.add(1);
            Long res1 = Estimator(new ArrayList<Integer>(list1));
            assertEquals(48+28L, res1, 0.01);

            list1.add(2);
            Long res2 = Estimator(new ArrayList<Integer>(list1));
            assertEquals(48+28L+0L, res1, 0.01);

            Long res11 = Estimator(new ArrayList<Integer>(Arrays.asList(1)));
            assertEquals(48+28L+0L, res11, 0.01);

            Long res22 = Estimator(new ArrayList<Integer>(Arrays.asList(1,2)));
            assertEquals(48+28L*2L, res2, 0.01);

            Long res3 = Estimator(new ArrayList<Integer>(Arrays.asList(1,2,33)));
            assertEquals(48+28L*3L, res3, 0.01);

            ArrayList<Integer>  arrayList = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7));
            Long res7 = Estimator(arrayList);
            assertEquals(48+28L*arrayList.size(), res7, 0.01);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
