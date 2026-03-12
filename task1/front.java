package task1;

import javax.swing.*;
import java.awt.*;

public class front {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Reflection Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);

        JPanel panel = new JPanel(new BorderLayout());
        JTextField inputField = new JTextField("java.util.ArrayList");
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JButton analyzeButton = new JButton("Analyze");
        analyzeButton.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel(" Enter type name: "), BorderLayout.WEST);
        topPanel.add(inputField, BorderLayout.CENTER);
        topPanel.add(analyzeButton, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        inputField.addActionListener(e -> analyzeButton.doClick());
        analyzeButton.addActionListener(e -> {
            try {
                String result = engine.analyze(inputField.getText());
                resultArea.setText(result);
                resultArea.setCaretPosition(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}