import static org.junit.Assert.*;
import org.junit.Test;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
ДЗ 02. Измерение памяти
Написать стенд для определения размера объекта.
Определить размер пустой строки и пустых контейнеров.
Определить рост размера контейнера от количества элементов в нем.

Например:
Object — 8 bytes,
Empty String — 40 bytes
Array — from 12 bytes

*/

public class MemEstimator {


    private static final int LONG_SIZE = 8;
    private static final int INT_SIZE = 4;
    private static final int BYTE_SIZE = 1;
    private static final int BOOLEAN_SIZE = 1;
    private static final int CHAR_SIZE = 2;
    private static final int SHORT_SIZE = 2;
    private static final int FLOAT_SIZE = 4;
    private static final int DOUBLE_SIZE = 8;

    private static final boolean jvm64 = true;
    private static final Long base_strlen_j8 = 116L;
    private static final Long base_ArrayList_j8 = 1185L;

    private static final String linesep = System.getProperty("line.separator");
    

    @Test
    public void test_int() {
        try {
            System.out.println( Estimator(new int[]{1,2,3}) );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_long() {
        try {
            System.out.println( Estimator(new long[]{1,2,3}) );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    //Написать стенд для определения размера объекта.
    public void test_String() {
        try {
            Long result = Estimator("t");
            assertEquals(base_strlen_j8+2L, result, 0.01);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /*
    Определить размер пустой строки
    Определить рост размера контейнера от количества элементов в нем.
    */
    @Test
    public void test_Strings(){
        try {

            Long res0 = Estimator("");
            assertEquals(base_strlen_j8+0L, res0, 0.01);

            Long res1 = Estimator("a");
            assertEquals(base_strlen_j8+2L, res1, 0.01);

            Long res2 = Estimator("ab");
            assertEquals(base_strlen_j8+4L, res2, 0.01);

            Long res3 = Estimator("abd");
            assertEquals(base_strlen_j8+6L, res3, 0.01);

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

    ArrayList с параметрами падает с
    java.lang.reflect.InaccessibleObjectException: Unable to make field private final java.lang.String java.lang.module.ModuleDescriptor.name accessible: module java.base does not "opens java.lang.module" to unnamed module @61a485d2
    так как java 9 module strong incapsulation
    The exception is caused by the Java Platform Module System that was introduced in Java 9, particularly its implementation of strong encapsulation. It only allows access under certain conditions, the most prominent ones are:
    https://stackoverflow.com/questions/41265266/how-to-solve-inaccessibleobjectexception-unable-to-make-member-accessible-m
    woraroand
    -XaddExports:java.base/jdk.internal.loader=ALL-UNNAMED
    or switch to java 8
    */
    @Test
    public void test_ArrayList(){
        try {
            Long res0 = Estimator(new ArrayList<Integer>());
            assertEquals(100+0L, res0, 0.01);

            ArrayList<Integer> list1 = new ArrayList<Integer>();
            list1.add(1);
            Long res1 = Estimator(new ArrayList<Integer>(list1));
            assertEquals(base_ArrayList_j8+1L, res1, 0.01);

            list1.add(2);
            Long res2 = Estimator(new ArrayList<Integer>(list1));
            assertEquals(base_ArrayList_j8+1L, res1, 0.01);

            Long res11 = Estimator(new ArrayList<Integer>(Arrays.asList(1)));
            assertEquals(base_ArrayList_j8+1L, res11, 0.01);

            Long res22 = Estimator(new ArrayList<Integer>(Arrays.asList(1,2)));
            assertEquals(base_ArrayList_j8+93L, res2, 0.01);

            Long res3 = Estimator(new ArrayList<Integer>(Arrays.asList(1,2,33)));
            assertEquals(base_ArrayList_j8+(93*2L-1), res3, 0.01);

            // test is failed becase of compaction??
            Long res7 = Estimator(Estimator(new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7))));
            assertEquals(base_ArrayList_j8+(93*6L-1), res7, 0.01);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    public static Long Estimator(Object o) throws IllegalAccessException {
        return Estimator(o, new HashSet<>());
    }


    public static Long Estimator(Object o, Set<Object> visited) throws IllegalAccessException{


        if (o == null) {
            return 0L;
        }
        if (visited.contains(o)) {
            return 0L;
        }
        visited.add(o);

        int referenceSize = jvm64 ? 8 : 4;
        long headerSize = jvm64 ? 16 : 8;
        System.out.print("headerSize is "); System.out.println(headerSize);
        System.out.print("referenceSize is "); System.out.println(referenceSize);
        System.out.println();
        Long size = headerSize;

        Class clazz = o.getClass();
        StringBuilder sb = new StringBuilder();
        if (clazz.isArray()) {
            sb.append("class is ");

            sb.append("<");
            sb.append(clazz.getCanonicalName());
            sb.append("> ");


            if (clazz == long[].class) {
                long[] objs = (long[]) o;
                size += objs.length * LONG_SIZE;
                sb.append("length: ");
                sb.append(objs.length);

            } else if (clazz == int[].class) {
                int[] objs = (int[]) o;
                //sb.append("prev_size: "); sb.append(size);
                size += objs.length * INT_SIZE;
                sb.append("length: "); sb.append(objs.length);
                // sb.append(" * "); sb.append(INT_SIZE); sb.append(" = ");sb.append(objs.length * INT_SIZE);

            } else if (clazz == byte[].class) {
                byte[] objs = (byte[]) o;
                size += objs.length * BYTE_SIZE;
                sb.append("length: ");
                sb.append(objs.length);
            } else if (clazz == boolean[].class) {
                boolean[] objs = (boolean[]) o;
                size += objs.length * BOOLEAN_SIZE;
                sb.append("length: ");
                sb.append(objs.length);
            } else if (clazz == char[].class) {
                char[] objs = (char[]) o;
                size += objs.length * CHAR_SIZE;
                sb.append("length: ");
                sb.append(objs.length);
            } else if (clazz == short[].class) {
                short[] objs = (short[]) o;
                size += objs.length * SHORT_SIZE;
                sb.append("length: ");
                sb.append(objs.length);
            } else if (clazz == float[].class) {
                float[] objs = (float[]) o;
                size += objs.length * FLOAT_SIZE;
                sb.append("length: ");
                sb.append(objs.length);
            } else if (clazz == double[].class) {
                double[] objs = (double[]) o;
                size += objs.length * DOUBLE_SIZE;
                sb.append("length: ");
                sb.append(objs.length);
            } else {
                sb.append("class is RefArray..");

                Object[] objs = (Object[]) o;
                for (Object obj : objs) {
                    size += Estimator(obj, visited) + referenceSize;
                }
            }
            size += INT_SIZE;

            sb.append(" +=");
            sb.append(size);
            sb.append(linesep);
            System.out.println(sb);


        } else { //is not array

            // Using Java's reflection for inspect of field of object
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {

                //if (field.getName()=="java.lang.module.ModuleDescriptor.name") {}
                //added access to private fields
                field.setAccessible(true);
                sb.insert(0, "field: " + field.getName() + "(");
                String fieldType = field.getGenericType().toString();
                sb.append(fieldType);
                sb.append(")");

                switch (fieldType) {
                    case "long":
                        size += LONG_SIZE;
                        break;
                    case "int":
                        size += INT_SIZE;
                        break;
                    case "byte":
                        size += BYTE_SIZE;
                        break;
                    case "boolean":
                        size += BOOLEAN_SIZE;
                        break;
                    case "char":
                        size += CHAR_SIZE;
                        break;
                    case "short":
                        size += SHORT_SIZE;
                        break;
                    case "float":
                        size += FLOAT_SIZE;
                        break;
                    case "double":
                        size += DOUBLE_SIZE;
                        break;
                    default:
                        System.out.println("drill down --->");
                        size += Estimator(field.get(o), visited) + referenceSize;
                        break;
                }

                sb.append(" +=");
                sb.append(size);
                sb.append(" ");
                System.out.println(sb);

            }

        }

        sb.append(linesep);
        sb.append("---------");
        sb.append("sum up: ");
        sb.append(size);

        /*
        sb.append(linesep);
        sb.append("Object stack:");

        for(Object obj : visited) {
            System.out.println(obj.getClass().getName());
        }
        visited.forEach(System.out::println);

        visited.toArray().length();
        */

        System.out.println(sb);


        return size;
    }


}
