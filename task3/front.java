package task3;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class front extends JFrame {
    private Object targetObject;
    private JTextArea infoArea;
    private JTextField methodNameField;
    private JTextField paramsField;
    private JTextArea resultArea;

    public front(Object obj) {
        this.targetObject = obj;
        initGui();
        updateDisplay();
    }

    private void initGui() {
        setTitle("Java Reflection: Inspector & Dynamic Caller");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));

        // Central panel: Object state and methods info
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        infoArea.setBackground(new Color(250, 250, 250));
        
        // Right panel: Control
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setPreferredSize(new Dimension(300, 0));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        methodNameField = new JTextField();
        paramsField = new JTextField();
        JButton runButton = new JButton("Call Method");
        
        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setBorder(BorderFactory.createTitledBorder("Call Result"));

        controlPanel.add(new JLabel("Method Name:"));
        controlPanel.add(methodNameField);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(new JLabel("Parameters (comma-separated):"));
        controlPanel.add(paramsField);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(runButton);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(new JScrollPane(resultArea));

        add(new JScrollPane(infoArea), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        runButton.addActionListener(e -> executeAction());

        setVisible(true);
    }

    private void executeAction() {
        String methodName = methodNameField.getText().trim();
        String paramsRaw = paramsField.getText().trim();

        try {
            // Using the engine to parse parameters and call the method
            Object[] args = parseArguments(paramsRaw);
            
            // Calling the method via the engine
            Object result = engine.call(targetObject, methodName, args);
            
            resultArea.setText("Success! Result: " + (result == null ? "void" : result.toString()));
            resultArea.setForeground(new Color(0, 100, 0));
            
            updateDisplay();
        } catch (expention ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError("Error: " + (ex.getCause() != null ? ex.getCause() : ex.getMessage()));
        }
    }

    private void updateDisplay() {
        // Combining object state and public methods info into the infoArea
        String state = inspector.getObjectState(targetObject);
        String methods = inspector.getPublicMethodsDescription(targetObject);
        infoArea.setText(state + "\n" + methods);
    }

    private Object[] parseArguments(String input) {
        if (input.isEmpty()) return new Object[0];
        String[] parts = input.split(",");
        Object[] result = new Object[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String p = parts[i].trim();
            if (p.matches("-?\\d+")) result[i] = Integer.parseInt(p);
            else if (p.equalsIgnoreCase("true") || p.equalsIgnoreCase("false")) result[i] = Boolean.parseBoolean(p);
            else result[i] = p;
        }
        return result;
    }

    private void showError(String msg) {
        resultArea.setText(msg);
        resultArea.setForeground(Color.RED);
    }

    public static void main(String[] args) {
        new front(new TestDevice());
    }
}

// Test class
class TestDevice {
    private String model = "X-Ray 3000";
    private int power = 100;
    
    public void setPower(int p) { this.power = p; }
    public String upgrade(String newModel) { 
        this.model = newModel; 
        return "Model upgraded!"; 
    }
}