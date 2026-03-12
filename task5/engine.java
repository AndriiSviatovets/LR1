package task5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class engine implements InvocationHandler {
    private final Object target;

    public engine(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TRACE
        System.out.println("\n[TRACE] Method call: " + method.getName());
        System.out.println("[TRACE] Arguments: " + (args == null ? "isn`t" : Arrays.toString(args)));

        // PROFILE
        long start = System.nanoTime();

        // Call the actual method on the target object
        Object result;
        try {
            result = method.invoke(target, args);
        } catch (Exception e) {
            // Exception handling with error trace
            System.err.println("[ERROR] Method failed with error: " + e.getCause());
            throw e.getCause();
        }

        // PROFILE (end)
        long end = System.nanoTime();
        double durationMs = (end - start) / 1_000_000.0; 

        // TRACE (end)
        System.out.println("[PROFILE] Execution time: " + durationMs + " ms");
        System.out.println("[TRACE] Returned value: " + result);

        return result;
    }

    // Статичний метод для зручного створення проксі
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new engine(target)
        );
    }
}