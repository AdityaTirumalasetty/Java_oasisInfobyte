package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class NumberGuessingGameGUI extends Frame {
    private Random random;
    private int minValue;
    private int maxValue;
    private int maxAttempts;
    private int score;
    private int targetNumber;
    private int attempts;

    private Label label;
    private TextField textField;
    private Button guessButton;
    private TextArea resultTextArea;
    private Button playAgainButton;

    public NumberGuessingGameGUI() {
        random = new Random();
        minValue = 1;
        maxValue = 100;
        maxAttempts = 10;
        score = 0;
        attempts = 0;

        setLayout(new FlowLayout());

        label = new Label("Enter your guess number:");
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        add(label);

        textField = new TextField(10);
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.LIGHT_GRAY);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(textField);

        guessButton = new Button("Guess");
        guessButton.setBackground(Color.GRAY);
        guessButton.setForeground(Color.BLACK);
        guessButton.setFont(new Font("Arial", Font.PLAIN, 14));
        add(guessButton);

        resultTextArea = new TextArea("", 9, 35);
        resultTextArea.setBackground(Color.DARK_GRAY);
        resultTextArea.setForeground(Color.LIGHT_GRAY);
        resultTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        add(resultTextArea);

        playAgainButton = new Button("Play Again");
        playAgainButton.setBackground(Color.GRAY);
        playAgainButton.setForeground(Color.BLACK);
        playAgainButton.setFont(new Font("Arial", Font.PLAIN, 14));
        add(playAgainButton);

        initializeGame();

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeGame();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        setTitle("Number Guessing Game");
        setSize(400, 300);
        setBackground(new Color(30, 30, 30));
        setForeground(Color.LIGHT_GRAY);
        setVisible(true);
    }

    private void initializeGame() {
        targetNumber = random.nextInt(maxValue - minValue + 1) + minValue;
        attempts = 0;
        resultTextArea.setText("I've selected a number between " + minValue + " and " + maxValue + ". Guess it!");
        resultTextArea.append("\nYour current score: " + score);
        enableInput(true);
    }

    private void processGuess() {
        try {
            int guess = Integer.parseInt(textField.getText());
            attempts++;

            if (guess < minValue || guess > maxValue) {
                resultTextArea.append("\nPlease enter a number within the specified range.");
            } else if (guess == targetNumber) {
                resultTextArea.append("\nYou've guessed the correct number in " + attempts + " attempts.");
                score += findScore(attempts);
                resultTextArea.append("\nYour current score: " + score);
                enableInput(false);
            } else if (attempts >= maxAttempts) {
                resultTextArea.append("\nSorry, you've run out of attempts. The correct number was " + targetNumber + ".");
                enableInput(false);
            } else if (guess < targetNumber) {
                resultTextArea.append("\n"+guess+" is Too low! Try again.");
            } else {
                resultTextArea.append("\n"+guess+" is Too High! Try again.");
            }
        } catch (NumberFormatException ex) {
            resultTextArea.append("\nPlease enter a valid number.");
        }
    }

    private void enableInput(boolean enable) {
        textField.setEnabled(enable);
        guessButton.setEnabled(enable);
        playAgainButton.setEnabled(!enable);
    }

    private int findScore(int attempts) {
        return 100 - attempts;
    }

    public static void main(String[] args) {
        new NumberGuessingGameGUI();
    }
}

