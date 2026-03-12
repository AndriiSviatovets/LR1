package task5;

public class console {
    public static void main(String[] args) throws InterruptedException {
        // Create the real calculator instance
        Calculator realCalculator = new CalculatorImpl();

        // Create the engine that will generate the proxy
        Calculator proxy = engine.createProxy(realCalculator, Calculator.class);

        // Use the proxy to call methods and see the logging in action
        System.out.println("___Launching tests on Calculator proxy___");

        proxy.add(10, 20);
        
        proxy.power(2.0, 10.0);
        
        proxy.longTask();

        System.out.println("\n___Tests completed___");
    }
}