package task2;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class engine {
    public static String getObjectState(Object obj) {
        if (obj == null) return "Object is null";
        
        Class<?> clazz = obj.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("Object type: ").append(clazz.getCanonicalName()).append("\n");
        sb.append("___Current State (Fields)___\n");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                sb.append(Modifier.toString(field.getModifiers())).append(" ")
                  .append(field.getType().getSimpleName()).append(" ")
                  .append(field.getName()).append(" = ")
                  .append(value).append("\n");
            } catch (IllegalAccessException e) {
                sb.append(field.getName()).append(" = <not accessible>\n");
            }
        }
        return sb.toString();
    }

    // Get list of invokable methods (public, no-arg) for the given object
    public static List<Method> getInvokableMethods(Object obj) {
        List<Method> result = new ArrayList<>();
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method m : methods) {
            // Filter parameters and modifiers (only public, no-arg methods)
            if (Modifier.isPublic(m.getModifiers()) && m.getParameterCount() == 0) {
                result.add(m);
            }
        }
        return result;
    }
}