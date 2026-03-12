package task3;

import java.lang.reflect.*;

public class engine {
    public static Object call(Object obj, String method, Object... args) 
            throws expention, IllegalAccessException, InvocationTargetException {
        
        Class<?> testClass = obj.getClass();

        // Determine the runtime types of the provided arguments for method matching
        Class<?>[] argTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = (args[i] != null) ? args[i].getClass() : Object.class;
        }

        Method targMethod = null;
        // Search for a method with the given name and compatible parameter types
        for (Method m : testClass.getMethods()) {
            if (m.getName().equals(method) && matcher(m.getParameterTypes(), argTypes)) {
                targMethod = m;
                break;
            }
        }

        if (targMethod == null) {
            throw new expention("Method not found: " + method + " with the specified parameters");
        }

        targMethod.setAccessible(true);
        return targMethod.invoke(obj, args);
    }

    private static boolean matcher(Class<?>[] methodArgsTypes, Class<?>[] argsTypes) {
        if (methodArgsTypes.length != argsTypes.length) {
            return false;
        }

        for (int i = 0; i < methodArgsTypes.length; i++) {
            if (!isCompatible(methodArgsTypes[i], argsTypes[i])) {
                return false;
            }
        }
        return true;
    }

    // Helper method to check if the provided argument type can be assigned to the method parameter type
    private static boolean isCompatible(Class<?> target, Class<?> source) {

        if (target.isAssignableFrom(source)) return true;

        if (target == int.class && source == Integer.class) return true;
        if (target == double.class && source == Double.class) return true;
        if (target == boolean.class && source == Boolean.class) return true;
        if (target == long.class && source == Long.class) return true;
        if (target == float.class && source == Float.class) return true;
        if (target == char.class && source == Character.class) return true;
        if (target == byte.class && source == Byte.class) return true;
        if (target == short.class && source == Short.class) return true;

        return false;
    }
}