package task2;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.List;

public class front {
    private Object targetObject;
    private JTextArea stateArea;
    private JComboBox<MethodWrapper> methodBox;
    private JLabel resultLabel;

    public front(Object obj) {
        this.targetObject = obj;
        createGui();
        refreshInfo();
    }

    private void createGui() {
        JFrame frame = new JFrame("Object Inspector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 500);
        frame.setLayout(new BorderLayout(10, 10));

        // State display area
        stateArea = new JTextArea();
        stateArea.setEditable(false);
        stateArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        frame.add(new JScrollPane(stateArea), BorderLayout.CENTER);

        // Control panel for method invocation
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        methodBox = new JComboBox<>();
        JButton callButton = new JButton("Call Method");
        resultLabel = new JLabel("Result: - ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        controlPanel.add(new JLabel("Select method to call (no parameters):"));
        controlPanel.add(methodBox);
        controlPanel.add(callButton);

        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(resultLabel, BorderLayout.NORTH);

        callButton.addActionListener(e -> {
            MethodWrapper wrapper = (MethodWrapper) methodBox.getSelectedItem();
            if (wrapper != null) {
                try {
                    Object result = wrapper.method.invoke(targetObject);
                    resultLabel.setText("Result: " + (result == null ? "void" : result.toString()));
                    refreshInfo(); 
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error calling method: " + ex.getCause());
                }
            }
        });

        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    private void refreshInfo() {
        stateArea.setText(engine.getObjectState(targetObject));
        
        if (methodBox.getItemCount() == 0) {
            List<Method> methods = engine.getInvokableMethods(targetObject);
            for (Method m : methods) {
                methodBox.addItem(new MethodWrapper(m));
            }
        }
    }

    private static class MethodWrapper {
        Method method;
        MethodWrapper(Method m) { this.method = m; }
        @Override
        public String toString() { 
            return method.getName() + "() : " + method.getReturnType().getSimpleName(); 
        }
    }

    public static void main(String[] args) {
        JTextField nameField = new JTextField("Andrii");
        JTextField ageField = new JTextField("19");
        
        Object[] inputFields = {
            "Ім'я користувача:", nameField,
            "Вік:", ageField
        };

        // Call window to create a Person object
        int option = JOptionPane.showConfirmDialog(null, inputFields, 
                "Create Person Object", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) name = "Default Person";
            
            int age;
            try {
                age = Integer.parseInt(ageField.getText().trim());
            } catch (NumberFormatException e) {
                age = 18;
            }

            Person testPerson = new Person(name, age);
            // Start the GUI with the created Person object
            SwingUtilities.invokeLater(() -> new front(testPerson));
        }
    }
}

// Test class for demonstration
class Person {
    private String name;
    private int age;
    private int clicks = 0;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void birthday() { age++; }
    public String greet() { return "Hi, I am " + name; }
    public void incrementClicks() { clicks++; }
    public int getAge() { return age; }
}