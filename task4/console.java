package task4;
    import java.lang.reflect.Array;

public class console {
    public static void main(String[] args) {
        System.out.println("___Test 1: One-dimensional array (int)___");
        Object intArray = engine.createArray(int.class, 5);
        Array.set(intArray, 0, 10);
        Array.set(intArray, 1, 20);
        System.out.println("Original: " + engine.arrayToString(intArray));

        intArray = engine.resizeArray(intArray, 8);
        System.out.println("Expanded to 8: " + engine.arrayToString(intArray));

        System.out.println("\n___Test 2: Matrix (String)___");
        Object strMatrix = engine.createMatrix(String.class, 2, 2);
        
        // Get the first row and set values
        Object row0 = Array.get(strMatrix, 0);
        Array.set(row0, 0, "A1");
        Array.set(row0, 1, "A2");
        
        System.out.println("Matrix 2x2: " + engine.arrayToString(strMatrix));

        strMatrix = engine.resizeMatrix(strMatrix, 3, 3);
        System.out.println("Matrix after resize to 3x3: " + engine.arrayToString(strMatrix));
        
        // Add value to the new cell
        Object row2 = Array.get(strMatrix, 2);
        Array.set(row2, 2, "NEW");
        System.out.println("After adding to new cell: " + engine.arrayToString(strMatrix));
    }
}