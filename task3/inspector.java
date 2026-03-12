package task3;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

// This class is responsible for inspecting the state of an object and providing information about its public methods
public class inspector {

    public static String getObjectState(Object obj) {
        if (obj == null) return "Object is null";
        
        Class<?> clazz = obj.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("Object types (real): ").append(clazz.getName()).append("\n");
        sb.append(" ___State (all fields)___ \n");

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

    // Returns a list of ALL public methods for viewing
    public static String getPublicMethodsDescription(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n___List of public methods___\n");
        Method[] methods = obj.getClass().getDeclaredMethods();
        
        for (Method m : methods) {
            if (Modifier.isPublic(m.getModifiers())) {
                sb.append("public ").append(m.getReturnType().getSimpleName()).append(" ")
                  .append(m.getName()).append("(")
                  .append(Arrays.stream(m.getParameterTypes())
                          .map(Class::getSimpleName)
                          .collect(Collectors.joining(", ")))
                  .append(")\n");
            }
        }
        return sb.toString();
    }
}