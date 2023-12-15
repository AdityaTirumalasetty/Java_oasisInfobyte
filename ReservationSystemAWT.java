package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Reservation {
    private static int nextId = 1;

    private final int id;
    private final String name;
    private final String date;
    private final int numberOfGuests;

    public Reservation(String name, String date, int numberOfGuests) {
        this.id = nextId++;
        this.name = name;
        this.date = date;
        this.numberOfGuests = numberOfGuests;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}

public class ReservationSystemAWT extends Frame {
    private final List<Reservation> reservations = new ArrayList<>();
    private final TextArea outputTextArea;
    public ReservationSystemAWT() {
        setTitle("Online Reservation System");
        setSize(500, 250);
        setLayout(new BorderLayout());
        setBackground(Color.decode("#382435"));

        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setBackground(Color.white);
        outputTextArea.setForeground(Color.decode("#b68cb3"));
        add(outputTextArea, BorderLayout.CENTER);

        Panel panel = new Panel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.decode("#b68cb3"));

        Button makeReservationButton = new Button("Make a Reservation");
        Button viewReservationsButton = new Button("View All Reservations");
        Button cancelReservationButton = new Button("Cancel a Reservation");
        Button exitButton = new Button("Exit");

        makeReservationButton.setBackground(Color.decode("#382435"));
        viewReservationsButton.setBackground(Color.decode("#382435"));
        cancelReservationButton.setBackground(Color.decode("#382435"));
        exitButton.setBackground(Color.decode("#382435"));

        makeReservationButton.setForeground(Color.decode("#b68cb3"));
        viewReservationsButton.setForeground(Color.decode("#b68cb3"));
        cancelReservationButton.setForeground(Color.decode("#b68cb3"));
        exitButton.setForeground(Color.decode("#b68cb3"));

        makeReservationButton.addActionListener(e -> showMakeReservationDialog());

        viewReservationsButton.addActionListener(e -> showAllReservations());

        cancelReservationButton.addActionListener(e -> showCancelReservationDialog());

        exitButton.addActionListener(e -> System.exit(0));

        panel.add(makeReservationButton);
        panel.add(viewReservationsButton);
        panel.add(cancelReservationButton);
        panel.add(exitButton);

        add(panel, BorderLayout.SOUTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    private void showMakeReservationDialog() {
        Frame frame = new Frame();
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 2));
        frame.setBackground(Color.decode("#b68cb3"));

        Label nameLabel = new Label("Customer Name:");
        nameLabel.setForeground(Color.decode("#382435"));
        TextField nameTextField = new TextField();

        Label dateLabel = new Label("Reservation Date:");
        dateLabel.setForeground(Color.decode("#382435"));
        TextField dateTextField = new TextField();

        Label guestsLabel = new Label("Number of Guests:");
        guestsLabel.setForeground(Color.decode("#382435"));
        TextField guestsTextField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setBackground(Color.decode("#382435"));
        submitButton.setForeground(Color.decode("#b68cb3"));

        submitButton.addActionListener(e -> {
            String name = nameTextField.getText();
            String date = dateTextField.getText();
            int numberOfGuests = Integer.parseInt(guestsTextField.getText());
            Reservation reservation = new Reservation(name, date, numberOfGuests);
            reservations.add(reservation);
            outputTextArea.append("Reservation made with Reservation ID " + reservation.getId() + "\n");
            frame.dispose();
        });

        frame.add(nameLabel);
        frame.add(nameTextField);
        frame.add(dateLabel);
        frame.add(dateTextField);
        frame.add(guestsLabel);
        frame.add(guestsTextField);
        frame.add(submitButton);

        frame.setVisible(true);
    }

    private void showAllReservations() {
        outputTextArea.setText("All Reservations:\n");

        if (reservations.isEmpty()) {
            outputTextArea.append("No reservations have been made yet.\n");
        } else {
            for (Reservation reservation : reservations) {
                outputTextArea.append(reservation.getId() + " - " +
                        reservation.getName() + " - " +
                        reservation.getDate() + " - " +
                        reservation.getNumberOfGuests() + "\n");
            }
        }
    }

    private void showCancelReservationDialog() {
        Frame frame = new Frame();
        frame.setSize(400, 100);
        frame.setLayout(new GridLayout(2, 1));
        frame.setBackground(Color.decode("#b68cb3"));
        Label idLabel = new Label("Reservation ID to cancel:");
        idLabel.setForeground(Color.decode("#382435"));
        TextField idTextField = new TextField();
        Button cancelReservationButton = new Button("Cancel Reservation");
        cancelReservationButton.setBackground(Color.decode("#382435"));
        cancelReservationButton.setForeground(Color.decode("#b68cb3"));

        cancelReservationButton.addActionListener(e -> {
            int reservationId = Integer.parseInt(idTextField.getText());

            if (cancelReservation(reservationId)) {
                outputTextArea.append("Reservation with Reservation ID " + reservationId + " cancelled.\n");
            } else {
                outputTextArea.append("Reservation with Reservation ID " + reservationId + " not found.\n");
            }
            frame.dispose();
        });
        frame.add(idLabel);
        frame.add(idTextField);
        frame.add(cancelReservationButton);
        frame.setVisible(true);
    }

    private boolean cancelReservation(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == reservationId) {
                reservations.remove(reservation);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ReservationSystemAWT reservationSystemAWT = new ReservationSystemAWT();
        reservationSystemAWT.setVisible(true);
    }
}
