import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.*;
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


    @Test
    public void testString() {

    }


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
            System.out.println( Estimator("t") );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_Strings(){
        try {
            System.out.println( Estimator("t") );
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
            System.out.println(sb);


        } else {

            // Using reflection for inspect of field of object
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {

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
                        System.out.println("drill down..");
                        size += Estimator(field.get(o), visited) + referenceSize;
                        break;
                }

                sb.append(" +=");
                sb.append(size);
                System.out.println(sb);

            }

        }

        sb.append("sum up: ");
        sb.append(size);
        return size;
    }


}
