package com.company;

import javax.swing.*;
import java.awt.*;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class ATM extends JFrame {
    private BankAccount bankAccount;
    private JTextField amountField;
    private JTextArea messageArea;

    public ATM(BankAccount account) {
        bankAccount = account;
        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(200, 230, 255));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 45, 98));
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setForeground(Color.WHITE);
        amountField = new JTextField();

        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        amountLabel.setFont(labelFont);
        amountField.setFont(labelFont);

        JButton checkBalanceButton = createButton("Check Balance");
        JButton depositButton = createButton("Deposit");
        JButton withdrawButton = createButton("Withdraw");
        messageArea = new JTextArea();

        Font textAreaFont = new Font("Arial", Font.PLAIN, 14);
        messageArea.setFont(textAreaFont);

        checkBalanceButton.addActionListener(e ->
                messageArea.setText("Current balance: $" + String.format("%.2f", bankAccount.getBalance())));

        depositButton.addActionListener(e -> {
            double amount = getAmountFromField();
            bankAccount.deposit(amount);
            updateMessage("Deposit successful. New balance: $");
        });

        withdrawButton.addActionListener(e -> {
            double amount = getAmountFromField();
            if (bankAccount.withdraw(amount)) {
                updateMessage("Withdrawal successful. New balance: $");
            } else {
                updateMessage("Invalid withdrawal amount or insufficient balance.");
            }
        });

        addComponentsToPanel(panel, amountLabel, amountField, checkBalanceButton, depositButton, withdrawButton);
        add(panel);

        JScrollPane scrollPane = new JScrollPane(messageArea);
        panel.add(scrollPane);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        button.setFont(buttonFont);
        return button;
    }

    private void addComponentsToPanel(JPanel panel, Component... components) {
        for (Component component : components) {
            panel.add(component);
        }
    }

    private double getAmountFromField() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void updateMessage(String message) {
        messageArea.setText(message + String.format("%.2f", bankAccount.getBalance()));
    }
}

public class ATMTask {
    public static void main(String[] args) {
        double initialBalance = 1000.0;
        BankAccount userAccount = new BankAccount(initialBalance);

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new ATM(userAccount);
        });
    }
}

