package task1;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class engine {

    public static String analyze(Object input) throws ClassNotFoundException {
        Class<?> testClass;
        if (input instanceof String) {
            testClass = getClassFromName((String) input);
        } else if (input instanceof Class<?>) {
            testClass = (Class<?>) input;
        } else {
            return "Incorrect input type";
        }

        StringBuilder sb = new StringBuilder();

        Package pkg = testClass.getPackage();
        sb.append("Package: ").append(pkg != null ? pkg.getName() : "default").append("\n");

        int modifiers = testClass.getModifiers();
        sb.append(Modifier.toString(modifiers)).append(" ");
        
        if (testClass.isInterface()) {
            sb.append("interface ");
        } else if (testClass.isPrimitive()) {
            sb.append("primitive ");
        } else {
            sb.append("class ");
        }
        sb.append(testClass.getSimpleName()).append("\n");


        Class<?> superclass = testClass.getSuperclass();
        if (superclass != null) {
            sb.append("Extends: ").append(superclass.getCanonicalName()).append("\n");
        }

        Class<?>[] interfaces = testClass.getInterfaces();
        if (interfaces.length > 0) {
            sb.append("Implements: ")
              .append(Arrays.stream(interfaces).map(Class::getSimpleName).collect(Collectors.joining(", ")))
              .append("\n");
        }

        sb.append("\n ____Fields____ \n");
        for (Field field : testClass.getDeclaredFields()) {
            sb.append(Modifier.toString(field.getModifiers())).append(" ")
              .append(field.getType().getSimpleName()).append(" ")
              .append(field.getName()).append(";\n");
        }

        sb.append("\n ____Constructors____ \n");
        for (Constructor<?> constructor : testClass.getDeclaredConstructors()) {
            sb.append(Modifier.toString(constructor.getModifiers())).append(" ")
              .append(testClass.getSimpleName()).append("(")
              .append(formatParameters(constructor.getParameterTypes()))
              .append(");\n");
        }

        sb.append("\n ____Methods____ \n");
        for (Method method : testClass.getDeclaredMethods()) {
            sb.append(Modifier.toString(method.getModifiers())).append(" ")
              .append(method.getReturnType().getSimpleName()).append(" ")
              .append(method.getName()).append("(")
              .append(formatParameters(method.getParameterTypes()))
              .append(");\n");
        }

        return sb.toString();
    }

    private static String formatParameters(Class<?>[] params) {
        return Arrays.stream(params)
                     .map(Class::getSimpleName)
                     .collect(Collectors.joining(", "));
    }

    private static Class<?> getClassFromName(String name) throws ClassNotFoundException {
        switch (name) {
            case "int": return int.class;
            case "double": return double.class;
            case "boolean": return boolean.class;
            case "char": return char.class;
            case "long": return long.class;
            case "float": return float.class;
            case "short": return short.class;
            case "byte": return byte.class;
            case "void": return void.class;
            default: return Class.forName(name);
        }
    }
}