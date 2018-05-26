import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
/** The MemEstimator class try to estimate how many memory used by Java object
 *  @author  Dmitry Shirokov
 * */
public class MemEstimator {

    private static final int BOOLEAN_SIZE = 1;
    private static final boolean jvm64 = true;
    private static final String linesep = System.getProperty("line.separator");


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

        StringBuilder sb = new StringBuilder();

        Class clazz = o.getClass();
        if (clazz.isArray()) {
            sb.append("class is ");

            sb.append("<");
            sb.append(clazz.getCanonicalName());
            sb.append("> ");


            if (clazz == long[].class) {
                long[] objs = (long[]) o;
                size += objs.length * Long.BYTES;
                sb.append("length: ");
                sb.append(objs.length);

            } else if (clazz == int[].class) {
                int[] objs = (int[]) o;
                size += objs.length * Integer.BYTES;
                sb.append("length: "); sb.append(objs.length);

            } else if (clazz == byte[].class) {
                byte[] objs = (byte[]) o;
                size += objs.length * Byte.BYTES;
                sb.append("length: ");
                sb.append(objs.length);
            } else if (clazz == boolean[].class) {
                boolean[] objs = (boolean[]) o;
                size += objs.length * BOOLEAN_SIZE;
                sb.append("length: ");
                sb.append(objs.length);
            } else if (clazz == char[].class) {
                char[] objs = (char[]) o;
                size += objs.length * Character.BYTES;
                sb.append("length: ");
                sb.append(objs.length);
            } else if (clazz == short[].class) {
                short[] objs = (short[]) o;
                size += objs.length * Short.BYTES;

                sb.append("length: ");
                sb.append(objs.length);
            } else if (clazz == float[].class) {
                float[] objs = (float[]) o;
                size += objs.length * Float.BYTES;
                sb.append("length: ");
                sb.append(objs.length);
            } else if (clazz == double[].class) {
                double[] objs = (double[]) o;
                size += objs.length * Double.BYTES;
                sb.append("length: ");
                sb.append(objs.length);
            } else {
                sb.append("class is RefArray..");

                Object[] objs = (Object[]) o;
                for (Object obj : objs) {
                    size += Estimator(obj, visited) + referenceSize;
                }
            }
            size += Integer.BYTES;

            sb.append(" +=");
            sb.append(size);
            sb.append(linesep);
            System.out.println(sb);


        } else { //is not array

            // Using Java's reflection for inspect of field of object
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {

                //added permission for private fields
                field.setAccessible(true);

                sb.insert(0, "field: " + field.getName() + "(");
                String fieldType = field.getGenericType().toString();
                sb.append(fieldType);
                sb.append(")");

                //check the filed's modifier
                int mods = field.getModifiers();
                if (Modifier.isStatic(mods)){
                    sb.append("..skip the static ")
                            .append("<")
                            .append(field.getName())
                            .append(">")
                            .append(linesep);
                }
                else { //is not static modifier

                    switch (fieldType) {
                        case "long":
                            size += Long.BYTES;
                            break;
                        case "int":
                            size += Integer.BYTES;
                            break;
                        case "byte":
                            size += Byte.BYTES;
                            break;
                        case "boolean":
                            size += BOOLEAN_SIZE;
                            break;
                        case "char":
                            size += Character.BYTES;
                            break;
                        case "short":
                            size += Short.BYTES;
                            break;
                        case "float":
                            size += Float.BYTES;
                            break;
                        case "double":
                            size += Double.BYTES;
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

        }

        sb.append(linesep);
        sb.append("---------");
        sb.append("sum up: ");
        sb.append(size);


        System.out.println(sb);


        return size;
    }


}
