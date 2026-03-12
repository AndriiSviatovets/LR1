package task5;

interface Calculator {
    int add(int a, int b);
    double power(double base, double exp);
    void longTask() throws InterruptedException;
}

// Real
class CalculatorImpl implements Calculator {
    
    public int add(int a, int b) {
        return a + b;
    }

    
    public double power(double base, double exp) {
        return Math.pow(base, exp);
    }

    
    public void longTask() throws InterruptedException {
        Thread.sleep(500);
    }
}