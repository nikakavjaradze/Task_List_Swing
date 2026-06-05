package com.New;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main {

    static JTextField textField;
    static JPanel bottomPanel;
    static DefaultListModel<String> model;
    static JList<String> list;
    static JLabel counterLabel;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Task List");
        frame.setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());
        frame.setSize(500,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new DefaultListModel<>();
        list = new JList<>(model);
        list.setBackground(Color.BLACK);
        list.setForeground(Color.WHITE);
        list.setFixedCellHeight(50);
        list.setFont(new Font("Arial", Font.PLAIN, 20));
        list.setSelectionBackground(Color.DARK_GRAY);
        list.setSelectionForeground(Color.WHITE);
        list.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        counterLabel = new JLabel();
        counterLabel.setBackground(Color.WHITE);

        textField = createTextField();
        textField.addActionListener(e -> addTask());

        JButton btn = new JButton("ADD");
        btn.addActionListener(e -> addTask());
        btn.addActionListener(e -> {
                    String task = textField.getText();
                    if (!task.isEmpty()){
                        model.addElement(task);
                        textField.setText("");
                    }
                });
        counterLabel.setText("Task: " + model.size());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(textField, BorderLayout.CENTER);
        topPanel.add(btn, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);

        bottomPanel = createButtonPanel();
        frame.add(bottomPanel, BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane(list);
        frame.add(scroll, BorderLayout.CENTER);

        frame.setVisible(true);

    }
    private static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setLayout(new BorderLayout());
        field.setEditable(true);
        field.setSize(500,0);
        field.setPreferredSize(new Dimension(0, 45));
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        return field;
    }
    private static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 16));
        btn.setMargin(new Insets(8, 12, 8, 12));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        return btn;
    }
    private static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3, 10, 10));
        panel.setBackground(Color.BLACK);

        String[] buttons = {"Done", "Delete", "Clear All"};

        for (String text : buttons) {
            JButton btn = createButton(text);
            panel.add(btn);
            btn.addActionListener(e -> handleClick(text));
        }
        return panel;
    }
    private static void handleClick(String value) {
        switch (value) {
            case "Done":
                int in = list.getSelectedIndex();
                if (in == -1) {
                    JOptionPane.showMessageDialog(null, "Choose Task");
                } else {
                    String task = model.get(in);
                    if (!task.startsWith("<html>✓ ")) {
                        model.set(in, "<html>✓ <s>" + task + "</s></html>");
                    } else {
                        task = task.replace("<html>✓ <s>", "").replace("</s></html>", "");
                        model.set(in, task);
                    }
                }
                break;
            case "Delete":
                int ind = list.getSelectedIndex();
                if (ind != -1) {
                    if (!model.isEmpty()) {
                        model.remove(ind);
                        counterLabel.setText("Task: " + model.size());
                    } else {
                        JOptionPane.showConfirmDialog(null, "Choose Task");
                    }
            } else {
                    JOptionPane.showMessageDialog(null, "You Don't Have Any Task");
            }
                break;
            case "Clear All":
                if (model.isEmpty()){
                    JOptionPane.showMessageDialog(null, "You Don't Have Any Task");
                } else {
                    int answer = JOptionPane.showConfirmDialog(null, "Are You Sure?", "Confirm To Clear All", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        model.clear();
                        counterLabel.setText("Task: " + model.size());
                    }
                }
                break;
            }
        }
        private static void addTask() {
        String task = textField.getText().trim();
        if (!task.isEmpty()) {
            model.addElement(task);
            textField.setText("");
        }
        }
    }