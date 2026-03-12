package task4;

import java.lang.reflect.Array;
import java.util.Arrays;

public class engine {

    // Creation of a one-dimensional array
    public static Object createArray(Class<?> type, int size) {
        return Array.newInstance(type, size);
    }

    // Сreation of a matrix (2D array)
    public static Object createMatrix(Class<?> type, int rows, int cols) {
        return Array.newInstance(type, rows, cols);
    }

    // Change the size of a one-dimensional array while preserving values
    public static Object resizeArray(Object oldArray, int newSize) {
        Class<?> type = oldArray.getClass().getComponentType();
        Object newArray = Array.newInstance(type, newSize);
        
        int oldLength = Array.getLength(oldArray);
        int preserveLength = Math.min(oldLength, newSize);
        
        System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        return newArray;
    }

    // Change the size of a matrix while preserving values
    public static Object resizeMatrix(Object oldMatrix, int newRows, int newCols) {
        Class<?> matrixType = oldMatrix.getClass(); // напр. int[][]
        Class<?> componentType = matrixType.getComponentType().getComponentType(); // напр. int
        
        Object newMatrix = Array.newInstance(componentType, newRows, newCols);
        
        int oldRows = Array.getLength(oldMatrix);
        int preserveRows = Math.min(oldRows, newRows);

        for (int i = 0; i < preserveRows; i++) {
            Object oldRow = Array.get(oldMatrix, i);
            Object newRow = Array.get(newMatrix, i);
            
            int oldCols = Array.getLength(oldRow);
            int preserveCols = Math.min(oldCols, newCols);
            
            System.arraycopy(oldRow, 0, newRow, 0, preserveCols);
        }
        return newMatrix;
    }

    // Utility method to convert an array (including multi-dimensional) to a string representation
    public static String arrayToString(Object array) {
        if (array == null) return "null";
        
        // Use deepToString for automatic handling of nested arrays
        if (array instanceof Object[]) {
            return Arrays.deepToString((Object[]) array);
        } else {
            // For primitive arrays (int[], double[] etc.) deepToString doesn't work directly as Object[]
            // So we use Reflection for manual string building
            StringBuilder sb = new StringBuilder("[");
            int length = Array.getLength(array);
            for (int i = 0; i < length; i++) {
                sb.append(Array.get(array, i));
                if (i < length - 1) sb.append(", ");
            }
            sb.append("]");
            return sb.toString();
        }
    }
}